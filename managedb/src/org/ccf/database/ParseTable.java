package org.ccf.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ParseTable {

	public void startParsing (String fileName){
		String file = fileName;
		BufferedReader br = null;
		String line = "";
		String fileSplitBy = ",";
		ArrayList<String[]> parsingResult = new ArrayList<String[]>();
		
		try{	
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null){
					String[] data = line.split(fileSplitBy);
					parsingResult.add(data);		
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
		//Test purpose
		
		for(int i=0;i<parsingResult.size();i++){
			   String[] myString= parsingResult.get(i);
			   System.out.println("the length is"+myString.length);
			   for(int j=0;j<myString.length;j++){
			      System.out.print(myString[j]); 
			   }
			   System.out.print("\n");

			}
 
	}
}
