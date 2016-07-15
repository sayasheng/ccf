package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ActivityDataSearchDialog extends DialogCombo{
	private static String title = "活動內容資料查詢";
	private static String iconpath = "img/activity_data_icon_32x32.png";
	private UiDbInterface mUiDbInterface = new UiDbInterface();
	private String month;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		   if(event.widget == mCancelButton){
				mDialog.close();
			} //Cancel
			else if(event.widget == mOkButton) {
				MainUI.resetAllTexts();
				MainUI.setSearchFormText("活動內容資料");
				MainUI.setSearchYearText(mDBListComboYear.getText());
				month = mDBListComboMonth.getText();
				MainUI.setSearchMonthText(month);
				
				if (month.equals("全部月份")) {
					try {
						MainUI.setAllDataToTable1(mUiDbInterface.getActivityDataHeader(),mUiDbInterface.getActivityDataByAllYear(mDBListComboYear.getText()),false,0);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					MainUI.setAllDataToTable1(mUiDbInterface.getActivityDataHeader(),mUiDbInterface.getActivityDataByYearAndMonth(mDBListComboYear.getText(),month),false,0);
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
	  mLabelTextYear = "選擇活動年度:" ;
	  mLabelTextMonth ="選擇活動月份:" ;
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
