package net.timentraining.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebUI {
	
	private EventFiringWebDriver driver;
	
	
	
	public WebUI(EventFiringWebDriver driver) {
		this.driver=driver;
	}
	
	
	//WAIT
	private WebDriverWait get_wait_obj() {
		return new WebDriverWait(driver, TestBaseSettings.WEBDRIVER_EXPLICIT_TIMEOUT);
	}
	public void wait_for_element_to_get_visible(By by) {
		get_wait_obj().until(ExpectedConditions.visibilityOfElementLocated(by));
	}	
	
	
	//NAVIGATE
	public void launch(String url) {
		driver.get(url);
		ExtentReporter.reportInfo("website launched: "+ url);
	}
	public void navigate(String url) {
		launch(url);
	}
	public void navigate_back() {
		driver.navigate().back();
	}
	public void navigate_forward() {
		driver.navigate().forward();
	}
	public void refresh_page() {
		driver.navigate().refresh();
	}
	
	
	//FIND
	public WebElement find_by_id(String id) {
		WebElement element = null;
		try {
		element = driver.findElement(By.id(id));	
		} catch (Exception e) {
			ExtentReporter.reportError(e.getMessage());
		}
		
		return element;
	}
	public WebElement find_by_name(String name) {
		WebElement element = null;
		try {
		element = driver.findElement(By.name(name));	
		} catch (Exception e) {
			ExtentReporter.reportError(e.getMessage());
		}
		
		return element;
	}
	public WebElement find_by_className(String className) {
			WebElement element = null;
			try {
			element = driver.findElement(By.className(className));	
			} catch (Exception e) {
				ExtentReporter.reportError(e.getMessage());
			}
			
			return element;
	}
	public WebElement find_by_xpath(String xpath) {
		WebElement element = null;
		try {
		element = driver.findElement(By.xpath(xpath));	
		} catch (Exception e) {
			ExtentReporter.reportError(e.getMessage());
		}
		
		return element;
	}
	public List<WebElement> find_elements_by_xpath(String xpath) {
		List<WebElement> element = new ArrayList<WebElement>();
		try {
		element = driver.findElements(By.xpath(xpath));	
		} catch (Exception e) {
			ExtentReporter.reportError(e.getMessage());
		}
		
		return element;
	}
	
	
	
	
	
	//CLICKS
	
	public void click_element_by_visible_text(String text) {
		try {
			List<WebElement> elements = find_elements_by_xpath("//*[contains(text(),'"+text+"')]");
			if(elements.size()>1) {
				throw new RuntimeException("Cannot click on element. Multiple element found by visible text ["+ text +"]");
			}else {
				click_element(elements.get(0));
			}
		} catch (Exception e) {
			ExtentReporter.reportError(e.getMessage());
		}
		
	}
	

	public void click_element(WebElement element) {
		element.click();
		String type = null;
		
		try {
			type = element.getText();
		} catch (Exception e) {
			
		}
		
		if(type == null) {
			type = element.getTagName();
		}
		
		ExtentReporter.reportInfo("Clicked on element. [tag:"+ type +"]");
	}
	
	public void click_element_by_id(String id) {
		find_by_id(id).click();
		ExtentReporter.reportInfo("Clicked on element by id. [ID:"+id+"]");
	}
	public void click_element_by_name(String name) {
		find_by_name(name).click();
		ExtentReporter.reportInfo("Clicked on element by name. [NAME:"+name+"]");
	}
	public void click_element_by_className(String className) {
		find_by_className(className).click();
		ExtentReporter.reportInfo("Clicked on element by className. [CLASSNAME:"+className+"]");
	}
	public void click_element_by_xpath(String xpath) {
		find_by_xpath(xpath).click();
		ExtentReporter.reportInfo("Clicked on element by xpath. [XPATH:"+xpath+"]");
	}
	
	//SENDKEYS / ENTER TEXT
	public void enter_text_by_id(String id, String textToEnter) {
		WebElement e = find_by_id(id);
		e.clear();
		e.sendKeys(textToEnter);
		ExtentReporter.reportInfo("Entered text on element by id. [ID:"+id+" TEXT: "+textToEnter+"]");
	}
	public void enter_text_by_name(String name, String textToEnter) {
		WebElement e = find_by_name(name);
		e.clear();
		e.sendKeys(textToEnter);
		ExtentReporter.reportInfo("Entered text on element by name. [NAME:"+name+" TEXT: "+textToEnter+"]");
	}
	public void enter_text_by_className(String className, String textToEnter) {
		WebElement e = find_by_className(className);
		e.clear();
		e.sendKeys(textToEnter);
		ExtentReporter.reportInfo("Entered text on element by className. [CLASSNAME:"+className+" TEXT: "+textToEnter+"]");
	}
	public void enter_text_by_xpath(String xpath, String textToEnter) {
		WebElement e = find_by_xpath(xpath);
		e.clear();
		e.sendKeys(textToEnter);
		ExtentReporter.reportInfo("Entered text on element by xpath. [XPATH:"+xpath+" TEXT: "+textToEnter+"]");
	}
	
	
	//READ / GET TEXT
	public String read_text_from_element_by_id(String id) {
		String text =  find_by_id(id).getText();
		ExtentReporter.reportInfo("Text retrieved from element by id. [ID:"+id+" TEXT: "+text+"]");
		return text;
	}
	public String read_text_from_element_by_name(String name) {
		String text =  find_by_name(name).getText();
		ExtentReporter.reportInfo("Text retrieved from element by name. [NAME:"+name+" TEXT: "+text+"]");
		return text;
	}
	public String read_text_from_element_by_className(String className) {
		String text =  find_by_className(className).getText();
		ExtentReporter.reportInfo("Text retrieved from element by className. [CLASSNAME:"+className+" TEXT: "+text+"]");
		return text;
		
	}
	public String read_text_from_element_by_xpath(String xpath) {
		String text =  find_by_xpath(xpath).getText();
		ExtentReporter.reportInfo("Text retrieved from element by xpath. [XPATH:"+xpath+" TEXT: "+text+"]");
		return text;
	}
	
	//DROPDOWN /SELECT
	
	public void select_from_dropdown_by_id(String id, String option) {
		Select dropdown  = new Select(find_by_id(id));
		dropdown.selectByVisibleText(option);
		ExtentReporter.reportInfo("Option selected in dropdown. Option: ["+option+"]");
	}
	public void select_from_dropdown_by_name(String name, String option) {
		Select dropdown  = new Select(find_by_name(name));
		dropdown.selectByVisibleText(option);
		ExtentReporter.reportInfo("Option selected in dropdown. Option: ["+option+"]");
	}
	public void select_from_dropdown_by_className(String className, String option) {
		Select dropdown  = new Select(find_by_className(className));
		dropdown.selectByVisibleText(option);
		ExtentReporter.reportInfo("Option selected in dropdown. Option: ["+option+"]");
	}
	public void select_from_dropdown_by_xpath(String xpath, String option) {
		Select dropdown  = new Select(find_by_xpath(xpath));
		dropdown.selectByVisibleText(option);
		ExtentReporter.reportInfo("Option selected in dropdown. Option: ["+option+"]");
	}
	
	// ACTIONS / MOUSE OPERATIONS
	private Actions get_action_obj() {
		return new Actions(driver);
	}
	public void mouse_over_to_element_by_id(String id) {
		get_action_obj().moveToElement(find_by_id(id)).build().perform();
		ExtentReporter.reportInfo("Moved to an element");
	}
	public void mouse_over_to_element_by_name(String name) {
		get_action_obj().moveToElement(find_by_name(name)).build().perform();
		ExtentReporter.reportInfo("Moved to an element");
	}
	public void mouse_over_to_element_by_className(String className) {
		get_action_obj().moveToElement(find_by_className(className)).build().perform();
		ExtentReporter.reportInfo("Moved to an element");
	}
	public void mouse_over_to_element_by_xpath(String xpath) {
		get_action_obj().moveToElement(find_by_xpath(xpath)).build().perform();
		ExtentReporter.reportInfo("Moved to an element");
	}
	public void drag_and_drop(WebElement source, WebElement target) {
		get_action_obj().dragAndDrop(source, target).build().perform();
		ExtentReporter.reportInfo("Drag and drop operation performed");
	}
	public void right_click_on_element(WebElement element) {
		get_action_obj().contextClick(element).build().perform();
		ExtentReporter.reportInfo("Right clicked on element");
	}
	public void press_enter() {
		get_action_obj().sendKeys(Keys.ENTER).build().perform();
		ExtentReporter.reportInfo("key pressed [ENTER]");
	}
	
	
	//SCREENSHOTS
	public String takeScreenshot() {
	
		return takeScreenshot(driver);
	}
	public static String takeScreenshot (WebDriver driver) {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		String imageFile = System.getProperty("user.dir")+"/target/screenshots/"+System.currentTimeMillis()+".png";
		File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return imageFile;
	}
	
	//COUNTS AND CHECKS
	
	
	//COUNTS
	public int count_elements_has_text(String text) {
		List<WebElement> elements = find_elements_by_xpath("//*[contains(text(),'"+text+"')]");
		ExtentReporter.reportInfo("Total number of element found that has text: ["+text+"] = "+ elements.size());
		return elements.size();
	}
	
	//CHECKS
	public boolean check_element_exist_by_visible_text(String text) {
		int count =find_elements_by_xpath("//*[contains(text(),'"+text+"')]").size();
		boolean b = count > 0;
		ExtentReporter.reportInfo("Check element present by element's visible text. Element found: "+ count);
		return b;
	}
	

	public boolean check_element_exist(WebElement element) {
		boolean b =  element.isDisplayed();
		ExtentReporter.reportInfo("Check element present by element object. Element found: "+ b);
		return b;
	}
	
	public boolean check_element_exist_by_element_id(String id) {
		boolean b = find_by_id(id) !=null;
		ExtentReporter.reportInfo("Check element present by element's id. Element found: "+ b);
		return b;
	}
	public boolean check_element_exist_by_element_name(String name) {
		boolean b = find_by_name(name) !=null;
		ExtentReporter.reportInfo("Check element present by element's name. Element found: "+ b);
		return b;
	}

	public boolean check_element_exist_by_element_className(String className) {
		boolean b = find_by_className(className) !=null;
		ExtentReporter.reportInfo("Check element present by element's className. Element found: "+ b);
		return b;
	}
	public boolean check_element_exist_by_element_xpath(String xpath) {
		boolean b = find_by_xpath(xpath) !=null;
		ExtentReporter.reportInfo("Check element present by element's xpath. Element found: "+ b);
		return b;
	}
	
	
	//SWITCH TO
	public Alert swicthToAlert() {
		return driver.switchTo().alert();
	}
	
	public void swicthToFrame(int index) {
		driver.switchTo().frame(index);
	}
	public void swicthToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}
	public void swicthToFrame(WebElement frameElement) {
		driver.switchTo().frame(frameElement);
	}
	
	
	

}
