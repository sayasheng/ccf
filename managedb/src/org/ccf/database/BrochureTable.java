package org.ccf.database;

import java.sql.SQLException;

public class BrochureTable {
	public String[] brochureHeader= {"姓名","志願服務記錄冊證號","手冊在中心","領紀錄冊年","領紀錄冊月","領紀錄冊日","100年底家扶累計時數"};
	private String brochureTableName = "brochure";
	private String brochureCreateTableSql ="CREATE TABLE brochure (" + 
			"	  姓名	VARCHAR(50) NOT NULL" +
			"  , 志願服務記錄冊證號	VARCHAR(50)" +
			"  , 手冊在中心	VARCHAR(30)" +
			"  , 領紀錄冊年	VARCHAR(30)" +
			"  , 領紀錄冊月	VARCHAR(30)" +
			"  , 領紀錄冊日	VARCHAR(30)" +
			"  , 100年底家扶累計時數	VARCHAR(30))";
	private String insertAllDataSQL = "insert into brochure"
			+ "(姓名,志願服務記錄冊證號,手冊在中心,領紀錄冊年,領紀錄冊月,領紀錄冊日,100年底家扶累計時數) values" 
		    + "(?,?,?,?,?,?,?)"; 

	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess brochure_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from brochure";
	
	public void createBrochureTable(){
		db.createTable(brochureCreateTableSql);
	}
	public void dropBrochureTable(){
		db.dropTable(brochureTableName);
	}
	
	public void insertAllDataIntoBrochureTable(String name, String number, String center, String year, String month, String day, String hours) throws SQLException
	{
		if (db.con == null)
		{
			db.createConnection();
		} 
		//System.out.println(activity+""+service+""+team+""+person);
		try{
			db.pst = db.con.prepareStatement(insertAllDataSQL);
			db.pst.setString(1, name); 
			db.pst.setString(2, number); 
			db.pst.setString(3, center);
			db.pst.setString(4, year);
			db.pst.setString(5, month);
			db.pst.setString(6, day);
			db.pst.setString(7, hours);
			db.pst.executeUpdate(); 
	    }catch(SQLException e){ 
	      System.out.println("insertAllDataIntoBrochureTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToBrochureTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[brochureHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
	    dropBrochureTable();
		
		//2. Create a table after dropping. 
	    createBrochureTable();
		
		//3. Start to import excel data into database.
		 data = brochure_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<brochureHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (brochureHeader.length-1)){
		    			insertAllDataIntoBrochureTable(myData[0],myData[1],
		    					myData[2],myData[3],myData[4],myData[5],myData[6]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[brochureHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	public boolean exportBrochureTableToExcel(String directory){
		boolean result = false ;
		try {
			result = brochure_xsl.exportToExcel(brochureTableName,brochureHeader,directory,"BrochureTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromBrochureTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,brochureTableName);
	}
}	
