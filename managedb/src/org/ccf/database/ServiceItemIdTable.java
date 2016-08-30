package org.ccf.database;

import java.sql.SQLException;

public class ServiceItemIdTable {
	public	String[] serviceItemIdHeader= {"代碼","名稱"};
	private String  serviceItemIdTableName = "serviceitemid";
	private String serviceItemIdCreateTableSql ="CREATE TABLE serviceitemid (" + 
	"	   代碼	VARCHAR(50) NOT NULL" +
	"  , 名稱	TEXT NOT NULL )";


	private String insertAllDataSQL = "insert into serviceitemid"
			+ "(代碼, 名稱) values" 
		    + "(?,?)"; 
	
	private String selectAllDataSQL= "select * from serviceitemid";
	
	  
	DBFunctions db = new DBFunctions();
	private ExcelFileProcess serviceitemid_xsl = new ExcelFileProcess();
	
	private int dataRows;
	private int dataCols;
	
	public void createServiceItemIdTable(){
		db.createTable(serviceItemIdCreateTableSql);
	}
	public void dropServiceItemIdTable(){
		db.dropTable(serviceItemIdTableName);
	}
	
	public String[] getServiceItemIdHeader(){
		return serviceItemIdHeader;
	}
	
	public void insertAllDataIntoServiceItemIdTable(String id, String activityname) throws SQLException
	{
		if (db.con == null)
		{
			db.createConnection();
		} 
		//System.out.println(activity+""+service+""+team+""+person);
		try{
			db.pst = db.con.prepareStatement(insertAllDataSQL);
			db.pst.setString(1, id); 
			db.pst.setString(2, activityname); 
			db.pst.executeUpdate(); 
	    }catch(SQLException e){ 
	      System.out.println("insertAllDataIntoServiceItemIdTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToServiceItemIdTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[serviceItemIdHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
	    dropServiceItemIdTable();
		
		//2. Create a table after dropping. 
	    createServiceItemIdTable();
		
		//3. Start to import excel data into database.
		 data = serviceitemid_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<serviceItemIdHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (serviceItemIdHeader.length-1)){
		    			insertAllDataIntoServiceItemIdTable(myData[0],myData[1]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[serviceItemIdHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	public boolean exportServiceItemIdTableToExcel(String directory){
		boolean result = false ;
		try {
			result = serviceitemid_xsl.exportToExcel(serviceItemIdTableName,serviceItemIdHeader,directory,"ServiceItemIdTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromServiceItemIdTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,serviceItemIdTableName);
	}
}
