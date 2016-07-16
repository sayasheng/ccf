package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class InsuranceSearchNameDialog extends DialogCombo {
	private static String title = "保險資料搜尋_個人姓名";
	private static String iconpath = "img/insurance_search_name_icon_32x32.png";
	private static UiDbInterface mUiDbInterface = new UiDbInterface();
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		    if(event.widget == mCancelButton){
				mDialog.close();
			} //Cancel
			else if(event.widget == mOkButton) {
				MainUI.resetAllTexts();
				MainUI.setSearchFormText("保險資料資料_個人姓名");
				MainUI.setSearchNameText(mDBListComboInsuranceSearchName.getText());
				//mDBListComboYear.getText();
				try {
					MainUI.setAllDataToTable1(mUiDbInterface.getInsuranceInfoHeader(),mUiDbInterface.getInsuranceInfoByName(mDBListComboInsuranceSearchName.getText()),false,0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			} //OK
		 }
      }; //listener
	
	private String[] getAllMembersName() throws SQLException {
		return mUiDbInterface.getGroupMemberName();
	}
	private String[] getComboYear() throws SQLException{
		String[] years = new String[mUiDbInterface.getGroupYears().length+1];
		years[0] = "所有年度";
	
		for (int i=1 ; i <years.length ; i++){
			years[i] = mUiDbInterface.getGroupYears()[i-1];
		}
		return years;
	}
	
	public InsuranceSearchNameDialog(Display display) throws SQLException{
		  super (display,title,iconpath);		
		  DialogCombo();
		  mComboInsuranceSearchNameList = getAllMembersName();
		 // mComboYearList = getComboYear();
		  
		  createDialogNameDropList(mDialog);
		//  createDialogYearDropList(mDialog);
		  createDialogButton(mDialog);
		  mDialog.pack();
	      mDialog.open();
	      mOkButton.addListener(SWT.Selection, mListener);
	      mCancelButton.addListener(SWT.Selection, mListener);
	}
}
