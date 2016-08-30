package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ServiceHoursRegularSearchDetailDiaog extends DialogCombo {
	private static String title = "服務時數統計資料查詢_個人明細";
	private static String iconpath = "img/service_hours_regular_icon_32x32.png";
	private UiDbInterface mUiDbInterface = new UiDbInterface();
	
	public ServiceHoursRegularSearchDetailDiaog(Display display,String groupyear ,String searchyear, String name) throws SQLException{
		  super (display,title,iconpath);		
		  DialogCombo();
		  createServiceHoursRegularSearchDetailInfo(mDialog);
		  createServiceHoursRegularSearchDetailTabFolder(mDialog);
		
		  mServiceHoursGroupYearText.setText(groupyear);
		  mServiceHoursSerachYearText.setText(searchyear);
		  mSerivceHoursAwardNameText.setText(name);
		 
		  mUiDbInterface.getServiceHoursRegularAllHoursSortData(MainUI.getSearchGroupYearText(),MainUI.getSearchYearText());
		  setAllDataToTotalHoursTable(mUiDbInterface.getServiceHoursRegularAllHoursSortHeader(),mUiDbInterface.getServiceHoursRegularAllHoursSortData(groupyear,searchyear));
		  setAllDataServiceHoursTable(mUiDbInterface.getServiceHoursRegularServiceHoursSortHeader(),mUiDbInterface.getServiceHoursRegularServiceHoursSortData(groupyear,searchyear));
		  setAllDataPersonalDetailTable(mUiDbInterface.getServiceHoursRegularPersonalDetailHeader(),mUiDbInterface.getServiceHoursRegularPersonalDetailData(searchyear,name));
		  mDialog.pack();
	      mDialog.open();
	}
   
	private void setAllDataToTotalHoursTable(String[] header, String[][] data){
		
		String [] oneRowData = new String[header.length]; 
		mTableTotalHours = new Table(mScrolledCompositeTotalHours,SWT.NONE);  
        mTableTotalHours.setHeaderVisible(true);
        mTableTotalHours.setLinesVisible(true);
	    mScrolledCompositeTotalHours.setContent(mTableTotalHours);
		mScrolledCompositeTotalHours.setExpandHorizontal(true);
		mScrolledCompositeTotalHours.setExpandVertical(true);
		mScrolledCompositeTotalHours.setAlwaysShowScrollBars(true);
		mScrolledCompositeTotalHours.setMinSize(mTableTotalHours.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		
		for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			TableColumn column = new TableColumn(mTableTotalHours, SWT.NONE);
            column.setText(header[loopIndex]);
            column.setWidth(100);
          }
		
		for (int i=0; i < data.length ;i++){
       	 TableItem item = new TableItem(mTableTotalHours, SWT.None);
	    	for (int j=0; j<header.length ; j++){ 	
	    		//3-B. We extract one row of data from data object 
	    		oneRowData[j] = data[i][j].toString();
               
	    		//3-C. Write one row of data gotten from database
	    		item.setText(j,oneRowData[j]);
	    		//3-D. Colored the rows which matches the search requirement.
	    		if(oneRowData[j].equals(mSerivceHoursAwardNameText.getText()))
	    			item.setForeground(MainUI.mTextItemColorRed);
	    	}
		}
		
		 for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			 mTableTotalHours.getColumn(loopIndex).pack();
	          }
	}	
	
	private void setAllDataServiceHoursTable(String[] header, String[][] data){
		
		String [] oneRowData = new String[header.length]; 
		mTableServiceHours = new Table(mScrolledCompositeServiceHours,SWT.NONE);  
        mTableServiceHours.setHeaderVisible(true);
        mTableServiceHours.setLinesVisible(true);
        mScrolledCompositeServiceHours.setContent(mTableServiceHours);
        mScrolledCompositeServiceHours.setExpandHorizontal(true);
        mScrolledCompositeServiceHours.setExpandVertical(true);
        mScrolledCompositeServiceHours.setAlwaysShowScrollBars(true);
        mScrolledCompositeServiceHours.setMinSize(mTableServiceHours.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			TableColumn column = new TableColumn(mTableServiceHours, SWT.NONE);
            column.setText(header[loopIndex]);
            column.setWidth(100);
          }
		
		for (int i=0; i < data.length ;i++){
       	 TableItem item = new TableItem(mTableServiceHours, SWT.None);
	    	for (int j=0; j<header.length ; j++){ 	
	    		//3-B. We extract one row of data from data object 
	    		oneRowData[j] = data[i][j].toString();
               
	    		//3-C. Write one row of data gotten from database
	    		item.setText(j,oneRowData[j]);
	    		//3-D. Colored the rows which matches the search requirement.
	    		if(oneRowData[j].equals(mSerivceHoursAwardNameText.getText()))
	    			item.setForeground(MainUI.mTextItemColorRed);
	    	}
		}
		
		 for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			 mTableServiceHours.getColumn(loopIndex).pack();
	          }
	}
 
 	private void setAllDataPersonalDetailTable(String[] header, String[][] data){
		
		String [] oneRowData = new String[header.length]; 
		mTablePersonalDetail = new Table(mScrolledCompositePersonalDetail,SWT.NONE);  
		mTablePersonalDetail.setHeaderVisible(true);
		mTablePersonalDetail.setLinesVisible(true);
        mScrolledCompositePersonalDetail.setContent(mTablePersonalDetail);
        mScrolledCompositePersonalDetail.setExpandHorizontal(true);
        mScrolledCompositePersonalDetail.setExpandVertical(true);
        mScrolledCompositePersonalDetail.setAlwaysShowScrollBars(true);
        mScrolledCompositePersonalDetail.setMinSize(mTablePersonalDetail.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
        for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			TableColumn column = new TableColumn(mTablePersonalDetail, SWT.NONE);
         column.setText(header[loopIndex]);
         column.setWidth(100);
       }
		
		for (int i=0; i < data.length ;i++){
    	 TableItem item = new TableItem(mTablePersonalDetail, SWT.None);
	    	for (int j=0; j<header.length ; j++){ 	
	    		//3-B. We extract one row of data from data object 
	    		oneRowData[j] = data[i][j].toString();
            
	    		//3-C. Write one row of data gotten from database
	    		item.setText(j,oneRowData[j]);
	    		//3-D. Colored the rows which matches the search requirement.
	    		//if(oneRowData[j].equals(mSerivceHoursAwardNameText.getText()))
	    			//item.setForeground(MainUI.mTextItemColorRed);
	    	}
		}
		
		 for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			 mTablePersonalDetail.getColumn(loopIndex).pack();
	          }
	}
}
