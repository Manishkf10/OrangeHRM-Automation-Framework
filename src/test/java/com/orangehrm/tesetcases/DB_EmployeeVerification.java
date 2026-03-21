package com.orangehrm.tesetcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PIMpage;
import com.orangehrm.utilities.DBconnectionUtil;
import com.orangehrm.utilities.MyDataProvider;
import com.orangehrm.utilities.MyExtentReport;

public class DB_EmployeeVerification extends BaseTest{

	private LoginPage lp;
	private PIMpage pim;
	private HomePage hp;
	
	@BeforeMethod
	public void pageSetUp() {
		lp=new LoginPage(getDriver());
		pim=new PIMpage(getDriver());
		hp=new HomePage(getDriver()); 
	}
	
	@Test(dataProvider = "empDetails",dataProviderClass = MyDataProvider.class)
	public void verifyEmployeeDetailsFromDB(String emp_id,String name) {
	
		MyExtentReport.logStep("DB_EmployeeVerification starting");
		lp.login(ppt.getProperty("username"),ppt.getProperty("password"));
		pim.clickPIMtab();
		
		MyExtentReport.logStep("searching for employee");
		pim.searchEmlyee(name);
		String empId=emp_id;
		
		
		//fetching data from DB
		MyExtentReport.logStep("connection to database");
		Map<String, String> empDetails= DBconnectionUtil.getEmloyeeDetails(empId);
		MyExtentReport.logStep("getting employee details from Database");
		String firstName= empDetails.get("firstName");
		String middleName= empDetails.get("middleName");
		String lastName= empDetails.get("lastName");
		String empFirstAndMiddleName=(firstName+" "+middleName).trim();
		MyExtentReport.logStep("varifing employee first and middle name");
	
		//validation
		SoftAssert sf=getSoftAssert();
		sf.assertTrue(pim.verifyEmployeeFirstAndMiddleName(empFirstAndMiddleName), "unable to verify epmloyee firstName And Middle Name"); 
		MyExtentReport.logStep("varifing employee last name");
		sf.assertTrue(pim.verifyEmployeeLastName(lastName),"unable to match employee last name");  
		sf.assertAll();
	
		hp.logout();
		
	}
	
}
