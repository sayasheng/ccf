package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ExportServiceHoursDialog extends DialogCombo{
	private static String title = "服務時數輸出條件";
	private static String iconpath = "img/export_service_hours_icon_32x32.png";
	private static Display mDisplay;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		
			if(event.widget == mServiceHoursAwardButton){
				try {
					ExportServiceHoursAwardDialog mExportServiceHoursAwardDialog = new ExportServiceHoursAwardDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
			else if(event.widget == mServiceHoursRegularButton) {
				try {
					ExportServiceHoursRegularDialog mExportServiceHoursRegularDialog = new ExportServiceHoursRegularDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
		}
      };
	
	
	public ExportServiceHoursDialog(Display display) throws SQLException{
		  super (display,title,iconpath);
		  display = mDisplay;
		  DialogCombo();
		  mServiceHoursDialogText ="輸出條件經由:";
		  createServiceHoursialog(mDialog);
		  mServiceHoursAwardButton.addListener(SWT.Selection, mListener);
		  mServiceHoursRegularButton.addListener(SWT.Selection, mListener);
		  mDialog.pack();
	      mDialog.open();
	}
}
