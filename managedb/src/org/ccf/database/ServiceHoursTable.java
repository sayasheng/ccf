package org.ccf.database;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class ServiceHoursTable {
	public	String[] serviceHoursHeader= {"姓名","活動","serviceType","年","月","時數","可累計"};
	private String serviceHoursTableName = "servicehours";
	private String serviceHoursCreateTableSql ="CREATE TABLE servicehours (" + 
	"	   姓名	VARCHAR(50) NOT NULL" +
	"  , 活動	TEXT NOT NULL" +
	"  , serviceType VARCHAR(50) " +
	"  , 年	VARCHAR(30) NOT NULL" +
	"  , 月	VARCHAR(30) NOT NULL " +
	"  , 時數	VARCHAR(50) NOT NULL" +
	"  , 可累計	VARCHAR(30) NOT NULL)";


	private String insertAllDataSQL = "insert into servicehours"
			+ "(姓名, 活動, serviceType, 年, 月, 時數, 可累計) values" 
		    + "(?,?,?,?,?,?,?)"; 
	
	private String selectAllDataSQL= "select * from servicehours";
	private String selectByNameSQL = "select * from servicehours where 姓名 = ?";
    private String selectByDateSQL = "select * from servicehours where 年 = ? and 月 = ?";
	  
	DBFunctions db = new DBFunctions();
	private ExcelFileProcess servicehourse_xsl = new ExcelFileProcess();
	
	private int dataRows;
	private int dataCols;
	
	public void createServiceHoursTable(){
		db.createTable(serviceHoursCreateTableSql);
	}
	public void dropServiceHoursTable(){
		db.dropTable(serviceHoursTableName);
	}
	
	public String[] getServiceHoursHeader(){
		return serviceHoursHeader;
	}

	public void insertAllDataIntoServiceHoursTable(String name, String activity, String servicetype, String year, String month, String totalhrs, String isaccumulated) throws SQLException
	{
		if (db.con == null)
		{
			db.createConnection();
		} 
		//System.out.println(name+""+activity+""+year+""+month+""+totalhrs+""+isaccumulated);
		try{
			db.pst = db.con.prepareStatement(insertAllDataSQL);
			db.pst.setString(1, name); 
			db.pst.setString(2, activity); 
			db.pst.setString(3, servicetype);
			db.pst.setString(4, year);
			db.pst.setString(5, month);
			db.pst.setString(6, totalhrs);
			db.pst.setString(7, isaccumulated);
			db.pst.executeUpdate(); 
	    }catch(SQLException e){ 
	      System.out.println("insertDataServiceHoursTable Exception :" + e.toString()); 
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
	
	public String[][] queryAllFromServiceHoursTable() throws SQLException
	{	
		return db.queryAllFromTable (selectAllDataSQL,serviceHoursTableName);
		/*String[][] data = null;
		try{ 
			 if(db.con == null)
			 {
				db.createConnection();
			 }
		      db.stat = db.con.createStatement(); 
		      db.rs = db.stat.executeQuery(selectAllDataSQL); 
            
		  	int rows;
			if (!db.rs.last()){
				System.out.println("[ServiceHours]queryAllFromServiceHoursTable: No data.");
			}
			rows = db.rs.getRow();
			//System.out.println("row:"+rows);
			db.rs.beforeFirst();
			
			int cols = db.rs.getMetaData().getColumnCount();
			//System.out.println("cols:"+cols);
			data = new String[rows][cols];
			for (int i=0; i<rows; i++){
				db.rs.next();
			for (int j = 0; j<cols; j++){
				data[i][j] = db.rs.getString(j+1);
				//System.out.println("Get data:"+data[i][j].toString());
			}
		  }
		    }catch(SQLException e){ 
		      System.out.println("selectAllDataFromServiceHoursTable Exception :" + e.toString()); 
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
		return data; */
	}
	
	public void queryServiceHoursTableByName(String name, boolean isSaveFile) throws SQLException
	{
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			 
				db.pst = db.con.prepareStatement(selectByNameSQL);
				db.pst.setString(1, name);   
				db.rs = db.pst.executeQuery();
			 /*	
		      while(db.rs.next()) 
		       { 
		    	  try {
					System.out.println(new String (db.rs.getBytes("活動"),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		      } */
				
				int rows;
				if (!db.rs.last()){
					System.out.println("[ServiceHours]queryServiceHoursTableByDate: No data.");
				}
				rows = db.rs.getRow();
				System.out.println("row:"+rows);
				db.rs.beforeFirst();
				
				int cols = db.rs.getMetaData().getColumnCount();
				System.out.println("cols:"+cols);
				Object[][] data = new Object[rows][cols];
				for (int i=0; i<rows; i++){
					db.rs.next();
				for (int j = 0; j<cols; j++){
					data[i][j] = db.rs.getObject(j+1);
					//System.out.println("Get data:"+data[i][j].toString());
				}
			}
			
			if(!isSaveFile)
				System.out.println("[ServiceHours]queryServiceHoursTableByName: No data save");

				servicehourse_xsl.exportQueryResultToExcel(data, serviceHoursHeader, "serviceHoursName");
					
		    }catch(SQLException e){ 
		      System.out.println("queryServiceHoursTableByName Exception :" + e.toString()); 
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
	
	public void queryServiceHoursTableByDate(String year, String month, boolean isSaveFile) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			 
				db.pst = db.con.prepareStatement(selectByDateSQL);
				db.pst.setString(1, year);
				db.pst.setString(2, month);
				db.rs = db.pst.executeQuery();
			/*	
		      while(db.rs.next()) 
		      {
		    	  try {
					System.out.println(new String (db.rs.getBytes("活動"),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		      } */
				
				int rows;
				if (!db.rs.last()){
					System.out.println("[ServiceHours]queryServiceHoursTableByDate: No data.");
				}
				rows = db.rs.getRow();
				System.out.println("row:"+rows);
				db.rs.beforeFirst();
				
				int cols = db.rs.getMetaData().getColumnCount();
				System.out.println("cols:"+cols);
				Object[][] data = new Object[rows][cols];
				for (int i=0; i<rows; i++){
					db.rs.next();
				for (int j = 0; j<cols; j++){
					data[i][j] = db.rs.getObject(j+1);
					//System.out.println("Get data:"+data[i][j].toString());
				}
			}
			
			if(!isSaveFile)
				System.out.println("[ServiceHours]queryServiceHoursTableByName: No data save");

				servicehourse_xsl.exportQueryResultToExcel(data, serviceHoursHeader, "serviceHoursYM");
				
		    }catch(SQLException e){ 
		      System.out.println("[ServiceHours]queryServiceHoursTableByName Exception :" + e.toString()); 
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

	
	public boolean exportServiceHoursTableToExcel(String directory){
		boolean result = false ;
		try {
			result = servicehourse_xsl.exportToExcel(serviceHoursTableName,serviceHoursHeader,directory,"ServiceHoursTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean importExcelToServiceHourseTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[serviceHoursHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropServiceHoursTable();
		
		//2. Create a table after dropping. 
		createServiceHoursTable();
		
		//3. Start to import excel data into database.
		 data = servicehourse_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<serviceHoursHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (serviceHoursHeader.length-1)){
		    			insertAllDataIntoServiceHoursTable(myData[0],myData[1],
		    					myData[2],myData[3],myData[4],myData[5],myData[6]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[serviceHoursHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}	
}
