package org.ccf.main;

import java.sql.SQLException;

import org.ccf.database.ActivityInfoTable;
import org.ccf.database.ActivitySearchAndRegisterInfo;
import org.ccf.database.ActivityTable;
import org.ccf.database.AwardTable;
import org.ccf.database.BrochureTable;
import org.ccf.database.ContactsInfo;
import org.ccf.database.DBFunctions;
import org.ccf.database.EmergencyContactTable;
import org.ccf.database.ExcelFileProcess;
import org.ccf.database.GroupTable;
import org.ccf.database.InsuranceInfo;
import org.ccf.database.MeetingTable;
import org.ccf.database.MeetingTimeTable;
import org.ccf.database.PersonalInfoTable;
import org.ccf.database.ServiceHoursAwardAndRegularInfo;
import org.ccf.database.ServiceHoursTable;
import org.ccf.database.ServiceItemIdTable;
import org.ccf.database.TakeLeaveTable;
import org.ccf.database.TeacherLeaderTable;
import org.ccf.database.TrainingTable;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class UiDbInterface {
	private ActivityInfoTable mActivityInfoTable = new ActivityInfoTable();
	private ActivityTable mActivityTable = new ActivityTable();
	private AwardTable mAwardTable = new AwardTable();
	private BrochureTable mBrochureTable = new BrochureTable();
	private EmergencyContactTable mEmergencyContactTable = new EmergencyContactTable();
	private GroupTable mGroupTable = new GroupTable();
	private MeetingTable mMeetingTable = new MeetingTable();
	private MeetingTimeTable mMeetingTimeTable = new MeetingTimeTable();
	private PersonalInfoTable mPersonalInfoTable = new PersonalInfoTable();
	private ServiceHoursTable mServiceHoursTable = new ServiceHoursTable();
	private ServiceItemIdTable mServiceItemIdTable = new ServiceItemIdTable();
	private TakeLeaveTable mTakeLeaveTable = new TakeLeaveTable();
	private TeacherLeaderTable mTeacherLeaderTable = new TeacherLeaderTable();
	private TrainingTable mTrainingTable = new TrainingTable();
	private ExcelFileProcess mExcelFileProcess = new ExcelFileProcess ();
	private ContactsInfo mContactsInfo = new ContactsInfo();
	private InsuranceInfo mInsuranceInfo = new InsuranceInfo();
	private ActivitySearchAndRegisterInfo mActivitySearchAndRegisterInfo = new ActivitySearchAndRegisterInfo();
	private ServiceHoursAwardAndRegularInfo mServiceHoursAwardAndRegularInfo = new ServiceHoursAwardAndRegularInfo();
	private  DBFunctions mDBFunctions = new DBFunctions();
	
	public final String[] dblist =
	          new String[] {
			  	"展愛隊分組 (Group)",
			    "隊員獲獎紀錄 (Award)",
			    "會議時數統計 (Meeting)",
	            "隊員受訓紀錄 (Training)",
	            "對員手冊統計 (Brochure)",
	            "歷屆活動紀錄 (Activity)",
	            "活動內容 (ActivityInfo)", 
	            "個人資料 (PersonalInfo)",
	            "開會出席紀錄 (TakeLeave)",
	            "會議時間紀錄 (MeetingTime)",
	            "服務時數統計 (ServiceHours)",
	            "歷屆指導老師 (TeacherLeader)",	
	            "服務項目代碼與名稱 (ServiceItemId)",        
	            "隊員緊急連絡人資料 (EmergencyContact)"};
	
   public int calculateTextSizeWidth (String filepath ,Text text){
	     int columns = filepath.length();
	     int width;
	     
	     GC gc = new GC (text);
	     FontMetrics fm = gc.getFontMetrics ();
	     width = columns * fm.getAverageCharWidth ();
	     gc.dispose ();
		
	     return width;
   }
  //Database ++ 
   public boolean exportSelectedDbToExcel (String dbname, String directory){
	   if (dbname.equals(dblist[0])){
		   return mGroupTable.exportGroupTableToExcel(directory);   
	   }else if (dbname.equals(dblist[1])){
		   return mAwardTable.exportAwardTableToExcel(directory);
	   }else if (dbname.equals(dblist[2])){
		   return mMeetingTable.exportMeetingTableToExcel(directory);
	   }else if (dbname.equals(dblist[3])){
		   return mTrainingTable.exportTrainingTableToExcel(directory);
	   }else if (dbname.equals(dblist[4])){
		   return mBrochureTable.exportBrochureTableToExcel(directory);
	   }else if (dbname.equals(dblist[5])){
		   return mActivityTable.exportActivityTableToExcel(directory);
	   }else if (dbname.equals(dblist[6])){
		   return mActivityInfoTable.exportActivityInfoTableToExcel(directory);
	   }else if (dbname.equals(dblist[7])){
		   return mPersonalInfoTable.exportPersonalInfoTableToExcel(directory);
	   }else if (dbname.equals(dblist[8])){
		   return mTakeLeaveTable.exportTakeLeaveTableToExcel(directory);
	   }else if (dbname.equals(dblist[9])){
		  return mMeetingTimeTable.exportMeetingTimeTableToExcel(directory);
	   }else if (dbname.equals(dblist[10])){
		   return mServiceHoursTable.exportServiceHoursTableToExcel(directory);
	   }else if (dbname.equals(dblist[11])){
		   return mTeacherLeaderTable.exportTeacherLeaderTableToExcel(directory);
	   }else if (dbname.equals(dblist[12])) {
		   return mServiceItemIdTable.exportServiceItemIdTableToExcel(directory);
	   }
	   	return mEmergencyContactTable.exportEmergencyContactTableToExcel(directory);
	   
   }
   
   public boolean compareExcelHeaderWithDB(String filename, String[] dbtable){
	   return mExcelFileProcess.compareExcelHeaderWithDB(filename, dbtable);
   }
   
   public boolean importSelectedFileToDatabase(String filepath, String dbname)throws SQLException{
	  
	   if (dbname.equals(dblist[0])){
		   return mGroupTable.importExcelToGroupTable(filepath);	   
	   }else if (dbname.equals(dblist[1])){
		   return mAwardTable.importExcelToAwardTable(filepath);
	   }else if (dbname.equals(dblist[2])){
		   return mMeetingTable.importExcelToMeetingTable(filepath);
	   }else if (dbname.equals(dblist[3])){
		   return mTrainingTable.importExcelToTrainingTable(filepath);
	   }else if (dbname.equals(dblist[4])){
		   return mBrochureTable.importExcelToBrochureTable(filepath);
	   }else if (dbname.equals(dblist[5])){
		   return mActivityTable.importExcelToActivityTable(filepath);
	   }else if (dbname.equals(dblist[6])){
		   return mActivityInfoTable.importExcelToActivityInfoTable(filepath);
	   }else if (dbname.equals(dblist[7])){
		   return mPersonalInfoTable.importExcelToPersonalInfoTable(filepath);
	   }else if (dbname.equals(dblist[8])){
		   return mTakeLeaveTable.importExcelToTakeLeaveTable(filepath);
	   }else if (dbname.equals(dblist[9])){
		  return mMeetingTimeTable.importExcelToMeetingTimeTable(filepath);
	   }else if (dbname.equals(dblist[10])){
		   return mServiceHoursTable.importExcelToServiceHourseTable(filepath);
	   }else if (dbname.equals(dblist[11])){
		   return mTeacherLeaderTable.importExcelToTeacherLeaderTable(filepath);
	   }else if (dbname.equals(dblist[12])) {
		   return mServiceItemIdTable.importExcelToServiceItemIdTable(filepath);
	   }
	   	return mEmergencyContactTable.importExcelToEmergencyContactTable(filepath);
	   
    }
  
   public String[] getDBTableHeader(String selectedTable) {
	    
	   if (selectedTable.equals(dblist[0])){
		   return mGroupTable.groupHeader;
	   }else if (selectedTable.equals(dblist[1])){
		   return mAwardTable.awardHeader;
	   }else if (selectedTable.equals(dblist[2])){
		   return mMeetingTable.meetingHeader;
	   }else if (selectedTable.equals(dblist[3])){
		   return mTrainingTable.trainingHeader;
	   }else if (selectedTable.equals(dblist[4])){
		   return mBrochureTable.brochureHeader;
	   }else if (selectedTable.equals(dblist[5])){
		   return mActivityTable.activityHeader;
	   }else if (selectedTable.equals(dblist[6])){
		   return mActivityInfoTable.activityInfoHeader;
	   }else if (selectedTable.equals(dblist[7])){
		   return mPersonalInfoTable.personalInfoHeader;
	   }else if (selectedTable.equals(dblist[8])){
		   return mTakeLeaveTable.takeLeaveHeader;
	   }else if (selectedTable.equals(dblist[9])){
		   return mMeetingTimeTable.meetingTimeHeader;
	   }else if (selectedTable.equals(dblist[10])){
		   return mServiceHoursTable.serviceHoursHeader;
	   }else if (selectedTable.equals(dblist[11])){
		   return mTeacherLeaderTable.teacherLeaderHeader;
	   }else if (selectedTable.equals(dblist[12])){
		   return mServiceItemIdTable.serviceItemIdHeader;
	   }
		return mEmergencyContactTable.emergencyContactHeader;	   
   }
   
   public String [][] queryAllDataFromDB(String dbname) throws SQLException {
    	 if (dbname.equals(dblist[0])){
  		   return mGroupTable.queryAllFromGroupTable();
  	   }else if (dbname.equals(dblist[1])){
  		   return mAwardTable.queryAllFromAwardTable();
  	   }else if (dbname.equals(dblist[2])){
  		   return mMeetingTable.queryAllFromMeetingTable();
  	   }else if (dbname.equals(dblist[3])){
  		   return mTrainingTable.queryAllFromTrainingTable();
  	   }else if (dbname.equals(dblist[4])){
  		   return mBrochureTable.queryAllFromBrochureTable();
  	   }else if (dbname.equals(dblist[5])){
  		   return mActivityTable.queryAllFromActivityTable();
  	   }else if (dbname.equals(dblist[6])){
  		   return mActivityInfoTable.queryAllFromActivityInfoTable();
  	   }else if (dbname.equals(dblist[7])){
  		   return mPersonalInfoTable.queryAllFromPersonalInfoTable();
  	   }else if (dbname.equals(dblist[8])){
  		   return mTakeLeaveTable.queryAllFromTakeLeaveTable();
  	   }else if (dbname.equals(dblist[9])){
  		  return mMeetingTimeTable.queryAllFromMeetingTimeTable();
  	   }else if (dbname.equals(dblist[10])){
  		   return mServiceHoursTable.queryAllFromServiceHoursTable();
  	   }else if (dbname.equals(dblist[11])){
  		   return mTeacherLeaderTable.queryAllFromTeacherLeaderTable();
  	   }else if (dbname.equals(dblist[12])){
  		   return mServiceItemIdTable.queryAllFromServiceItemIdTable();
  	   }
  	   	return mEmergencyContactTable.queryAllFromEmergencyContactTable();
    }
   //Database --
    public boolean createDatabase() {
    	return mDBFunctions.createDatabase();
    }
    public boolean checkDatabaseTable() {
    	return mDBFunctions.checkDatabaseTable();
    }
	public String getTableNotExists() {
		return mDBFunctions.getTableNotExists();
	}
    public String[] getGroupYears() throws SQLException{
    	return mGroupTable.queryYearFromGroupTable();
    }
    public String[] getGroupMemberName() throws SQLException{
    	return mGroupTable.queryNameFromGroupTable();
    }
    public String[] getActivityYears() throws SQLException{
    	return mActivityTable.queryYearFromActivityTable();
    }
    //Contact info ++
    public String[][] getContactInfoNoAddress(String years, boolean allmembers) throws SQLException {
    	return mContactsInfo.getContactInfoNoAddressByYear(years, allmembers);
    }
    public String[] getContactInfoNoAddressHeader(){
    	return mContactsInfo.getContactInfoNoAddressHeader();
    }
    public boolean exportContactInfoNoAddress (String directory, String years, boolean allmembers){
    	return mContactsInfo.exportContactInfoNoAddressToExcel(directory,years,allmembers);
    }
    public String[][]getContactInfoAddress(String years, boolean allmembers) throws SQLException {
    	return mContactsInfo.getContactInfoAddressByYear(years, allmembers);
    }
    public String[] getContactInfoAddressHeader(){
    	return mContactsInfo.getContactInfoAddressHeader();
    }
    public boolean exportContactInfoAddress (String directory, String years, boolean allmembers){
    	return mContactsInfo.exportContactInfoAddressToExcel(directory,years,allmembers);
    }
    //Contact info --
    
    //Insurance info ++
    public String[] getInsuranceInfoHeader(){
    	return mInsuranceInfo.getInsuranceInfoHeader();
    }
    public String[][] getInsuranceInfo(String year ,boolean allmembers) throws SQLException{
    	return mInsuranceInfo.getInsuranceData(year,allmembers);
    }
    public boolean exportInsuracenInfoName(String directory, String name) {
    	return mInsuranceInfo.exportInsuranceDataNameToExcel(directory,name);
    }
    public boolean exportInsuracenInfoYear (String directory, String year, boolean members){
    	return mInsuranceInfo.exportInsuranceDataYearToExcel(directory,year,members);
    }
    public String[][] getInsuranceInfoByName(String name) throws SQLException{
    	return mInsuranceInfo.getInsuranceDataByName(name);
    }
    //Insurance info -- 
    
    //Activity info ++
    public String[] getActivityRegisterHeader(){
    	return mActivitySearchAndRegisterInfo.getActivityRegisterHeader();
    }
    public String[][] getActivityRegisterDataByYear(String year) throws SQLException {
    	
		return mActivitySearchAndRegisterInfo.getActivityRegisterDataByYear(year);
		
    }
    public String[][] getActivityRegisterDataByYearAndMonth(String year, String month) throws SQLException{
    	return mActivitySearchAndRegisterInfo.getActivityRegisterDataByYearandMonth(year,month);
    }
    public boolean exportActivityRegisterByAllYear (String directory, String year) {
    	return mActivitySearchAndRegisterInfo.exportActivityRegisterToExcelByYear(directory, year);
    }
    public boolean exportActivityRegisterToExcelByYearAndMonth (String directory, String year, String month){
    	return mActivitySearchAndRegisterInfo.exportActivityRegisterToExcelByYearAndMonth(directory,year,month);
    }
    public String[] getActivityDataHeader(){
    	return mActivitySearchAndRegisterInfo.getActivityDataHeader();
    }
    public String[][] getActivityDataByAllYear(String year) throws SQLException{
    	return mActivitySearchAndRegisterInfo.getActivityDataByYear(year);
    }
    public String[][] getActivityDataByYearAndMonth(String year, String month) {
    	return mActivitySearchAndRegisterInfo.getActivityDataByYearAndMonth(year,month);
    }
    public boolean exportActivityDataToExcelByYearAndMonth(String directory, String year, String month) {
    	return mActivitySearchAndRegisterInfo.exportActivityDataToExcelByYearAndMonth(directory, year, month);
    }
    public boolean exportActivityDataToExcelByAllYear(String directory, String year) {
    	return mActivitySearchAndRegisterInfo.exportActivityDataToExcelByYear(directory,year);
    }
    //Activity info --
    
    //Service hours award and regular info ++
    public String[] getServiceHoursAwardHeader () {
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursAwardHeader();
    }
    
    public String[][] getServiceHoursAwardData(String group_year, String search_year) throws SQLException{
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursAwardData(group_year,Integer.valueOf(search_year));
    }
    
    public String[] getServiceHoursAwardPersonalDetailHeader(){
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursAwardPersonalDetailHeader();
    }
    public String[][] getServiceHoursAwardPersonalDetail(String seach_name, String search_year) throws SQLException {
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursAwardPersonalDetail(seach_name,Integer.valueOf(search_year));
    }
    public boolean exportSerivceHoursAwardData (String directory,String group_year, String search_year) {
    	return mServiceHoursAwardAndRegularInfo.exportServiceHoursAwardData(directory,group_year,Integer.valueOf(search_year)); 	
    }
    public String[] getServiceHoursRegularHeader (){
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularHeader();
    }
    public String[] getServiceHoursRegularAllHoursSortHeader(){
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularAllHoursSortHeader();
    }
    public String[] getServiceHoursRegularServiceHoursSortHeader(){
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularServiceHoursSortHeader();
    }
    public String[] getServiceHoursRegularPersonalDetailHeader(){
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularPersonalDetailHeader();
    }
    public String[][] getServiceHoursRegularData(String groupyear, String searchyear) throws SQLException{
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularData(groupyear, Integer.valueOf(searchyear));
    }
    public String[][] getServiceHoursRegularAllHoursSortData(String groupyear, String searchyear) throws SQLException{
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularAllHoursSortData(groupyear, Integer.valueOf(searchyear));
    }
    public String[][] getServiceHoursRegularServiceHoursSortData (String groupyear, String searchyear) throws SQLException{
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularServiceHoursSortData(groupyear, Integer.valueOf(searchyear));
    }
    public String[][] getServiceHoursRegularPersonalDetailData (String searchyear, String searchname) throws SQLException{
    	return mServiceHoursAwardAndRegularInfo.getServiceHoursRegularPersonalDetailData(Integer.valueOf(searchyear),searchname);
    }
    public boolean exportServiceHoursRegularData (String directory,String group_year, String search_year) {
    	return mServiceHoursAwardAndRegularInfo.exportServiceHoursRegularData(directory,group_year,Integer.valueOf(search_year));
    }
    //Service hours award and regular info ++
}
