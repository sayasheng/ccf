package org.ccf.main;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ActivityRegisterSearchDialog extends DialogCombo{
	private static String title = "活動報名資料查詢";
	private static String iconpath = "img\\activity_register_icon_32x32.png";
	private static UiDbInterface mUiDbInterface = new UiDbInterface();
	private String month;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		   if(event.widget == mCancelButton){
				mDialog.close();
			} //Cancel
			else if(event.widget == mOkButton) {
				month = mDBListComboMonth.getText();
				if (month.equals("全部月份")) {
				try {
					MainUI.setAllDataToTable1(mUiDbInterface.getActivityRegisterHeader(),mUiDbInterface.getActivityRegisterDataByYear(mDBListComboYear.getText()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}else{
					try {
						MainUI.setAllDataToTable1(mUiDbInterface.getActivityRegisterHeader(),mUiDbInterface.getActivityRegisterDataByYearAndMonth(mDBListComboYear.getText(),month));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				/*SimpleDateFormat sdf = new SimpleDateFormat("MM/dd (E)");
				DateFormat df = DateFormat.getDateInstance();
				Date date = null;
				try {
					date = df.parse("2016/1/28");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int day = calendar.get(calendar.DAY_OF_WEEK);
				switch (day) {
			    case Calendar.THURSDAY:
			      System.out.println("(四)");
			      break;
			    case Calendar.TUESDAY:
			      System.out.println(Calendar.TUESDAY);
			      break;
			    default:  
			      System.out.println("others");
			    }
				System.out.println("Ashley get day is-===>" + day);
				System.out.println(sdf.format(calendar.getTime()));*/
				mDialog.close();
			} //OK
		 }
      }; //listener 
	
	private String[] getComboYear() throws SQLException{
		return mUiDbInterface.getActivityYears();
	}
	
	public ActivityRegisterSearchDialog(Display display) throws SQLException{
	super (display,title,iconpath);		
	  DialogCombo();
	  mComboYearList = getComboYear();
	  createDialogYearDropList(mDialog);
	  createDialogMonthDropList(mDialog);
	  createDialogButton(mDialog);
	  mDialog.pack();
      mDialog.open();
	  mOkButton.addListener(SWT.Selection, mListener);
      mCancelButton.addListener(SWT.Selection, mListener);
    
	}
}
