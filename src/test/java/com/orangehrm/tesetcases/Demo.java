package com.orangehrm.tesetcases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
@Listeners(com.orangehrm.listeners.MyListener.class)
public class Demo extends BaseTest{
	
	 @Test(priority=1)
	 void t1() {
		 System.out.println("t1");
	 }
	 @Test(priority=2)
	 void t2() {
		 System.out.println("t2");
	 }
	 @Test(priority=3)
	 void t3() {
		 System.out.println("t3");
	 }

}
