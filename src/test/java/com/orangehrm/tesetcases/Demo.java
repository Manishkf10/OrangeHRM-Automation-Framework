package com.orangehrm.tesetcases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.orangehrm.base.BaseTest;
@Listeners(com.orangehrm.listeners.MyListener.class)
public class Demo extends BaseTest{
	
	 @Test(priority=1)
	 void t1() {
		 Faker faker =new Faker();
		 String i=faker.number().digits(5);
		 System.out.println(i);
	 }
	

}
