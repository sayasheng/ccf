package org.ccf.database;

import java.sql.SQLException;

public class InsuranceInfo {
	private String[] insuranceInfoHeader =  {"組別","年度","姓名","出生年","身分證號","家裡電話","公司電話","行動電話","地址","Email"};
	private String insuranceInfoWithoutLeaveMemberSQL = "select A1.組別,A1.年度,A2.姓名,A2.出生年,A2.身分證號,A2.家裡電話, A2.公司電話,A2.行動電話,A2.地址,A2.Email from mgroup A1, personalinfo A2 where A1.年度 = ? and A1.姓名 = A2.姓名 and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY A1.組別 DESC";
	private String insuranceInfoAllMembersSQL = "select A1.組別,A1.年度,A2.姓名,A2.出生年,A2.身分證號,A2.家裡電話, A2.公司電話,A2.行動電話,A2.地址,A2.Email from mgroup A1, personalinfo A2 where A1.年度 = ? and A1.姓名 = A2.姓名  ORDER BY A1.組別 DESC";
	private String insuranceInfoSelectedByNameAndYearSQL = "select A1.組別,A1.年度,A2.姓名,A2.出生年,A2.身分證號,A2.家裡電話, A2.公司電話,A2.行動電話,A2.地址,A2.Email from mgroup A1, personalinfo A2 where A1.姓名= ? and A1.姓名 = A2.姓名  ORDER BY A1.組別 DESC";
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess insuranceinfo_xsl = new ExcelFileProcess();
	
	public String[] getInsuranceInfoHeader(){
		return insuranceInfoHeader;
	}
	
	/*public String[][] getNameOfAllMembers(){
		String [][]data = null;
		try {
			 data = db.queryAllFromTable(insuranceInfoNameOfAllMembersSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("get data"+data.length);
		for (int i=0; i < data.length ;i++){
			for (int j=0 ; j<insuranceInfoHeader.length; j++){
			System.out.println(data[i][j]);
			}
		}
		return data;
	
	}*/
	
/*	public String[][] getInsuranceData(){
		
		String [][]data = null;
		try {
			 data = db.queryAllFromTable(insuranceInfoWithoutLeaveMemberSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("get data"+data.length);
		/*for (int i=0; i < data.length ;i++){
			for (int j=0 ; j<contactInfoNoAddressHeader.length; j++){
			System.out.println(data[i][j]);
			}
		}*/
		//return data;
	
	//}
	public boolean exportInsuranceDataNameToExcel(String directory, String name){
		boolean result = false ;
		try {
			result = insuranceinfo_xsl.exportToExcel(getInsuranceDataByName(name),getInsuranceInfoHeader(),directory,"保險資料(個人姓名)","保險資料(個人姓名)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean exportInsuranceDataYearToExcel(String directory, String year, boolean members){
		boolean result = false ;
		try {
			result = insuranceinfo_xsl.exportToExcel(getInsuranceData(year,members),getInsuranceInfoHeader(),directory,"保險資料(年度)","保險資料(年度)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String[][] getInsuranceData(String year, boolean allmembers) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			    if (allmembers == true)
			    	db.pst = db.con.prepareStatement(insuranceInfoAllMembersSQL);
			    else 
			    	db.pst = db.con.prepareStatement(insuranceInfoWithoutLeaveMemberSQL);
			    
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
					System.out.println("[InsuranceInfo]getInsuranceData: No data.");
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
					data[i][j] = db.rs.getString(j+1);
					//System.out.println("[InsuranceInfo]Get data:"+data[i][j].toString());
				}
			}
			return data;
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
	
	public String[][] getInsuranceDataByName(String name) throws SQLException
	{
		
		try{ 
			if(db.con == null)
			{
				db.createConnection();
			}
			   
			    db.pst = db.con.prepareStatement(insuranceInfoSelectedByNameAndYearSQL);
			    
				db.pst.setString(1, name);
			//	db.pst.setString(2, year);
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
					System.out.println("[InsuranceInfo]getInsuranceData: No data.");
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
					data[i][j] = db.rs.getString(j+1);
					//System.out.println("[InsuranceInfo]Get data:"+data[i][j].toString());
				}
			}
			return data;
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
	 
	
}
