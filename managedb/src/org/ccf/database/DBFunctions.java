package org.ccf.database;

import java.io.UnsupportedEncodingException;
import java.sql.*;
public class DBFunctions {

	public  Connection con = null; //Database objects
	public  Statement stat = null; //statement passed to sql db
	public  ResultSet rs = null; //result set
	public  PreparedStatement pst = null; //the variable passed to sql db, using ? for variable
	
	//private String dropdbSQL = "DROP TABLE User";
	private String insertdbSQL = "insert into activityInfo"
			+ "(ACTIVITY, SERVICE_INFO, TEAM_IN_CHARGE, PERSON_IN_CHARGE, PEOPLE_REQUIRE, ACTIVITY_YEAR, AM, PM) values" 
		    + "(?,?,?,'TEST1','TEST2','TEST3','TEST4','TEST5')"; 
				      
	private String selectSQL= "select * from activityInfo";
	
	public void createConnection(){
		try {
				//Register driver
				Class.forName("org.gjt.mm.mysql.Driver"); 
				/*get connection
				 *localhost:server name
				 *3306: port number
				 *test: DB name
				 **/
				//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
				//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccf?useUnicode=true&characterEncoding=Big5",
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccf?useUnicode=true&characterEncoding=utf-8","root","1234");
		}catch (ClassNotFoundException e){
			System.out.println("DriverClassNotFound:"+e.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkIfTableExists(String tableName){
		boolean exist = false;
		
		try{
			if(con == null)
				createConnection();
	    	
			 PreparedStatement tableQuery = con.prepareStatement("show tables like ?");
			 tableQuery.setString(1, ""+tableName);
			 ResultSet result = tableQuery.executeQuery();
			
			if (result.getRow()>0){
				//System.out.println("table exists");
				exist = true;
			}
			else {
				//System.out.println("table dosen't exist");
				exist = false;
			} 
		}catch(SQLException e){ 
		      System.out.println("checkIfTableExists Exception :" + e.toString()); 
		    }finally{ 
		      close(); 
		    } 
		return exist;
	}
	

	public void createTable(String tableName){ 
		try{ 
		      if(con == null)
		    	  createConnection();
				
			  stat = con.createStatement(); 
		      stat.executeUpdate(tableName); 
			//  stat.executeUpdate(activityInfodb);
		    }catch(SQLException e){ 
		      System.out.println("CreateDB Exception :" + e.toString()); 
		    }finally{ 
		      close(); 
		    } 
	}
	

	public void insertTable( String activity,String service_info, String team_in_charge){ 
		try{ 
			  if(con == null)
				  createConnection();
		      pst = con.prepareStatement(insertdbSQL); 
		      
		      pst.setString(1, activity); 
		      pst.setString(2, service_info); 
		      pst.setString(3, team_in_charge);
		      pst.executeUpdate(); 
		    }catch(SQLException e){ 
		      System.out.println("InsertDB Exception :" + e.toString()); 
		    }finally{ 
		      close(); 
		    } 
	}
	
	public void dropTable(String tableName){ 
		String dropdbSQL = "DROP TABLE" + tableName;
		try{ 
		      stat = con.createStatement(); 
		      stat.executeUpdate(dropdbSQL); 
		    }catch(SQLException e){ 
		      System.out.println("DropDB Exception :" + e.toString()); 
		    }finally{ 
		      close(); 
		    } 
	}
	
	public void selectTable(){ 
		try{ 
			if(con == null)
				createConnection();
		      stat = con.createStatement(); 
		      rs = stat.executeQuery(selectSQL); 
		      System.out.println("ACTIVITY"); 
		      while(rs.next()) 
		      { 
		       // System.out.println(rs.getInt("id")+"\t\t"+ 
		        //rs.getString("name")+"\t\t"+rs.getString("passwd")); 
		    	  try {
					System.out.println(new String (rs.getBytes("NAME"),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		      } 
		    }catch(SQLException e){ 
		      System.out.println("DropDB Exception :" + e.toString()); 
		    }finally{ 
		      close(); 
		    } 
	} 
	
	public void close(){
		try {
				if(rs!=null)
				{
					rs.close();
					rs = null; 
				} 
				if(stat!=null) 
				{ 
					stat.close(); 
					stat = null; 
				} 
				if(pst!=null) 
				{ 
					pst.close(); 
					pst = null; 
		      } 
		    }catch(SQLException e){ 
		      System.out.println("Close Exception :" + e.toString()); 
		    } 
		}
	}
	

