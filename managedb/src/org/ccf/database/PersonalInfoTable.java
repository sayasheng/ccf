package org.ccf.database;

public class PersonalInfoTable {
	/* PersonalInfo table title description
	 * NO: NO
	 * NAME:姓名
	 * BIRTH_YEAR:出生年
	 * BIRTH_MONTH:出生月
	 * BIRTH_DAY:出生日
	 * ID:身分證號
	 * HOME_PHONE:家裡電話
	 * COMP_PHONE:公司電話
	 * CELL_PHONE:行動電話
	 * ADDRESS:地址
	 * EMAIL:EMAIL
	 * SKILL:專長
	 * JOIN_YEAR:加入年
	 * JOIN_MONTH:加入月
	 * QUALIFIED_DAY:授證時間
	 * CONTACT_TIME:可連絡時間
	 */
	private String personalInfo = "CREATE TABLE personalinfo (" + 
    "    NO  VARCHAR(10) " +
    "  , NAME     VARCHAR(50) " + 
    "  , BIRTH_YEAR  VARCHAR(30) " +
    "  , BIRTH_MONTH  VARCHAR(30) " +
    "  , BIRTH_DAY  VARCHAR(30) " +
    "  , ID  VARCHAR(30) " +
    "  , HOME_PHONE VARCHAR(50) " +
    "  , COMP_PHONE VARCHAR(50) " +
    "  , CELL_PHONE VARCHAR(50) " +
    "  , ADDRESS TEXT " +
    "  , EMAIL TEXT"+ 
    "  , SKILL TEXT" +
    "  , JOIN_YEAR VARCHAR(30) "+ 
    "  , JOIN_MONTH VARCHAR(30) " +
    "  , CONTACT_TIME VARCHAR(30)" +
    "  , QUALIFIED_DAY VARCHAR(30)" +
    "  , PRIMARY KEY (NO))";
	
	DBFunctions db = new DBFunctions();
	
	public void createPersonalInfoTable(){
		db.createTable(personalInfo);
	}
	
}
