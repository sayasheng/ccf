package org.ccf.database;

import java.sql.SQLException;

public class TakeLeaveTable {
	
	public String[] takeLeaveHeader = {"姓名","年","月","會議"};
	private String takeLeaveTableName = "takeleave";
	private String takeLeaveCreateTableSql ="CREATE TABLE takeleave (" + 
	"	   姓名	VARCHAR(50) NOT NULL" +
	"  , 年	VARCHAR(30)" +
	"  , 月	VARCHAR(30)" +
	"  , 會議	VARCHAR(30))";
	private String insertAllDataSQL = "insert into takeleave"
			+ "(姓名,年,月,會議) values" 
		    + "(?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess takeleave_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from takeleave";
	
	public void createTakeLeaveTable(){
		db.createTable(takeLeaveCreateTableSql);
	}
	public void dropTakeLeaveTable(){
		db.dropTable(takeLeaveTableName);
	}

	public void insertAllDataIntoTakeLeaveTable(String name, String year, String month, String meeting) throws SQLException
    {
	if (db.con == null)
	{
		db.createConnection();
	} 
	try{
		db.pst = db.con.prepareStatement(insertAllDataSQL);
		db.pst.setString(1, name); 
		db.pst.setString(2, year); 
		db.pst.setString(3, month);
		db.pst.setString(4, meeting);
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataTakeLeaveTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToTakeLeaveTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[takeLeaveHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropTakeLeaveTable();
		
		//2. Create a table after dropping. 
		createTakeLeaveTable();
		
		//3. Start to import excel data into database.
		 data = takeleave_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<takeLeaveHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (takeLeaveHeader.length-1)){
		    			insertAllDataIntoTakeLeaveTable(myData[0],myData[1]
		    					,myData[2],myData[3]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[takeLeaveHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	public boolean exportTakeLeaveTableToExcel(String directory){
		boolean result = false ;
		try {
			result = takeleave_xsl.exportToExcel(takeLeaveTableName,takeLeaveHeader,directory,"TakeLeaveTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromTakeLeaveTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,takeLeaveTableName);
	}
	
}
