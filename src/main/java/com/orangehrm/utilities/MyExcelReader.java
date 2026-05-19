package com.orangehrm.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	private static final String FILE_PATH=System.getProperty("user.dir")+"/src/test/resources/testdata/testData1.xlsx";


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
	
	public static String getSingleCellValue(String sheetname, int rownum, int col) {
		
		
		try(
				FileInputStream fi=new FileInputStream(FILE_PATH);
				Workbook book=WorkbookFactory.create(fi)){
			Sheet sheet=book.getSheet(sheetname);
			if(sheet==null) {
				throw new IllegalArgumentException("sheet not found : "+sheetname);
			}
			Row row=sheet.getRow(rownum);
			
			
			if(row==null) {
				throw new IllegalArgumentException("row not found at : "+row);
			}
			if(row.getRowNum()==0) {
				throw new IllegalArgumentException("Are you searching for header?");
			}
			Cell cell=row.getCell(col);
			
			if(cell==null) {
				throw new IllegalArgumentException("cell is empty");
			}
			DataFormatter df=new DataFormatter();
			 return df.formatCellValue(cell);
			
			}
		
		catch(IOException e) {
			
			throw new IllegalArgumentException("unable to read cellvalue");
		}
		
	}
	
	
}