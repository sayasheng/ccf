package org.ccf.database;

import java.sql.SQLException;

public class TrainingTable {
	public String[] trainingHeader = {"姓名","課程名稱","課程日期","訓練證號","訓練時數"};
	private String trainingTableName = "training";
	private String trainingCreateTableSql ="CREATE TABLE training (" + 
	"	   姓名	VARCHAR(50) NOT NULL" +
	"  , 課程名稱	VARCHAR(50)" +
	"  , 課程日期	VARCHAR(50)" +
	"  , 訓練證號	VARCHAR(50)" +
	"  , 訓練時數	VARCHAR(50))";
	private String insertAllDataSQL = "insert into training"
			+ "(姓名,課程名稱,課程日期,訓練證號,訓練時數) values" 
		    + "(?,?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess training_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from training";
	
	public void createTrainingTable(){
		db.createTable(trainingCreateTableSql);
	}
	public void dropTrainingTable(){
		db.dropTable(trainingTableName);
	}

	public void insertAllDataIntoTrainingTable(String name, String classname, String day, String number, String hours) throws SQLException
    {
	if (db.con == null)
	{
		db.createConnection();
	} 
	try{
		db.pst = db.con.prepareStatement(insertAllDataSQL);
		db.pst.setString(1, name); 
		db.pst.setString(2, classname); 
		db.pst.setString(3, day);
		db.pst.setString(4, number);
		db.pst.setString(5, hours);
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataTrainingTable Exception :" + e.toString()); 
    }finally{ 
    	 if (db.rs != null)
	      {
	    	  db.rs.close();
	      }
	      if(db.con != null)
	      {
	    	  db.close(); 
	      }
    } 
}
	
	public boolean importExcelToTrainingTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[trainingHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropTrainingTable();
		
		//2. Create a table after dropping. 
		createTrainingTable();
		
		//3. Start to import excel data into database.
		 data = training_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<trainingHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (trainingHeader.length-1)){
		    			insertAllDataIntoTrainingTable(myData[0],myData[1]
		    					,myData[2],myData[3],myData[4]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[trainingHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	
	public boolean exportTrainingTableToExcel(String directory){
		boolean result = false ;
		try {
			result = training_xsl.exportToExcel(trainingTableName,trainingHeader,directory,"TrainingTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromTrainingTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,trainingTableName);
	}
	
}
