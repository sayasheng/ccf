package org.ccf.database;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class ServiceHoursTable {
	/* ServiceHours table title description
	 * NAME:姓名
	 * ACTIVITY:活動
	 * YEAR:年
	 * MONTH:月
	 * TOTAL_HOURS:時數
	 * IS_ACCUMULATED:可累計
	 */
	private String serviceHours ="CREATE TABLE servicehours (" + 
	"	 NAME VARCHAR(50) NOT NULL" +
	"  , ACTIVITY TEXT NOT NULL" +
	"  , YEAR VARCHAR(30) NOT NULL" +
	"  , MONTH VARCHAR(30) NOT NULL " +
	"  , TOTAL_HOURS VARCHAR(50) NOT NULL" +
	"  , IS_ACCUMULATED VARCHAR(30) NOT NULL)";
	
	DBFunctions db = new DBFunctions();
	
	private String insertAllData = "insert into servicehours"
			+ "(NAME, ACTIVITY, YEAR, MONTH, TOTAL_HOURS, IS_ACCUMULATED) values" 
		    + "(?,?,?,?,?,?)"; 
	
	private String selectAllData= "select * from servicehours";
	private String selectByName = "select * from servicehours where NAME = ?";
    private String selectByDate = "select * from servicehours where YEAR = ? and month = ?";
	private String exportData = "SELECT * INTO OUTFILE 'servicehours.txt' FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' FROM servicehours" ;
    
	public void createServiceHoursTable(){
		db.createTable(serviceHours);
	}
	
	public void insertAllDataIntoServiceHoursTable(String name, String activity, String year, String month, String totalhrs, String isaccumulated) throws SQLException
	{
		if (db.con == null)
		{
			db.createConnection();
		} 
		try{
			db.pst = db.con.prepareStatement(insertAllData);
			db.pst.setString(1, name); 
			db.pst.setString(2, activity); 
			db.pst.setString(3, year);
			db.pst.setString(4, month);
			db.pst.setString(5, totalhrs);
			db.pst.setString(6, isaccumulated);
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
	
	public void queryFromServiceHoursTable() throws SQLException
	{
		try{ 
			 if(db.con == null)
			 {
				db.createConnection();
			 }
		      db.stat = db.con.createStatement(); 
		      db.rs = db.stat.executeQuery(selectAllData); 

		      while(db.rs.next()) 
		      { 
		    	  try {
					System.out.println(new String (db.rs.getBytes("NAME"),"UTF-8"));
				  } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	}
	
	public void queryServiceHoursTableByName(String name) throws SQLException
	{
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			 
				db.pst = db.con.prepareStatement(selectByName);
				db.pst.setString(1, name);   
				db.rs = db.pst.executeQuery();
		      while(db.rs.next()) 
		       { 
		    	  try {
					System.out.println(new String (db.rs.getBytes("ACTIVITY"),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		      } 
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
	
	public void queryServiceHoursTableByDate(String year, String month) throws SQLException
	{
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			 
				db.pst = db.con.prepareStatement(selectByDate);
				db.pst.setString(1, year);
				db.pst.setString(2, month);
				db.rs = db.pst.executeQuery();
		      while(db.rs.next()) 
		      {
		    	  try {
					System.out.println(new String (db.rs.getBytes("ACTIVITY"),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		      } 
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
	
	public void exportServiceHoursTableToFile() throws SQLException
	{
		try{ 
			 if(db.con == null)
			 {
				db.createConnection();
			 }
		      db.stat = db.con.createStatement(); 
		      db.rs = db.stat.executeQuery(exportData);
		    }
		    catch(SQLException e){ 
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
		
	}
	
}
