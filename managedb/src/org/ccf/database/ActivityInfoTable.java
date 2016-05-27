package org.ccf.database;

import java.sql.SQLException;

public class ActivityInfoTable {
	public 	String[] activityInfoHeader = {"活動","服務內容","中心負責組別","中心負責社工"};
	private String activityInfoTableName = "activityInfo";
	private String activityInfoCreateTableSql ="CREATE TABLE activityinfo (" + 
	"	 活動	TEXT" +
	"  , 服務內容	TEXT" +
	"  , 中心負責組別	VARCHAR(50)" +
	"  , 中心負責社工	VARCHAR(50))";
	
	private String insertAllDataSQL = "insert into activityinfo"
			+ "(活動, 服務內容, 中心負責組別,	中心負責社工) values" 
		    + "(?,?,?,?)"; 
	private String selectAllDataSQL= "select * from activityinfo";
	private ExcelFileProcess activityinfo_xsl = new ExcelFileProcess();
	
	
	DBFunctions db = new DBFunctions();
	
	public void createActivityInfoTable(){
		db.createTable(activityInfoCreateTableSql);
	}
	
	public void dropActivityInfoTable(){
		db.dropTable(activityInfoTableName);
	}
	
	public void insertAllDataIntoActivityInfoTable(String activity, String service, String team, String person) throws SQLException
	{
		if (db.con == null)
		{
			db.createConnection();
		} 
		//System.out.println(activity+""+service+""+team+""+person);
		try{
			db.pst = db.con.prepareStatement(insertAllDataSQL);
			db.pst.setString(1, activity); 
			db.pst.setString(2, service); 
			db.pst.setString(3, team);
			db.pst.setString(4, person);
			db.pst.executeUpdate(); 
	    }catch(SQLException e){ 
	      System.out.println("insertAllDataIntoActivityInfoTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToActivityInfoTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[activityInfoHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropActivityInfoTable();
		
		//2. Create a table after dropping. 
		createActivityInfoTable();
		
		//3. Start to import excel data into database.
		 data = activityinfo_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<activityInfoHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (activityInfoHeader.length-1)){
		    			insertAllDataIntoActivityInfoTable(myData[0],myData[1],
		    					myData[2],myData[3]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[activityInfoHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	
	public boolean exportActivityInfoTableToExcel(String directory){
		boolean result = false ;
		try {
			result = activityinfo_xsl.exportToExcel(activityInfoTableName,activityInfoHeader,directory,"ActivityInfoTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public String[][] queryAllFromActivityInfoTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,activityInfoTableName);
	}
}
