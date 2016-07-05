package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class InsuranceSearchYearDialog extends DialogCombo{
	private static String title = "保險資料搜尋_年度";
	private static String iconpath = "img/insurance_search_year_icon_32x32.png";
	private static UiDbInterface mUiDbInterface = new UiDbInterface();
	private String years;

	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		   if(event.widget == mCancelButton){
				mDialog.close();
			} //Cancel
			else if(event.widget == mOkButton) {
				years = mDBListComboYear.getText();
				if (mDBListComboYesOrNo.getText().equals("否"))
					try {
						 MainUI.setAllDataToTable1(mUiDbInterface.getInsuranceInfoHeader(),mUiDbInterface.getInsuranceInfo(years,false));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else
					try {
						 MainUI.setAllDataToTable1(mUiDbInterface.getInsuranceInfoHeader(),mUiDbInterface.getInsuranceInfo(years,true));
						} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				mDialog.close();
			} //OK
		 }
      }; //listener 
	private String[] getComboYear() throws SQLException{
		return mUiDbInterface.getGroupYears();
	}
	
	public InsuranceSearchYearDialog(Display display) throws SQLException{
		  super (display,title,iconpath);		
		  DialogCombo();
		  mComboYearList = getComboYear();
		  createDialogYearDropList(mDialog);
	      createDialogDropListToProveYesOrNoConditions(mDialog);
		  createDialogButton(mDialog);
		  mDialog.pack();
	      mDialog.open();
		  mOkButton.addListener(SWT.Selection, mListener);
	      mCancelButton.addListener(SWT.Selection, mListener);
		 
	}
}
