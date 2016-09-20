package org.ccf.database;

import java.sql.SQLException;

public class ServiceHoursAwardAndRegularInfo {
	private String[] serviceHoursAwardHeader =  {"組別","姓名","加入年月","離隊總年數","歷年服務時數","服務冊時數"};
	private String[] serviceHoursAwardPersonalDetailHeader = {"","年度","服務時數","獲獎"};
	private String[] serviceHoursRegularHeader = {"組別","姓名","總時數","服務時數","1~3月服務時數","4~6月服務時數","7~9月服務時數","10~12月服務時數"};
	private String[] serviceHoursRegularAllHoursSortHeader ={"序號","組別","姓名","總時數"};
	private String[] serviceHoursRegularServiceHoursSortHeader ={"序號","組別","姓名","服務時數"};
	private String[] serviceHoursRegularPersonalDetailHeader ={"","活動","月份","時數"};
	//private String serviceHoursAwardSQL = "select 姓名 ,SUM(時數),可累計 from servicehours where 年  < ? and 可累計 = 'TRUE' GROUP BY (姓名)";
	//private String serviceHoursAwardSQL = "select A1.組別,A1.姓名,A2.加入年,A2.加入月,A2.LeftDuration, IFNULL(A3.歷年服務時數,0) as 歷年服務時數, A4.100年底家扶累計時數  AS 100年底家扶累計時數 , A5.101年前服務冊時數  AS 101年前服務冊時數 , (IFNULL(100年底家扶累計時數,0) + IFNULL(101年前服務冊時數,0)) AS 服務冊時數 from mgroup A1 left outer join personalinfo A2 on A1.姓名 = A2.姓名  left outer join (select 姓名 as 姓名 ,SUM(時數) as 歷年服務時數  from servicehours where 年  <= ? and 可累計 = 'TRUE' GROUP BY (姓名))A3 on A1.姓名 = A3.姓名   left outer join brochure A4 on A1.姓名 = A4.姓名  left outer join (select 姓名 as 姓名,SUM(時數) as 101年前服務冊時數  from servicehours where 年 >= 101 and 年 <= ? and 可累計 = 'TRUE' GROUP BY (姓名))A5 on A5.姓名 = A4.姓名  where A1.年度 = ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY A1.組別 ASC";
	private String serviceHoursAwardSQL = "select A1.組別,A1.姓名,A2.加入年,A2.加入月,A2.LeftDuration , IFNULL(A3.歷年服務時數,0) as 歷年服務時數, A4.100年底家扶累計時數  AS 100年底家扶累計時數 , IFNULL(A5.100年前拿到服務冊人的服務時數,0)  AS 100年前拿到服務冊人的服務時數 , A6.100年後拿到服務冊人的服務時數  AS 100年後拿到服務冊人的服務時數 ,(IFNULL(100年底家扶累計時數,0) + IFNULL(100年前拿到服務冊人的服務時數,0) + IFNULL(100年後拿到服務冊人的服務時數,0)) AS 服務冊時數  from mgroup A1 " +
			"left outer join personalinfo A2 on A1.姓名 = A2.姓名 " + 
			"left outer join (select 姓名 as 姓名 ,SUM(時數) as 歷年服務時數  from servicehours where 年  <= ? and 可累計 = 'TRUE' GROUP BY (姓名))A3 on A1.姓名 = A3.姓名  " + 
			"left outer join brochure A4 on A1.姓名 = A4.姓名  " + 
			"left outer join (select 姓名 as 姓名,SUM(時數) as 100年前拿到服務冊人的服務時數 from servicehours where 年 >= 101 and 年 <= ? and 可累計 = 'TRUE' GROUP BY (姓名))A5 on A5.姓名 = A4.姓名  and A4.領紀錄冊年 <= 100 " + 
			"left outer join (select B1.姓名 as 姓名,SUM(B1.時數) as 100年後拿到服務冊人的服務時數  from servicehours B1, brochure C1 where B1.姓名 = C1.姓名   and (case when B1.月 < 10 then concat(B1.年,0,B1.月) else concat(B1.年,B1.月) end) >= (case when C1.領紀錄冊月 < 10 then concat(C1.領紀錄冊年,0,C1.領紀錄冊月) else concat(C1.領紀錄冊年,C1.領紀錄冊月) end) and  B1.年 <= ? and B1.可累計 = 'TRUE' GROUP BY (B1.姓名))A6 on A6.姓名 = A4.姓名  and A4.領紀錄冊年 >= 101 " +
			"where A1.年度 = ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY A1.組別 ASC";
	private String serviceHoursAwardPeronalDetailSQL = "select A1.年,SUM(A1.時數) as 時數,A2.獲獎  from servicehours A1 left outer join (select 姓名,年,GROUP_CONCAT(award separator ',') as 獲獎 from award where 姓名 = ? GROUP BY 年)A2 on A1.年 = A2.年 and A1.姓名 = A2.姓名  where A1.姓名 = ? and A1.年 <= ? and 可累計 = 'TRUE' GROUP by A1.年 ORDER BY (0+A1.年)";
	private String serviceHoursRegularSQL = "select A1.組別,A1.姓名,A2.totalservice as totalservice,A3.meetinghrs as meetinghrs,(IFNULL(totalservice,0) + IFNULL(meetinghrs,0)) AS 總時數 ,IFNULL(A4.service時數,0) AS service時數 , IFNULL(A5.1to3時數,0) AS 1to3時數, IFNULL(A6.4to6時數,0) AS 4to6時數, IFNULL(A7.7to9時數,0) AS 7to9時數 , IFNULL(A8.10to12時數,0) AS 10to12時數 from mgroup A1 left outer join (select 姓名 as 姓名,SUM(時數) as totalservice from servicehours where 年 = ? GROUP by (姓名))A2 on A1.姓名=A2.姓名  left outer join (select 姓名 as 姓名,SUM(時數) as meetinghrs from meeting where 年 = ? GROUP by (姓名))A3 on A1.姓名=A3.姓名  left outer join (select 姓名 as 姓名,SUM(時數)as service時數  from servicehours where 年 = ? and 可累計 = 'TRUE' GROUP by (姓名))A4 on A1.姓名 = A4.姓名 " +
			"left outer join (select 姓名 as 姓名,SUM(時數)as 1to3時數  from servicehours where 年 = ? and 月 <= '3' and 可累計 = 'TRUE' GROUP by (姓名))A5 on A1.姓名 = A5.姓名  " +
			"left outer join (select 姓名 as 姓名,SUM(時數)as 4to6時數  from servicehours where 年 = ? and 月>='4' and 月 <= '6' and 可累計 = 'TRUE' GROUP by (姓名))A6 on A1.姓名 = A6.姓名 " +
			"left outer join (select 姓名 as 姓名,SUM(時數)as 7to9時數  from servicehours where 年 = ? and 月>='7' and 月 <= '9' and 可累計 = 'TRUE' GROUP by (姓名))A7 on A1.姓名 = A7.姓名 " +
			"left outer join (select 姓名 as 姓名,SUM(時數)as 10to12時數  from servicehours where 年 = ? and 月>='10' and 月 <= '12' and 可累計 = 'TRUE' GROUP by (姓名))A8 on A1.姓名 = A8.姓名 " +
			"where A1.年度= ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY A1.組別 ASC";
	private String serviceHoursAllHoursSortSQL = "select A1.組別,A1.姓名,A2.totalservice as totalservice,A3.meetinghrs as meetinghrs,(IFNULL(totalservice,0) + IFNULL(meetinghrs,0)) AS 總時數  from mgroup A1 left outer join (select 姓名 as 姓名,SUM(時數) as totalservice from servicehours where 年 = ? GROUP by (姓名))A2 on A1.姓名=A2.姓名  left outer join (select 姓名 as 姓名,SUM(時數) as meetinghrs from meeting where 年 = ? GROUP by (姓名))A3 on A1.姓名=A3.姓名 " +
			"where A1.年度= ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY 總時數  DESC";
	private String serviceHoursRegularServiceHoursSortSQL ="select A1.組別,A1.姓名, IFNULL(A2.service時數,0) as servicehrs from mgroup A1 left outer join (select 姓名 as 姓名,SUM(時數)as service時數  from servicehours where 年 = ? and 可累計 = 'TRUE' GROUP by (姓名))A2 on A1.姓名 = A2.姓名  where A1.年度= ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY servicehrs DESC";
	private String serviceHoursRegularPersonalDetailSQL = "select A1.活動,A1.月,A1.時數  from servicehours A1 where A1.年 = ? and A1.姓名  = ? union all select A2.會議,A2.月,A2.時數  from meeting A2 where 年 = ? and 姓名  = ?";
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess servicehoursawardandregularinfo_xsl = new ExcelFileProcess();
	
	public String[] getServiceHoursAwardHeader(){
		return serviceHoursAwardHeader;
	}
	public String[] getServiceHoursAwardPersonalDetailHeader(){
		return serviceHoursAwardPersonalDetailHeader;
	}
	public String[] getServiceHoursRegularHeader() {
		return serviceHoursRegularHeader;
	}
	public String[] getServiceHoursRegularAllHoursSortHeader(){
		return serviceHoursRegularAllHoursSortHeader;
	}
	public String[] getServiceHoursRegularServiceHoursSortHeader(){
		return serviceHoursRegularServiceHoursSortHeader;
	}
	public String[] getServiceHoursRegularPersonalDetailHeader() {
		return serviceHoursRegularPersonalDetailHeader;
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
			 myData[i][4] = dbdata[i][5];
			 //服務冊時數
			 myData[i][5] = dbdata[i][9];
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
				db.pst.setInt(3,searchyear);
				db.pst.setString(4,groupyear);
				db.rs = db.pst.executeQuery();
			/* db.stat = db.con.createStatement(); 
			  db.rs = db.stat.executeQuery(serviceHoursAwardSQL);*/
				
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
	
	
	public boolean exportServiceHoursAwardData(String directory,String groupyear, int searchyear) {
		boolean result = false ;
		String [][] myData =null;
		String [][] mySingleData = null;
		servicehoursawardandregularinfo_xsl.createWorkbook(directory, groupyear+"歷年服務時數及獲獎_統計至"+searchyear);
		
		try {
			//Get all service hours and award data
			myData = getServiceHoursAwardData (groupyear, searchyear);
			
			for (int i =0 ; i < myData.length ;i++){
				//System.out.println(myData[i][1]);
				//1. Get 姓名 (column 1) from all data and query database then write to a excel file
				mySingleData = getServiceHoursAwardPersonalDetail(myData[i][1],searchyear);	
				//2.Start the sheet number from 1 to build the sheet for single data.
			    //Assign hyperlink_colume to (0) because we don't need to any hyper link in each single data sheet
				servicehoursawardandregularinfo_xsl.exportToExcelWithHyperlink(mySingleData,getServiceHoursAwardPersonalDetailHeader(),myData[i][1],i+1,0,0);
			}
			 //3.Give the sheet number (0) to build the main sheet.
			 //Assign hyperlink_colume to (1) to build the hyperlink for each column 1
			servicehoursawardandregularinfo_xsl.exportToExcelWithHyperlink(myData,getServiceHoursAwardHeader(),"總計",0,1,1);
			/*
			for (int i = 0 ; i<mySignleData.length ; i++ ){
				for (int j=0; j<serviceHoursAwardPersonalDetailHeader.length; j++ ) {
					System.out.println(mySignleData[i][j]);
				}
			}*/

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		result = servicehoursawardandregularinfo_xsl.writeWorkbook();
		if(result == false) 
			return result;
		result = servicehoursawardandregularinfo_xsl.closeWorkbook();
	  return result;
	}
	
	public String[][] parseServiceHoursRegularDataByColume (String[][] dbdata,int rows) {
		 String[][] myData = new String[rows][serviceHoursRegularHeader.length];
	
		 for(int i=0; i<rows ;i++){
			 //組別
			 myData[i][0] = dbdata[i][0];
			 //姓名
			 myData[i][1] = dbdata[i][1];
			 //總時數
			 myData[i][2] = dbdata[i][4];
			 //服務時數
			 myData[i][3] = dbdata[i][5];
			 //1~3月服務時數
			 myData[i][4] = dbdata[i][6];
			 //4~6月服務時數
			 myData[i][5] = dbdata[i][7];
			 //7~9月服務時數
			 myData[i][6] = dbdata[i][8];
			 //10~12月服務時數
			 myData[i][7] = dbdata[i][9];
		 }
		 
		 return myData;
	}
	
	public String[][] getServiceHoursRegularData(String groupyear, int searchyear) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(serviceHoursRegularSQL);
				db.pst.setInt(1,searchyear);
				db.pst.setInt(2,searchyear);
				db.pst.setInt(3,searchyear);
				db.pst.setInt(4,searchyear);
				db.pst.setInt(5,searchyear);
				db.pst.setInt(6,searchyear);
				db.pst.setInt(7,searchyear);
				db.pst.setString(8,groupyear);
				db.rs = db.pst.executeQuery();
			/* db.stat = db.con.createStatement(); 
			  db.rs = db.stat.executeQuery(serviceHoursAwardSQL);*/
				
				int rows;
				if (!db.rs.last()){
					System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularData: No data.");
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
				return parseServiceHoursRegularDataByColume(data,rows);
			//return data;
		    }catch(SQLException e){ 
		      System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularData Exception :" + e.toString()); 
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
	
	public String[][] parseServiceHoursRegularAllHoursSortDataByColum(String[][] dbdata,int rows) {
		 String[][] myData = new String[rows][serviceHoursRegularAllHoursSortHeader.length];
			
		 for(int i=0; i<rows ;i++){
			 //序號
			 myData[i][0] = String.valueOf(i+1);
			 //組別
			 myData[i][1] = dbdata[i][0];
			 //姓名
			 myData[i][2] = dbdata[i][1];
			 //總時數
			 myData[i][3] = dbdata[i][4];
		 }
	 /* for(int i=0; i<rows; i++ ){
		 for (int j=0; j <serviceHoursRegularAllHoursSortHeader.length;j++ ){
			 System.out.println( myData[i][j]);
		 }
	 }*/	 
	 
		 return myData;
	}
	
	public String[][] getServiceHoursRegularAllHoursSortData(String groupyear, int searchyear) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(serviceHoursAllHoursSortSQL);
			    db.pst.setInt(1,searchyear);
				db.pst.setInt(2,searchyear);
				db.pst.setString(3,groupyear);
				db.rs = db.pst.executeQuery();
			/* db.stat = db.con.createStatement(); 
			  db.rs = db.stat.executeQuery(serviceHoursAwardSQL);*/
				
				int rows;
				if (!db.rs.last()){
					System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularAllHoursSortData: No data.");
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
				return parseServiceHoursRegularAllHoursSortDataByColum(data,rows);
			//return data;
		    }catch(SQLException e){ 
		      System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularAllHoursSortData Exception :" + e.toString()); 
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
	
	public String[][] parseServiceHoursRegularServiceHoursSortDataByColum(String[][] dbdata,int rows) {
		 String[][] myData = new String[rows][serviceHoursRegularServiceHoursSortHeader.length];
			
		 for(int i=0; i<rows ;i++){
			 //序號
			 myData[i][0] = String.valueOf(i+1);
			 //組別
			 myData[i][1] = dbdata[i][0];
			 //姓名
			 myData[i][2] = dbdata[i][1];
			 //服務時數
			 myData[i][3] = dbdata[i][2];
		 }
	/*  for(int i=0; i<rows; i++ ){
		 for (int j=0; j <serviceHoursRegularServiceHoursSortHeader.length;j++ ){
			 System.out.println( myData[i][j]);
		 }
	 } */
	 
		 return myData;
	}

	
	public String[][] getServiceHoursRegularServiceHoursSortData(String groupyear, int searchyear) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(serviceHoursRegularServiceHoursSortSQL);
			    db.pst.setInt(1,searchyear);
				db.pst.setString(2,groupyear);
				db.rs = db.pst.executeQuery();
			/* db.stat = db.con.createStatement(); 
			  db.rs = db.stat.executeQuery(serviceHoursAwardSQL);*/
				
				int rows;
				if (!db.rs.last()){
					System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularServiceHoursSortData: No data.");
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
				return parseServiceHoursRegularServiceHoursSortDataByColum(data,rows);
			//return data;
		    }catch(SQLException e){ 
		      System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularServiceHoursSortData Exception :" + e.toString()); 
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
	
	public String[][] parseServiceHoursRegularPersonalDetailDataByColum(String[][] dbdata,int rows) {
		 String[][] myData = new String[rows][serviceHoursRegularPersonalDetailHeader.length];
	
		 for(int i=0; i<rows ;i++){
			 //編號
			 myData[i][0] = String.valueOf(i+1);
			 //活動
			 myData[i][1] = dbdata[i][0];
			 //月份
			 myData[i][2] = dbdata[i][1];
			 //時數
			 myData[i][3] = dbdata[i][2];
		 }
	/*  for(int i=0; i<rows; i++ ){
		 for (int j=0; j <serviceHoursRegularServiceHoursSortHeader.length;j++ ){
			 System.out.println( myData[i][j]);
		 }
	 } */
	 
		 return myData;
	}

	
	public String[][] getServiceHoursRegularPersonalDetailData(int searchyear, String name) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(serviceHoursRegularPersonalDetailSQL);
			    db.pst.setInt(1,searchyear);
				db.pst.setString(2,name);
				db.pst.setInt(3,searchyear);
				db.pst.setString(4,name);
				db.rs = db.pst.executeQuery();
			/* db.stat = db.con.createStatement(); 
			  db.rs = db.stat.executeQuery(serviceHoursAwardSQL);*/
				
				int rows;
				if (!db.rs.last()){
					System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularPersonalDetailData: No data.");
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
				return parseServiceHoursRegularPersonalDetailDataByColum(data,rows);
			//return data;
		    }catch(SQLException e){ 
		      System.out.println("[ServiceHoursAwardAndRegularInfo]getServiceHoursRegularPersonalDetailData Exception :" + e.toString()); 
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

	public boolean exportServiceHoursRegularData(String directory,String groupyear, int searchyear) {
		boolean result = false ;
		String [][] myData =null;
		String [][] myServiceHoursRegularAllHoursSortData = null;
		String [][] myServiceHoursRegularSortData = null;
		String [][] myServiceHoursRegularPersonalDetailData = null;
		
		servicehoursawardandregularinfo_xsl.createWorkbook(directory, "服務時數統計_"+searchyear);
		
		try {
			//Get all service hours data
			myData = getServiceHoursRegularData (groupyear, searchyear);
			myServiceHoursRegularAllHoursSortData = getServiceHoursRegularAllHoursSortData(groupyear, searchyear);
			myServiceHoursRegularSortData = getServiceHoursRegularServiceHoursSortData(groupyear, searchyear);
			
			 //1.Set sheet for 總時數排序
			  servicehoursawardandregularinfo_xsl.exportToExcelForServiceHoursSortFormat(myServiceHoursRegularAllHoursSortData,getServiceHoursRegularAllHoursSortHeader() , "總時數排序", 1, 27);
			  //2.Set sheet for 服務時數排序
			  servicehoursawardandregularinfo_xsl.exportToExcelForServiceHoursSortFormat(myServiceHoursRegularSortData, getServiceHoursRegularServiceHoursSortHeader(), "服務時數排序", 2, 27);
			  
			for (int i =0 ; i < myData.length ;i++){
				//System.out.println(myData[i][1]);
				//3. Get 姓名 (column 1) from all data and query database then write to a excel file
				myServiceHoursRegularPersonalDetailData = getServiceHoursRegularPersonalDetailData(searchyear,myData[i][1]);	
				//4.Start the sheet number from 1 to build the sheet for single data.
			    //Assign hyperlink_colume to (0) because we don't need to any hyper link in each single data sheet
				servicehoursawardandregularinfo_xsl.exportToExcelWithHyperlink(myServiceHoursRegularPersonalDetailData,getServiceHoursRegularPersonalDetailHeader(),myData[i][1],i+3,0,0);
			} 
			 
			   //5.Set sheet for name_link and give the sheet number (0) to build the main sheet.
			   //Assign hyperlink_colume to (3) to build the hyperlink for each column 1
			   servicehoursawardandregularinfo_xsl.exportToExcelWithHyperlink(myData,getServiceHoursRegularHeader(),"name_link",0,1,3);
			/*
			for (int i = 0 ; i<mySignleData.length ; i++ ){
				for (int j=0; j<serviceHoursAwardPersonalDetailHeader.length; j++ ) {
					System.out.println(mySignleData[i][j]);
				}
			}*/

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		result = servicehoursawardandregularinfo_xsl.writeWorkbook();
		if(result == false) 
			return result;
		result = servicehoursawardandregularinfo_xsl.closeWorkbook();
	  return result;
	}
}
