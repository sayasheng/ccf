package org.ccf.main;


import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import org.ccf.database.PersonalInfoTable;
import org.ccf.database.ServiceHoursTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
import com.hexapixel.widgets.generic.ImageCache;
//import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.graphics.Rectangle;


public class MainUI {
	  Display display;

	  Shell shell;

	  Menu menuBar, sqlMangMenu,contactMenu, insuranceMenu, activityMenu, serviceHoursMenu, editMenu, aboutMenu;
	  
      //Header items
	  MenuItem sqlMangMenuHeader, contactMenuHeader, insuranceMenuHeader, activityMenuHeader, serviceHoursMenuHeader, editMenuHeader;
      
	  //Drop items of file import
	  MenuItem sqlMangImportFromExcelItem,sqlMangExportToExcelItem,fileExitItem;
	 
	  //Drop items of contact
	  MenuItem  contactInfoSearchItem, exportContactInfoItem; 
     
	  //Drop items of insurance
	  MenuItem insuranceSearchItem, exportInsuranceItem;
	  
	  //Drop items of activity
	  MenuItem activityDataSearchItem,exportActivityDataItem;
	  
	  //Drop items of service hours per year
	  MenuItem serviceHoursSearchItem, exportServiceHoursItem;
	  
      MenuItem editCopyItem;
      //Layout
      Group groupTableTop,groupTableButtom,groupTableTopSeach;
      RowLayout outerRowLayout;
      
      //ScrolledComposite
      public static ScrolledComposite  topScrolledComposite,buttomScrolledComposite;
      //Tables
      public static Table topTable,buttomTable;         
     // public final TableCursor cursor = new TableCursor(table, SWT.NONE);
     // public static TableCursor cursor;
	  //Label
	  public Label topDBLabel,buttomDBLabel,topSearchFormLabel,topSearchGroupYearLabel,topSearchGroupExitLabel,topSearchNameLabel,topSearchYearLabel,topSearchMonthLabel;
	  public static Text text,topDBText,buttomDBText,topSearchFormText,topSearchGroupYearText,topSearchGroupExitText,topSearchNameText,topSearchYearText,topSearchMonthText;
	  //Font
	  public Font boldFont;
	  public static Color mTextColor, mTextItemColorBlue;
	  //Button
	  private Button queryByConditionButton,queryAllButton,deleteButton, addButton,importDBButton,exportDBButton;
	  //Dialogs
	  private  QueryAllDialog mQueryAllDialog;
	  private  ImportDbDialog mImportDbDialog; 
	  private  ExportDbDialog mExportDbDialog;
	  private  ContactInfoSearchDialog mContactInfoSearchDialog;
	  private  ExportContactInfoDialog mExportContactInfoDialog; 
	  private  InsuranceSearchDialog mInsuranceSearchDialog;
	  private  ExportInsuranceDialog mExportInsuranceDialog;
	  private  ActivitySearchDialog mActivitySearchDialog;
	  private  ExportActivityDialog mExportActivityDialog;
	  private  ServiceHoursSearchDialog mServiceHoursSearchDialog;
	  private  ExportServiceHoursDialog mExportServiceHoursDialog;
	 

	  
	  //Image icons
	  private Image importDBImg,exportDBImg,exitImg,queryAllImg,deleteImg,addImg,queryByConditionImg,contactInfoSearchImg,exportContactInfoImg,insuranceSearchImg,exportInsuranceImg,activityDataSearchImg,exportActivityDataImg,serviceHoursSearchImg,exportServiceHoursImg;
	  private UiDbInterface mUiDbInterface = new UiDbInterface();  
	 
	/*  private static MouseAdapter mMouseAdapterListener = new MouseAdapter() {
	      public void mouseDown(MouseEvent e) {
	    	  final Text text = new Text(MainUI.cursor, SWT.NONE);
	          TableItem row = MainUI.cursor.getRow();
	          row.getText(1);
	          int column = MainUI.cursor.getColumn();
	          text.setText(row.getText(column));
	          
	          System.out.println("Ashley get row:" + row.getText(1));
	          System.out.println("Ashley get column:"+ column);
	          System.out.println("Ashley get text:"+text);
	        }
	      }*/

	  public MainUI() {
	    display = new Display();
	    shell = new Shell(display);
	  
	    shell.setLocation(20,20);
	    shell.setImage(ImageCache.getImage("selection_recycle_24.png"));
	    shell.setText("展愛隊資料庫管理系統");
	    shell.setSize(1100, 750);
	    
		mTextColor = display.getSystemColor(SWT.COLOR_WHITE);
		mTextItemColorBlue = display.getSystemColor(SWT.COLOR_BLUE);
  
	   // text = new Text(shell, SWT.BORDER);
	   // text.setBounds(840, 670, 250, 20);
	   // text.setText("Author:Saya Sheng & Ashley Sheng Version:1.0.0");
	   // text.setEditable(false);
        
	    menuBar = new Menu(shell, SWT.BAR);
	    //檔案輸入 settings ++
	  /*  sqlMangMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    sqlMangMenuHeader.setText("&資料庫管理");
	    sqlMangMenu = new Menu(shell, SWT.DROP_DOWN);
	    sqlMangMenuHeader.setMenu(sqlMangMenu);
	    
		importImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/import_icon_16x16.png"));
	    sqlMangImportFromExcelItem = new MenuItem(sqlMangMenu, SWT.PUSH);
	    sqlMangImportFromExcelItem.setText("資料庫輸入");
	    sqlMangImportFromExcelItem.setImage(importImg);
	    sqlMangImportFromExcelItem.addSelectionListener(new MenuItemListener());
	    
	    exportImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/export_icon_16x16.png"));
	    sqlMangExportToExcelItem = new MenuItem(sqlMangMenu, SWT.PUSH);
	    sqlMangExportToExcelItem.setText("資料庫輸出");
	    sqlMangExportToExcelItem.setImage(exportImg);
	    sqlMangExportToExcelItem.addSelectionListener(new MenuItemListener());
	    
	    exitImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/exit_icon_16x16.png"));
	    fileExitItem = new MenuItem(sqlMangMenu, SWT.PUSH);
	    fileExitItem.setText("離開");
	    fileExitItem.setImage(exitImg);
	    fileExitItem.addSelectionListener(new MenuItemListener()); */
	    //檔案輸入 settings --
	    
	    //通訊錄  settings ++
	    contactMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    contactMenuHeader.setText("通訊錄");
	    contactMenu = new Menu(shell, SWT.DROP_DOWN);
	    contactMenuHeader.setMenu(contactMenu);
	  
	    contactInfoSearchImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/contact_search_icon_16x16.png"));
	    contactInfoSearchItem = new MenuItem(contactMenu, SWT.PUSH);
	    contactInfoSearchItem.setImage(contactInfoSearchImg);
	    contactInfoSearchItem.setText("通訊錄查詢");
	    
	    exportContactInfoImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/export_contact_icon_16x16.png"));
	    exportContactInfoItem = new MenuItem(contactMenu, SWT.PUSH);
	    exportContactInfoItem.setImage(exportContactInfoImg);
	    exportContactInfoItem.setText("通訊錄輸出");
	    
	    contactInfoSearchItem.addSelectionListener(new MenuItemListener());
	    exportContactInfoItem.addSelectionListener(new MenuItemListener());
	    //通訊錄 settings --
	    
	    //保險資料 settings ++
	    insuranceMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    insuranceMenuHeader.setText("保險");
	    insuranceMenu = new Menu(shell, SWT.DROP_DOWN);
	    insuranceMenuHeader.setMenu(insuranceMenu);
	    
	    insuranceSearchImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/insurance_icon_16x16.png"));
	    insuranceSearchItem = new MenuItem(insuranceMenu, SWT.PUSH);
	    insuranceSearchItem.setImage(insuranceSearchImg);
	    insuranceSearchItem.setText("保險資料查詢");
	    
	    exportInsuranceImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/export_insurance_icon_16x16.png"));
	    exportInsuranceItem = new MenuItem(insuranceMenu, SWT.PUSH);
	    exportInsuranceItem.setImage(exportInsuranceImg);
	    exportInsuranceItem.setText("保險資料輸出");
	    
	    insuranceSearchItem.addSelectionListener(new MenuItemListener());
	    exportInsuranceItem.addSelectionListener(new MenuItemListener());
	    //保險資料 settings --
	    
	    
	    //活動資料 settings ++
	    activityMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    activityMenuHeader.setText("活動");
	    activityMenu = new Menu(shell, SWT.DROP_DOWN);
	    activityMenuHeader.setMenu(activityMenu);
	    
	    activityDataSearchImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/activity_search_icon_16x16.png"));
	    activityDataSearchItem = new MenuItem(activityMenu, SWT.PUSH);
	    activityDataSearchItem.setImage(activityDataSearchImg);
	    activityDataSearchItem.setText("活動資料查詢");
	        
	    exportActivityDataImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/export_activity_icon_16x16.png"));
	    exportActivityDataItem = new MenuItem(activityMenu, SWT.PUSH);
	    exportActivityDataItem.setImage(exportActivityDataImg);
	    exportActivityDataItem.setText("活動資料輸出");
	    
	    activityDataSearchItem.addSelectionListener(new MenuItemListener());
	    exportActivityDataItem.addSelectionListener(new MenuItemListener());
	    //活動資料 settings --
	    
	    //歷史服務時數 settings ++
	    serviceHoursMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    serviceHoursMenuHeader.setText("服務時數 ");
	    serviceHoursMenu = new Menu(shell, SWT.DROP_DOWN);
	    serviceHoursMenuHeader.setMenu(serviceHoursMenu);
	    
	   
	    serviceHoursSearchImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/service_hours_search_icon_16x16.png"));
	    serviceHoursSearchItem = new MenuItem(serviceHoursMenu, SWT.PUSH);
	    serviceHoursSearchItem.setImage(serviceHoursSearchImg);
	    serviceHoursSearchItem.setText("服務時數查詢");
	        
	    exportServiceHoursImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/export_service_hours_icon_16x16.png"));
	    exportServiceHoursItem = new MenuItem(serviceHoursMenu, SWT.PUSH);
	    exportServiceHoursItem.setImage(exportServiceHoursImg);
	    exportServiceHoursItem.setText("服務時數輸出");
	    
	    serviceHoursSearchItem.addSelectionListener(new MenuItemListener());
	    exportServiceHoursItem.addSelectionListener(new MenuItemListener());
	    //歷史服務時數 settings --
	    
	    editMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    editMenuHeader.setText("Edit");

	    editMenu = new Menu(shell, SWT.DROP_DOWN);
	    editMenuHeader.setMenu(editMenu);

	    editCopyItem = new MenuItem(editMenu, SWT.PUSH);
	    editCopyItem.setText("Copy");

	   
	    editCopyItem.addSelectionListener(new MenuItemListener());

	  
        //Shell configuration
	    shell.setMenuBar(menuBar);
	   
	    //Layout in Shell

	  // Color borderColor = display.getSystemColor(SWT.COLOR_WIDGET_BORDER);
	     Color borderColor = display.getSystemColor(SWT.COLOR_BLACK);
	    
	    shell.setLayout(new GridLayout(1,false));
	    
      //createMainUI(shell);
	    createTopGroup(shell);
	   // createButtomGroup(shell);
	  
	  
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
	      
	      if(((MenuItem) event.widget).getText().equals("通訊錄查詢")) {
	    	  try {
				  mContactInfoSearchDialog = new ContactInfoSearchDialog(display);
	    	  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	      if(((MenuItem) event.widget).getText().equals("通訊錄輸出")){
	    	  try {
	    		  mExportContactInfoDialog = new ExportContactInfoDialog(display);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	 
	      if(((MenuItem) event.widget).getText().equals("保險資料查詢")) {
	    	  try {
				mInsuranceSearchDialog = new InsuranceSearchDialog(display);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	      if(((MenuItem) event.widget).getText().equals("保險資料輸出")) {
	    	  mExportInsuranceDialog = new ExportInsuranceDialog(display);
	      }
	      
	      if(((MenuItem) event.widget).getText().equals("活動資料查詢")) {
	    	  try {
				mActivitySearchDialog = new ActivitySearchDialog(display);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	      if(((MenuItem) event.widget).getText().equals("活動資料輸出")) {
	    	  try {
				mExportActivityDialog = new ExportActivityDialog(display);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	      if(((MenuItem) event.widget).getText().equals("服務時數查詢")) {
	    	  try {
	    		  mServiceHoursSearchDialog = new ServiceHoursSearchDialog(display);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	      if(((MenuItem) event.widget).getText().equals("服務時數輸出")) {
	    	  try {
	    		  mExportServiceHoursDialog = new ExportServiceHoursDialog(display);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	    groupTableTop.setText("資料庫內容與查詢結果");	    
	    boldFont = new Font(groupTableTop.getDisplay(), new FontData( "Arial", 12, SWT.BOLD ) );   
	    groupTableTop.setFont(boldFont);
	    createTopDBLable(groupTableTop);
	    createTopSearchConditionGroup(groupTableTop);
	    creatTopTable(groupTableTop);
	    creatTopButton(groupTableTop); 	    
	}
	
	private void createTopDBLable(Composite topgroup){	
		Composite composite = new Composite(topgroup, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 10;
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
		topDBText.setEnabled(false);
		topDBText.setBackground(mTextColor);
	}
	
	private void createTopSearchConditionGroup(Composite topgroup){
		groupTableTopSeach = new Group(topgroup, SWT.NONE);
		groupTableTopSeach.setLayoutData(new GridData(SWT.FILL,SWT.FILL, true, true));
		groupTableTopSeach.setLayout(new GridLayout(1, true));
		groupTableTopSeach.setText("查詢結果與條件 ");		
		boldFont = new Font(groupTableTop.getDisplay(), new FontData( "Arial", 8, SWT.BOLD ) );   
		groupTableTopSeach.setFont(boldFont);
		createTopSearchForm (groupTableTopSeach); 
		createTopSearchConditionItems(groupTableTopSeach);
	}
	
	private void createTopSearchForm (Composite topgroupsearch){
		Composite composite = new Composite(topgroupsearch, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 5;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        topSearchFormLabel = new Label (composite,SWT.NONE);
		topSearchFormLabel.setText("查詢表單名稱:");
		topSearchFormText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		topSearchFormText.setEditable(false);
		topSearchFormText.setEnabled(false);
		topSearchFormText.setBackground(mTextColor);	
	}
	public static void setSearchFormText(String search_form_name) {
		topSearchFormText.setSize(250,topSearchFormText.getSize().y);
		topSearchFormText.setText(search_form_name);
	}
	
	public static String getSearchGroupYearText() {
		return topSearchGroupYearText.getText();
	}
	public static void setSearchGroupYearText(String group_year) {
		topSearchGroupYearText.setText(group_year);
	}
	
	public static void setSearchGroupExitText (String exit) {
		topSearchGroupExitText.setText(exit);
	}
	public static String getSearchYearText() {
		return topSearchYearText.getText();
	}
	public static void setSearchYearText(String seach_year) {
		topSearchYearText.setText(seach_year);
	}
	public static void setSearchMonthText(String search_month) {
		topSearchMonthText.setText(search_month);
	};
	public static void setSearchNameText (String name) {
		topSearchNameText.setText(name);
	}
	
	public static void resetAllTexts() {
		topSearchFormText.setSize(71,18);
		topSearchFormText.setText("");
		topSearchGroupYearText.setText("");
		topSearchNameText.setText("");
		topSearchYearText.setText("");
		topSearchMonthText.setText("");
		topSearchGroupExitText.setText("");
		topDBText.setSize(71, 18);
		topDBText.setText("");
		//setTopDBText("");
	}
	
	private void createTopSearchConditionItems (Composite topgroupsearch){
		Composite composite = new Composite(topgroupsearch, SWT.NONE);
        GridLayout gridLayout = new GridLayout(12, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 5;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        topSearchGroupYearLabel = new Label(composite,SWT.NONE);
		topSearchGroupYearLabel.setText("組別年度:");
		topSearchGroupYearText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		topSearchGroupYearText.setEditable(false);
		topSearchGroupYearText.setEnabled(false);
		topSearchGroupYearText.setBackground(mTextColor);
		
		topSearchNameLabel = new Label(composite,SWT.NONE);
		topSearchNameLabel.setText("姓名:");
		topSearchNameText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		topSearchNameText.setEditable(false);
		topSearchNameText.setEnabled(false);
		topSearchNameText.setBackground(mTextColor);
		
		topSearchYearLabel = new Label(composite,SWT.NONE);
		topSearchYearLabel.setText("年:");
		topSearchYearText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		topSearchYearText.setEditable(false);
		topSearchYearText.setEnabled(false);
		topSearchYearText.setBackground(mTextColor);
		
		topSearchMonthLabel = new Label(composite,SWT.NONE);
		topSearchMonthLabel.setText("月:");
		topSearchMonthText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		topSearchMonthText.setEditable(false);
		topSearchMonthText.setEnabled(false);
		topSearchMonthText.setBackground(mTextColor);
		
		topSearchGroupExitLabel = new Label(composite,SWT.NONE);
		topSearchGroupExitLabel.setText("包含展友或退隊:");
		topSearchGroupExitText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		topSearchGroupExitText.setEditable(false);
		topSearchGroupExitText.setEnabled(false);
		topSearchGroupExitText.setBackground(mTextColor);
		
	}
	
	private void creatTopTable(Composite topgroup) {
		 topScrolledComposite = new ScrolledComposite (topgroup,SWT.BORDER);
		 GridData gridLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		 gridLayout.heightHint = 520;
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
	        GridLayout gridLayout = new GridLayout(6, false);
	        gridLayout.marginWidth = 0;
	        gridLayout.marginHeight = 0;
	        gridLayout.verticalSpacing = 0;
	        gridLayout.horizontalSpacing = 3;
	        composite.setLayout(gridLayout);

	        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	        composite.setLayoutData(gridData);

	        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);

	        queryAllButton = new Button(composite, SWT.PUSH);  
		    queryAllImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/query_all_icon_32x32.png"));
		    queryAllButton.setImage(queryAllImg);
		    queryAllButton.setText("資料庫查詢");
		    
		 
		    queryByConditionButton = new Button(composite, SWT.PUSH);
		    queryByConditionImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/query_condition_icon_32x32.png"));
		    queryByConditionButton.setImage(queryByConditionImg);
		    queryByConditionButton.setText("資料庫條件查詢");
		    
	
		    deleteButton = new Button(composite, SWT.PUSH);
		    deleteImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/delete_icon_32x32.png"));
		    deleteButton.setImage(deleteImg);
		    deleteButton.setText("資料庫資料刪除");
		    
		    
		    addButton = new Button(composite, SWT.PUSH);
	        addImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/add_icon_32x32.png"));
		    addButton.setText("資料庫資料新增");
		    addButton.setImage(addImg);
		    
		    importDBButton = new Button(composite, SWT.PUSH);
		    importDBImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/import_icon_32x32.png"));
	        importDBButton.setText("資料庫資料輸出");
	        importDBButton.setImage(importDBImg);
		    
		    exportDBButton = new Button(composite, SWT.PUSH);
	        exportDBImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/export_icon_32x32.png"));
	        exportDBButton.setText("資料庫資料輸入");
	        exportDBButton.setImage(exportDBImg);
		    
		    //Listener
		    Listener listener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					if (event.widget == queryAllButton){
						mQueryAllDialog = new QueryAllDialog(display);
					}else if (event.widget == queryByConditionButton){
						
					}else if (event.widget == deleteButton) {
						
					}else if (event.widget == addButton) {
						
					}else if (event.widget == importDBButton){
						 mImportDbDialog = new ImportDbDialog(display);	
					}else if (event.widget == exportDBButton){
						mExportDbDialog = new ExportDbDialog(display); 
					}
				}
		      };

		   queryAllButton.addListener(SWT.Selection, listener);
		   queryByConditionButton.addListener(SWT.Selection, listener);
		   deleteButton.addListener(SWT.Selection, listener);
		   addButton.addListener(SWT.Selection, listener);
		   importDBButton.addListener(SWT.Selection, listener);
		   exportDBButton.addListener(SWT.Selection, listener);
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
	        addImg = new Image(display,MainUI.class.getClassLoader().getResourceAsStream("img/add_icon_32x32.png"));
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
	
	public static void setAllDataToTable1(String[] header, String[][] data, boolean setCellFontColor, int columeNum){
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
		
		if (setCellFontColor) {
			for (int i=0; i < data.length ;i++){
		       	 TableItem item = new TableItem(topTable, SWT.None);
			    	for (int j=0; j<header.length ; j++){ 	
			    		//3-B. We extract one row of data from data object 
			    		oneRowData[j] = data[i][j].toString();

			    		//3-C. Write one row of data gotten from database
			    		item.setText(j,oneRowData[j]);
			    		
			    		// 3-D. Set specified cell color 
			    		
			    		item.setForeground(columeNum, mTextItemColorBlue);
			    	}
				}
		}else {
			for (int i=0; i < data.length ;i++){
				TableItem item = new TableItem(topTable, SWT.None);
				for (int j=0; j<header.length ; j++){ 	
	    		//3-B. We extract one row of data from data object 
	    		oneRowData[j] = data[i][j].toString();

	    		//3-C. Write one row of data gotten from database
	    		item.setText(j,oneRowData[j]);
	    		
	    	}
			}
		}
		 for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
	        	topTable.getColumn(loopIndex).pack();
	          }
		 
		//Add cursor
		 //   cursor = new TableCursor(topTable, SWT.NONE);
		  //  cursor.addMouseListener(mMouseAdapterListener);
		/*  
		 * This function will show entire row is selected.
		 * */
		 /*   
		 topTable.addListener(SWT.MouseDown, new Listener(){
	         public void handleEvent(Event event){
	             Point pt = new Point(event.x, event.y);
	             TableItem item = topTable.getItem(pt);
	          
	             if(item != null) {
	                 for (int col = 0; col < topTable.getColumnCount(); col++) {
	                     Rectangle rect = item.getBounds(col);
	                     if (rect.contains(pt)) {
	                       //  System.out.println("item clicked.");
	                        // System.out.println("column is " + col);
	                         System.out.println("Get row is" + item.getText(1));
	                        
	                     }
	                 }
	             }
	         }
	     }); */
	}
}
