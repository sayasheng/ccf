package org.ccf.database;

import java.sql.SQLException;

public class EmergencyContactTable {
	public String[] emergencyContactHeader= {"姓名","緊急連絡人1","緊急連絡人1電話","緊急連絡人1關係","緊急連絡人2","緊急連絡人2電話","緊急連絡人2關係"};
	private String  emergencyContactTableName = "emergencycontact";
	private String emergencyContactCreateTableSql ="CREATE TABLE emergencycontact (" + 
	"	   姓名	VARCHAR(50) NOT NULL" +
	"  , 緊急連絡人1	VARCHAR(50)" +
	"  , 緊急連絡人1電話	VARCHAR(30)" +
	"  , 緊急連絡人1關係	VARCHAR(30)" +
	"  , 緊急連絡人2 VARCHAR(50)" +
	"  , 緊急連絡人2電話	VARCHAR(30)" +
	"  , 緊急連絡人2關係	VARCHAR(30))";
	
	private String insertAllDataSQL = "insert into emergencycontact"
			+ "(姓名,緊急連絡人1,緊急連絡人1電話,緊急連絡人1關係,緊急連絡人2,緊急連絡人2電話,緊急連絡人2關係) values" 
		    + "(?,?,?,?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess emergencycontact_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from emergencycontact";
	
	public void createEmergencyContactTable(){
		db.createTable(emergencyContactCreateTableSql);
	}
	public void dropEmergencyContactTable(){
		db.dropTable(emergencyContactTableName);
	}
	
	public void insertAllDataIntoEmergencyContactTable(String name, String contact1, String tel1, String relationship1, String contact2, String tel2, String relationship2) throws SQLException
    {
		if (db.con == null)
		{
			db.createConnection();
		} 
		try{
			db.pst = db.con.prepareStatement(insertAllDataSQL);
			db.pst.setString(1, name); 
			db.pst.setString(2, contact1); 
			db.pst.setString(3, tel1);
			db.pst.setString(4, relationship1);
			db.pst.setString(5, contact2); 
			db.pst.setString(6, tel2);
			db.pst.setString(7, relationship2);
			db.pst.executeUpdate(); 
		}catch(SQLException e){ 
			System.out.println("insertDataEmergencyContactTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToEmergencyContactTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[emergencyContactHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropEmergencyContactTable();
		
		//2. Create a table after dropping. 
		createEmergencyContactTable();
		
		//3. Start to import excel data into database.
		 data = emergencycontact_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j< emergencyContactHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (emergencyContactHeader.length-1)){
		    			insertAllDataIntoEmergencyContactTable(myData[0],myData[1]
		    					,myData[2],myData[3],myData[4],myData[5],myData[6]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[emergencyContactHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	
	public boolean exportEmergencyContactTableToExcel(String directory){
		boolean result = false ;
		try {
			result = emergencycontact_xsl.exportToExcel(emergencyContactTableName,emergencyContactHeader,directory,"EmergencyContactTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromEmergencyContactTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,emergencyContactTableName);
	}
}
