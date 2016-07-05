package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ActivitySearchDialog extends DialogCombo {
	private static String title = "活動資料查詢條件";
	private static String iconpath = "img/activity_search_icon_32x32.png";
	private static Display mDisplay;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		
			if(event.widget == mActivityContainSearchButton){
				
				try {
					ActivityDataSearchDialog mActivityDataSearchDialog = new ActivityDataSearchDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
			else if(event.widget == mActivityRegisterSearchButton) {
				try {
					ActivityRegisterSearchDialog mActivityRegisterSearchDialog = new ActivityRegisterSearchDialog(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
		}
      };
	
	
	public ActivitySearchDialog(Display display) throws SQLException{
		  super (display,title,iconpath);
		  display = mDisplay;
		  DialogCombo();
		  mActivityDialogText ="查詢條件經由:";
		  createActivityDialog(mDialog);
		  mActivityContainSearchButton.addListener(SWT.Selection, mListener);
		  mActivityRegisterSearchButton.addListener(SWT.Selection, mListener);
		  mDialog.pack();
	      mDialog.open();
	}
}
