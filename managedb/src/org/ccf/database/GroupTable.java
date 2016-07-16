package org.ccf.database;

import java.sql.SQLException;

public class GroupTable {
	public  String[] groupHeader= {"姓名","年度","身分","組別","職務"};
	//"group" is one of the keywords of mySQL so rename it to mgroup in order to prevent system crashed from error
	private String groupTableName = "mgroup"; 
	private String groupCreateTableSql ="CREATE TABLE mgroup (" + 
			"	   姓名	VARCHAR(50) NOT NULL" +
			"  , 年度	VARCHAR(30) NOT NULL" +
			"  , 身分	VARCHAR(30) NOT NULL" +
			"  , 組別	VARCHAR(30) NOT NULL" +
			"  , 職務	VARCHAR(50) NOT NULL)";
	
	private String insertAllDataSQL = "insert into mgroup"
			+ "(姓名, 年度, 身分, 組別, 職務) values" 
		    + "(?,?,?,?,?)";
	private String selectAllDataSQL = "select * from mgroup";
	private String selectYearSQL = "select 年度 from mgroup GROUP BY 年度  ORDER BY (0+年度)";
	private String selectMemberNameSQL = "select 姓名 from mgroup GROUP BY 姓名";
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess group_xsl = new ExcelFileProcess();
	
	public void createGroupTable(){
		db.createTable(groupCreateTableSql);
	}
	public void dropGroupTable(){
		db.dropTable(groupTableName);
	}
	
	public void insertAllDataIntoGroupTable(String name, String year, String identity, String group, String charge) throws SQLException
    {
	if (db.con == null)
	{
		db.createConnection();
	} 
	try{
		db.pst = db.con.prepareStatement(insertAllDataSQL);
		db.pst.setString(1, name); 
		db.pst.setString(2, year); 
		db.pst.setString(3, identity);
		db.pst.setString(4, group);
		db.pst.setString(5, charge);
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataGroupTable Exception :" + e.toString()); 
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
	
	
	public boolean importExcelToGroupTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[groupHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropGroupTable();
		
		//2. Create a table after dropping. 
		createGroupTable();
		
		//3. Start to import excel data into database.
		 data = group_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<groupHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (groupHeader.length-1)){
		    			insertAllDataIntoGroupTable(myData[0],myData[1]
		    					,myData[2],myData[3],myData[4]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[groupHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	
	public boolean exportGroupTableToExcel(String directory){
		boolean result = false ;
		try {
			result = group_xsl.exportToExcel(groupTableName,groupHeader,directory,"GroupTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromGroupTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,groupTableName);
	}
	
	@SuppressWarnings("null")
	public String[] queryYearFromGroupTable() throws SQLException{
		String [] myYear;
		String [][] data;
		 
		data = db.queryAllFromTable(selectYearSQL);
		myYear = new String[data.length];
		
		for (int i=0; i<data.length; i++){
		for (int j=0; j< 1; j++){ // just one column of data 
			myYear[i]=data[i][j].toString();
		}
	  }
		return myYear;
	}
   
	public String[] queryNameFromGroupTable() throws SQLException{
		String [] myName;
		String [][] data;
		 
		data = db.queryAllFromTable(selectMemberNameSQL);
		myName = new String[data.length];
		
		for (int i=0; i<data.length; i++){
		for (int j=0; j< 1; j++){ // just one column of data 
			myName[i]=data[i][j].toString();
		}
	  }
		return myName;
	}
}
