package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;

public class ServiceHoursRegularSearchDialog extends DialogCombo{
	private static String title = "服務時數統計資料查詢";
	private static String iconpath = "img/service_hours_regular_icon_32x32.png";
	private UiDbInterface mUiDbInterface = new UiDbInterface();
    private static Display mDisplay;
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		   if(event.widget == mCancelButton){
				mDialog.close();
			} //Cancel
			else if(event.widget == mOkButton) {
				/*try {	
	     				MainUI.resetAllTexts();
						MainUI.setSearchFormText("服務時數統計資料資料");
						MainUI.setSearchGroupYearText(mDBListComboYear.getText());
						MainUI.setSearchYearText(mDBListComboServiceHoursYear.getText());
					//	MainUI.setAllDataToTable1(mUiDbInterface.getServiceHoursAwardHeader(),mUiDbInterface.getServiceHoursAwardData(mDBListComboYear.getText(),mDBListComboServiceHoursYear.getText()),true,1);
 
						MainUI.topTable.addListener(SWT.MouseDoubleClick, new Listener(){
				         public void handleEvent(Event event){
				             Point pt = new Point(event.x, event.y);
				             TableItem item = MainUI.topTable.getItem(pt);
				          
				             if(item != null) {
				                 for (int col = 0; col < MainUI.topTable.getColumnCount(); col++) {
				                     Rectangle rect = item.getBounds(col);
				                     if (rect.contains(pt)) {
				                         if(!item.getText(1).equals("")){
				                    	 try {
											ServiceHoursAwardDetailDialog mServiceHoursAwardDetailDialog = new ServiceHoursAwardDetailDialog(mDisplay,item.getText(1),MainUI.getSearchYearText());
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				                         }
				                     }
				                 }
				             }
				         }
				     }); 
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				mDialog.close();
			}//OK
		 }
      }; //listener 
    
    private String[] getGroupYear() throws SQLException{
		return mUiDbInterface.getGroupYears();
	}
	private String[] getActivityYear() throws SQLException{
		return mUiDbInterface.getActivityYears();
	}
	
	public ServiceHoursRegularSearchDialog(Display display) throws SQLException{
	super (display,title,iconpath);	
	  mDisplay = display;
	  DialogCombo();
	  mLabelTextYear = "選擇組別年度: ";
	  mComboYearList = getGroupYear();
	  mLabelTextServiceHoursYear = "選擇服務時數統計年度: ";
	  mComboServiceHoursYearList = getActivityYear();
	  createDialogYearDropList(mDialog);
	  createDialogServiceHoursAwardYearDropList(mDialog);
	  createDialogButton(mDialog);
	 // mOkButton.addListener(SWT.Selection, mListener);
      //mCancelButton.addListener(SWT.Selection, mListener);
	  mDialog.pack();
      mDialog.open();
	
	}

}
