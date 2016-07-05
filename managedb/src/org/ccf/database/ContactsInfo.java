package org.ccf.database;

import java.sql.SQLException;

public class ContactsInfo {
	public String[] contactInfoNoAddressHeader =  {"組別","年度","姓名","行動電話","可聯絡時間","Email"};
	public String[] contactInfoAddressHeader =  {"組別","年度","姓名","行動電話","可聯絡時間","Email","地址"};
	private String contactInfoNoAddressSQL = "select A1.組別,A1.年度,A2.姓名, A2.行動電話,A2.可聯絡時間,A2.Email from mgroup A1, personalinfo A2 where A1.姓名 = A2.姓名 and A1.年度  = ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY A1.組別 DESC";
	private String contactInfoNoAddressAllSQL = "select A1.組別,A1.年度,A2.姓名, A2.行動電話,A2.可聯絡時間,A2.Email from mgroup A1, personalinfo A2 where A1.姓名 = A2.姓名 and A1.年度  = ? ORDER BY A1.組別 DESC";
	private String contactInfoAddressSQL = "select A1.組別,A1.年度,A2.姓名, A2.行動電話,A2.可聯絡時間,A2.Email,A2.地址  from mgroup A1, personalinfo A2 where A1.姓名 = A2.姓名 and A1.年度  = ? and (A1.組別 != '退隊') and (A1.組別 != '展友') ORDER BY A1.組別 DESC";
	private String contactInfoAddressAllSQL = "select A1.組別,A1.年度,A2.姓名, A2.行動電話,A2.可聯絡時間,A2.Email,A2.地址  from mgroup A1, personalinfo A2 where A1.姓名 = A2.姓名 and A1.年度  = ? ORDER BY A1.組別 DESC";
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess contactsinfo_xsl = new ExcelFileProcess();
	
	
	public String [][] getContactInfoNoAddressByYear(String years, boolean all)throws SQLException 
	{
		
		String[][] data = null;
		
		try{ 
			 if(db.con == null)
			 {
				db.createConnection();
			 }
			 if (all == true)
				 db.pst = db.con.prepareStatement(contactInfoNoAddressAllSQL);
			 else 
				 db.pst = db.con.prepareStatement(contactInfoNoAddressSQL);
			 
			 db.pst.setString(1, years);   
			 db.rs = db.pst.executeQuery();

		  	int rows;
			if (!db.rs.last()){
				System.out.println("[ContactsInfo_getContactInfoNoAddressByYear]: No data.");
			}
			rows = db.rs.getRow();
			//System.out.println("[ContactsInfo_getContactInfoNoAddressByYear]row:"+rows);
			db.rs.beforeFirst();
			
			int cols = db.rs.getMetaData().getColumnCount();
			//System.out.println("cols:"+cols);
			data = new String[rows][cols];
			for (int i=0; i<rows; i++){
				db.rs.next();
			for (int j = 0; j<cols; j++){
				data[i][j] = db.rs.getString(j+1);
				//System.out.println("[ContactsInfo_getContactInfoNoAddressByYear]Get data:"+data[i][j].toString());
			}
		  } 
		    }catch(SQLException e){ 
		     // System.out.println("[ContactsInfo_getContactInfoNoAddressByYear] "+tableName+""+"Exception :" + e.toString());
		      return data;
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
		return data;	
	}
	
	public String [][] getContactInfoAddressByYear(String years, boolean all)throws SQLException 
	{
		
		String[][] data = null;
		
		try{ 
			 if(db.con == null)
			 {
				db.createConnection();
			 }
			 if (all == true)
				 db.pst = db.con.prepareStatement(contactInfoAddressAllSQL);
			 else 
				 db.pst = db.con.prepareStatement(contactInfoAddressSQL);
			 
			 db.pst.setString(1, years);   
			 db.rs = db.pst.executeQuery();

		  	int rows;
			if (!db.rs.last()){
				//System.out.println("[ContactsInfo_getContactInfoAddressByYear]: No data.");
			}
			rows = db.rs.getRow();
			//System.out.println("[ContactsInfo_getContactInfoAddressByYear]row:"+rows);
			db.rs.beforeFirst();
			
			int cols = db.rs.getMetaData().getColumnCount();
			//System.out.println("cols:"+cols);
			data = new String[rows][cols];
			for (int i=0; i<rows; i++){
				db.rs.next();
			for (int j = 0; j<cols; j++){
				data[i][j] = db.rs.getString(j+1);
				//System.out.println("[ContactsInfo_getContactInfoNoAddressByYear]Get data:"+data[i][j].toString());
			}
		  }
		    }catch(SQLException e){ 
		     // System.out.println("[ContactsInfo_getContactInfoNoAddressByYear] "+tableName+""+"Exception :" + e.toString());
		      return data;
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
		return data;	
	}
	
	
	public String[][] getContactInfoNoAddress(){
	
			String [][]data = null;
			try {
				 data = db.queryAllFromTable(contactInfoNoAddressSQL);
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
			return data;
		
	}
	public String[] getContactInfoNoAddressHeader(){
		return contactInfoNoAddressHeader;
	}
	
	public String[][] getContactInfoAddress(){
		
		String [][]data = null;
		try {
			 data = db.queryAllFromTable(contactInfoAddressSQL);
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
		return data;
	
	}
	
	public String[] getContactInfoAddressHeader(){
		return contactInfoAddressHeader;
	}
	
	public boolean exportContactInfoNoAddressToExcel(String directory,String years, boolean allmembers){
		boolean result = false ;
		try {
			result = contactsinfo_xsl.exportToExcel(getContactInfoNoAddressByYear(years,allmembers),getContactInfoNoAddressHeader(),directory,"通訊錄(不含地址)","通訊錄(不含地址)");
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean exportContactInfoAddressToExcel(String directory, String years, boolean allmembers){
		boolean result = false ;
		
		try {
			result = contactsinfo_xsl.exportToExcel(getContactInfoAddressByYear(years,allmembers),getContactInfoAddressHeader(),directory,"通訊錄(含地址)","通訊錄(含地址)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
