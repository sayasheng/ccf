package org.ccf.database;

import java.sql.SQLException;

public class PersonalInfoTable {
	public String[] personalInfoHeader= {"No","姓名","出生年","出生月","出生日","身分證號","家裡電話",
										 "公司電話","行動電話","地址","Email","專長","加入年",
										 "加入月","LeftDuration","授證時間","可聯絡時間","教育程度","職業別",
										 "種族","性別"};
	private String personalInfoTableName = "personalinfo";													
	private String personalInfoCreateTableSql = "CREATE TABLE personalinfo (" + 
    "    NO 	VARCHAR(10) " +
    "  , 姓名     	VARCHAR(50) " + 
    "  , 出生年  	VARCHAR(30) " +
    "  , 出生月  	VARCHAR(30) " +
    "  , 出生日  	VARCHAR(30) " +
    "  , 身分證號  	VARCHAR(30) " +
    "  , 家裡電話 	VARCHAR(50) " +
    "  , 公司電話 	VARCHAR(50) " +
    "  , 行動電話 	VARCHAR(50) " +
    "  , 地址 	TEXT " +
    "  , EMAIL 	TEXT"+ 
    "  , 專長 	TEXT" +
    "  , 加入年 	VARCHAR(30) "+ 
    "  , 加入月 	VARCHAR(30) " +
    "  , LeftDuration VARCHAR(30) "+
    "  , 授證時間	VARCHAR(30)" +
    "  , 可聯絡時間 	VARCHAR(30)" +
    "  , 教育程度	VARCHAR(30)" +
    "  , 職業別		VARCHAR(30)" +
    "  , 種族	VARCHAR(30)" +
    "  , 性別 	VARCHAR (10)" +
    "  , PRIMARY KEY (NO))";
	
	private String insertAllDataSQL = "insert into personalinfo"
			+ "(No,姓名,出生年,出生月,出生日,身分證號,家裡電話,公司電話,行動電話,地址,Email,專長,加入年,加入月,"
			+ "LeftDuration,授證時間,可聯絡時間,教育程度,職業別,種族,性別) values" 
		    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
    private DBFunctions db = new DBFunctions();
	private ExcelFileProcess personalinfo_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from personalinfo ORDER BY (0+NO)";
	
	public void createPersonalInfoTable(){
		db.createTable(personalInfoCreateTableSql);
	}
	public void dropPersonalInfoTable(){
		db.dropTable(personalInfoTableName);
	}
	

	public void insertAllDataIntoPersonalInfoTable(String no,String name, String byear, String bmonth, String bday, String id,
			String homephone,String officephone,String cell,String address,String email,String expertise,
			String jyear,String jmonth,String leftduration,String certime,String contacttime,String education,
			String job,String race,String gender) throws SQLException
    {
	if (db.con == null)
	{
		db.createConnection();
	} 
	try{
		db.pst = db.con.prepareStatement(insertAllDataSQL);
		db.pst.setString(1, no); 
		db.pst.setString(2, name); 
		db.pst.setString(3, byear);
		db.pst.setString(4, bmonth);
		db.pst.setString(5, bday);
		db.pst.setString(6, id);
		db.pst.setString(7, homephone);
		db.pst.setString(8, officephone);
		db.pst.setString(9, cell);
		db.pst.setString(10, address);
		db.pst.setString(11, email);
		db.pst.setString(12, expertise);
		db.pst.setString(13, jyear);
		db.pst.setString(14, jmonth);
		db.pst.setString(15, leftduration);
		db.pst.setString(16, certime);
		db.pst.setString(17, contacttime);
		db.pst.setString(18, education);
		db.pst.setString(19, job);
		db.pst.setString(20, race);
		db.pst.setString(21, gender);
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataPersonalInfoTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToPersonalInfoTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[personalInfoHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropPersonalInfoTable();
		
		//2. Create a table after dropping. 
		createPersonalInfoTable();
		
		//3. Start to import excel data into database.
		 data = personalinfo_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<personalInfoHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (personalInfoHeader.length-1)){
		    			insertAllDataIntoPersonalInfoTable(myData[0],myData[1],myData[2],myData[3],myData[4],
		    					myData[5],myData[6],myData[7],myData[8],myData[9],
		    					myData[10],myData[11],myData[12],myData[13],myData[14],
		    					myData[15],myData[16],myData[17],myData[18],myData[19],
		    					myData[20]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[personalInfoHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	public boolean exportPersonalInfoTableToExcel(String directory){
		boolean result = false ;
		try {
			result = personalinfo_xsl.exportToExcel(personalInfoTableName,personalInfoHeader,directory,"PersonalInfoTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromPersonalInfoTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,personalInfoTableName);
	}
	
}
