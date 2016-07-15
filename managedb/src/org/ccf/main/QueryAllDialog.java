package org.ccf.main;


import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.hexapixel.widgets.generic.ImageCache;

public class QueryAllDialog  {
	
	private final Shell mDialog ; 
	private Combo mDBListCombo;
	private Button mOkButton,mCancelButton;
	private Label mDBNameLabel;
	private UiDbInterface mUiDbInterface = new UiDbInterface();
	 //Listener
    private Listener mListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
			if (event.widget == mOkButton){
				try {
					MainUI.resetAllTexts();
					MainUI.setTopDBText(mDBListCombo.getText());
					String mySelect = mDBListCombo.getText();
					String[][] myData= mUiDbInterface.queryAllDataFromDB(mySelect);
					if (myData == null)
					{
					  MainUI.setAllDataToTable1(mUiDbInterface.getDBTableHeader(mDBListCombo.getText()));
					}else{
					  MainUI.setAllDataToTable1(mUiDbInterface.getDBTableHeader(mDBListCombo.getText()),myData,false,0);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mDialog.close();
			}
			else if(event.widget == mCancelButton){
				mDialog.close();
			}
		}
      };
      
    public QueryAllDialog(Display parentDisplay) {
    	mDialog =
    	        new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    	//The window icon 
    	final Image image = new Image(parentDisplay,QueryAllDialog.class.getClassLoader().getResourceAsStream("img/query_all_icon_32x32.png"));
    	mDialog.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    	mDialog.setImage(image);
    	mDialog.setText("全部查詢");
    	mDialog.setLocation(300,300);
    	mDialog.setLayout(new GridLayout(1, true));
    	createDBDropList(mDialog);
    	createDBButton(mDialog);
    	mDialog.pack();
    	mDialog.open();
    
    }
   
    private void createDBDropList(Composite dialogshell){
    	Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 60;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);
    	
        mDBNameLabel = new Label (composite,SWT.NONE);
        mDBNameLabel.setText("選擇搜尋資料庫名稱: ");
        mDBListCombo = new Combo (composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        mDBListCombo.setItems(mUiDbInterface.dblist);
    	mDBListCombo.select(0);
    
    	
    }
    
    private void createDBButton(Composite dialogshell){
    	Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 180;
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
