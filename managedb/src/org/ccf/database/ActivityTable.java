package org.ccf.database;

import java.sql.SQLException;

public class ActivityTable {
	public 	String[] activityHeader = {"活動","活動負責人","年","起始月","起始日",
									   "起始時間","結束月","結束日","結束時間",
									   "地點","人力","活動內容","服務人次"};
	private String activityTableName = "activity";
	private String activityCreateTableSql ="CREATE TABLE activity (" + 
	"	 活動	TEXT" +
	"  , 活動負責人	VARCHAR(50)" +
	"  , 年	VARCHAR(30)" +
	"  , 起始月	VARCHAR(30)" +
	"  , 起始日	VARCHAR(30)" +
	"  , 起始時間	VARCHAR(30)" +
	"  , 結束月	VARCHAR(30)" +
	"  , 結束日	VARCHAR(30)" +
	"  , 結束時間	VARCHAR(30)" +
	"  , 地點	VARCHAR(30)" +
	"  , 人力 	VARCHAR(30)" +
	"  , 活動內容	VARCHAR(50)" +
	"  , 服務人次	VARCHAR(30))";


	private String insertAllDataSQL = "insert into activity"
			+ "(活動, 活動負責人, 年, 起始月, 起始日, 起始時間, 結束月, 結束日, 結束時間, 地點, 人力, 活動內容, 服務人次) values" 
		    + "(?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess activity_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from activity";
	
	
	public void createActivityTable(){
		db.createTable(activityCreateTableSql);
	}
	public void dropActivityTable(){
		db.dropTable(activityTableName);
	}
	
	public void insertAllDataIntoActivityTable(String activity, String personInCharge, String startYear, String startMonth, String startDay, String startTime,
			   	String endMonth, String endDay, String endTime, String place, String hrpower, String content, String number) throws SQLException
	{
		if (db.con == null)
		{
			db.createConnection();
		} 
		try{
			db.pst = db.con.prepareStatement(insertAllDataSQL);
			db.pst.setString(1, activity); 
			db.pst.setString(2, personInCharge); 
			db.pst.setString(3, startYear);
			db.pst.setString(4, startMonth);
			db.pst.setString(5, startDay);
			db.pst.setString(6, startTime);	
			db.pst.setString(7, endMonth);
			db.pst.setString(8, endDay);
			db.pst.setString(9, endTime);
			db.pst.setString(10, place);
			db.pst.setString(11, hrpower);
			db.pst.setString(12, content);
			db.pst.setString(13, number);
			db.pst.executeUpdate(); 
	    }catch(SQLException e){ 
	      System.out.println("insertDataActivityTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToActivityTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[activityHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropActivityTable();
		
		//2. Create a table after dropping. 
		createActivityTable();
		
		//3. Start to import excel data into database.
		 data = activity_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<activityHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (activityHeader.length-1)){
		    			insertAllDataIntoActivityTable(myData[0],myData[1]
		    					,myData[2],myData[3],myData[4],myData[5],myData[6]
		    					,myData[7],myData[8],myData[9],myData[10],myData[11]
		    				    ,myData[12]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[activityHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	public boolean exportActivityTableToExcel(String directory){
		boolean result = false ;
		try {
			result = activity_xsl.exportToExcel(activityTableName,activityHeader,directory,"ActivityTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String[][] queryAllFromActivityTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,activityTableName);
	}
}

