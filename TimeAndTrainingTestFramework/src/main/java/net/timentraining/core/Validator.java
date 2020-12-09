package net.timentraining.core;

import org.openqa.selenium.WebElement;

public abstract class Validator {
	
	


	public void verify_text_present_in_element(WebElement element,String textToFind) {
		if(element.getText().contains(textToFind)) {
			success("text ["+textToFind+"] presnet in element");
		}else {
			failure("text ["+textToFind+"] NOT presnet in element");
		}
	}
	
	public <T> void verify_euqal(T expected, T actual) {
	if(expected.equals(actual)) {
		success("Compare values are equal. Expected:["+expected+"], actual ["+actual+"]");
	}else {
		failure("Expected:["+expected+"], found ["+actual+"]");
	}
	}
	
	
	public void failure(String msg) {
		ExtentReporter.reportFail(msg);
	}
	public void success(String msg) {
		ExtentReporter.reportPass(msg);
	}
	

}
