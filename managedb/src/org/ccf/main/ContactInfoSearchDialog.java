package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ContactInfoSearchDialog extends DialogCombo{
	private static String title = "通訊錄資料查詢條件";
	private static String iconpath = "img/contact_search_icon_32x32.png";
	
	private static Display mDisplay;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		
			if(event.widget == mContactInfoWithAddressButton){
				try {
					ContactInfoDialogWithAddress mContactInfoDialogWithAddress = new ContactInfoDialogWithAddress(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
			else if(event.widget == mContactInfoWithoutAddressButton) {
				try {
					ContactInfoDialogWithoutAddress mContactInfoDialogWithoutAddress = new ContactInfoDialogWithoutAddress(mDisplay);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
		}
      };
	
	
	public ContactInfoSearchDialog(Display display) throws SQLException{
		  super (display,title,iconpath);
		  display = mDisplay;
		  DialogCombo();
		  mContactInfoDialogText ="查詢條件經由:";
		  createContactInfoDialog(mDialog);
		  mContactInfoWithAddressButton.addListener(SWT.Selection, mListener);
		  mContactInfoWithoutAddressButton.addListener(SWT.Selection, mListener);
		  mDialog.pack();
	      mDialog.open();
	}
}
