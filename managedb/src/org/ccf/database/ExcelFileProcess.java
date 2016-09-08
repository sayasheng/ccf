package org.ccf.database;

import java.net.URL;
import java.sql.SQLException;
import java.io.IOException;
import java.io.File;

import jxl.Cell;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import java.io.FileOutputStream;

public class ExcelFileProcess {
	DBFunctions db = new DBFunctions();
	private static WritableWorkbook mWorkbook;
	
	
	public boolean exportToExcelForServiceHoursSortFormat(String[][] data, String[] header, String sheetname, int sheetnumber,int data_set_size) throws SQLException {
		Label label;
		boolean result = false;
		int data_set_number;
        int count = -1;
		try {
			Sheet sheet = mWorkbook.createSheet(sheetname,sheetnumber);          
			WritableCellFormat cellFormat = new WritableCellFormat();
			WritableFont myBlackFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			WritableFont myBlueFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			WritableFont myRedFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			
		    CellView cell = new CellView();
		    
		   //Cell format with black color and board line settings
			cellFormat.setFont(myBlackFont); // 指定字型
			cellFormat.setAlignment(Alignment.CENTRE); // 對齊
			cellFormat.setWrap(true); //Wrap text
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cell.setFormat(cellFormat); //Auto cell size
			
			//Cell format with blue color and board line settings
			myBlueFont.setColour(Colour.BLUE);
		    WritableCellFormat myBlueFontCellFormat = new WritableCellFormat(myBlueFont);
		    myBlueFontCellFormat.setAlignment(Alignment.CENTRE);
		    myBlueFontCellFormat.setWrap(true);
		    myBlueFontCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		  //Cell format with red color and board line settings
		    myRedFont.setColour(Colour.RED);
		    WritableCellFormat myRedFontCellFormat = new WritableCellFormat(myRedFont);
		    myRedFontCellFormat.setAlignment(Alignment.CENTRE);
		    myRedFontCellFormat.setWrap(true); //Wrap text
		    myRedFontCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		    //1. Get how many data set (i.e. the row number of a set of data)
		    if((data.length % data_set_size) != 0)
		    	data_set_number = (data.length / data_set_size)+1 ;
		    else
		    	data_set_number = data.length / data_set_size;
		   
		  
		    for (int k = 0 ; k < data_set_number ; k++){
		    	 int shift_data_colume = k * (header.length +1);
		    	 //2. Set header
		    	for (int j = 0; j < header.length ;j++){
					  label = new Label(j+shift_data_colume+1, 1, header[j], cellFormat);
					  ((WritableSheet) sheet).addCell(label);
					  //Auto cell size
					  ((WritableSheet) sheet).setColumnView(j+shift_data_colume+1, 10);
				}
		    
		    	//Note:label function(col,row,data,cellformat)
		    	//Each data set only contains 27 rows (in this case)
		    	for (int i = 0; i < data_set_size; i++){		
		    		count ++ ;
		    		//if count doesn't exceed total data length and keep writing data to excel file.
		    		if(count < data.length) {
		    		for (int j = 0; j <header.length ;j++){
						 //(i+2) parameter is to prevent table header from being erased by data.
						// use different font color for different colume data.
		    			if(j==1 || j==2){
							label = new Label (j+shift_data_colume+1,i+2,data[count][j],myBlueFontCellFormat);
						}else if (j==3) {
							label = new Label (j+shift_data_colume+1,i+2,data[count][j],myRedFontCellFormat);
						}else {
						label = new Label (j+shift_data_colume+1,i+2,data[count][j],cellFormat);
						}
						((WritableSheet)sheet).addCell(label);
					}
		    	  }	
				}

		    }
			
			
    		/**Comment these two lines and rewrite into two individual function is 
			* because we need to add all data in the sheet at once and write into
			* workbook together. 
			**/
			//mWorkbook.write();
		   // mWorkbook.close();
			result = true;
		}catch (WriteException e) {
				e.printStackTrace();
			result = false;
		 }
		
		return result;
	}
	
	public boolean exportToExcelWithHyperlink(String[][] data, String[] header, String sheetname, int sheetnumber, int hyperlink_colume, int hyperlink_start_page)throws SQLException {	
		Label label;
		boolean result = false;
		
		try {
			Sheet sheet = mWorkbook.createSheet(sheetname,sheetnumber);          
			WritableCellFormat cellFormat = new WritableCellFormat();
			WritableFont myFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			WritableFont hyperLinkFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			
		    CellView cell = new CellView();
		    
			cellFormat.setFont(myFont); // 指定字型
			cellFormat.setAlignment(Alignment.LEFT); // 對齊
			cellFormat.setWrap(true); //Wrap text
			cell.setFormat(cellFormat); //Auto cell size
			
			//Hyper link font
			hyperLinkFont.setColour(Colour.BLUE);
			hyperLinkFont.setUnderlineStyle(UnderlineStyle.SINGLE);
		    WritableCellFormat hyperLinkCellFormat = new WritableCellFormat(hyperLinkFont);
		   
			
			for (int i = 0; i < header.length ;i++){
				  label = new Label(i, 0, header[i], cellFormat);
				  ((WritableSheet) sheet).addCell(label);
				  //Auto cell size
				  ((WritableSheet) sheet).setColumnView(i, 8);
			}
			if(sheetnumber != 0) { //build the single sheet to hold detail information
			for (int i = 0; i < data.length; i++){
				for (int j = 0; j <header.length ;j++){
					 //(i+1) parameter is to prevent table header from being erased by data.
					 label = new Label (j,i+1,data[i][j],cellFormat);
					 ((WritableSheet)sheet).addCell(label);
					 //Auto cell size
					 cell=sheet.getColumnView(j);
					  if(cell.getSize() < ((int)data[i][j].toString().length()*350))
						  cell.setSize((int)data[i][j].toString().length()*350);
					 ((WritableSheet) sheet).setColumnView(j, cell);
					// System.out.println("Get data:"+data[i][j].toString());
				}
			 }
			}
			else { //build the 總計 sheet
				for (int i = 0; i < data.length; i++){
					for (int j = 0; j <header.length ;j++){
						//The first sheet(0) is 總計,so hyper link page starts from sheet(1)
						Sheet mSheet = mWorkbook.getSheet(i+hyperlink_start_page); 
					    //(i+1) parameter is to prevent table header from being erased by data.
					    label = new Label (j,i+1,data[i][j].toString(),cellFormat);
						//Insert hyperlink on each row and column 1 (姓名)
					    if (j == hyperlink_colume) {
							WritableHyperlink hl = new WritableHyperlink(j,i+1 ,data[i][j].toString(),(WritableSheet) mSheet,0,0);
							((WritableSheet) sheet).addHyperlink(hl);
							label = new Label (j,i+1,data[i][j].toString(),hyperLinkCellFormat);
							((WritableSheet)sheet).addCell(label);
							 }
					    else {
						 //sheet
						 ((WritableSheet)sheet).addCell(label); 
					    }
						 //Auto cell size
						 cell=sheet.getColumnView(j);
						
						 if(cell.getSize() < ((int)data[i][j].toString().length()*350))
							  cell.setSize((int)data[i][j].toString().length()*350);
						 ((WritableSheet) sheet).setColumnView(j, cell);
						
					}
				}
			}
			/**Comment these two lines and rewrite into two individual function is 
			* because we need to add all data in the sheet at once and write into
			* workbook together. 
			**/
			//mWorkbook.write();
		   // mWorkbook.close();
			result = true;
		}catch (WriteException e) {
				e.printStackTrace();
			result = false;
		 }
		
		return result;
	}
	
   
	
    public boolean compareExcelHeaderWithDB(String filename, String[] dbtable) {
    	boolean matchDB = false;
    	int row = 1; //Only need to get header
 
    	try {	
    		Workbook workbook = Workbook.getWorkbook(new File(filename));
    		Sheet sheet = workbook.getSheet(0);
    		sheet.getRow(0);
    		
    		for (int i=0; i< row ; i++){
		    	for(int j=0; j<dbtable.length; j++){
		    		String myData = sheet.getCell(j,i).getContents();
		    		if(!myData.equals(dbtable[j])) {
		    			matchDB = false ;
		    			break;
		    		}
		    		matchDB = true ;
		    	}
		    	
		    }
    		
    	}catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return matchDB;
    }
    
	public Object [][] importFromExcel(String filename)
	{
		//String fileName = filename+".xls";
		String fileName = filename;
		Object [][] data = null;
		
		try {	
			Workbook workbook = Workbook.getWorkbook(new File(fileName));
		    int sheetNum = workbook.getNumberOfSheets();
		    
		    Sheet sheet = workbook.getSheet(0);
		    
		    int row = sheet.getRows();
		    int col = sheet.getColumns();
		    data = new Object [row][col];
		   
		    for (int i=1; i< row ; i++){
		    	for(int j=0; j<col; j++){
		    		String myData = sheet.getCell(j,i).getContents();
		    		data [i][j]=myData;
		    		//System.out.print(data[i][j].toString());
		    	}	    	
		    }
		   
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		
		return data;
		
	}
	
	public void createWorkbook (String directory, String filename) {
		String file = directory+"\\"+filename+".xls";
		try {
			mWorkbook = Workbook.createWorkbook(new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean writeWorkbook() {
		boolean result = false ;
		try {
			mWorkbook.write();
			result = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean closeWorkbook() {
		boolean result = false;
		try {
			mWorkbook.close();
			result = true ;
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Mainly for export multiple sheet included in an excel file
	 * @param data
	 * @param header
	 * @param sheetname
	 * @param sheetnumber
	 * @return
	 * @throws SQLException
	 */
	public boolean exportToExcel(String[][] data, String[] header, String sheetname, int sheetnumber)throws SQLException {
		Label label;
		boolean result = false;
	
		try {
			
			Sheet sheet = mWorkbook.createSheet(sheetname,sheetnumber);
		    
			//WritableFont myFont = new WritableFont(WritableFont.createFont("標楷體"), 14);        
			WritableFont myFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			//myFont.setColour(Colour.BLACK);            
			WritableCellFormat cellFormat = new WritableCellFormat();
		    CellView cell = new CellView();
		    
			cellFormat.setFont(myFont); // 指定字型
			//cellFormat.setBackground(Colour.WHITE); // 背景顏色
			cellFormat.setAlignment(Alignment.LEFT); // 對齊
			cellFormat.setWrap(true); //Wrap text
			cell.setFormat(cellFormat); //Auto cell size
			
			for (int i = 0; i < header.length ;i++){
				  label = new Label(i, 0, header[i], cellFormat);
				  ((WritableSheet) sheet).addCell(label);
				  //Auto cell size
				  ((WritableSheet) sheet).setColumnView(i, 8);
			}
			
			for (int i = 0; i < data.length; i++){
				for (int j = 0; j <header.length ;j++){
					 //(i+1) parameter is to prevent table header from being erased by data.
					 label = new Label (j,i+1,data[i][j],cellFormat);
					 ((WritableSheet)sheet).addCell(label);
					 //Auto cell size
					 cell=sheet.getColumnView(j);
					  if(cell.getSize() < ((int)data[i][j].toString().length()*350))
						  cell.setSize((int)data[i][j].toString().length()*350);
					 ((WritableSheet) sheet).setColumnView(j, cell);
					// System.out.println("Get data:"+data[i][j].toString());
				}
			}
			/**Comment these two lines and rewrite into two individual function is 
			* because we need to add all data in the sheet at once and write into
			* workbook together. 
			**/
			//mWorkbook.write();
		   // mWorkbook.close();
			result = true;
		}catch (WriteException e) {
				e.printStackTrace();
			result = false;
		 }
		
		return result;
	}
	
	/**
	 * Mainly for export single sheet included in an excel file
	 * @param data
	 * @param header
	 * @param directory
	 * @param filename
	 * @param sheetname
	 * @return
	 * @throws SQLException
	 */
	public boolean exportToExcel(String[][] data, String[] header, String directory, String filename, String sheetname)throws SQLException {
		Label label;
		String file = directory+"\\"+filename+".xls";
		boolean result = false;
		
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(file));
			Sheet sheet = workbook.createSheet(sheetname, 0);
			//WritableFont myFont = new WritableFont(WritableFont.createFont("標楷體"), 14);        
			WritableFont myFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			myFont.setColour(Colour.BLACK);            
			WritableCellFormat cellFormat = new WritableCellFormat();
			CellView cell = new CellView();
		     
			cellFormat.setFont(myFont); // 指定字型
			//cellFormat.setBackground(Colour.WHITE); // 背景顏色
			cellFormat.setAlignment(Alignment.LEFT); // 對齊
			cellFormat.setWrap(true); //Wrap text by "/r/n"
		    cell.setFormat(cellFormat);
		  
			
		    for (int i = 0; i < header.length ;i++){
				 label = new Label(i, 0, header[i].toString(), cellFormat);
				  ((WritableSheet) sheet).addCell(label);
				     //Auto cell size
					((WritableSheet) sheet).setColumnView(i, 8);	
				  //System.out.println("Get data==>"+col[i].toString());
			}
			
			for (int i = 0; i < data.length; i++){
				for (int j = 0; j <header.length ;j++){
					 //(i+1) parameter is to prevent table header from being erased by data.
					 label = new Label (j,i+1,data[i][j].toString(),cellFormat);
					 //sheet
					 ((WritableSheet)sheet).addCell(label); 
					 //Auto cell size
					 cell=sheet.getColumnView(j);
					 
					  if(cell.getSize() < ((int)data[i][j].toString().length()*350))
						  cell.setSize((int)data[i][j].toString().length()*350);
					 ((WritableSheet) sheet).setColumnView(j, cell);
					
				}
			}
				
			workbook.write();
			workbook.close();
			result = true;
		}catch (IOException e) {
			e.printStackTrace();
			result = false;
		}catch (WriteException e) {
				e.printStackTrace();
			result = false;
		 }
		
		return result;
	}
	
	/**
	 * Mainly for export database into an excel file.
	 * @param tablename
	 * @param header
	 * @param directory
	 * @param filename
	 * @return
	 * @throws SQLException
	 */
	public boolean exportToExcel(String tablename, String[] header, String directory, String filename) throws SQLException
	{		
		Object[][]data;	
		data = db.getAllDataFromTable(tablename, true);
		Label label;
		String file = directory+"\\"+filename+".xls";
		boolean result = false;
		//CellView cell = new CellView();
		//Test purpose
		/*
	    for (int i=0; i < serviceHours.length ;i++){
	    	for (int j=0; j<serviceHoursCol.length ; j++){
	    		 System.out.print(""+serviceHours[i][j].toString());
	    	}
	    }*/
		try {
			
			WritableWorkbook workbook = Workbook.createWorkbook(new File(file));
			//WritableSheet sheet = workbook.createSheet(tablename, 0);
			Sheet sheet = workbook.createSheet(tablename, 0);
			//WritableFont myFont = new WritableFont(WritableFont.createFont("標楷體"), 14);  
			WritableFont myFont = new WritableFont(WritableFont.createFont("Calibri"), 12);        
			myFont.setColour(Colour.BLACK);            
			WritableCellFormat cellFormat = new WritableCellFormat();
			
		    cellFormat.setFont(myFont); // 指定字型
			//cellFormat.setBackground(Colour.WHITE); // 背景顏色
			cellFormat.setAlignment(Alignment.LEFT); // 對齊
			//cell.setFormat(cellFormat); //Auto size
			
			for (int i = 0; i < header.length ;i++){
				  label = new Label(i, 0, header[i].toString(), cellFormat);
				  ((WritableSheet) sheet).addCell(label);
				  //System.out.println("Get data==>"+col[i].toString());
			}
			
			for (int i = 0; i < data.length; i++){
				for (int j = 0; j <header.length ;j++){
					 //(i+1) parameter is to prevent table header from being erased by data.
					 label = new Label (j,i+1,data[i][j].toString(),cellFormat);
					 ((WritableSheet) sheet).addCell(label);
					//cell Auto size
					/* cell=sheet.getColumnView(j);
					 cell.setAutosize(true);
					 ((WritableSheet) sheet).setColumnView(j, cell);*/
					
				}
			}
				
			workbook.write();
			workbook.close();
			result = true;
		}catch (IOException e) {
			e.printStackTrace();
			result = false;
		}catch (WriteException e) {
				e.printStackTrace();
			result = false;
		 }
		
		return result;
	}
	
	/**
	 * Mainly for test purpose function.
	 * @param queryResult
	 * @param col
	 * @param FileName
	 * @throws SQLException
	 */
	public void exportQueryResultToExcel(Object[][] queryResult, String[] col, String FileName) throws SQLException
	{		
		Object[][]data = queryResult;	
			
		Label label;
		String filename = FileName+".xls";
		//Test purpose
		/*
	    for (int i=0; i < serviceHours.length ;i++){
	    	for (int j=0; j<serviceHoursCol.length ; j++){
	    		 System.out.print(""+serviceHours[i][j].toString());
	    	}
	    }*/
		try {
			
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
			//WritableSheet sheet = workbook.createSheet(FileName, 0);
			Sheet sheet = workbook.createSheet(FileName, 0);
			WritableFont myFont = new WritableFont(WritableFont.createFont("標楷體"), 14);        
			myFont.setColour(Colour.BLACK);            
			WritableCellFormat cellFormat = new WritableCellFormat();
		//	CellView cell = new CellView();
			
			cellFormat.setFont(myFont); // 指定字型
			//cellFormat.setBackground(Colour.WHITE); // 背景顏色
			cellFormat.setAlignment(Alignment.CENTRE); // 對齊
			//cell.setFormat(cellFormat); //Auto size
			
			for (int i = 0; i < col.length ;i++){
				  label = new Label(i, 0, col[i].toString(), cellFormat);
				  ((WritableSheet) sheet).addCell(label);
			}
			
			for (int i = 0; i < data.length; i++){
				for (int j = 0; j <col.length ;j++){
					label = new Label (j,i+1,data[i][j].toString(),cellFormat);
					 ((WritableSheet) sheet).addCell(label);
					 //Cell auto size
					/* cell=sheet.getColumnView(j);
					 cell.setAutosize(true);
					 ((WritableSheet) sheet).setColumnView(j, cell);*/
					
				}
			}
				
			workbook.write();
			workbook.close();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (WriteException e) {
				e.printStackTrace();
		 }
		
		
	}
}
