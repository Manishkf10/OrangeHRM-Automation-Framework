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
	private By employeeSearchById=By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input");
	private By searchButton=By.xpath("//button[@type=\"submit\"]");
	private By empFirstMiddleName=By.xpath("//div[@role=\"table\"]/div[2]/div/div/div[3]");
	private By empLastName=By.xpath("//div[@role=\"table\"]/div[2]/div/div/div[4]");
	private By addEmployee=By.linkText("Add Employee");
	private By addEmployeeImg=By.xpath("//img[@class=\"employee-image\"]");
	private By EmpFName=By.xpath("//input[@placeholder=\"First Name\"]");
	private By EmpMName=By.xpath("//input[@placeholder='Middle Name']");
	private By EmpLName=By.xpath("//input[@placeholder='Last Name']");
	private By EmpId=By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input");
	private By createLoginDetails=By.xpath("//p[text()='Create Login Details']/following-sibling::div");
	private By userName=By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input");
	private By statusEnabled=By.xpath("//label[text()='Enabled']/input");
	private By password=By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input");
	private By confirmPassword=By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input");
	private By submit=By.xpath("//button[@type='submit']");
	private By resultEmpId=By.xpath("//div[@role='table']/div[2]/div/div/div[2]/div");
	private By deleteEmpButtton=By.xpath("//div[@role='table']/div[2]/div/div/div[9]//button[2]");
	private By confirmDelete=By.xpath("//div[@class='orangehrm-modal-footer']/button[2]");
			
	
	
	
	
	
	
//Action
	public void clickPIMtab() {
		act.clickOn(PIMtab);
	}
	public void searchEmlyee(String name) {
		act.enterText(employeeSearch, name);
		act.clickOn(searchButton);
		//BaseTest.staticWait(1111);
		act.scrollPage(empFirstMiddleName);
	}
	public boolean verifyEmployeeFirstAndMiddleName(String empFirstMiddle){
		return act.compareText(empFirstMiddleName, empFirstMiddle);
	}
	public boolean verifyEmployeeLastName(String empLast){
		return act.compareText(empLastName, empLast);
	}
	public void clickAddEmployee() {
		act.clickOn(addEmployee);
	}
	public void addEmployeeImg(String path) {
		act.clickOn(addEmployeeImg);
		act.enterText(addEmployeeImg, path);
	}
	public void setNewEmpDetails(String fname,String mName,String lname,String empId,String username,String pass, String conPass) {
		act.enterText(EmpFName, fname);
		act.enterText(EmpMName, mName);
		act.enterText(EmpLName, lname);
		act.clickOn(createLoginDetails);
		act.enterText(EmpId, empId);;
		act.enterText(userName, username);	
		act.enterText(password, pass);
		act.enterText(confirmPassword, conPass);
		
		act.clickOn(submit);
		BaseTest.staticWait(3000);
		
		
	}
	public void searchById(String id) {
		act.enterText(employeeSearchById, id);
		act.clickOn(searchButton);
		act.scrollPage(resultEmpId);
	}
	
	public boolean verifyEmployeeId(String id){
		return act.compareText(resultEmpId, id);
	}
	
	public void deleteEmp() {
		act.clickOn(deleteEmpButtton);
		BaseTest.staticWait(2000);
		act.clickOn(confirmDelete);
	}
	
	 

}
