package org.ccf.database;

import java.sql.SQLException;

public class TeacherLeaderTable {
	public String[] teacherLeaderHeader = {"期數","起始年","起始月","起始日",
										   "結束年","結束月","結束日","老師","隊長",
										   "副隊長","組長","司庫","顧問"};
	private String teacherLeaderTableName = "teacherleader";
	private String teacherLeaderCreateTableSql ="CREATE TABLE teacherleader (" + 
	"	 期數	VARCHAR(30)" +
	"  , 起始年	VARCHAR(30)" +
	"  , 起始月	VARCHAR(30)" +
	"  , 起始日	VARCHAR(30)" +
	"  , 結束年	VARCHAR(30)" +
	"  , 結束月	VARCHAR(30)" +
	"  , 結束日	VARCHAR(30)" +
	"  , 老師	VARCHAR(50)" +
	"  , 隊長	VARCHAR(50)" +
	"  , 副隊長 	VARCHAR(50)" +
	"  , 組長	VARCHAR(50)" +
	"  , 司庫	VARCHAR(50)" +
	"  , 顧問	VARCHAR(50))";
	private String insertAllDataSQL = "insert into teacherleader"
			+ "(期數,起始年,起始月,起始日,結束年,結束月,結束日,老師,隊長,副隊長,組長,司庫,顧問) values" 
		    + "(?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	
	private DBFunctions db = new DBFunctions();
	private ExcelFileProcess teacherleader_xsl = new ExcelFileProcess();
	private String selectAllDataSQL= "select * from teacherleader";
	
	
	public void createTeacherLeaderTable(){
		db.createTable(teacherLeaderCreateTableSql);
	}
	public void dropTeacherLeaderTable(){
		db.dropTable(teacherLeaderTableName);
	}
	
	public void insertAllDataIntoTeacherLeaderTable(String number,String startYear, String startMonth, String startDay,
		   	String endYear, String endMonth, String endDay, String teacher, String teamleader, String deputy, String groupleader,
		   	String finance, String consultant) throws SQLException
{
	if (db.con == null)
	{
		db.createConnection();
	} 
	try{
		db.pst = db.con.prepareStatement(insertAllDataSQL);
		db.pst.setString(1, number); 
		db.pst.setString(2, startYear);
		db.pst.setString(3, startMonth);
		db.pst.setString(4, startDay);
		db.pst.setString(5, endYear);
		db.pst.setString(6, endMonth);
		db.pst.setString(7, endDay);
		db.pst.setString(8, teacher);
		db.pst.setString(9, teamleader);
		db.pst.setString(10, deputy);
		db.pst.setString(11, groupleader);
		db.pst.setString(12, finance);
		db.pst.setString(13, consultant);
		db.pst.executeUpdate(); 
    }catch(SQLException e){ 
      System.out.println("insertDataTeacherLeaderTable Exception :" + e.toString()); 
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
	
	public boolean importExcelToTeacherLeaderTable(String filepath) throws SQLException{
		Object [][]data = null;
		String [] myData = new String[teacherLeaderHeader.length];
	    boolean isFinished = false ;
		//1. Drop the table is the table exists.
		dropTeacherLeaderTable();
		
		//2. Create a table after dropping. 
		createTeacherLeaderTable();
		
		//3. Start to import excel data into database.
		 data = teacherleader_xsl.importFromExcel(filepath);
		 //3-A. Since the first row is column header,we ignore the first row
		 for (int i=1; i < data.length ;i++){
		    	for (int j=0; j<teacherLeaderHeader.length ; j++){
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = data[i][j].toString();
		    		//3-C. Write one row of data into database
		    		if (j== (teacherLeaderHeader.length-1)){
		    			insertAllDataIntoTeacherLeaderTable(myData[0],myData[1]
		    					,myData[2],myData[3],myData[4],myData[5],myData[6]
		    					,myData[7],myData[8],myData[9],myData[10],myData[11]
		    				    ,myData[12]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[teacherLeaderHeader.length];
		    		}
		    	}
		  isFinished = true;
		 }
		return isFinished;
	}
	
	public boolean  exportTeacherLeaderTableToExcel(String directory){
		boolean result = false ;
		try {
			result = teacherleader_xsl.exportToExcel(teacherLeaderTableName,teacherLeaderHeader,directory,"TeacherLeaderTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public String[][] queryAllFromTeacherLeaderTable() throws SQLException
	{
		return db.queryAllFromTable (selectAllDataSQL,teacherLeaderTableName);
	}
	
}
