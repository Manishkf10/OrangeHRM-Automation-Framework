package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseTest;

public class PIMpage {

	ActionDriver act;
	
	public PIMpage(WebDriver driver) {
		act=BaseTest.getActionDriver();
	}
	
//Locators
	private By PIMtab=By.xpath("//ul[@class=\"oxd-main-menu\"]/li[2]");
	private By employeeSearch=By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
	private By searchButton=By.xpath("//button[@type=\"submit\"]");
	private By empFirstMiddleName=By.xpath("//div[@role=\"table\"]/div[2]/div/div/div[3]");
	private By empLastName=By.xpath("//div[@role=\"table\"]/div[2]/div/div/div[4]");
	
	
//Action
	public void clickPIMtab() {
		act.clickOn(PIMtab);
	}
	public void searchEmlyee(String name) {
		act.enterText(employeeSearch, name);
		act.clickOn(searchButton);
		BaseTest.staticWait(1000);
		act.scrollPage(empFirstMiddleName);
	}
	public boolean verifyEmployeeFirstAndMiddleName(String empFirstMiddle){
		return act.compareText(empFirstMiddleName, empFirstMiddle);
	}
	public boolean verifyEmployeeLastName(String empLast){
		return act.compareText(empLastName, empLast);
	}

}
