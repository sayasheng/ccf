package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public abstract class DialogCombo {
	protected Shell mDialog ;
	protected Combo mDBListComboYear;
	protected Combo mDBListComboYesOrNo;
	protected Combo mDBListComboInsuranceSearchName;
	protected Combo mDBListComboMonth;
	protected Combo mDBListComboServiceHoursYear;
	protected Label mDBNameLabelYear;
	protected Label mDBNameLabelYesOrNo;
	protected Label mDBNameLabelName;
	protected Label mDBNameLabelMonth;
	protected Label mDBNameLabelServiceHoursYear;
	protected Label mDBNameLabelServiceHoursAwardName;
	protected Label mDBNameLabelServiceHoursGroupYear;
	protected Label mDBNameLabelServiceHoursSearchYear;
	protected Button mOkButton,mCancelButton;
	protected Button mNameSearchButton, mYearSearchButton,  mContactInfoWithAddressButton,mContactInfoWithoutAddressButton,mActivityContainSearchButton,mActivityRegisterSearchButton,
					 mServiceHoursAwardButton,mServiceHoursRegularButton;
	protected String[] mComboYearList;
	protected String[] mComboListYesOrNo = {"否","是"};
	protected String[] mComboInsuranceSearchNameList;
	protected String[] mComboMonthList = {"全部月份","1","2","3","4","5","6","7","8","9","10","11","12"};
	protected String[] mComboServiceHoursYearList;
	protected UiDbInterface mUiDbInterface = new UiDbInterface();
	protected DirectoryDialog mDirectoryDialog;
	protected Button mDirPathButton;
	protected Text mDirPathSelectedText,mSerivceHoursAwardNameText,mServiceHoursGroupYearText,mServiceHoursSerachYearText;
	protected String mContactInfoDialogText,mInsuranceDialogText,mActivityDialogText,mServiceHoursDialogText;
	protected String mLabelTextYear = "選擇年度: ";
	protected String mLabelTextServiceHoursYear = "選擇歷史服務時數及獲獎統計年度: ";
	protected String mLabelTextMonth = "選擇月份: ";
	protected ScrolledComposite  mScrolledComposite,mScrolledCompositeTotalHours,mScrolledCompositeServiceHours,mScrolledCompositePersonalDetail;
	protected Table mTable,mTableTotalHours,mTableServiceHours,mTablePersonalDetail;
	protected int mDialogWidth = 620;
	protected int mDialogHeigh = 100;
	protected MessageBox  mErrorFileDialog;
	protected Composite compositeFolder;
	protected GridLayout gridLayoutFolder;
	protected TabFolder tabFolder;
	protected TabItem itemTotalHours,itemServiceHours,itemPersonalDetail;
  
	
	private String mTitleText;
	private String mIconPath;
	private String mLabelTextYesOrNo = "搜尋結果是否包含展友與退隊: ";
	private String mLabelTextInsuranceSearchName = "選擇姓名: ";
	private Display mDisplay;
	private Label mContactInfoSearchLabel,mInsuranceSearchLabel,mActivitySearchLabel,mServiceHoursSearchLabel;


	public DialogCombo (Display display, String title, String img) {
		this.mDisplay = display;
		this.mTitleText = title;
		this.mIconPath = img;	
	} 
	
	public void DialogCombo(){
		mDialog =
    	        new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    	//The window icon 
    	final Image image = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream(this.mIconPath));
    	mDialog.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    	mDialog.setImage(image);
    	mDialog.setText(this.mTitleText);
    	mDialog.setLocation(300,300);
    	mDialog.setLayout(new GridLayout(1, true));
    	//createDialogDropList(mDialog);
    	//createDialogDropListToProveYesOrNoConditions(mDialog);
    	//createDialogButton(mDialog);
    	//mDialog.pack();
    	//mDialog.open();
	}
	
	protected void createDialogYearDropList(Composite dialogshell){
    	Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);
    	
        mDBNameLabelYear = new Label (composite,SWT.NONE);
        mDBNameLabelYear.setText(mLabelTextYear);
        
        mDBListComboYear = new Combo (composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        mDBListComboYear.setItems(mComboYearList);
    	mDBListComboYear.select(0);	
    }
	
	protected void createDialogMonthDropList(Composite dialogshell){
    	Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);
    	
        mDBNameLabelMonth = new Label (composite,SWT.NONE);
        mDBNameLabelMonth.setText(mLabelTextMonth);
        
        mDBListComboMonth = new Combo (composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        mDBListComboMonth.setItems(mComboMonthList);
        mDBListComboMonth.select(0);
    	
    }
	
	protected void createDialogButton(Composite dialogshell){
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
        
       // mOkButton.addListener(SWT.Selection, mListener);
        //mCancelButton.addListener(SWT.Selection, mListener);
    }
	
	protected void createDialogDropListToProveYesOrNoConditions(Composite dialogshell){
    	Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);
    	
        mDBNameLabelYesOrNo = new Label (composite,SWT.NONE);
        mDBNameLabelYesOrNo.setText(this.mLabelTextYesOrNo);
        
        mDBListComboYesOrNo = new Combo (composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        mDBListComboYesOrNo.setItems(this.mComboListYesOrNo);
        mDBListComboYesOrNo.select(0);
    	
    }
	
	protected void createFileText(Composite dialogshell){
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
      
        //mDirPathButton.addListener(SWT.Selection, mListener);
        
	}
	
	protected void createContactInfoDialog(Composite dialogshell){
		Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 10;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
        mContactInfoSearchLabel = new Label(composite,SWT.NONE);
        //mInsuranceSearchLabel.setText("搜尋條件經由:");
        mContactInfoSearchLabel.setText(mContactInfoDialogText);
        mContactInfoWithAddressButton = new Button(composite, SWT.PUSH);
        mContactInfoWithoutAddressButton = new Button(composite, SWT.PUSH);
        
        Image addressimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/contact_address_icon_48x48.png"));
        mContactInfoWithAddressButton.setText("通訊錄(含地址)");
        mContactInfoWithAddressButton.setImage(addressimg);
        Image withoutaddressimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/contact_without_address_icon_48x48.png"));
        mContactInfoWithoutAddressButton.setText("通訊錄(不含地址)");
        mContactInfoWithoutAddressButton.setImage(withoutaddressimg);
	}
	
	protected void createInsuranceDialog(Composite dialogshell){
		Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 10;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
        mInsuranceSearchLabel = new Label(composite,SWT.NONE);
        //mInsuranceSearchLabel.setText("搜尋條件經由:");
        mInsuranceSearchLabel.setText(mInsuranceDialogText);
        mNameSearchButton = new Button(composite, SWT.PUSH);
        mYearSearchButton = new Button(composite, SWT.PUSH);
        
        Image nameimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/insurance_search_name_icon_48x48.png"));
        mNameSearchButton.setText("個人姓名");
        mNameSearchButton.setImage(nameimg);
        Image yearimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/insurance_search_year_icon_48x48.png"));
        mYearSearchButton.setImage(yearimg);
        mYearSearchButton.setText("年度");
	}
	
	protected void createDialogNameDropList(Composite dialogshell){
    	Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);
    	
        mDBNameLabelName = new Label (composite,SWT.NONE);
        mDBNameLabelName.setText(mLabelTextInsuranceSearchName);
        
        mDBListComboInsuranceSearchName = new Combo (composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        mDBListComboInsuranceSearchName.setItems(mComboInsuranceSearchNameList);
        mDBListComboInsuranceSearchName.select(0);
    	
    }
	
	protected void createActivityDialog(Composite dialogshell){
		Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 10;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
        mActivitySearchLabel = new Label(composite,SWT.NONE);
        //mInsuranceSearchLabel.setText("搜尋條件經由:");
        mActivitySearchLabel.setText(mActivityDialogText);
        mActivityContainSearchButton = new Button(composite, SWT.PUSH);
        mActivityRegisterSearchButton = new Button(composite, SWT.PUSH);
        
        Image containimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/activity_data_icon_48x48.png"));
        mActivityContainSearchButton.setText("活動內容");
        mActivityContainSearchButton.setImage(containimg);
        Image registerimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/activity_register_icon_48x48.png"));
        mActivityRegisterSearchButton.setImage(registerimg);
        mActivityRegisterSearchButton.setText("活動報名");
	}
	

	protected void createServiceHoursialog(Composite dialogshell){
		Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 10;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
        mServiceHoursSearchLabel = new Label(composite,SWT.NONE);
        //mInsuranceSearchLabel.setText("搜尋條件經由:");
        mServiceHoursSearchLabel.setText(mServiceHoursDialogText);
        mServiceHoursAwardButton = new Button(composite, SWT.PUSH);
        mServiceHoursRegularButton = new Button(composite, SWT.PUSH);
        
        Image awardimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/service_hours_award_icon_48x48.png"));
        mServiceHoursAwardButton.setText("歷年服務時數與獲獎統計");
        mServiceHoursAwardButton.setImage(awardimg);
        Image regularimg = new Image(this.mDisplay,DialogCombo.class.getClassLoader().getResourceAsStream("img/service_hours_regular_icon_48x48.png"));
        mServiceHoursRegularButton.setText("服務時數統計");
        mServiceHoursRegularButton.setImage(regularimg);
	}
	
	protected void createDialogServiceHoursAwardYearDropList(Composite dialogshell){
    	Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(6, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);
    	
        mDBNameLabelServiceHoursYear = new Label (composite,SWT.NONE);
        mDBNameLabelServiceHoursYear.setText(mLabelTextServiceHoursYear);
        
        mDBListComboServiceHoursYear = new Combo (composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        mDBListComboServiceHoursYear.setItems(mComboServiceHoursYearList);
        mDBListComboServiceHoursYear.select(0);	
    }
	
	protected void createDialogNameLabelAndText(Composite dialogshell) {
		Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        mDBNameLabelServiceHoursAwardName = new Label (composite,SWT.NONE);
		mDBNameLabelServiceHoursAwardName.setText("姓名: ");
		mSerivceHoursAwardNameText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		mSerivceHoursAwardNameText.setEditable(false);
		mSerivceHoursAwardNameText.setEnabled(false);
		mSerivceHoursAwardNameText.setBackground(MainUI.mTextColor);
		
	}
	
	protected void createDialogTable(Composite dialogshell) {
		 mScrolledComposite = new ScrolledComposite (dialogshell,SWT.BORDER);
		 //GridData gridLayout = new GridData(SWT.FILL, SWT.RESIZE, true, true);
		 GridData gridLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		 gridLayout.heightHint = 250;
		 gridLayout.widthHint = 500;
		 mScrolledComposite.setLayoutData(gridLayout);

		 mTable = new Table(mScrolledComposite,SWT.BORDER |SWT.FULL_SELECTION);  
		 mTable.setHeaderVisible(true);
		 mTable.setLinesVisible(true);
		 
		 mScrolledComposite.setContent(mTable);
		 mScrolledComposite.setExpandHorizontal(true);
		 mScrolledComposite.setExpandVertical(true);
		 mScrolledComposite.setAlwaysShowScrollBars(true);
		 mScrolledComposite.setMinSize(mTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	      
	}
	

	protected void createServiceHoursRegularSearchDetailInfo(Composite dialogshell) {
		Composite composite = new Composite(dialogshell, SWT.NONE);
        GridLayout gridLayout = new GridLayout(6, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 5;
        composite.setLayout(gridLayout);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gridData);

        mDBNameLabelServiceHoursGroupYear =  new Label (composite,SWT.NONE);
        mDBNameLabelServiceHoursGroupYear.setText("組別年度: ");
        mServiceHoursGroupYearText =  new Text(composite,SWT.SINGLE | SWT.BORDER);
        mServiceHoursGroupYearText.setEditable(false);
        mServiceHoursGroupYearText.setEnabled(false);
        mServiceHoursGroupYearText.setBackground(MainUI.mTextColor);
        
        mDBNameLabelServiceHoursSearchYear =  new Label (composite,SWT.NONE);
        mDBNameLabelServiceHoursSearchYear.setText("查詢年度: ");
        mServiceHoursSerachYearText =  new Text(composite,SWT.SINGLE | SWT.BORDER);
        mServiceHoursSerachYearText.setEditable(false);
        mServiceHoursSerachYearText.setEnabled(false);
        mServiceHoursSerachYearText.setBackground(MainUI.mTextColor);
        
        mDBNameLabelServiceHoursAwardName = new Label (composite,SWT.NONE);
		mDBNameLabelServiceHoursAwardName.setText("姓名: ");
		mSerivceHoursAwardNameText = new Text(composite,SWT.SINGLE | SWT.BORDER);
		mSerivceHoursAwardNameText.setEditable(false);
		mSerivceHoursAwardNameText.setEnabled(false);
		mSerivceHoursAwardNameText.setBackground(MainUI.mTextColor);	 
	}
	
	protected void createServiceHoursRegularSearchDetailTabFolder(Composite dialogshell) {
		tabFolder = new TabFolder(dialogshell, SWT.NONE | SWT.BORDER);
        GridData gridLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridLayout.heightHint = 250;
	    gridLayout.widthHint = 350;
		tabFolder.setLayoutData(gridLayout);
		
		mScrolledCompositeTotalHours = new ScrolledComposite (tabFolder,SWT.BORDER );
		mScrolledCompositeTotalHours.setExpandHorizontal(true);
	    mScrolledCompositeTotalHours.setExpandVertical(true);
		itemTotalHours = new TabItem(tabFolder, SWT.NONE);  
        itemTotalHours.setText("總時數排序");
        itemTotalHours.setControl(mScrolledCompositeTotalHours);
        
		mTableTotalHours = new Table(mScrolledCompositeTotalHours,SWT.NONE);  
        mTableTotalHours.setHeaderVisible(true);
        mTableTotalHours.setLinesVisible(true);
	    mScrolledCompositeTotalHours.setContent(mTableTotalHours);
		mScrolledCompositeTotalHours.setExpandHorizontal(true);
		mScrolledCompositeTotalHours.setExpandVertical(true);
		mScrolledCompositeTotalHours.setAlwaysShowScrollBars(true);
		mScrolledCompositeTotalHours.setMinSize(mTableTotalHours.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		
		mScrolledCompositeServiceHours = new ScrolledComposite (tabFolder,SWT.BORDER );
	    mScrolledCompositeServiceHours.setExpandHorizontal(true);
	    mScrolledCompositeServiceHours.setExpandVertical(true);
		itemServiceHours = new TabItem(tabFolder, SWT.NONE);   
		itemServiceHours.setText("服務時數排序");
		itemServiceHours.setControl(mScrolledCompositeServiceHours);
		mTableServiceHours = new Table(mScrolledCompositeServiceHours,SWT.NONE);  
        mTableServiceHours.setHeaderVisible(true);
        mTableServiceHours.setLinesVisible(true);
        mScrolledCompositeServiceHours.setContent(mTableServiceHours);
        mScrolledCompositeServiceHours.setExpandHorizontal(true);
        mScrolledCompositeServiceHours.setExpandVertical(true);
        mScrolledCompositeServiceHours.setAlwaysShowScrollBars(true);
        mScrolledCompositeServiceHours.setMinSize(mTableServiceHours.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		mScrolledCompositePersonalDetail = new ScrolledComposite (tabFolder,SWT.BORDER );
        mScrolledCompositePersonalDetail.setExpandHorizontal(true);
        mScrolledCompositePersonalDetail.setExpandVertical(true);
		itemPersonalDetail = new TabItem(tabFolder, SWT.NONE);  
		itemPersonalDetail.setText("個人活動明細");
		itemPersonalDetail.setControl(mScrolledCompositePersonalDetail);
		mTablePersonalDetail = new Table(mScrolledCompositePersonalDetail,SWT.NONE);  
		mTablePersonalDetail.setHeaderVisible(true);
		mTablePersonalDetail.setLinesVisible(true);
        mScrolledCompositePersonalDetail.setContent(mTablePersonalDetail);
        mScrolledCompositePersonalDetail.setExpandHorizontal(true);
        mScrolledCompositePersonalDetail.setExpandVertical(true);
        mScrolledCompositePersonalDetail.setAlwaysShowScrollBars(true);
        mScrolledCompositePersonalDetail.setMinSize(mTablePersonalDetail.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		  
        tabFolder.setSelection(0);
        
        tabFolder.addSelectionListener(new SelectionListener() {
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			 widgetSelected(e);	
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			}
  	    });
	}
	
	

	
}
