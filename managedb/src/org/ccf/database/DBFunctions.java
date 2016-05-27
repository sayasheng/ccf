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
	    	
			  pst = con.prepareStatement("show tables like ?");
			  pst.setString(1,tableName);
			  rs = pst.executeQuery();
			
			if (rs.getRow()>0){
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
	

	public void createTable(String createTableSQL){ 
		try{ 
		      if(con == null)
		    	  createConnection();
				
			  stat = con.createStatement(); 
		      stat.executeUpdate(createTableSQL); 
			//  stat.executeUpdate(activityInfodb);
		    }catch(SQLException e){ 
		      System.out.println("CreateDB Exception :" + e.toString()); 
		    }finally{ 
		      close(); 
		    } 
	}
	
	
	public Object[][] getAllDataFromTable(String tableName, boolean extraRow)throws SQLException{
		 if(con == null)
		 {
			 createConnection();
		 }

		try {
				pst = con.prepareStatement("select * from " + tableName);
				rs = pst.executeQuery();
			
			int rows;
			if (!rs.last()){
				return null;
			}
			rows = rs.getRow();
			rs.beforeFirst();
			if (extraRow) rows++;

			int cols = rs.getMetaData().getColumnCount();
			Object[][] data = new Object[rows][cols];
			for (int i=0; i<rows-1; i++){
					rs.next();
				for (int j = 0; j<cols; j++){
					data[i][j] = rs.getObject(j+1);
				}
			}

			//if there is an extra row, fill it with empty strings
			if (extraRow){
				for (int j = 0; j<cols; j++){
					data[rows-1][j] ="";
				}
			}
			//else get the last real row of data
			else{
				for (int j = 0; j<cols; j++){
					data[rows-1][j] = rs.getObject(j+1);
				}
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insertTable( String activity,String service_info, String team_in_charge){ 
		if(con == null)
		 {
			 createConnection();
		 }
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
		String dropdbSQL = "DROP TABLE If Exists " + tableName;
		System.out.print(dropdbSQL);	
		try{ 
			if(con == null)
			  createConnection();
		      stat = con.createStatement(); 
		      stat.executeUpdate(dropdbSQL); 
		    }catch(SQLException e){ 
		      System.out.println("DropDB Exception:" + e.toString()); 
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
	
	public String[][] queryAllFromTable(String selectAllDataSQL) throws SQLException
	{
		String[][] data = null;
	
		try{ 
			 if(con == null)
			 {
				createConnection();
			 }
	
		      stat = con.createStatement(); 
		      rs = stat.executeQuery(selectAllDataSQL); 

		  	int rows;
			if (!rs.last()){
				System.out.println("[DBFunctions_queryAllFromTable]: No data.");
			}
			rows = rs.getRow();
			//System.out.println("[DBFunctions_queryAllFromTable] row:"+rows);
			rs.beforeFirst();
			
			int cols = rs.getMetaData().getColumnCount();
			//System.out.println("cols:"+cols);
			data = new String[rows][cols];
			for (int i=0; i<rows; i++){
				rs.next();
			for (int j = 0; j<cols; j++){
				data[i][j] = rs.getString(j+1);
				//System.out.println("[DBFunctions_queryAllFromTable]Get data:"+data[i][j].toString());
			}
		  }
		    }catch(SQLException e){ 
		     // System.out.println("[DBFunctions_queryAllFromTable] "+tableName+""+"Exception :" + e.toString());
		      return data;
		    }finally{
		      if (rs != null)
		      {
		    	  rs.close();
		      }
		      if(con != null)
		      {
		    	  close(); 
		      }
		    }
		return data;
	}
	
	public String[][] queryAllFromTable(String selectAllDataSQL, String tableName) throws SQLException
	{
		String[][] data = null;
		try{ 
			 if(con == null)
			 {
				createConnection();
			 }
		      stat = con.createStatement(); 
		      rs = stat.executeQuery(selectAllDataSQL); 
            
		  	int rows;
			if (!rs.last()){
				System.out.println("[DBFunctions_queryAllFromTable] "+tableName+": No data.");
			}
			rows = rs.getRow();
			//System.out.println("row:"+rows);
			rs.beforeFirst();
			
			int cols = rs.getMetaData().getColumnCount();
			//System.out.println("cols:"+cols);
			data = new String[rows][cols];
			for (int i=0; i<rows; i++){
				rs.next();
			for (int j = 0; j<cols; j++){
				data[i][j] = rs.getString(j+1);
				//System.out.println("Get data:"+data[i][j].toString());
			}
		  }
		    }catch(SQLException e){ 
		      System.out.println("[DBFunctions_queryAllFromTable] "+tableName+""+"Exception :" + e.toString());
		      return data;
		    }finally{
		      if (rs != null)
		      {
		    	  rs.close();
		      }
		      if(con != null)
		      {
		    	  close(); 
		      }
		    }
		return data;
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
	

