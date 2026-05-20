package com.orangehrm.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.orangehrm.base.BaseTest;
import com.orangehrm.factory.DriverFactory;
import com.orangehrm.utilities.MyExtentReport;
import com.orangehrm.utilities.RetryAnalyzer;

public class MyListener implements ITestListener,IAnnotationTransformer{
	
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

	@Override
	public void onStart(ITestContext context) {
		MyExtentReport.getReporter();
	}

	@Override
	public void onFinish(ITestContext context) {
		MyExtentReport.endTest();
	}


	@Override
	public void onTestStart(ITestResult result) {
		
		String browser;
		try {
			browser=result.getTestContext().getCurrentXmlTest().getParameter("browser");
		}catch(Exception e) {
			browser="";
		}
		String testName=result.getMethod().getMethodName();
		if (MyExtentReport.getTest() == null) {
	        MyExtentReport.startTest(testName+" ("+browser+")");
	        MyExtentReport.logStep("Test Started : "+testName);
	    }
		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			BaseTest.staticWait(500);
			MyExtentReport.logStepWithScreenshot(DriverFactory.getDriver(), "Test Passed successfully", testName+" -passed ✅");
		}
		else {
			MyExtentReport.APIlogStep("Test Passed successfully : "+ testName+" -passed ✅");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		String failureMessage= result.getThrowable().getMessage();
		MyExtentReport.logStep(failureMessage);
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			MyExtentReport.logFailure(DriverFactory.getDriver(), "Test failed", testName+" -failed ❌");
		}
		else {
			MyExtentReport.APIlogFailure("Test failed : "+ testName+" -failed ❌");
		}
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		String reason = "No reason provided";

	    if (result.getThrowable() != null) {
	        reason = result.getThrowable().getMessage();
	    }
		MyExtentReport.logSkip(testName+" is skipped  ⚠️ & Reason is : "+reason);
		
	}

	

	
}
