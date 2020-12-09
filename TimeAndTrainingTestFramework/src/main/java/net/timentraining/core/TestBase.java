package net.timentraining.core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.timentraining.core.listeners.TestNGListener;
import net.timentraining.core.listeners.WebDriverListener;


@Listeners(TestNGListener.class)
public class TestBase  {
	private ThreadLocal<EventFiringWebDriver> driver = new ThreadLocal<EventFiringWebDriver>();
	protected WebUI webui;
	protected Validator validator;
	
	
	private void init_driver() {
		switch (TestBaseSettings.BROWSER.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver.set(new EventFiringWebDriver(new ChromeDriver()));
			break;
		case "ie":
			WebDriverManager.iedriver().setup();
			driver.set(new EventFiringWebDriver(new InternetExplorerDriver()));
			break;
		default:
			WebDriverManager.chromedriver().setup();
			driver.set(new EventFiringWebDriver(new ChromeDriver()));
			break;
		}
		
		driver.get().manage().timeouts().implicitlyWait(TestBaseSettings.WEBDRIVER_IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		webui = new WebUI(driver.get());
		driver.get().register(new WebDriverListener());
		
		
		
	}
	
	@BeforeSuite(alwaysRun = true)
	protected void beforeSuite() {
		if(TestBaseSettings.API_TEST_ONLY) {
			validator = new ValidateAPI();	
			}else{
				validator = new ValidateUI(webui);
			}
		ExtentReporter.init();
	}
	@AfterSuite(alwaysRun = true)
	protected void afterSuite() {
				
		
		ExtentReporter.save();
	}
	
	@BeforeClass
	protected void beforeClass() {
		
	}
	@AfterClass
	protected void afterClass() {
		
		
	}
	
	@BeforeMethod
	protected void beforeMethod() {
		if(!TestBaseSettings.API_TEST_ONLY)
			init_driver();
	}
	@AfterMethod(alwaysRun = true)
	protected void afterMethod() {
		if(!TestBaseSettings.API_TEST_ONLY)
			driver.get().close();
	}


	

}
