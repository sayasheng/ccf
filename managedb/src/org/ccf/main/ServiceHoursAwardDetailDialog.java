package org.ccf.main;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ServiceHoursAwardDetailDialog extends DialogCombo {
	private static String title = "歷年服務時數與獲獎統計_個人明細";
	private static String iconpath = "img/service_hours_award_icon_32x32.png";
	private UiDbInterface mUiDbInterface = new UiDbInterface();
	
	public ServiceHoursAwardDetailDialog(Display display, String name, String year) throws SQLException{
		  super (display,title,iconpath);		
		  DialogCombo();
		  createDialogNameLabelAndText(mDialog);
		  createDialogTable(mDialog);
		  mSerivceHoursAwardNameText.setText(name);
		  setAllDataToTable(mUiDbInterface.getServiceHoursAwardPersonalDetailHeader(),mUiDbInterface.getServiceHoursAwardPersonalDetail(name,year));
		  mDialog.pack();
	      mDialog.open();
	}
	
	private void setAllDataToTable(String[] header, String[][] data){
		
		String [] oneRowData = new String[header.length]; 
		 mTable = new Table(mScrolledComposite,SWT.BORDER |SWT.FULL_SELECTION);  
		 mTable.setHeaderVisible(true);
		 mTable.setLinesVisible(true);
		 
		 mScrolledComposite.setContent(mTable);
		 mScrolledComposite.setExpandHorizontal(true);
		 mScrolledComposite.setExpandVertical(true);
		 mScrolledComposite.setAlwaysShowScrollBars(true);
		 mScrolledComposite.setMinSize(mTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		 
		for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			TableColumn column = new TableColumn(mTable, SWT.NONE);
            column.setText(header[loopIndex]);
            column.setWidth(100);
          }
		
		for (int i=0; i < data.length ;i++){
       	 TableItem item = new TableItem(mTable, SWT.None);
	    	for (int j=0; j<header.length ; j++){ 	
	    		//3-B. We extract one row of data from data object 
	    		oneRowData[j] = data[i][j].toString();

	    		//3-C. Write one row of data gotten from database
	    		item.setText(j,oneRowData[j]);
	    		
	    		//item.setForeground(1,mTextItemColorBlue);
	    	}
		}
		
		 for (int loopIndex = 0; loopIndex < header.length; loopIndex++) {
			 mTable.getColumn(loopIndex).pack();
	          }
	}	 
}
