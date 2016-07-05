package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ActivityDataSearchDialog extends DialogCombo{
	private static String title = "活動內容資料查詢";
	private static String iconpath = "img\\activity_data_icon_32x32.png";
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
						MainUI.setAllDataToTable1(mUiDbInterface.getActivityDataHeader(),mUiDbInterface.getActivityDataByAllYear(mDBListComboYear.getText()));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					MainUI.setAllDataToTable1(mUiDbInterface.getActivityDataHeader(),mUiDbInterface.getActivityDataByYearAndMonth(mDBListComboYear.getText(),month));
				}
				mDialog.close();
			} //OK
		 }
      }; //listener 
	
	private String[] getComboYear() throws SQLException{
		return mUiDbInterface.getActivityYears();
	}
	
	public ActivityDataSearchDialog(Display display) throws SQLException{
	super (display,title,iconpath);		
	  DialogCombo();
	  mComboYearList = getComboYear();
	  createDialogYearDropList(mDialog);
	  createDialogMonthDropList(mDialog);
	  createDialogButton(mDialog);
	  mOkButton.addListener(SWT.Selection, mListener);
      mCancelButton.addListener(SWT.Selection, mListener);
	  mDialog.pack();
      mDialog.open();
	
	}
}
