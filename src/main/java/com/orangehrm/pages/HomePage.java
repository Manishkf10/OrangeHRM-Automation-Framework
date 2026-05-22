package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseTest;

public class HomePage {

	ActionDriver act;
	
	public HomePage(WebDriver driver) {
	
		act=BaseTest.getActionDriver();
	}
	
//Locators
	By deshBoradLogo=By.xpath("//div[@class=\"oxd-topbar-header-title\"]/span");
	By userIcon=By.xpath("//li[@class=\"oxd-userdropdown\"]/span");
	By logoutButton=By.xpath("//ul[@role=\"menu\"]/li[4]");
	
	
//Actions
	public boolean isDeshboardDisplayed() {
		return act.isVisible(deshBoradLogo);
	}
	public void logout() {
		act.clickOn(userIcon);
		act.clickOn(logoutButton);
	}
	public boolean isLoginPageLoaded() {
	    return act.isVisible(logoutButton);
	}
	
}
