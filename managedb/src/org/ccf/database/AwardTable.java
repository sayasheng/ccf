package org.ccf.database;

import java.sql.SQLException;

public class AwardTable {
	public String[] awardHeader= {"姓名","年","月","日","award"};
	private String awardTableName = "award";
	private String awardCreateTableSql ="CREATE TABLE award (" + 
	"	   姓名	VARCHAR(50) NOT NULL" +
	"  , 年	VARCHAR(30)" +
	"  , 月	VARCHAR(30)" +
	"  , 日	VARCHAR(30)" +
	"  , award	VARCHAR(50))";
	private String insertAllDataSQL = "insert into award"
			+ "(姓名,年,月,日,award) values" 
		    + "(?,?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess award_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from award";
	
	public void createAwardTable(){
		db.createTable(awardCreateTableSql);
	}
	public void dropAwardTable(){
		db.dropTable(awardTableName);
	}
	
	public void insertAllDataIntoAwardTable(String name, String year, String month, String day, String award) throws SQLException
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
		db.pst.setString(4, day);
		db.pst.setString(5, award);
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataAwardTable Exception :" + e.toString()); 
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

public boolean importExcelToAwardTable(String filepath) throws SQLException{
	Object [][]data = null;
	String [] myData = new String[awardHeader.length];
    boolean isFinished = false ;
	//1. Drop the table is the table exists.
	dropAwardTable();
	
	//2. Create a table after dropping. 
	createAwardTable();
	
	//3. Start to import excel data into database.
	 data = award_xsl.importFromExcel(filepath);
	 //3-A. Since the first row is column header,we ignore the first row
	 for (int i=1; i < data.length ;i++){
	    	for (int j=0; j<awardHeader.length ; j++){
	    		//3-B. We extract one row of data from data object 
	    		myData[j] = data[i][j].toString();
	    		//3-C. Write one row of data into database
	    		if (j== (awardHeader.length-1)){
	    			insertAllDataIntoAwardTable(myData[0],myData[1]
	    					,myData[2],myData[3],myData[4]);
	    			//3-D. Reset myData array after data insert.
	    			myData = new String[awardHeader.length];
	    		}
	    	}
	  isFinished = true;
	 }
	return isFinished;
}

	public boolean exportAwardTableToExcel(String directory){
		boolean result = false ;
		try {
			result = award_xsl.exportToExcel(awardTableName,awardHeader,directory,"AwardTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String[][] queryAllFromAwardTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,awardTableName);
	}
}
