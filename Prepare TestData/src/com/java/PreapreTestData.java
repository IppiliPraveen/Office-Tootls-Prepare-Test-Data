package com.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PreapreTestData {
	
	static List<String> fileList = new ArrayList<String>();

    public static void main(String[] args) {
    	
        String filePath = "D:/VPD_Model/TestingSheetTravel.csv";

        PreapreTestData preapreTestData = new PreapreTestData();
        
        String fPath = "C:/Users/ippilip/Eclipse-Practice/Prepare TestData/src/com/html/epidemiology/";
        
        preapreTestData.getFiles(fPath);
        String resData = preapreTestData.testData(filePath,fPath);
        preapreTestData.createCSV("VPD_Epidemiology", resData);
        System.out.println(resData);
    }
    

	
	public List<String> getFiles(String path) {
		
		File[] files = new File(path).listFiles();

		for (File file : files) {
		    if (file.isFile()) {
		    	fileList.add(file.getName());
		    }
		}
		System.out.println(fileList);
		return fileList;
	}
    
	public String testData(String filePath, String fPath) {
		String header = "";
		String rowTestData = "";
		HtmlParser hp = new HtmlParser();
		int count =0;
		for (String fname : fileList) {
			String event_id=fname.substring(fname.lastIndexOf('_')+1,fname.lastIndexOf('.'));
			Map<String, String> dataMap = hp.idsData(fPath+fname);
			try {
				FileReader fr = new FileReader(filePath);
				BufferedReader br = new BufferedReader(fr);
				String ln = "";
				
				while ((ln = br.readLine()) != null) {
					String rowData =event_id;
					List<String> hdrs = Arrays.asList(ln.split(","));
					if (hdrs != null) {
						for (String hd : hdrs) {
							int c = 0;
							if(count==0) {
								header += hd + ",";
							}
							for (String ld : dataMap.keySet()) {
								if (hd.equals(ld)) {
									c++;
									rowData += dataMap.get(ld) + ",";
								}
							}
							if (c == 0) {
								rowData += ",";
							}
						}
					}
					rowTestData += rowData.substring(0, rowData.lastIndexOf(',') - 1) + "\n";
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			count++;
		}
		rowTestData = header.substring(0, header.lastIndexOf(',') - 1)+"\n"+rowTestData;
		return rowTestData.substring(0,rowTestData.length()-1);
	}
	
	public void createCSV(String file, String c) {
		String strPath = "D:/VPD_Model/TestData/";
		String strName = file;
		try {

			File file1 = new File(strPath + "" + strName + ".csv");
			System.out.println(file + " Creating....." + file1.createNewFile());
			
			FileWriter fWriter = new FileWriter(
					"D:/VPD_Model/TestData/"+ strName + ".csv");

			fWriter.write(c);
			fWriter.close();

			System.out.println(file+" test data is created successfully with the content.");
			
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}
}
