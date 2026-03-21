package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.orangehrm.base.BaseTest;

public class MyExtentReport {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap=new HashMap<>();
	
	
	public synchronized static ExtentReports getReporter() {
		if(extent==null) {
			String path= System.getProperty("user.dir")+"\\reports\\extentReports\\myreports.html";
			ExtentSparkReporter spark= new ExtentSparkReporter(path);
			spark.config().setReportName("automation reports");
			spark.config().setDocumentTitle("orangehrm reports");
			spark.config().setTheme(Theme.DARK);
			
			extent=new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("operating system",System.getProperty("os.name") );
			extent.setSystemInfo("java version",System.getProperty("java.version") );
			extent.setSystemInfo("user name",System.getProperty("user.name") );
			
		}
		return extent;
	}
	
	//to start test
	public synchronized static ExtentTest startTest(String testName) {
		System.out.println(testName+" is started");
		ExtentTest extentTest= getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}
	
	//to end test
	public synchronized static void endTest() {
		getReporter().flush();
	}
	
	//to get current thread's test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}
	
	//to get name of current test
	public static String getTestName() {
		ExtentTest currentTest=getTest();
		if(currentTest!=null) {
			return currentTest.getModel().getName();
		}
		else {
			return "no test is currently active for this thread";
		}
	}
	
	//log a step
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}
	
	//log a step validation with screenshot
	public static void logStepWithScreenshot(WebDriver driver,String logMessage,String screenshotMessage) {
		getTest().pass(logMessage);
		
		//add screenshot method
		attachSceenshot(driver, screenshotMessage);
	}
	//log a step validation API
	public static void APIlogStep(String logMessage) {
		getTest().pass(logMessage);
	} 

	
	//log a failure
	public static void logFailure(WebDriver driver,String logMessage,String screenshotMessage) {
		String colorMessage=String.format("<span style='color:red;'>%s</span>", logMessage);
		getTest().fail(colorMessage);
		
		//add screenshot method
		attachSceenshot(driver, screenshotMessage);
	}
	//log a failure for API
	public static void APIlogFailure(String logMessage) {
		String colorMessage=String.format("<span style='color:red;'>%s</span>", logMessage);
		getTest().fail(colorMessage);
		
	}
	
	//log a skip
	public static void logSkip(String logMessage) {
		String colorMessage=String.format("<span style='color:orange;'>%s</span>", logMessage);
		getTest().skip(colorMessage);
	}
	
	
	//take screenshot wirh date and time in file
	public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) {
	
		TakesScreenshot ts=(TakesScreenshot)driver;
		File scr=ts.getScreenshotAs(OutputType.FILE);
		//Format date and time for file name
		String timeStamp=  new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		//saving screenshot to a file
		String path=System.getProperty("user.dir")+"\\reports\\screenshots\\"+screenshotName+"_"+timeStamp+".png";
		File dest=new File(path);
		try {
			FileUtils.copyFile(scr, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//converting screenshot to base64 for embedding in the report
		String base64Format=convertToBase64(scr);
		return base64Format;
	}
	
	//utility for coverting screenshot to base64 format
	public static String convertToBase64(File screenshotFile) {
		String base64Format="";
		//read file content to byte array
		byte[] fileContent;
		try {
			fileContent=FileUtils.readFileToByteArray(screenshotFile);
			base64Format=Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Format;
		
	}
	
	
	//to attached screenshot to report using Base64
	public static void attachSceenshot(WebDriver driver, String message) {
		try {
			String screenshotBase64=takeScreenshot(driver, getTestName());
			getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
		} catch (Exception e) {
			getTest().fail("failed to attach screenshot :"+message);
			e.printStackTrace();
		}
	}
	
	
	//Register WebDriver for current Thread
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}
	
	
}
