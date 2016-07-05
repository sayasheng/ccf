package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ExportActivityDialog extends DialogCombo{
	private static String title = "活動資料輸出條件";
	private static String iconpath = "img/export_activity_icon_32x32.png";
	private static UiDbInterface mUiDbInterface = new UiDbInterface();
	private static Display mDisplay;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		
			if(event.widget == mActivityContainSearchButton){
				try {
					ExportActivityDataDialog mExportActivityDataDialog = new ExportActivityDataDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
			else if(event.widget == mActivityRegisterSearchButton) {
				try {
					ExportActivityRegisterDialog mActivityRegisterSearchDialog = new ExportActivityRegisterDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
		}
      };
	
	
	public ExportActivityDialog(Display display) throws SQLException{
		  super (display,title,iconpath);
		  display = mDisplay;
		  DialogCombo();
		  mActivityDialogText ="輸出條件經由:";
		  createActivityDialog(mDialog);
		  mActivityContainSearchButton.addListener(SWT.Selection, mListener);
		  mActivityRegisterSearchButton.addListener(SWT.Selection, mListener);
		  mDialog.pack();
	      mDialog.open();
	}
}
