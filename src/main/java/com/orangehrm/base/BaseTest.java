package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.MyExtentReport;
import com.orangehrm.utilities.MyLogsManager;



public class BaseTest {

	//public WebDriver driver;
	//public static ActionDriver act;
	protected static Properties ppt;
	public static Logger log=MyLogsManager.getLogs(BaseTest.class);
	
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> act=new ThreadLocal<>();
	private static ThreadLocal<SoftAssert> soft=ThreadLocal.withInitial(SoftAssert::new);
	
	
	@BeforeSuite
	public void readConfig() {
	
		try {
			ppt=new Properties();
			FileInputStream fi=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties");
			ppt.load(fi);
			log.info("config.properties loaded");
		
		} catch (IOException e) {
			log.info("unable to read config.properties");
		}
		
	}

	@BeforeMethod
	public synchronized void setup() {
		launchBrowser();
		setBrowserProperties();
		createActionDriverOnce();
	}
	
	@AfterMethod
	public void tearDown() {
		staticWait(200);
		if(driver!=null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				log.info("unable to quit driver");
			}
		}
		driver.remove();
		act.remove();
		log.info("WebDriver instance terminated");
		log.info("ActionDriver instance terminated");
		
	}
	
	protected void launchBrowser() {
		String browser = ppt.getProperty("browser");
		log.info("selected browser is :" + browser);
		switch (browser.toLowerCase()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			
			chromeOptions.addArguments("--window-size=1920,1080");
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-infobars");
			chromeOptions.addArguments("--disable-extensions");
			chromeOptions.addArguments("--remote-allow-origins=*");
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--headless=new");
			
			driver.set(new ChromeDriver(chromeOptions));
			MyExtentReport.registerDriver(getDriver());
			break;
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--width=1920");
			firefoxOptions.addArguments("--height=1080");
			firefoxOptions.addArguments("--headless");
			driver.set(new FirefoxDriver(firefoxOptions));
			MyExtentReport.registerDriver(getDriver());
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--window-size=1920,1080");
			edgeOptions.addArguments("--disable-notifications");
			edgeOptions.addArguments("--no-sandbox");
			edgeOptions.addArguments("--disable-dev-shm-usage");
			edgeOptions.addArguments("--headless=new");
			driver.set(new EdgeDriver(edgeOptions));
			MyExtentReport.registerDriver(getDriver());
			break;
		default:
			log.info("unable to launch browser");
		}
		log.info("WebDriver instance initialized");
		//System.out.println("WebDriver instance id :" + Thread.currentThread().getId());
	}
	
	protected void setBrowserProperties() {
		String url= ppt.getProperty("url");
		log.info("selected url is :"+url);
		getDriver().get(url);
		//getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ppt.getProperty("implicitWail"))));
	}
	
	public static void staticWait(int num){
		//int seconds=Integer.parseInt(ppt.getProperty("staticWait"));
		LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(num));
	}
	
	//getter for ppt
	public static String getPpt(String key) { 
		String value=ppt.getProperty(key);
		return value;
	}
	
	//getter for webDriver
	public static WebDriver getDriver() {
		if(driver.get()==null) {
			log.info("WebDriver not initialized");
		}
		return driver.get();
	}
	
	//getter for ActionDriver
		public static ActionDriver getActionDriver() {
			if(act.get()==null) {
				log.info("ActionDriver in not initialized");
			}
			//System.out.println("ActionDriver instance id :"+Thread.currentThread().getId());
			return act.get();
		}

	//singletoneactionDriver
	public void createActionDriverOnce() {
		
			act.set(new ActionDriver(getDriver()));
		
	}
	//getter for softAssert
	public SoftAssert getSoftAssert() {
		return soft.get();
	}
	
	
	
}
