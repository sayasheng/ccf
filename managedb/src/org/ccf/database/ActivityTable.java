package org.ccf.database;

public class ActivityTable {
	/* Activity table title description
	 * ACTIVITY:活動
	 * PERSON_IN_CHARGE:活動負責人
	 * START_YEAR:起始年
	 * START_MONTH:起始月
	 * START_DAY:起始日
	 * START_TIME:起始時間
	 * END_YEAR:結束年
	 * END_MONTH:結束月
	 * END_DAY:結束日
	 * END_TIME:結束時間
	 */
	private String activity ="CREATE TABLE activity (" + 
	"	 ACTIVITY TEXT" +
	"  , PERSON_IN_CHARGE VARCHAR(50)" +
	"  , START_YEAR VARCHAR(30)" +
	"  , START_MONTH VARCHAR(30)" +
	"  , START_DAY VARCHAR(30)" +
	"  , START_TIME VARCHAR(30)" +
	"  , END_YEAR VARCHAR(30)" +
	"  , END_MONTH VARCHAR(30)" +
	"  , END_DAY VARCHAR(30)" +
	"  , END_TIME VARCHAR(30))";
	
DBFunctions db = new DBFunctions();
	
	public void createActivityTable(){
		db.createTable(activity);
	}
}
