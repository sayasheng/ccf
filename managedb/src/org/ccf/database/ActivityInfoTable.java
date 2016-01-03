package org.ccf.database;

public class ActivityInfoTable {
	/* ActivityInfo table title description
	 * ACTIVITY:活動
	 * SERVICE_INFO:服務內容
	 * TEAM_IN_CHARGE:中心負責組別
	 * PERSON_IN_CHARGE:中心負責社工
	 * PEOPLE_REQUIRE:人力
	 * ACTIVITY_YEAR:年
	 * AM:上午
	 * PM:下午
	 */
	private String activityInfo ="CREATE TABLE activityinfo (" + 
	"	 ACTIVITY TEXT" +
	"  , SERVICE_INFO TEXT" +
	"  , TEAM_IN_CHARGE VARCHAR(50)" +
	"  , PERSON_IN_CHARGE VARCHAR(50)" +
	"  , PEOPLE_REQUIRE VARCHAR(30)" +
	"  , ACTIVITY_YEAR VARCHAR(30)" +
	"  , AM VARCHAR(30)" +
	"  , PM VARCHAR(30))";
	
	DBFunctions db = new DBFunctions();
	
	public void createActivityInfoTable(){
		db.createTable(activityInfo);
	}
}
