package org.ccf.database;

import java.sql.SQLException;
import java.io.IOException;
import java.io.File;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import java.io.FileOutputStream;

public class ExcelFileProcess {
	DBFunctions db = new DBFunctions();
	private static WritableWorkbook mWorkbook;

   
	
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
			//myFont.setColour(Colour.BLACK);            
			WritableCellFormat cellFormat = new WritableCellFormat();
		    CellView cell = new CellView();
		    
			//cellFormat.setFont(myFont); // 指定字型
			//cellFormat.setBackground(Colour.WHITE); // 背景顏色
			cellFormat.setAlignment(Alignment.CENTRE); // 對齊
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
			//myFont.setColour(Colour.BLACK);            
			WritableCellFormat cellFormat = new WritableCellFormat();
			CellView cell = new CellView();
		     
			//cellFormat.setFont(myFont); // 指定字型
			//cellFormat.setBackground(Colour.WHITE); // 背景顏色
			cellFormat.setAlignment(Alignment.CENTRE); // 對齊
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
			//myFont.setColour(Colour.BLACK);            
			WritableCellFormat cellFormat = new WritableCellFormat();
			
			//cellFormat.setFont(myFont); // 指定字型
			//cellFormat.setBackground(Colour.WHITE); // 背景顏色
			cellFormat.setAlignment(Alignment.CENTRE); // 對齊
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
