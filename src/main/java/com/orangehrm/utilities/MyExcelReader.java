package com.orangehrm.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyExcelReader {

	public static List<String[]> getCellData(String filePath,String sheetName) {
		List<String[]> data=new ArrayList<>();
		
		try(FileInputStream fi=new FileInputStream(filePath);
			Workbook workbook=WorkbookFactory.create(fi)){
			
			Sheet sheet=workbook.getSheet(sheetName);
			int lastCol= sheet.getRow(0).getLastCellNum();
			
			DataFormatter df=new DataFormatter();
			
			for(Row row:sheet) {
				if(row.getRowNum()==0) {
					continue;
				}
				String[] cellData=new String[lastCol];
				for(int i=0;i<lastCol;i++) {
					cellData[i]=df.formatCellValue(row.getCell(i));
				}
				data.add(cellData);
			}
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("unable to read Excel file from "+filePath);
		}
		
		return data;
	}
}