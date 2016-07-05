package org.ccf.database;

import java.sql.SQLException;

public class MeetingTimeTable {
	public String[] meetingTimeHeader= {"年","月","日","會議","負責人","開始時間","結束時間"};
	private String meetingTimeTableName = "meetingtime";
	private String meetingTimeCreateTableSql ="CREATE TABLE meetingtime (" +
	"    年	VARCHAR(30)" +
	"  , 月	VARCHAR(30)" +
	"  , 日	VARCHAR(30)" +
	"  , 會議  VARCHAR(50)" +
	"  , 負責人 VARCHAR(50)" +
	"  , 開始時間 VARCHAR(50)" +
	"  , 結束時間 VARCHAR(50))";
	private String insertAllDataSQL = "insert into meetingtime"
			+ "(年,月,日,會議,負責人,開始時間,結束時間) values" 
		    + "(?,?,?,?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess meetingtime_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from meetingtime";
	
	public void createMeetingTimeTable(){
		db.createTable(meetingTimeCreateTableSql);
	}
	public void dropMeetingTimeTable(){
		db.dropTable(meetingTimeTableName);
	}
	
	public void insertAllDataIntoMeetingTimeTable(String year, String month, String day, String meeting, String host, String start, String end) throws SQLException
    {
	if (db.con == null)
	{
		db.createConnection();
	} 
	try{
		db.pst = db.con.prepareStatement(insertAllDataSQL);
		db.pst.setString(1, year); 
		db.pst.setString(2, month);
		db.pst.setString(3, day);
		db.pst.setString(4, meeting);
		db.pst.setString(5, host);
		db.pst.setString(6, start);
		db.pst.setString(7, end); 
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataMeetingTimeTable Exception :" + e.toString()); 
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
	public boolean importExcelToMeetingTimeTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[meetingTimeHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropMeetingTimeTable();
		
		//2. Create a table after dropping. 
		createMeetingTimeTable();
		
		//3. Start to import excel data into database.
		 data = meetingtime_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<meetingTimeHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (meetingTimeHeader.length-1)){
		    			insertAllDataIntoMeetingTimeTable(myData[0],myData[1]
		    					,myData[2],myData[3],myData[4],myData[5],myData[6]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[meetingTimeHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	public boolean exportMeetingTimeTableToExcel(String directory){
		boolean result = false ;
		try {
			result = meetingtime_xsl.exportToExcel(meetingTimeTableName,meetingTimeHeader,directory,"MeetingTimeTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromMeetingTimeTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,meetingTimeTableName);
	}
	
}
