package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.ccf.database.*;

public class ImportDbDialog {
	private Shell mDialog;
	private Button mFilePathButton,mOkButton,mCancelButton;
    private Text mFilePathSelectedText;
	private Combo mDBListCombo;
	private Label mDBNameLabel;
	private int mDialogWidth = 620;
	private int mDialogHeigh = 120;
	private String fileExtension = "xls";
	private MessageBox  mErrorFileDialog;
	private UiDbInterface mUiDbInterface = new UiDbInterface();
    private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
			if (event.widget == mFilePathButton){
				int width ;
				String filePath = null;
				String extension = null;
			
				FileDialog dialog = new FileDialog(mDialog, SWT.MULTI);
		    	
				dialog.getFileName();
		  	    dialog.setFilterPath("c:\\"); // Windows specific 
		  	    filePath = dialog.open();
		  	    //System.out.println(filePath);
		  	   
		  	    if (filePath != null)
		  	    {   
		  	    	int i = filePath.lastIndexOf('.');
		  	    	if (i > 0) {
		  	    	    extension = filePath.substring(i+1);
		  	    	}
		  	    	if (!extension.equals(fileExtension)){
		  	    		mErrorFileDialog = new MessageBox (mDialog , SWT.ERROR);
		  	    		mErrorFileDialog.setText("錯誤");
		  	    		mErrorFileDialog.setMessage("選擇輸入檔案格式錯誤!!請選擇副檔名為.xls的檔案");
		  	    		mErrorFileDialog.open();
		  	    	}else {
		  	       
		  	    	width = mUiDbInterface.calculateTextSizeWidth(filePath, mFilePathSelectedText);
		  	    	if((width) > 500){ //Control shell windows size
		  	    	mDialog.setSize((width+120),mDialogHeigh);
		  	    	}
		  	    	mFilePathSelectedText.setSize (width,mDialogHeigh); 
		  	    	mFilePathSelectedText.setText(filePath);	
		  	    	}
		  	    }
		  	    
			}
			else if(event.widget == mCancelButton){
				mDialog.close();
			}
			else if(event.widget == mOkButton) {
				if(!mFilePathSelectedText.getText().equals("")){
					if(!mUiDbInterface.compareExcelHeaderWithDB(mFilePathSelectedText.getText(),mUiDbInterface.getDBTableHeader(mDBListCombo.getText()))){
						mErrorFileDialog = new MessageBox (mDialog , SWT.ERROR);
						mErrorFileDialog.setText("錯誤");
						mErrorFileDialog.setMessage("選擇資料庫的名稱或選擇輸入的檔案有誤!!"+"\n"+"請確認所選擇的資料庫名稱或輸入檔案是否正確 ");
						mErrorFileDialog.open();
					}else{
						try {
							if(!mUiDbInterface.importSelectedFileToDatabase(mFilePathSelectedText.getText(),mDBListCombo.getText()))
							{
								mErrorFileDialog = new MessageBox (mDialog , SWT.ERROR);
								mErrorFileDialog.setText("錯誤");
								mErrorFileDialog.setMessage("資料庫輸入失敗!!");
								mErrorFileDialog.open();
							}else{
								mErrorFileDialog = new MessageBox (mDialog , SWT.ICON_INFORMATION);
								mErrorFileDialog.setText("成功");
								mErrorFileDialog.setMessage("資料庫輸入成功!!");
								mErrorFileDialog.open();
								mDialog.close();
							}
							}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 } //catch
					} //else_1
				}else{
					mErrorFileDialog = new MessageBox (mDialog , SWT.ICON_WARNING);
					mErrorFileDialog.setText("警告");
					mErrorFileDialog.setMessage("請指定資料庫輸入檔案位置!!");
					mErrorFileDialog.open();	
				}
				
			} //OK
		 }
      }; //listener
	
	public ImportDbDialog (Display parentDisplay) {
		mDialog =
    	        new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		//The window icon 
    	final Image image = new Image(parentDisplay, ImportDbDialog.class.getClassLoader().getResourceAsStream("img/import_icon_32x32.png"));
    	
    	mDialog.setLayout(new GridLayout(1, true));
    	mDialog.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    	mDialog.setImage(image);
    	mDialog.setText("資料庫輸入");
    	mDialog.setLocation(300,300);
   
    	
    	createDBDropList(mDialog);
    	createFileText(mDialog);
    	createImportButton(mDialog);
    	
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
        mDBNameLabel.setText("選擇輸入資料庫: ");
        
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
        mFilePathButton = new Button(composite, SWT.PUSH);
        mFilePathButton.setText("選擇輸入檔案 ");
       
        
        mFilePathSelectedText = new Text(composite,SWT.SINGLE | SWT.BORDER);
        mFilePathSelectedText.setBackground(MainUI.getTextColor());
        mFilePathSelectedText.setEditable(false);
      
        mFilePathButton.addListener(SWT.Selection, mListener);
        
	}
	
	private void createImportButton(Composite dialogshell){
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
