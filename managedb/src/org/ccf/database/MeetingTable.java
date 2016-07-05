package org.ccf.database;

import java.sql.SQLException;

public class MeetingTable {
	public String[] meetingHeader= {"姓名","會議","年","月","時數"};
	private String meetingTableName = "meeting";
	private String meetingCreateTableSql ="CREATE TABLE meeting (" + 
	"	   姓名	VARCHAR(50) NOT NULL" +
	"  , 會議	VARCHAR(50)" +
	"  , 年	VARCHAR(30)" +
	"  , 月	VARCHAR(30)" +
	"  , 時數	VARCHAR(30))";
	private String insertAllDataSQL = "insert into meeting"
			+ "(姓名,會議,年,月,時數) values" 
		    + "(?,?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess meeting_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from meeting";
	
	public void createActivityTable(){
		db.createTable(meetingCreateTableSql);
	}
	
	public void dropActivityTable(){
		db.dropTable(meetingTableName);
	}
	
	public void insertAllDataIntoMeetingTable(String name, String meeting, String year, String month, String hours) throws SQLException
    {
	if (db.con == null)
	{
		db.createConnection();
	} 
	try{
		db.pst = db.con.prepareStatement(insertAllDataSQL);
		db.pst.setString(1, name); 
		db.pst.setString(2, meeting);
		db.pst.setString(3, year); 
		db.pst.setString(4, month);
		db.pst.setString(5, hours);
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataMeetingTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToMeetingTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[meetingHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropActivityTable();
		
		//2. Create a table after dropping. 
		createActivityTable();
		
		//3. Start to import excel data into database.
		 data = meeting_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<meetingHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (meetingHeader.length-1)){
		    			insertAllDataIntoMeetingTable(myData[0],myData[1]
		    					,myData[2],myData[3],myData[4]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[meetingHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	public boolean exportMeetingTableToExcel(String directory){
		boolean result = false ;
		try {
			result = meeting_xsl.exportToExcel(meetingTableName,meetingHeader,directory,"MeetingTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromMeetingTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,meetingTableName);
	}

	

}
