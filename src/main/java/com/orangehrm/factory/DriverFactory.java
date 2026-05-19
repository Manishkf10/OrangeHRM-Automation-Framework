package com.orangehrm.factory;

import java.net.URL;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {
	
	private static String browser;
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();

	public static String getConfig(String key) {
		ResourceBundle resource=ResourceBundle.getBundle("config");
		return resource.getString(key);
	}
	
	public static void initDriver() {
		String testExecutionMode=getConfig("testExecutionMode");
		browser=getConfig("browser");
		
		if(testExecutionMode.equals("local")) {
			driver.set(createLocalDriver());
			getDriver().manage().window().maximize();
		}else
			driver.set(createRemoteDriver());
		
	}
	
	private static WebDriver createLocalDriver() {
		
		switch(browser.toLowerCase()) {
		
		case "chrome" 	: return new ChromeDriver();
		case "firefox"	: return new FirefoxDriver();
		case "edge"		:return new EdgeDriver();
		default			:throw new RuntimeException("Invalid browser");
		
		}
	}
	
	private static WebDriver createRemoteDriver() {
		try {
			String gridUrl=getConfig("gridUrl");
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
		return driver.get();
	}
	public static void quitDriver() {

        if (driver.get() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
