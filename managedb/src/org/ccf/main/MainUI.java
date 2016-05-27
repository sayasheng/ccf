package org.ccf.main;


import java.sql.SQLException;

import org.ccf.database.PersonalInfoTable;
import org.ccf.database.ServiceHoursTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import  org.eclipse.swt.graphics.Color;


import com.hexapixel.widgets.generic.ImageCache;

public class MainUI {
	  Display display;

	  Shell shell;

	  Menu menuBar, sqlMangMenu,contactMenu, editMenu, aboutMenu;
	  
      //Header items
	  MenuItem sqlMangMenuHeader, contactMenuHeader, editMenuHeader;
      
	  //Drop items of file import
	  MenuItem sqlMangImportFromExcelItem,sqlMangExportToExcelItem,fileExitItem;
	 
	  //Drop items of contact
	  MenuItem  contactWithAddressItem, contactWithoutAddressItem, exportContactWithAddressItem,exportContactWithoutAddressItem ; 
     
      MenuItem editCopyItem;
      //Layout
      Group groupTableTop,groupTableButtom;
      RowLayout outerRowLayout;
      
      //ScrolledComposite
      public static ScrolledComposite  topScrolledComposite,buttomScrolledComposite;
      //Tables
      public static Table topTable,buttomTable;         

	  //Label
	  Label topDBLabel,buttomDBLabel;
	  public static Text text,topDBText,buttomDBText;
	  
	  //Font
	  Font boldFont;
	  //Button
	  Button queryByConditionButton,queryAllButton,deleteButton, addButton;
	  //Dialogs
	  private  QueryAllDialog mQueryAllDialog;
	  private  ImportDbDialog mImportDbDialog; 
	  private  ExportDbDialog mExportDbDialog;
	  private ExportContactInfoDialog mExportContactInfoDialog;
	  
	  public static Color mTextColor;
	  
	  //Image icons
	  private Image importImg,exportImg,exitImg,queryAllImg,deleteImg,addImg,queryByConditionImg,contactWithoutAddressImg,contactWithAddressImg
	  				,exportContactWithoutAddressImg,exportContactWithAddressImg;
	  private UiDbInterface mUiDbInterface = new UiDbInterface();  
	
	  public MainUI() {
	    display = new Display();
	    shell = new Shell(display);
	  
	    shell.setLocation(20,20);
	    shell.setImage(ImageCache.getImage("selection_recycle_24.png"));
	    shell.setText("展愛隊資料庫管理系統");
	    shell.setSize(1100, 750);
	    
		mTextColor = display.getSystemColor(SWT.COLOR_WHITE);
	   // text = new Text(shell, SWT.BORDER);
	   // text.setBounds(840, 670, 250, 20);
	   // text.setText("Author:Saya Sheng & Ashley Sheng Version:1.0.0");
	   // text.setEditable(false);
        
	    menuBar = new Menu(shell, SWT.BAR);
	    //檔案輸入 settings ++
	    sqlMangMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    sqlMangMenuHeader.setText("&資料庫管理");
	    sqlMangMenu = new Menu(shell, SWT.DROP_DOWN);
	    sqlMangMenuHeader.setMenu(sqlMangMenu);
	    
	    
		importImg = new Image(display, "img\\import_icon_16x16.png");
	    sqlMangImportFromExcelItem = new MenuItem(sqlMangMenu, SWT.PUSH);
	    sqlMangImportFromExcelItem.setText("資料庫輸入");
	    sqlMangImportFromExcelItem.setImage(importImg);
	    sqlMangImportFromExcelItem.addSelectionListener(new MenuItemListener());
	    
	    exportImg = new Image(display,"img\\export_icon_16x16.png");
	    sqlMangExportToExcelItem = new MenuItem(sqlMangMenu, SWT.PUSH);
	    sqlMangExportToExcelItem.setText("資料庫輸出");
	    sqlMangExportToExcelItem.setImage(exportImg);
	    sqlMangExportToExcelItem.addSelectionListener(new MenuItemListener());
	    
	    exitImg = new Image(display,"img\\exit_icon_16x16.png");
	    fileExitItem = new MenuItem(sqlMangMenu, SWT.PUSH);
	    fileExitItem.setText("離開");
	    fileExitItem.setImage(exitImg);
	    fileExitItem.addSelectionListener(new MenuItemListener());
	    //檔案輸入 settings --
	    
	    //通訊錄 settings ++ 
	    contactMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    contactMenuHeader.setText("通訊錄");
	    contactMenu = new Menu(shell, SWT.DROP_DOWN);
	    contactMenuHeader.setMenu(contactMenu);
	    
	    contactWithAddressImg = new Image(display,"img\\contact_address_icon_16x16.png");
	    contactWithAddressItem = new MenuItem(contactMenu, SWT.PUSH);
	    contactWithAddressItem.setImage(contactWithAddressImg);
	    contactWithAddressItem.setText("通訊錄(含地址)");

	    contactWithoutAddressImg = new Image(display,"img\\contact_icon_16x16.png");
	    contactWithoutAddressItem = new MenuItem(contactMenu, SWT.PUSH);
	    contactWithoutAddressItem.setImage(contactWithoutAddressImg);
	    contactWithoutAddressItem.setText("通訊錄(不含地址)");
	   
	    exportContactWithAddressImg = new Image(display,"img\\export_contact_address_icon_16x16.png");
	    exportContactWithAddressItem = new MenuItem(contactMenu, SWT.PUSH);
	    exportContactWithAddressItem.setImage(exportContactWithAddressImg);
	    exportContactWithAddressItem.setText("通訊錄(含地址)輸出");

	    exportContactWithoutAddressImg = new Image(display,"img\\export_contact_icon_16x16.png");
	    exportContactWithoutAddressItem = new MenuItem(contactMenu, SWT.PUSH);
	    exportContactWithoutAddressItem.setImage(exportContactWithoutAddressImg);
	    exportContactWithoutAddressItem.setText("通訊錄(不含地址)輸出");
	    
	    contactWithAddressItem.addSelectionListener(new MenuItemListener());
	    contactWithoutAddressItem.addSelectionListener(new MenuItemListener());
	    exportContactWithAddressItem.addSelectionListener(new MenuItemListener());
	    exportContactWithoutAddressItem.addSelectionListener(new MenuItemListener());
	
	    //通訊錄 settings --
	    
	    
	    
	    editMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    editMenuHeader.setText("&Edit");

	    editMenu = new Menu(shell, SWT.DROP_DOWN);
	    editMenuHeader.setMenu(editMenu);

	    editCopyItem = new MenuItem(editMenu, SWT.PUSH);
	    editCopyItem.setText("&Copy");

	   
	    editCopyItem.addSelectionListener(new MenuItemListener());

	  
        //Shell configuration
	    shell.setMenuBar(menuBar);
	   
	    //Layout in Shell

	  // Color borderColor = display.getSystemColor(SWT.COLOR_WIDGET_BORDER);
	     Color borderColor = display.getSystemColor(SWT.COLOR_BLACK);
	    
	    shell.setLayout(new GridLayout(1,false));
	    
    //createMainUI(shell);
	    createTopGroup(shell);
	    createButtomGroup(shell);
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	  }
      
	  
	  class MenuItemListener extends SelectionAdapter {
	    public void widgetSelected(SelectionEvent event) {
	      if (((MenuItem) event.widget).getText().equals("資料庫輸入")) {
	    	  mImportDbDialog = new ImportDbDialog(display);
	    	  
	      }
	      if (((MenuItem) event.widget).getText().equals("資料庫輸出")) {
	    	  mExportDbDialog = new ExportDbDialog(display);
	    	  
	      }
	      if (((MenuItem) event.widget).getText().equals("離開")) {
	        shell.close();
	      }
	      if(((MenuItem) event.widget).getText().equals("通訊錄(含地址)")) {
	    	  setAllDataToTable1(mUiDbInterface.getContactInfoAddressHeader(),mUiDbInterface.getContactInfoAddress());
	      }
	      if(((MenuItem) event.widget).getText().equals("通訊錄(不含地址)")) {
	    	  setAllDataToTable1(mUiDbInterface.getContactInfoNoAddressHeader(),mUiDbInterface.getContactInfoNoAddress());
	      }
	      if(((MenuItem) event.widget).getText().equals("通訊錄(含地址)輸出")){
	    	  mExportContactInfoDialog = new ExportContactInfoDialog(display, true);
	      }
	      if(((MenuItem) event.widget).getText().equals("通訊錄(不含地址)輸出")) {
	    	  mExportContactInfoDialog = new ExportContactInfoDialog(display, false);
	      }
	    }
	  }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 MainUI menuExample = new MainUI();
	}
			

	
	private Composite createTopViewArea (Composite area){
		final Composite topgroup = new Composite(area, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
        //gridLayout.marginWidth = 5;
        //gridLayout.marginHeight = 5;
        topgroup.setLayout(gridLayout);
        createTopGroup(topgroup);
        return topgroup;
	}
	
	private void createTopGroup(Composite topgroup){
		groupTableTop = new Group(topgroup, SWT.NONE);
	    groupTableTop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    groupTableTop.setLayout(new GridLayout(1, true));
	    groupTableTop.setText("資料庫內容");	    
	    boldFont = new Font(groupTableTop.getDisplay(), new FontData( "Arial", 12, SWT.BOLD ) );   
	    groupTableTop.setFont(boldFont);
	    createTopDBLable(groupTableTop);
	    creatTopTable(groupTableTop);
	    creatTopButton(groupTableTop);    
	}
	
	private void createTopDBLable(Composite topgroup){
		
		Composite composite = new Composite(topgroup, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);

		topDBLabel = new Label (composite,SWT.NONE);
		topDBLabel.setText("資料庫名稱: ");
		topDBText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		topDBText.setEditable(false);
		topDBText.setBackground(mTextColor);
		
		
	}
	private void creatTopTable(Composite topgroup) {
		 topScrolledComposite = new ScrolledComposite (topgroup,SWT.BORDER);
		 GridData gridLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		 gridLayout.heightHint = 50;
		 topScrolledComposite.setLayoutData(gridLayout);

		 topTable = new Table(topScrolledComposite,SWT.BORDER |SWT.FULL_SELECTION);  
		 topTable.setHeaderVisible(true);
		 topTable.setLinesVisible(true); 
		// topTable.addListener(SWT.Selection, topTableListener);
		 
		 topScrolledComposite.setContent(topTable);
	     topScrolledComposite.setExpandHorizontal(true);
	     topScrolledComposite.setExpandVertical(true);
	     topScrolledComposite.setAlwaysShowScrollBars(true);
	     topScrolledComposite.setMinSize(topTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	        
	      
	}
	
	private void creatTopButton (Composite topgroup) {
		    Composite composite = new Composite(topgroup, SWT.NONE);
	        GridLayout gridLayout = new GridLayout(3, false);
	        gridLayout.marginWidth = 0;
	        gridLayout.marginHeight = 0;
	        gridLayout.verticalSpacing = 0;
	        gridLayout.horizontalSpacing = 0;
	        composite.setLayout(gridLayout);

	        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	        composite.setLayoutData(gridData);

	        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);

	        queryAllButton = new Button(composite, SWT.PUSH);  
		    queryAllImg = new Image(display,"img\\query_all_icon_32x32.png");
		    queryAllButton.setImage(queryAllImg);
		    queryAllButton.setText("全部查詢");
		    
		 
		    queryByConditionButton = new Button(composite, SWT.PUSH);
		    queryByConditionImg = new Image(display,"img\\query_condition_icon_32x32.png");
		    queryByConditionButton.setImage(queryByConditionImg);
		    queryByConditionButton.setText("條件查詢");
		    
	
		    deleteButton = new Button(composite, SWT.PUSH);
		    deleteImg = new Image(display,"img\\delete_icon_32x32.png");
		    deleteButton.setImage(deleteImg);
		    deleteButton.setText("刪除");
		    
		   
		    //Listener
		    Listener listener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					if (event.widget == queryAllButton){
						mQueryAllDialog = new QueryAllDialog(display);
					}else if (event.widget == queryByConditionButton){
						
					}else if (event.widget == deleteButton) {
						
					}
				}
		      };

		   queryAllButton.addListener(SWT.Selection, listener);
		   queryByConditionButton.addListener(SWT.Selection, listener);
		   deleteButton.addListener(SWT.Selection, listener);
		   
	}
	
	private void createButtomGroup(Composite parent){
		groupTableButtom = new Group(parent, SWT.NONE);
		groupTableButtom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupTableButtom.setLayout(new GridLayout(1, true));
		groupTableButtom.setText("資料編輯");	    
	    boldFont = new Font(groupTableTop.getDisplay(), new FontData( "Arial", 12, SWT.BOLD ) );   
	    groupTableButtom.setFont(boldFont);
	    createButtomDBLable(groupTableButtom);
	    creatButtomTable(groupTableButtom);
	    creatButttomButton(groupTableButtom);    
	}
	
	private void createButtomDBLable(Composite buttomgroup){
		Composite composite = new Composite(buttomgroup, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);

		buttomDBLabel = new Label (composite,SWT.NONE);
		buttomDBLabel.setText("資料庫名稱: ");
		buttomDBText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		buttomDBText.setSize(200, buttomDBText.getSize().y);
		buttomDBText.setEditable(false);
		buttomDBText.setBackground(mTextColor);
		
	}
	private void creatButtomTable(Composite buttomgroup) {
		 buttomScrolledComposite = new ScrolledComposite (buttomgroup,SWT.BORDER);
		 GridData gridLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		 gridLayout.heightHint = 50;
		 buttomScrolledComposite.setLayoutData(gridLayout);
		 
		 buttomTable = new Table(buttomScrolledComposite,SWT.BORDER |SWT.FULL_SELECTION);  
		 buttomTable.setHeaderVisible(true);
		 buttomTable.setLinesVisible(true); 
		 
		 buttomScrolledComposite.setContent(buttomTable);
		 buttomScrolledComposite.setExpandHorizontal(true);
		 buttomScrolledComposite.setExpandVertical(true);
		 buttomScrolledComposite.setAlwaysShowScrollBars(true);
		 buttomScrolledComposite.setMinSize(buttomTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		 
	}
	
	private void creatButttomButton (Composite buttomgroup) {
		    Composite composite = new Composite(buttomgroup, SWT.NONE);
	        GridLayout gridLayout = new GridLayout(1, false);
	        gridLayout.marginWidth = 0;
	        gridLayout.marginHeight = 0;
	        gridLayout.verticalSpacing = 0;
	        gridLayout.horizontalSpacing = 0;
	        composite.setLayout(gridLayout);

	        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	        composite.setLayoutData(gridData);

	        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);

	        addButton = new Button(composite, SWT.PUSH);
	        addImg = new Image(display,"img\\add_icon_32x32.png");
		    addButton.setText("新增");
		    addButton.setImage(addImg);
		    
	}
	
	public static void setTopDBText (String input){
		topDBText.setSize(250,topDBText.getSize().y);
		topDBText.setText(input);
	}
	
	public static void setButtomBText (String input) {
		buttomDBText.setSize(250,buttomDBText.getSize().y);
		buttomDBText.setText(input);
	}
	
	public static Color getTextColor (){
		return mTextColor;
	}
	
	public static void setAllDataToTable1(String[] header){
		
		String [] oneRowData = new String[header.length]; 
		topTable = new Table(topScrolledComposite,SWT.BORDER |SWT.FULL_SELECTION);  
		topTable.setHeaderVisible(true);
		topTable.setLinesVisible(true); 
		 
		topScrolledComposite.setContent(topTable);
	    topScrolledComposite.setExpandHorizontal(true);
	    topScrolledComposite.setExpandVertical(true);
	    topScrolledComposite.setAlwaysShowScrollBars(true);
	    topScrolledComposite.setMinSize(topTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	     
		for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			TableColumn column = new TableColumn(topTable, SWT.NONE);
            column.setText(header[loopIndex]);
            column.setWidth(100);
          }
		
		 for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
	        	topTable.getColumn(loopIndex).pack();
	          }
	}
	
	public static void setAllDataToTable1(String[] header, String[][] data){
		/*
		 ServiceHoursTable servicehours = new ServiceHoursTable();
		 String [] header = servicehours.getServiceHoursHeader(); 
	     String [] myData = new String[header.length];
	     
	     String [][]  servicehoursdata= null;
	     try {
	     servicehoursdata = servicehours.queryAllFromServiceHoursTable(); 
	     } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	     for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
	            TableColumn column = new TableColumn(topTable, SWT.NONE);
	            column.setText(header[loopIndex]);
	            column.setWidth(300);
	          }
	        

	        for (int i=0; i < servicehoursdata.length ;i++){
	        	 TableItem item = new TableItem(topTable, SWT.None);
		    	for (int j=0; j<header.length ; j++){ 	
		    		//3-B. We extract one row of data from data object 
		    		myData[j] = servicehoursdata[i][j].toString();
		    		System.out.println("");
	 	    		//3-C. Write one row of data into database
		    		item.setText(j,myData[j]);
		    		/*
		    		if (j== (header.length-1)){
		    			item.setText(0,myData[0]);
		    			item.setText(1,myData[1]);
		    			item.setText(2,myData[2]);
		    			item.setText(3,myData[3]);
		    			item.setText(4,myData[4]);
		    			item.setText(5,myData[5]);
		    			item.setText(6,myData[6]);
		    			//3-D. Reset myData array after data insert.
		    			myData = new String[header.length];
		    		}*/
		    	//}
		// }        
	      /*for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
	        	topTable.getColumn(loopIndex).pack();
	          }*/
		
		String [] oneRowData = new String[header.length]; 
		topTable = new Table(topScrolledComposite,SWT.BORDER |SWT.FULL_SELECTION);  
		topTable.setHeaderVisible(true);
		topTable.setLinesVisible(true); 
		 
		topScrolledComposite.setContent(topTable);
	    topScrolledComposite.setExpandHorizontal(true);
	    topScrolledComposite.setExpandVertical(true);
	    topScrolledComposite.setAlwaysShowScrollBars(true);
	    topScrolledComposite.setMinSize(topTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	     
		for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			TableColumn column = new TableColumn(topTable, SWT.NONE);
            column.setText(header[loopIndex]);
            column.setWidth(100);
          }
		
		for (int i=0; i < data.length ;i++){
       	 TableItem item = new TableItem(topTable, SWT.None);
	    	for (int j=0; j<header.length ; j++){ 	
	    		//3-B. We extract one row of data from data object 
	    		oneRowData[j] = data[i][j].toString();

	    		//3-C. Write one row of data into database
	    		item.setText(j,oneRowData[j]);
	    	}
		}
		
		 for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
	        	topTable.getColumn(loopIndex).pack();
	          }
	}
}
