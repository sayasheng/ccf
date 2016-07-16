package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

public class ExportInsuranceYearDialog extends DialogCombo {
	private static String title = "保險資料輸出_年度";
	private static String iconpath = "img/insurance_search_year_icon_32x32.png";
	private static UiDbInterface mUiDbInterface = new UiDbInterface();
	private String years;
	private String mErrorFileDialogMessage = "保險資料輸出(年度)輸出成功!!";
	private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
		
			if(event.widget == mCancelButton){
				mDialog.close();
			}
			else if(event.widget == mOkButton) {
				if (mDirPathSelectedText.getText().equals("")){
					mErrorFileDialog = new MessageBox (mDialog , SWT.ICON_WARNING);
					mErrorFileDialog.setText("警告");
					mErrorFileDialog.setMessage("請指定資料庫輸出檔案位置!!");
					mErrorFileDialog.open();
					
				}else {
					mErrorFileDialog = new MessageBox (mDialog ,SWT.ICON_WARNING |SWT.OK | SWT.CANCEL);
					mErrorFileDialog.setText("警告");
					mErrorFileDialog.setMessage("執行此輸出資料庫檔案的動作,將會覆蓋原有的檔案!!");
	  	    		int buttonID = mErrorFileDialog.open();
	  	    		
	  	    		switch(buttonID) {
	  	            case SWT.OK:
	  	            	years = mDBListComboYear.getText();
	 
	  	            	if (mDBListComboYesOrNo.getText().equals("否"))
							mUiDbInterface.exportInsuracenInfoYear(mDirPathSelectedText.getText(),years,false);
						else
							mUiDbInterface.exportInsuracenInfoYear(mDirPathSelectedText.getText(),years,true);
	  	  				
	  	            	//System.out.println(mDirPathSelectedText.getText());	
	  	            	mErrorFileDialog = new MessageBox (mDialog , SWT.ICON_INFORMATION);
	  	            	mErrorFileDialog.setText("成功");
	  	            	mErrorFileDialog.setMessage(mErrorFileDialogMessage);
	  	            	mErrorFileDialog.open();
						mDialog.close();
	  	            	break;
	  	            case SWT.CANCEL:
	  	                break;
	  	          }
				}
			}
			else if (event.widget == mDirPathButton){
				mDirectoryDialog = new DirectoryDialog(mDialog);
				mDirectoryDialog.setText("檔案輸出");
				mDirectoryDialog.setMessage("選擇輸出資料儲存位置");
				String fileDir = mDirectoryDialog.open();
				if (fileDir != null) {
					int width = mUiDbInterface.calculateTextSizeWidth(fileDir, mDirPathSelectedText);
		  	    	if((width) > 500){ //Control shell windows size
		  	    	mDialog.setSize((width+120),mDialogHeigh);
		  	    	}
		  	    mDirPathSelectedText.setSize (width,mDialogHeigh); 
				mDirPathSelectedText.setText(fileDir);
				}
			}
		 }
      };
	
      
	private String[] getComboYear() throws SQLException{
		return mUiDbInterface.getGroupYears();
	}
	
	public ExportInsuranceYearDialog(Display display) throws SQLException{
		super (display,title,iconpath);		
		  DialogCombo();
		  mLabelTextYear = "選擇組別年度:" ;
		  mComboYearList = getComboYear();
		  createDialogYearDropList(mDialog);
	      createDialogDropListToProveYesOrNoConditions(mDialog);
		  createFileText(mDialog);
		  mDirPathButton.addListener(SWT.Selection, mListener);
		  createDialogButton(mDialog);
		  mOkButton.addListener(SWT.Selection, mListener);
		  mCancelButton.addListener(SWT.Selection, mListener);
		  mDialog.setSize(mDialogWidth,mDialogHeigh);
		  mDialog.pack();
	      mDialog.open();
		 
	}
	
}
