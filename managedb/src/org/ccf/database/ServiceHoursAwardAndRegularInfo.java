package org.ccf.database;

import java.sql.SQLException;

public class ServiceHoursAwardAndRegularInfo {
	private String[] serviceHoursAwardHeader =  {"組別","姓名","加入年月","離隊總年數","歷年服務時數","服務冊時數"};
	private String[] serviceHoursAwardPersonalDetailHeader = {"","年度","服務時數","獲獎"};
	//private String serviceHoursAwardSQL = "select 姓名 ,SUM(時數),可累計 from servicehours where 年  < ? and 可累計 = 'TRUE' GROUP BY (姓名)";
	private String serviceHoursAwardSQL = "select A1.組別,A1.姓名,A2.加入年,A2.加入月,A2.LeftDuration, A3.歷年服務時數, A4.100年底家扶累計時數  AS 100年底家扶累計時數 , A5.101年前服務冊時數  AS 101年前服務冊時數 , (100年底家扶累計時數+101年前服務冊時數) AS 服務冊時數 from mgroup A1 left outer join personalinfo A2 on A1.姓名 = A2.姓名  left outer join (select 姓名 as 姓名 ,SUM(時數) as 歷年服務時數  from servicehours where 年  <= ? and 可累計 = 'TRUE' GROUP BY (姓名))A3 on A1.姓名 = A3.姓名   left outer join brochure A4 on A1.姓名 = A4.姓名  left outer join (select 姓名 as 姓名,SUM(時數) as 101年前服務冊時數  from servicehours where 年 >= 101 and 年 <= ? and 可累計 = 'TRUE' GROUP BY (姓名))A5 on A1.姓名 = A5.姓名  where A1.年度 = ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY A1.組別 ASC";
	private String serviceHoursAwardPeronalDetailSQL = "select A1.年,SUM(A1.時數) as 時數,A2.獲獎  from servicehours A1 left outer join (select 姓名,年,GROUP_CONCAT(award separator ',') as 獲獎 from award where 姓名 = ? GROUP BY 年)A2 on A1.年 = A2.年 and A1.姓名 = A2.姓名  where A1.姓名 = ? and A1.年 <= ? and 可累計 = 'TRUE' GROUP by A1.年 ORDER BY (0+A1.年)";
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess servicehoursawardandregularinfo_xsl = new ExcelFileProcess();
	
	public String[] getServiceHoursAwardHeader(){
		return serviceHoursAwardHeader;
	}
	public String[] getServiceHoursAwardPersonalDetailHeader(){
		return serviceHoursAwardPersonalDetailHeader;
	}
	
	public String[][] parseServiceHoursAwardDataByColume(String[][] dbdata,int rows) {
		 String[][] myData = new String[rows][serviceHoursAwardHeader.length];
		 
		 for(int i=0; i<rows ;i++){
			 //組別
			 myData[i][0] = dbdata[i][0];
			 //姓名
			 myData[i][1] = dbdata[i][1];
			 //加入年.加入月
			 myData[i][2] = dbdata[i][2]+"."+dbdata[i][3];
			 //LeftDuration
			 myData[i][3] = dbdata[i][4];
			 //歷年服務時數
			 if(dbdata[i][5]=="")
				 myData[i][4] = "0";
			 else
				 myData[i][4] = dbdata[i][5];
			 //服務冊時數
			 if(dbdata[i][8]=="")
				 myData[i][5] = "0";
			 else
			 myData[i][5] = dbdata[i][8];
		 }
		/* for(int i=0; i<rows; i++ ){
			 for (int j=0; j <serviceHoursAwardHeader.length;j++ ){
				 System.out.println( myData[i][j]);
			 }
		 }	*/	 
		return myData; 
	}
	
	public String[][] getServiceHoursAwardData(String groupyear, int searchyear) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(serviceHoursAwardSQL);
				db.pst.setInt(1,searchyear);
				db.pst.setInt(2,searchyear);
				db.pst.setString(3,groupyear);
				db.rs = db.pst.executeQuery();
			/* db.stat = db.con.createStatement(); 
			  db.rs = db.stat.executeQuery(serviceHoursAwardSQL);*/

				
			
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
					System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursAwardData: No data.");
				}
				rows = db.rs.getRow();
				//System.out.println("row:"+rows);
				db.rs.beforeFirst();
				
				int cols = db.rs.getMetaData().getColumnCount();
				//System.out.println("cols:"+cols);
				String[][] data = new String[rows][cols];
				for (int i=0; i<rows; i++){
					db.rs.next();
				for (int j = 0; j<cols; j++){
					if (db.rs.getString(j+1)== null)
						data[i][j] = "";
					else
					data[i][j] = db.rs.getString(j+1);
					//System.out.println("[ServiceHoursAwardAndRegularInfo]Get data:"+data[i][j].toString());
				}
			}
			return	parseServiceHoursAwardDataByColume(data,rows);
			//return data;
		    }catch(SQLException e){ 
		      System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursAwardData Exception :" + e.toString()); 
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
		return null;
	 }
	public String[][] parseServiceHoursAwardPersonalDetailByColume(String[][] dbdata,int rows) {
		
		String[][] myData = new String[rows][serviceHoursAwardPersonalDetailHeader.length];
		 for(int i=0; i<rows ;i++){
			 //編號
			 myData[i][0] = String.valueOf(i+1);
			 //年度
			 myData[i][1] = dbdata[i][0];
			 //服務時數
			 myData[i][2] = dbdata[i][1];
			 //獲獎
			 if(dbdata[i][2]=="")
				 myData[i][3] ="";	 
			 else
				 myData[i][3] = dbdata[i][2];
			 
			 }
		/* for(int i=0; i<rows; i++ ){
			 for (int j=0; j <serviceHoursAwardHeader.length;j++ ){
				 System.out.println( myData[i][j]);
			 }
		 }	*/	 
		 
		return myData;
	}
	public String[][] getServiceHoursAwardPersonalDetail(String seach_name, int seach_year) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(serviceHoursAwardPeronalDetailSQL);
				db.pst.setString(1,seach_name);
				db.pst.setString(2,seach_name);
				db.pst.setInt(3, seach_year);
				db.rs = db.pst.executeQuery();
			  /* db.stat = db.con.createStatement(); 
			  db.rs = db.stat.executeQuery(serviceHoursAwardPeronalDetailSQL);*/

				
			
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
					System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursAwardPersonalDetail: No data.");
				}
				rows = db.rs.getRow();
				//System.out.println("row:"+rows);
				db.rs.beforeFirst();
				
				int cols = db.rs.getMetaData().getColumnCount();
				//System.out.println("cols:"+cols);
				String[][] data = new String[rows][cols];
				for (int i=0; i<rows; i++){
					db.rs.next();
				for (int j = 0; j<cols; j++){
					if (db.rs.getString(j+1)== null)
						data[i][j] = "";
					else
					data[i][j] = db.rs.getString(j+1);
					//System.out.println("[ServiceHoursAwardAndRegularInfo]Get data:"+data[i][j].toString());
				}
			}
				return parseServiceHoursAwardPersonalDetailByColume(data,rows);
			//return data;
		    }catch(SQLException e){ 
		      System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursAwardPersonalDetail Exception :" + e.toString()); 
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
		return null;
	 }
	
	
}
