package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.factory.DriverFactory;
import com.orangehrm.utilities.EnvironmentManager;
import com.orangehrm.utilities.MyExtentReport;
import com.orangehrm.utilities.MyLogsManager;



public class BaseTest {

	//public WebDriver driver;
	//public static ActionDriver act;
	protected static Properties ppt;

	public static Logger log=MyLogsManager.getLogs(BaseTest.class);
	private static ThreadLocal<ActionDriver> act=new ThreadLocal<>();
	private static ThreadLocal<SoftAssert> soft=ThreadLocal.withInitial(SoftAssert::new);
	
	
	@BeforeSuite
	public void readConfig() {
	
		try {
			ppt=new Properties();
			FileInputStream fi=new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/config.properties");
			ppt.load(fi);
			log.info("config.properties loaded");
		
		} catch (IOException e) {
			log.info("unable to read config.properties");
		}
		
	}

	@BeforeMethod
	@Parameters({"browser"})
	public synchronized void setup(@Optional("chrome")String browser) {
	
		DriverFactory.initDriver(browser);	
		setBrowserProperties();
		initializeActionDriver();
	}
	
	@AfterMethod
	public void tearDown() {
		staticWait(200);
		DriverFactory.quitDriver();
		act.remove();
		soft.remove();
		log.info("WebDriver instance terminated");
		log.info("ActionDriver instance terminated");
		
	}

	
	
	protected void setBrowserProperties() {
		
		String url = EnvironmentManager.getApplicationURL();
		log.info("Selected URL is: " + url);
		getDriver().get(url);
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ppt.getProperty("implicitWait"))));
}
	

	public static void staticWait(int num){
	
		LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(num));
	}
	
	//getter for ppt
	public static String getPpt(String key) { 
		String value=ppt.getProperty(key);
		return value;
	}
	
	public void initializeActionDriver() {
		 if(act.get() == null) {
		        act.set(new ActionDriver(DriverFactory.getDriver()));
		    }
			
		}	
	
	//getter for ActionDriver
		public static ActionDriver getActionDriver() {
			if(act.get()==null) {
				log.info("ActionDriver in not initialized");
			}
			//System.out.println("ActionDriver instance id :"+Thread.currentThread().getId());
			return act.get();
		}

	

	//getter for webDriver
	public  static WebDriver getDriver() {
	    return DriverFactory.getDriver();
	}
	
	//getter for softAssert
	public static SoftAssert getSoftAssert() {
		return soft.get();
	}
	
	
	
}
