package com.orangehrm.tesetcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.MyDataProvider;
@Listeners(com.orangehrm.listeners.MyListener.class)
public class TC003validLoginRemoteTest extends BaseTest{

	LoginPage lp;
	HomePage hp;
	
	@BeforeMethod
	public void pageSetup(){
		log.info(this.getClass().getSimpleName()+" is executed");
		lp=new LoginPage(getDriver());
		hp=new HomePage(getDriver());
	}
	
	@Test(dataProvider = "ValidLoginRemote",dataProviderClass = MyDataProvider.class)
	public void validLoginTest(String user,String password) {
		log.info(user+" & "+password);
		lp.login(user, password);
		Assert.assertTrue(hp.isDeshboardDisplayed(), "unable to locate Deshboard");
		hp.logout();
	}
}
