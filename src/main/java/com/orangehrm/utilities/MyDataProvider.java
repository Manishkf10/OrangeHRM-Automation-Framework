package com.orangehrm.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;

public class MyDataProvider {

	
	private static final String FILE_PATH=System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\testData1.xlsx";
	
	@DataProvider(name="ValidLoginLocal")
	public static Object[][] getValidLoginData(){
		return getSheetData("ValidLoginLocal");
	}
	@DataProvider(name="ValidLoginRemote")
	public static Object[][] getValidLoginDataForRemote(){
		return getSheetData("ValidLoginRemote");
	}
	@DataProvider(name="invalidData")
	public static Object[][] getInvalidLoginData(){
		return getSheetData("InvalidData");
	}
	@DataProvider(name="empDetails")
	public static Object[][] getEmpDetails(){
		return getSheetData("empDetails");
	}
	@DataProvider(name="CreateEmpDetails")
	public static Object[][] CreateEmpDetails(){
		return getSheetData("CreateEmpDetails");
	}
	
	
	private static Object[][] getSheetData(String sheetName){
		List<String[]> sheetData= MyExcelReader.getCellData(FILE_PATH, sheetName);
		
		Object[][] data=new Object[sheetData.size()][];
		
		for(int i=0;i<sheetData.size();i++) {
			data[i]=sheetData.get(i);
		}
		return data;
	}
}
