package com.orangehrm.actiondriver;

import java.awt.Desktop.Action;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseTest;
import com.orangehrm.factory.DriverFactory;
import com.orangehrm.utilities.MyExtentReport;

public class ActionDriver {

	WebDriver driver;
	WebDriverWait wait;
	static ActionDriver act;
	static Logger log=BaseTest.log;
	
	public ActionDriver(WebDriver driver) {
		this.driver=driver;
		wait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(BaseTest.getPpt("explicitWait"))));
		log.info("ActionDriver is initalized");
	}
	
	
	
	
// wait for element visibility
	public WebElement waitForEleVisibility(By by) {
		
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			log.error("unable to located element :"+by.toString()+e.getMessage());
			 throw e;
		}
	}
	
//wait for element clickable
	public WebElement waitForEleClickable(By by) {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(by));
			
		} catch (Exception e) {
			log.error("unable to click on : "+by.toString()+e.getMessage());
			throw e;
		}
	}
	
//combination of Extent and Log4j for simple info logs
	public void logBothinfo(String msg) {
		try {
			log.info(msg);
		} catch (Exception e) {
			System.out.println("logger fail in logBothinfo() from ActionDriver class"+e.getMessage());
		}
		try {
			MyExtentReport.logStep(msg);
		} catch (Exception e) {
			System.out.println("Extent log fail in logBothinfo() from ActionDriver class"+e.getMessage());
		}
	}
	
	
//for click
	public void clickOn(By by) {
		WebElement ele=waitForEleClickable(by);
		
		try {					
			applyColorBorder(ele, "green");
			ele.click();
			log.info("click on : "+by.toString());
			MyExtentReport.logStep("click on : "+by.toString());
		} catch (Exception e) {
			applyColorBorder(ele, "red");
			log.error("unable to click on : "+by.toString()+e.getMessage());
			MyExtentReport.logFailure(DriverFactory.getDriver(), "unable to click on : "+by.toString()+e.getMessage(), by.toString()+" is unable for click");
		}
	}

//for enter text
	public void enterText(By by, String value) {
		WebElement ele=waitForEleClickable(by);
		
		try {
			
			applyColorBorder(ele, "green");
			ele.sendKeys(Keys.CONTROL + "a");
			ele.sendKeys(Keys.DELETE);
			ele.sendKeys(value);
			logBothinfo("enter text : "+value+" in : "+by.toString());
			MyExtentReport.logStep("click on : "+by.toString());
		} catch (Exception e) {
			applyColorBorder(ele, "red");
			log.error("unable to enter text in : "+by.toString()+e.getMessage());
			MyExtentReport.logFailure(DriverFactory.getDriver(), "unable to click on : "+by.toString()+e.getMessage(), by.toString()+" is unable for click");
		}
	}
	
	//get text
	public String getEleText(By by) {
		
		WebElement ele=waitForEleVisibility(by);
		
		try {
			
			applyColorBorder(ele, "green");
			String value=ele.getText();
			logBothinfo("get text : "+value+" in : "+by.toString());
			MyExtentReport.logStep("click on : "+by.toString());
			return ele.getText();
		} catch (Exception e) {
			applyColorBorder(ele, "red");
			log.error("unable to enter text in : "+by.toString()+e.getMessage());
			MyExtentReport.logFailure(DriverFactory.getDriver(), "unable to click on : "+by.toString()+e.getMessage(), by.toString()+" is unable for click");
			return null;
		}
		
	}
	
//for compare text
	public boolean compareText(By by, String expected) {
		
		WebElement ele=waitForEleVisibility(by);
		try {
			
			wait.until(ExpectedConditions.textToBePresentInElement(ele, expected));
			String actual = ele.getText().trim();
			String expect = expected.trim();
			
		
		
			if (actual.equals(expected.trim())) {
				
				applyColorBorder(ele, "green");
				log.info("'" + expected + "' is matched to '" + actual + "' at location :" + by.toString());
				MyExtentReport.attachSceenshot(DriverFactory.getDriver(),"'" + expected + "' is matched to '" + actual + "' at location :" + by.toString());  
							
				return true;
			} else {
				applyColorBorder(ele, "red");
				
				log.info(expected + " is not matched to " + actual + " at location :" + by.toString());
				
				MyExtentReport.logFailure(DriverFactory.getDriver(),
						expected + " is not matched to " + actual + " at location :" + by.toString(),
						"Actual text is not similar to expected");
				
				return false;
			}
		} catch (Exception e) {

			applyColorBorder(ele, "red");
			log.error("unable to compare text : " + expected + e.getMessage());
			MyExtentReport.logFailure(DriverFactory.getDriver(), "unable to compare text : " + expected + e.getMessage(),
					"unable to compare text");
			return false;
		}

	}

	//check visibility
	public boolean isVisible(By by) {
		
		WebElement ele=waitForEleVisibility(by);
		try {
			
			boolean result=ele.isDisplayed();
			if(result) {
				applyColorBorder(ele, "green");
				log.info("element located by : "+by.toString()+" is visible");
				MyExtentReport.logStepWithScreenshot(DriverFactory.getDriver(), "element located by : "+by.toString()+" is visible", "element is visible");
				return true;
			}else {
				applyColorBorder(ele, "red");
				log.info("element located by : "+by+" is not visible");
				MyExtentReport.logFailure(DriverFactory.getDriver(),"element located by : "+by.toString()+" is not visible",by.toString()+" is not displayed");
				return false;
			}
		} catch (Exception e) {
			
			log.error("unable to check visibility of :"+by.toString()+e.getMessage());
			MyExtentReport.logFailure(DriverFactory.getDriver(),"unable to check visibility of :"+by.toString()+e.getMessage(),by.toString()+" is not displayed");
			return false;
		}
	}	
	
	// to color element border
	public WebElement applyColorBorder(WebElement ele, String color) {
		
		try {
			
			String script="arguments[0].style.border='3px solid "+color+"'";
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript(script,ele);
			logBothinfo("Color applied "+color+" to element "+ele.toString());
			return ele;
			
		} catch (Exception e) {
			log.error("unable to color applied "+color+" to element "+ele.toString()+e.getMessage());
			return null;
		}
	}
	
	//to scroll page till a element
	public void scrollPage(By by) {
		
		WebElement ele=waitForEleVisibility(by);
		
		try {
			
			applyColorBorder(ele, "green");
			JavascriptExecutor js =(JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", ele);
			log.info("scroll page to the element " +by.toString());
			MyExtentReport.logStep("scroll page to the element " +by.toString());
		} catch (Exception e) {
			applyColorBorder(ele, "red");
			log.error("unable to scroll page!!! "+e);
			
		}
	}
	
	//select dropDown by visible text
	public void dropDownSelectByVisibleText(By by,String value) {
		
		WebElement ele=waitForEleVisibility(by);
		
		try {			
			new Select(ele).selectByVisibleText(value);
			applyColorBorder(ele, "green");
			logBothinfo("Select dropDown with text : "+value);
		} catch (Exception e) {
			applyColorBorder(ele, "red");
			log.error("unable to select dropdown by text : "+value+e);
		}
		
		
	}
//	===================== DropDown Handling	=============================
	//select dropDown by value
	public void dropDownSelectByValue(By by,String value) {
		
		WebElement ele=waitForEleVisibility(by);
		
		try {
			
			new Select(ele).selectByValue(value);
			applyColorBorder(ele, "green");
			logBothinfo("Select dropDown with value : "+value);
		} catch (Exception e) {
			applyColorBorder(ele, "red");
			log.error("unable to select dropdown value : "+value+"\n"+e);
		}
		
		
	} 
	//select dropDown by index
		public void dropDownSelectByIndex(By by,int value) {
			
			WebElement ele=waitForEleVisibility(by);
			
			try {				
				new Select(ele).selectByIndex(value);
				applyColorBorder(ele, "green");
				logBothinfo("Select dropDown with index : "+value);
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("unable to select dropdown index : "+value+e);
			}
			
			
		}
	//to get all option of dropDown
		public List<String> getDropdownOptions(By by) {
			
			List<String> options=new ArrayList<>();
			WebElement ele=waitForEleVisibility(by);
			
			try {	
				applyColorBorder(ele, "green");
				Select select=new Select(ele);
				
				for(WebElement opt:select.getOptions()) {
					options.add(opt.getText());	
				}
				logBothinfo("Retrieved options from dropdown form  : "+by.toString());
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("Unable to retrieved options from dropdown form  : "+by.toString()+"!!!"+e);
			}
			return options;
		}

//		===================== JavaScript utilities	=============================
		// click using javaScript
		public void clickUsingJS(By by) {
			
			WebElement ele =waitForEleVisibility(by);
					
			try {
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", ele);
				applyColorBorder(ele, "green");
				logBothinfo("click element using javaScript "+by.toString());
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("unable to click element using javaScript "+by.toString()+e);
			}
		}
		
		//scroll to bottom
		public void scrollToBottom() {
			
			try {
				((JavascriptExecutor)driver).executeScript("\"window.scrollTo(0, document.body.scrollHeight);");
				logBothinfo("Scrolled to the bottom of the page");
			} catch (Exception e) {
				log.error("Unable to scrolled to the bottom of the page!!! "+e);
			}
		}
		
		//Switching between windows
		public void switchWindow(String windowTitle) {
			try {
				Set<String> windows=driver.getWindowHandles();
				for(String window:windows) {
					driver.switchTo().window(window);
					if(driver.getTitle().equals(windowTitle)) {
						logBothinfo("switched to window : "+windowTitle);
						return;
					}
				}log.warn("window with title "+windowTitle+" is not found");
			} catch (Exception e) {
				log.error("Unable to switch to window : "+windowTitle+" "+e);
			}
		}

//		===================== Frame Handling	=============================
		//to switch into i-frame
		public void switchToFrame(By by) {
			
			WebElement ele= waitForEleVisibility(by);
			
			try {
				driver.switchTo().frame(ele);
				logBothinfo("switch to frame located by : "+by.toString());
			} catch (Exception e) {
				log.error("Unable to switch to frame located by : "+by.toString()+e);
			}
		}
		
		//to switch back to default content
		public void switchToDefaultContent() {
			try {
				driver.switchTo().defaultContent();
				logBothinfo("switch back to default content ");
			} catch (Exception e) {
				log.error("unable to switch back to default content "+e);
			}
		}
		
//		===================== Alert Handling	=============================
		//to accept alert
		public void acceptAlert() {
			try {
				driver.switchTo().alert().accept();
				logBothinfo("Alert accepted");
			} catch (Exception e) {
				log.error("Unable to accept alert"+"\n"+e);
			}
		}
		//to dismiss alert
		public void dismissAlert() {
			try {
				driver.switchTo().alert().dismiss();;
				logBothinfo("Alert dismissed");
			} catch (Exception e) {
				log.error("Unable to dismiss alert"+"\n"+e);
			}
		}
		//to get alert text
		public String getAlertText() {
			try {
				logBothinfo("getting Alert text");
				return driver.switchTo().alert().getText();
				
			} catch (Exception e) {
				log.error("Unable to getting Alert text"+"\n"+e);
				return "";
			}
		}

//		===================== Browser Handling	=============================
		public boolean waitForPageLoad() throws TimeoutException {

		    return new WebDriverWait(driver,
			        Duration.ofSeconds(Integer.parseInt(BaseTest.getPpt("implicitWait"))))
			        .until(d ->
			                ((JavascriptExecutor) d)
			                        .executeScript("return document.readyState")
			                        .equals("complete"));
		}
		
	
		//to refresh page
		public void refreshPage() {

		    try {

		        driver.navigate().refresh();
		        waitForPageLoad(); 
		        logBothinfo("Page refreshed successfully");
		    } catch (Exception e) {
		        log.error("Unable to refresh page: " + e.getMessage());
		    }
		}
		
		//get current url
		public String getCurrentURL() {
			try {
				String url=driver.getCurrentUrl();
				logBothinfo("current URL is : "+url);
				return url;
			} catch (Exception e) {
				log.error("Unable to track current url"+"\n"+e);
				return "";
			}
		}
		
		//maximize window
		public void maximizeWindow() {
			try {
				driver.manage().window().maximize();
				logBothinfo("window maximize successfully");
			} catch (Exception e) {
				log.error("unable to maximize window"+"\n"+e);
				
			}
		}
		
		
//		===================== Advance Action Handling	=============================	
		//move mouse to element
		public void moveOntoElement(By by) {
			
			WebElement ele=waitForEleVisibility(by);
			try {
				Actions action=new Actions(driver);
				applyColorBorder(ele, "green");		
				action.moveToElement(ele).perform();
				logBothinfo("move to element located by : "+by.toString());
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("Unable to move on element located by : "+by.toString());
			}
		}
		
		//to drag and drop a element
		public void dragDrop(By source, By target) {
			
			WebElement sEle= waitForEleVisibility(source);
			WebElement tEle= waitForEleVisibility(target);
			
			try {
				Actions action=new Actions(driver);
				applyColorBorder(sEle, "green");
				applyColorBorder(tEle, "green");
				action.dragAndDrop(sEle, tEle).perform();
				logBothinfo(source.toString()+" is draged and drop in location "+"'"+target.toString()+"'");
			} catch (Exception e) {
				log.error("Unable to drag and drop element !!!"+e);
			}
		}
		
		//to double click
		public void doubleClicked(By by) {
			
			WebElement ele=waitForEleClickable(by);
			try {
				Actions action=new Actions(driver);
				applyColorBorder(ele, "green");
				action.doubleClick(ele).perform();
				logBothinfo("double clicked on locator : "+by.toString());
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("Unable to double clicked using Actions!!!"+e);
			}
		}
		//to right click
		public void rightClicked(By by) {
			
			WebElement ele=waitForEleClickable(by);
			
			try {
				Actions action=new Actions(driver);
				applyColorBorder(ele, "green");
				action.contextClick(ele).perform();
				logBothinfo("right clicked on locator : "+by.toString());
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("Unable to right clicked using Actions!!!"+e);
			}
		}
		
		//to sendskey with Actions class
		public void enterTextWithActionClass(By by, String value) {
			
			WebElement ele=waitForEleClickable(by);
			
			try {
				Actions action=new Actions(driver);
				applyColorBorder(ele, "green");
				action.sendKeys(ele, value).perform();
				
				logBothinfo("Enter text '"+value+"' in locator : "+by.toString());
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("unable to eneter text using Action(Selenium) class!!!"+e);
			}
					
		}
		//to upload file
		public void uploadFile(By by, String filePath) {
			
			WebElement ele=waitForEleClickable(by);
			
			try {
				applyColorBorder(ele, "green");
				ele.sendKeys(filePath);				
				logBothinfo("file uploaded");
				
			} catch (Exception e) {
				applyColorBorder(ele, "red");
				log.error("Unable to upload file!!!"+e);
			}
		}
}
