package com.orangehrm.tesetcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PIMpage;
import com.orangehrm.utilities.MyDataProvider;
import com.orangehrm.utilities.MyExcelReader;
import com.orangehrm.utilities.MyExtentReport;
@Listeners(com.orangehrm.listeners.MyListener.class)
public class TC004AdditionAndDelete extends BaseTest{

	private LoginPage lp;
	private PIMpage pim;
	
	@BeforeMethod
	void pageSetup() {
		
		lp=new LoginPage(getDriver());
		pim=new PIMpage(getDriver());
	}
	@Test(priority=1,dataProvider = "CreateEmpDetails",dataProviderClass = MyDataProvider.class)
	void addNewEmployee(String fname, String mname, String lname, String id,String username,String pass,String cpass,String img) {
		
		lp.login(MyExcelReader.getSingleCellValue("ValidLoginRemote", 1, 0), MyExcelReader.getSingleCellValue("ValidLoginRemote", 1, 1));
		pim.clickPIMtab();
		pim.clickAddEmployee();
		//pim.addEmployeeImg(img);
		pim.setNewEmpDetails(fname, mname, lname, id, username, pass, cpass);
		MyExtentReport.attachSceenshot(getDriver(), this.getClass().getMethods().toString());	
			
	}
	
	@Test(priority=2)
	public void searchEmployee() {
		
		lp.login(MyExcelReader.getSingleCellValue("ValidLoginRemote", 1, 0), MyExcelReader.getSingleCellValue("ValidLoginRemote", 1, 1));
		pim.clickPIMtab();
		String id=MyExcelReader.getSingleCellValue("CreateEmpDetails", 1, 3);
		pim.searchById(id);
		Assert.assertTrue(pim.verifyEmployeeId(id),"employee id not matched");
		
	}
	@Test(priority=3)
	public void deleteEmployee() {
		
		lp.login(MyExcelReader.getSingleCellValue("ValidLoginRemote", 1, 0), MyExcelReader.getSingleCellValue("ValidLoginRemote", 1, 1));
		pim.clickPIMtab();
		String id=MyExcelReader.getSingleCellValue("CreateEmpDetails", 1, 3);
		pim.searchById(id);
		Assert.assertTrue(pim.verifyEmployeeId(id),"employee id not matched");
		pim.deleteEmp();
		
	}
	
}
