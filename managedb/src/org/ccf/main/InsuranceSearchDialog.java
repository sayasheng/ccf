package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

public class InsuranceSearchDialog extends DialogCombo{
	private static String title = "保險資料查詢條件";
	private static String iconpath = "img/insurance_search_icon_32x32.png";
	private static Display mDisplay;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		
			if(event.widget == mNameSearchButton){
				try {
					InsuranceSearchNameDialog mInsuranceSearchNameDialog = new InsuranceSearchNameDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
			else if(event.widget == mYearSearchButton) {
				try {
					InsuranceSearchYearDialog mInsuranceSearchYearDialog = new InsuranceSearchYearDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
		}
      };
	
	
	public InsuranceSearchDialog(Display display) throws SQLException{
		  super (display,title,iconpath);
		  display = mDisplay;
		  DialogCombo();
		  mInsuranceDialogText ="查詢條件經由:";
		  createInsuranceDialog(mDialog);
		  mNameSearchButton.addListener(SWT.Selection, mListener);
		  mYearSearchButton.addListener(SWT.Selection, mListener);
		  mDialog.pack();
	      mDialog.open();
	}
}
