package com.orangehrm.tesetcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;


public class DemoTest extends BaseTest{

	@Test
	public void demo() {
	
		log.info(this.getClass().getSimpleName()+" is executed");
		String actualTitle=getDriver().getTitle();
		System.out.println(actualTitle);
	//	Assert.fail();
	}
}
