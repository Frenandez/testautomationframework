package net.timentraining.core;

import org.openqa.selenium.WebElement;

public  class ValidateUI extends Validator{
	
	private WebUI webUI;
	
	public ValidateUI(WebUI webUI) {
		this.webUI = webUI;
	}
	
	public void verify_text_present_in_page(String textToFind) {
		WebElement element =webUI.find_by_xpath("//*[contains(text(),'"+textToFind+"')]");
		if(element!=null) {
			success("text ["+textToFind+"] present in page");
		}else {
			failure("text ["+textToFind+"] NOT present in page");
		}
	}

	
	public void failure(String msg) {
		ExtentReporter.reportFail(msg);
	}


}
