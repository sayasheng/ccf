package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ExportInsuranceDialog extends DialogCombo {
	private static String title = "保險資料輸出條件";
	private static String iconpath = "img/export_insurance_icon_32x32.png";
	private static Display mDisplay;
	
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		
			if(event.widget == mNameSearchButton){
				try {
					ExportInsuranceNameDialog mExportInsuranceNameDialog = new ExportInsuranceNameDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
			else if(event.widget == mYearSearchButton) {
				try {
					ExportInsuranceYearDialog mExportInsuranceYearDialog = new ExportInsuranceYearDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
		}
      };
	
	
	public ExportInsuranceDialog(Display display) {
		// TODO Auto-generated constructor stub
		super(display, title, iconpath);
		display = mDisplay;
		  DialogCombo();
		  mInsuranceDialogText ="輸出檔案條件經由:";
		  createInsuranceDialog(mDialog);
		  mNameSearchButton.addListener(SWT.Selection, mListener);
		  mYearSearchButton.addListener(SWT.Selection, mListener);
		  mDialog.pack();
	      mDialog.open();
		
	}

}
