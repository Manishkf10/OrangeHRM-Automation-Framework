package com.orangehrm.tesetcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.MyDataProvider;

public class TC001InvalidLoginTest extends BaseTest{

	LoginPage lp;
	@BeforeMethod
	public void pageSetup() {
		log.info(this.getClass().getSimpleName()+" is executed");
		lp=new LoginPage(getDriver());
	}
	
	@Test(dataProvider = "invalidData",dataProviderClass = MyDataProvider.class)
	public void invalidLoginTest(String user,String password) {
		lp.login(user, password);
		boolean result=lp.validatingErrorMessage("Invalid credentials");
		Assert.assertEquals(true, result);
	}
}
