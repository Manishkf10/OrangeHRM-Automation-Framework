package com.orangehrm.factory;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.orangehrm.base.BaseTest;

public class DriverFactory {
	
	private static String browser;
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();
	public static Logger log=BaseTest.log;
	
	private static boolean isHeadless() {
	    return Boolean.parseBoolean(getConfig("headless"));
	}

	public static String getConfig(String key) {
		ResourceBundle resource;
		try {
			resource=ResourceBundle.getBundle("config");
			log.info("config file access successfull");
			return resource.getString(key);
			}catch(Exception e) {
				log.info("unaable to read config file in Driverfactory class");
				return null;
			}
		
		
	}
	
	public static void initDriver(String setBrowser) {
		String testExecutionMode=getConfig("testExecutionMode");
		browser=setBrowser;
		log.info("Test execution runiing on "+browser);
		if(testExecutionMode.equals("local")) {
			driver.set(createLocalDriver(browser));
			//getDriver().manage().window().maximize();
		}else
			driver.set(createRemoteDriver(browser));
		
	}
	
	private static WebDriver createLocalDriver(String browser) {
		log.info("Test execution runiing on "+browser);
		switch (browser.toLowerCase()) {

		case "chrome":
			ChromeOptions options = new ChromeOptions();
			if (isHeadless()) {
				options.addArguments("--headless=new");
				options.addArguments("--disable-gpu");
			}
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--window-size=1920,1080");

			return new ChromeDriver(options);

		case "firefox":
			FirefoxOptions f_options = new FirefoxOptions();

			if (isHeadless()) {
				f_options.addArguments("--headless");
			}

			f_options.addArguments("--width=1920");
			f_options.addArguments("--height=1080");
			return new FirefoxDriver(f_options);
			
		case "edge":
			EdgeOptions e_options = new EdgeOptions();

			if (isHeadless()) {
				e_options.addArguments("--headless=new");
				e_options.addArguments("--disable-gpu");
			}

			e_options.addArguments("--no-sandbox");
			e_options.addArguments("--disable-dev-shm-usage");
			e_options.addArguments("--window-size=1920,1080");
			return new EdgeDriver(e_options);
		default:
			throw new RuntimeException("Invalid browser");

		}
	}
	
	private static WebDriver createRemoteDriver(String browser) {
		try {
			String gridUrl=getConfig("gridUrl");
			log.info(" gridUrl :"+gridUrl);
			log.info("Test execution runiing on "+browser);
			switch(browser.toLowerCase()) {
			
			case "chrome":	ChromeOptions c_option=new ChromeOptions();
							c_option.addArguments("--headless=new");
							c_option.addArguments("--window-size=1920,1080");
							return new RemoteWebDriver(new URL(gridUrl),c_option);
			case "edge":		EdgeOptions e_option=new EdgeOptions();
							e_option.addArguments("--headless=new");
							e_option.addArguments("--window-size=1920,1080");
							return new RemoteWebDriver(new URL(gridUrl),e_option);
			case "firefox":	FirefoxOptions f_option=new FirefoxOptions();
							f_option.addArguments("--headless");
							f_option.addArguments("--width=1920");
							f_option.addArguments("--height=1080");
							return new RemoteWebDriver(new URL(gridUrl),f_option);
			default		:	 throw new RuntimeException("Invalid browser");
			}
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static WebDriver getDriver() {
		if(driver.get()==null) {
			 throw new RuntimeException("WebDriver is not initialized");
		}
		log.info("WebDriver is initialized");
		return driver.get();
	}
	public static void quitDriver() {

        if (driver.get() != null) {
            getDriver().quit();
            driver.remove();
            log.info("WebDriver terminated");
        }
    }
}
