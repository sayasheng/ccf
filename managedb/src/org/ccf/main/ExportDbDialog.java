package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ExportDbDialog {
		private Shell mDialog;
		private Combo mDBListCombo;
		private Label mDBNameLabel;
		private Button mDirPathButton,mOkButton,mCancelButton;
		private Text mDirPathSelectedText;
		private MessageBox  mErrorFileDialog;
		private UiDbInterface mUiDbInterface = new UiDbInterface();
		private DirectoryDialog mDirectoryDialog;
		private int mDialogWidth = 620;
		private int mDialogHeigh = 120;

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
			  	            	if(!mUiDbInterface.exportSelectedDbToExcel(mDBListCombo.getText(),mDirPathSelectedText.getText())){
			  	            		mErrorFileDialog = new MessageBox (mDialog , SWT.ERROR);
			  	            		mErrorFileDialog.setText("錯誤");
			  	            		mErrorFileDialog.setMessage("資料庫輸出失敗!!");
			  	            		mErrorFileDialog.open();
								}
								//System.out.println(mDirPathSelectedText.getText());	
			  	            	mErrorFileDialog = new MessageBox (mDialog , SWT.ICON_INFORMATION);
								mErrorFileDialog.setText("成功");
								mErrorFileDialog.setMessage("資料庫輸出成功!!");
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
						mDirectoryDialog.setText("資料庫檔案輸出");
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
		      
		public ExportDbDialog (Display parentDisplay) {
			mDialog =
	    	        new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
			final Image image = new Image(parentDisplay, ExportDbDialog.class.getClassLoader().getResourceAsStream("img/export_icon_32x32.png"));
			
			mDialog.setLayout(new GridLayout(1, true));
	    	mDialog.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    	mDialog.setImage(image);
	    	mDialog.setText("資料庫輸出");
	    	mDialog.setLocation(300,300);
	    	
	    	createDBDropList(mDialog);
	    	createFileText(mDialog);
	    	createExportButton(mDialog);
	    	
	    	mDialog.setSize(mDialogWidth,mDialogHeigh);
	    	//mDialog.pack();
	    	mDialog.open();
		}
		
		private void createDBDropList(Composite dialogshell){
	    	Composite composite = new Composite(dialogshell, SWT.NONE);
	        GridLayout gridLayout = new GridLayout(2, false);
	        gridLayout.marginWidth = 0;
	        gridLayout.marginHeight = 0;
	        gridLayout.verticalSpacing = 0;
	        gridLayout.horizontalSpacing = 0;
	        composite.setLayout(gridLayout);
	    	
	        mDBNameLabel = new Label (composite,SWT.NONE);
	        mDBNameLabel.setText("選擇輸出資料庫: ");
	        
	        mDBListCombo = new Combo (composite, SWT.DROP_DOWN | SWT.READ_ONLY);
	        mDBListCombo.setItems(mUiDbInterface.dblist);
	    	mDBListCombo.select(0);
	    	
	    }
		
		public void createFileText(Composite dialogshell){
			Composite composite = new Composite(dialogshell, SWT.NONE);
	        GridLayout gridLayout = new GridLayout(2,false);
	        gridLayout.marginWidth = 0;
	        gridLayout.marginHeight = 0;
	        gridLayout.verticalSpacing = 0;
	        gridLayout.horizontalSpacing = 3;
	    
	        composite.setLayout(gridLayout);
	        
	        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	        composite.setLayoutData(gridData);

	        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
	        mDirPathButton = new Button(composite, SWT.PUSH);
	        mDirPathButton.setText("選擇輸出檔案位置 ");
	       
	        
	        mDirPathSelectedText = new Text(composite,SWT.SINGLE | SWT.BORDER);
	        mDirPathSelectedText.setBackground(MainUI.getTextColor());
	        mDirPathSelectedText.setEditable(false);
	      
	        mDirPathButton.addListener(SWT.Selection, mListener);
	        
		}
		
		private void createExportButton(Composite dialogshell){
	    	Composite composite = new Composite(dialogshell, SWT.NONE);
	        GridLayout gridLayout = new GridLayout(2, false);
	        gridLayout.marginWidth = 260;
	        gridLayout.marginHeight = 0;
	        gridLayout.verticalSpacing = 0;
	        gridLayout.horizontalSpacing = 10;
	        composite.setLayout(gridLayout);

	        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	        composite.setLayoutData(gridData);

	        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
	        mOkButton = new Button(composite, SWT.PUSH);
	        mCancelButton = new Button(composite, SWT.PUSH);
	        
	        mOkButton.setText("確定");
	        mCancelButton.setText("取消");
	        
	        mOkButton.addListener(SWT.Selection, mListener);
	        mCancelButton.addListener(SWT.Selection, mListener);
	    }
}
