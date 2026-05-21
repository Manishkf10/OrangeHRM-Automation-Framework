package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseTest;

public class LoginPage {

	ActionDriver act;
	
	
	public LoginPage(WebDriver driver) {
		act=BaseTest.getActionDriver();
	}
	
//Locators
	By userName=By.name("username");
	By password=By.name("password");
	By loginButton=By.xpath("//button[@type='submit']");
	By errorMessage=By.xpath("//div[@class=\"orangehrm-login-form\"]/div/div/div/p");
	
	
//Action
	public void login(String uname, String pword) {
		if(!act.isVisible(userName)) {
			act.refreshPage();
		}
		act.enterText(userName, uname);
		act.enterText(password, pword);
		act.clickOn(loginButton);
	}
	public boolean validatingErrorMessage(String expected) {
		return act.compareText(errorMessage, expected);
	}
}
