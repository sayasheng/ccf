package org.ccf.database;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class ActivitySearchAndRegisterInfo {
	private String[] activityRegisterHeader =  {"","活動名稱","日期 ","時間","人力","負責組","負責人","報名名單"};
	private String[] activityDataHeader = {"","活動名稱","日期","時間","人力","負責組","負責人","服務內容","中心負責組別","中心負責社工"};
	private String activityRegisterByYearSQL = "select A1.活動,A1.起始月,A1.起始日,A1.結束月,A1.結束日,A1.起始時間,A1.結束時間,A1.人力,A2.組別,A1.活動負責人 from activity A1 left outer join mgroup A2 on A1.活動負責人 = A2.姓名 and A1.年 = A2.年度  where A1.年 = ? ORDER BY (0+A1.起始月) ASC";
	private String activityRegisterByYearAndMonthSQL = "select A1.活動,A1.起始月,A1.起始日,A1.結束月,A1.結束日,A1.起始時間,A1.結束時間,A1.人力,A2.組別,A1.活動負責人 from activity A1 left outer join mgroup A2 on A1.活動負責人 = A2.姓名 and A1.年 = A2.年度  where (A1.年 = ? and A1.起始月 = ?) ORDER BY (0+A1.起始月) ASC";
	private String activityDataByYearSQL = "select A1.活動,A1.起始月,A1.起始日,A1.結束月,A1.結束日,A1.起始時間,A1.結束時間,A1.人力,A1.活動負責人,A2.組別 ,A3.服務內容, A3.中心負責組別,A3.中心負責社工 from activity A1 left outer join mgroup A2 on A1.活動負責人 = A2.姓名 and A1.年 = A2.年度  left outer join activityInfo A3 on A3.活動 = A1.活動 where A1.年 = ? and A1.起始月 = ? ORDER BY (0+A1.起始月) ASC";
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess activitysearchandregsterinfo_xsl = new ExcelFileProcess();
	
	public String[] getActivityRegisterHeader(){
		return activityRegisterHeader;
	}
	public String[] getActivityDataHeader(){
		return activityDataHeader;
	}
	private class ActivityDataStruct {
		public String item = null;
		public String data = null;
		public String time = null;
		public String humanpower = null;
		public String group = null;
		public String person = null;
		public String serviceitem = null;
		public String groupccf = null;
		public String personccf = null;
				
	}
	
	public String[][] getActivityRegisterDataByYearandMonth(String year,String month) throws SQLException
	{
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			    db.pst = db.con.prepareStatement(activityRegisterByYearAndMonthSQL);
			    
				db.pst.setString(1, year);
				db.pst.setString(2, month);
				db.rs = db.pst.executeQuery();
			/* db.stat = db.con.createStatement(); 
		     db.rs = db.stat.executeQuery(activityRegisterByYearAndMonthSQL); */
			
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
					System.out.println("[ActivitySearchAndRegisterInfo]getActivityRegisterDataByYearandMonth: No data.");
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
					
					//System.out.println("[ActivitySearchAndRegisterInfo]Get data:"+data[i][j].toString());
				}
			}
			return getActivityRegisterYearByColume(data,rows,cols,year);

		    }catch(SQLException e){ 
		      System.out.println("[ActivitySearchAndRegisterInfo]getActivityRegisterDataByYearandMonth Exception :" + e.toString()); 
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
	
	public String[][] getActivityRegisterDataByYear(String year) throws SQLException
	{
	
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(activityRegisterByYearSQL);
			    
				db.pst.setString(1, year);
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
					System.out.println("[ActivitySearchAndRegisterInfo]getActivityRegisterDataByYear: No data.");
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
					//System.out.println("[ActivitySearchAndRegisterInfo]Get data:"+data[i][j].toString());
				}
			}
			return getActivityRegisterYearByColume(data,rows,cols,year);

		    }catch(SQLException e){ 
		      System.out.println("[ActivitySearchAndRegisterInfo]getActivityRegisterDataByYear Exception :" + e.toString()); 
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
	
	private String[][] getActivityRegisterYearByColume(String[][] dbresult, int row, int col, String year) {
		String[][] myData = new String[row][activityRegisterHeader.length];
		String myStartDate;
		String myEndDate;
		int mYear = Integer.valueOf(year)+1911;
		
		
		for (int i=0 ; i< row; i++){
			//for (int j=0; j< col; j++){
			    //活動編號 ++
				myData[i][0] = String.valueOf(i+1);
				//活動名稱 ++ 
				myData[i][1] = dbresult[i][0];
				//日期 ++
				if (dbresult[i][2].equals("") || dbresult[i][4].equals("")){
					myData[i][2] = dbresult[i][1]+"/";
				}
				else {
				myStartDate = String.valueOf(mYear)+"/"+dbresult[i][1]+"/"+dbresult[i][2];
				myEndDate = String.valueOf(mYear)+"/"+dbresult[i][3]+"/"+dbresult[i][4];
				myData[i][2] = getDateFormat(myStartDate,myEndDate);
				}
				//時間
			/*	if(dbresult[i][5] == null)
					myData[i][3] = ""+"-"+dbresult[i][6];
				else if (dbresult[i][6] == null)
					myData[i][3] = dbresult[i][5]+"-"+"";
				else if (dbresult[i][5]== null && dbresult[i][6] == null)
					myData[i][3] = ""+"-"+"";
				else
					myData[i][3] = dbresult[i][5]+"-"+dbresult[i][6]; */
				if(dbresult[i][5] == null)
					myData[i][3] = getTimeFormat("",dbresult[i][6]);
				else if (dbresult[i][6] == null)
					myData[i][3] = getTimeFormat(dbresult[i][5],"");
				else if (dbresult[i][5]== null && dbresult[i][6] == null)
					myData[i][3] = getTimeFormat("","");
				else 
					myData[i][3] = getTimeFormat(dbresult[i][5],dbresult[i][6]);
				//人力 
				myData[i][4] = dbresult[i][7];
				//負責組
				myData[i][5] = dbresult[i][8];
				//負責人
				myData[i][6] = dbresult[i][9];
				//報名名單
				myData[i][7] = "";
			//}
		}
		
		return myData;
	}
	
	private String getTimeFormat (String startTime, String endTime) {
		String time;
		return time = startTime+"-"+endTime;	
	}
	
	private String getDateFormat(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd (E)");
		DateFormat df = DateFormat.getDateInstance();
		Date date = null;
		String myDate;

		if(startDate.equals(endDate)){	
			try {
				date = df.parse(startDate);
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			myDate = sdf.format(calendar.getTime());
		}else{
			try {
				date = df.parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			String tempStartDate = sdf.format(calendar.getTime());			
			date = null;
			try {
				date = df.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			String tempEndDate = sdf.format(calendar.getTime());
			myDate = tempStartDate+"~"+tempEndDate;
		}
		return myDate;
	}
	
	public boolean exportActivityRegisterToExcelByYear (String directory, String year) {
		boolean result = false ;
		String [][] myData;
		activitysearchandregsterinfo_xsl.createWorkbook(directory, "活動報名資料("+year+")");
		
	    //export 12 month to each sheet
		for (int i=0; i < 12; i++) {
			myData = null;
		try {
			myData = getActivityRegisterDataByYearandMonth (year, Integer.toString(i+1));
			result = activitysearchandregsterinfo_xsl.exportToExcel(myData, getActivityRegisterHeader(),Integer.toString(i+1)+"月" ,i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		result = activitysearchandregsterinfo_xsl.writeWorkbook();
		if(result == false) 
			return result;
		result = activitysearchandregsterinfo_xsl.closeWorkbook();
	  return result;
	}
	
	public boolean exportActivityRegisterToExcelByYearAndMonth (String directory, String year, String month) {
		boolean result = false ;
		
		try {
			result = activitysearchandregsterinfo_xsl.exportToExcel(getActivityRegisterDataByYearandMonth(year, month),getActivityRegisterHeader(),directory,"活動報名資料("+year+"-"+month+")",month+"月");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return result;
	}
	
	public boolean exportActivityDataToExcelByYear(String directory, String year) {
		boolean result = false ;
		String [][] myData;
		activitysearchandregsterinfo_xsl.createWorkbook(directory, "活動內容資料("+year+")");
		
	    //export 12 month to each sheet
		for (int i=0; i < 12; i++) {
			myData = null;
		try {
			myData = getActivityDataByYearAndMonth (year, Integer.toString(i+1));
			result = activitysearchandregsterinfo_xsl.exportToExcel(myData, getActivityDataHeader(),Integer.toString(i+1)+"月" ,i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		result = activitysearchandregsterinfo_xsl.writeWorkbook();
		if(result == false) 
			return result;
		result = activitysearchandregsterinfo_xsl.closeWorkbook();
	  return result;
	}
	
	public boolean exportActivityDataToExcelByYearAndMonth(String directory, String year, String month) {
		boolean result = false ;
		try {
			result = activitysearchandregsterinfo_xsl.exportToExcel(getActivityDataByYearAndMonth(year, month),getActivityDataHeader(),directory,"活動內容資料("+year+"-"+month+")",month+"月");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String[][] getActivityDataByYearAndMonth(String year, String month) {
		String [][] myData = null;
		LinkedList <ActivityDataStruct> mActivityDataStruct = new LinkedList<ActivityDataStruct>();
		
		
			LinkedList <ActivityDataStruct> temp = null;
			try {
				temp = getActivityDataByYear(year,month);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int j=0; j<temp.size(); j++){
				mActivityDataStruct.addLast(temp.get(j));
			}

		myData = new String[mActivityDataStruct.size()][activityDataHeader.length];
		for (int i=0; i<mActivityDataStruct.size(); i++){
			//編號
			myData[i][0]= Integer.toString(i+1);
			//活動名稱
			myData[i][1]=mActivityDataStruct.get(i).item;
			//日期
			myData[i][2]=mActivityDataStruct.get(i).data;
			//時間
			myData[i][3]=mActivityDataStruct.get(i).time;
			//人力
			myData[i][4]=mActivityDataStruct.get(i).humanpower;
			//負責組
			myData[i][5]=mActivityDataStruct.get(i).group;
			//負責人
			myData[i][6]=mActivityDataStruct.get(i).person;
			//服務內容
			myData[i][7]=mActivityDataStruct.get(i).serviceitem;
			//中心負責組別
			myData[i][8]=mActivityDataStruct.get(i).groupccf;
			//中心負責社工
			myData[i][9]=mActivityDataStruct.get(i).personccf;
		}
		return myData;
	}
	
	@SuppressWarnings("null")
	public String[][] getActivityDataByYear (String year) {
		String [][] myData = null;
		LinkedList <ActivityDataStruct> mActivityDataStruct = new LinkedList<ActivityDataStruct>();
		
		for (int i=0; i<12 ;i++){
			LinkedList <ActivityDataStruct> temp = null;
			try {
				temp = getActivityDataByYear(year,Integer.toString(i+1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int j=0; j<temp.size(); j++){
				mActivityDataStruct.addLast(temp.get(j));
			}
		}
			
		myData = new String[mActivityDataStruct.size()][activityDataHeader.length];
		for (int i=0; i<mActivityDataStruct.size(); i++){
			//編號
			myData[i][0]= Integer.toString(i+1);
			//活動名稱
			myData[i][1]=mActivityDataStruct.get(i).item;
			//日期
			myData[i][2]=mActivityDataStruct.get(i).data;
			//時間
			myData[i][3]=mActivityDataStruct.get(i).time;
			//人力
			myData[i][4]=mActivityDataStruct.get(i).humanpower;
			//負責組
			myData[i][5]=mActivityDataStruct.get(i).group;
			//負責人
			myData[i][6]=mActivityDataStruct.get(i).person;
			//服務內容
			myData[i][7]=mActivityDataStruct.get(i).serviceitem;
			//中心負責組別
			myData[i][8]=mActivityDataStruct.get(i).groupccf;
			//中心負責社工
			myData[i][9]=mActivityDataStruct.get(i).personccf;
		}
		
		/*for (int i=0; i<mActivityDataStruct.size(); i++){
			for (int j =0; j<activityDataHeader.length ; j++){
				System.out.println(myData[i][j]);
			}
			
		}*/
		return myData;
	}
	public LinkedList <ActivityDataStruct> getActivityDataByYear(String year, String month) throws SQLException{		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(activityDataByYearSQL);
			    
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
					System.out.println("[ActivitySearchAndRegisterInfo]getActivityDataByYear: No data.");
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
					
					//System.out.println("[ActivitySearchAndRegisterInfo]Get data:"+data[i][j].toString());
				}
			}
				return getActivityDataYearByColume(data,rows,year);
			//return data;
		    }catch(SQLException e){ 
		      System.out.println("[InsuranceInfo]getInsuranceData Exception :" + e.toString()); 
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
	
	public LinkedList <ActivityDataStruct> getActivityDataYearByColume (String [][] databaseresult, int rows, String year) {
		
		int mYear = Integer.valueOf(year)+1911;
		ActivityDataStruct myActivityDataStruct;
		LinkedList <ActivityDataStruct> mActivityDataStruct = new LinkedList <ActivityDataStruct>();
		String myStartDate,myEndDate;
		
		
		for (int i =0; i< rows; i++) {
			myActivityDataStruct = new ActivityDataStruct();
			
			myStartDate = String.valueOf(mYear)+"/"+databaseresult[i][1]+"/"+databaseresult[i][2];
			myEndDate = String.valueOf(mYear)+"/"+databaseresult[i][3]+"/"+databaseresult[i][4];
			
			if (mActivityDataStruct.size() > 0) {
					if (!mActivityDataStruct.getLast().item.equals(databaseresult[i][0])){
						myActivityDataStruct.item = databaseresult[i][0];
						
						if (databaseresult[i][2].equals("") || databaseresult[i][4].equals("")){
							myActivityDataStruct.data = databaseresult[i][1]+"/";
						}else {
							myActivityDataStruct.data = getDateFormat (myStartDate,myEndDate);
						}
						myActivityDataStruct.time = getTimeFormat (databaseresult[i][5],databaseresult[i][6]);
						myActivityDataStruct.humanpower = databaseresult[i][7];
						myActivityDataStruct.person = databaseresult[i][8];
						myActivityDataStruct.group = databaseresult[i][9];
						myActivityDataStruct.serviceitem = databaseresult[i][10];
						myActivityDataStruct.groupccf = databaseresult[i][11];
						myActivityDataStruct.personccf = databaseresult[i][12];
						mActivityDataStruct.addLast(myActivityDataStruct);
					}else {
						if (databaseresult[i][2].equals("") || databaseresult[i][4].equals("")){
							mActivityDataStruct.getLast().data = databaseresult[i][1]+"/";
						}else {
						if(!mActivityDataStruct.getLast().data.contains(getDateFormat(myStartDate,myEndDate)))
							mActivityDataStruct.getLast().data	= mActivityDataStruct.getLast().data+"\n"+getDateFormat (myStartDate,myEndDate);
						}
						if(!mActivityDataStruct.getLast().time.contains(getTimeFormat (databaseresult[i][5],databaseresult[i][6])))
							mActivityDataStruct.getLast().time	= mActivityDataStruct.getLast().time+"\n"+getTimeFormat (databaseresult[i][5],databaseresult[i][6]);
						if (!mActivityDataStruct.getLast().humanpower.equals(databaseresult[i][7]))
							mActivityDataStruct.getLast().humanpower = mActivityDataStruct.getLast().humanpower+"\n"+databaseresult[i][7];
						if (!mActivityDataStruct.getLast().person.contains(databaseresult[i][8]))
							mActivityDataStruct.getLast().person = mActivityDataStruct.getLast().person+"\n"+databaseresult[i][8];
						if (!mActivityDataStruct.getLast().group.contains(databaseresult[i][9]))
							mActivityDataStruct.getLast().group = mActivityDataStruct.getLast().group+"\n"+databaseresult[i][9];
						mActivityDataStruct.getLast().serviceitem = databaseresult[i][10];
						mActivityDataStruct.getLast().groupccf = databaseresult[i][11];
						mActivityDataStruct.getLast().personccf = databaseresult[i][12];
					}
			}else {
				myActivityDataStruct.item = databaseresult[0][0];
				if (databaseresult[i][2].equals("") || databaseresult[i][4].equals("")){
					myActivityDataStruct.data = databaseresult[i][1]+"/";
				}else{
				myActivityDataStruct.data = getDateFormat (myStartDate,myEndDate);
				}
				myActivityDataStruct.time = getTimeFormat (databaseresult[0][5],databaseresult[0][6]);
				myActivityDataStruct.humanpower = databaseresult[0][7];
				myActivityDataStruct.person = databaseresult[0][8];
				myActivityDataStruct.group = databaseresult[0][9];
				myActivityDataStruct.serviceitem = databaseresult[0][10];
				myActivityDataStruct.groupccf = databaseresult[0][11];
				myActivityDataStruct.personccf = databaseresult[0][12];
				mActivityDataStruct.addFirst(myActivityDataStruct);
			}
		}
		/*for (int i =0 ; i <mActivityDataStruct.size(); i ++){
			System.out.println("Start print all =====>"+mActivityDataStruct.get(i).item);
			System.out.println(mActivityDataStruct.get(i).data);
			System.out.println(mActivityDataStruct.get(i).time);
			System.out.println(mActivityDataStruct.get(i).humanpower);
			System.out.println(mActivityDataStruct.get(i).person);
			System.out.println(mActivityDataStruct.get(i).group);
			System.out.println(mActivityDataStruct.get(i).serviceitem);
			System.out.println(mActivityDataStruct.get(i).groupccf);
			System.out.println("End print all ===>" + mActivityDataStruct.get(i).personccf);
		}*/
		return mActivityDataStruct;
	}
}
