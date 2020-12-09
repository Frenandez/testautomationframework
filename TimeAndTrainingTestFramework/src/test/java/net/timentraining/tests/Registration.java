package net.timentraining.tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import net.timentraining.core.TestBase;
import net.timentraining.core.TestBaseSettings;

public class Registration extends TestBase{
	
	@BeforeSuite
	public void my() {
		TestBaseSettings.set_api_test_only();
	}
	
	@Test
	public void api_only() {
//		
//		RestRequest restRequest = new RestRequest();
//		restRequest.setBaseURI("http://api.openweathermap.org/data/2.5/");
//		RestResponse response = restRequest.get("weather?q=London,uk&appid=d058a2d0a6113341468787210d9b84d9");
//		System.out.println(response.get_response_as_string());
//		
//		int  id = response.get_value_by_path("weather[0].id");
//		System.out.println(id);
	
	}
	@Test
	public void register_with_missing_required_fields() {
//		webui.lanuch("http://automationpractice.com/index.php");
//		webui.click_element_by_className("login");
//		webui.enter_text_by_id("email_create", "mynewemail@mailinator.com");
//		webui.click_element_by_id("SubmitCreate");
//		
//		webui.click_element_by_id("submitAccount");
//		validator.verify_text_present_in_page("There are 8 errors");
//		validator.verify_text_present_in_page("You must register at least one phone number.");
//		
//		
//		RestRequest restRequest = new RestRequest();
//		restRequest.setBaseURI("http://api.openweathermap.org/data/2.5/");
//		RestResponse response = restRequest.get("weather?q=London,uk&appid=d058a2d0a6113341468787210d9b84d9");
//		System.out.println(response.get_response_as_string());
//		
//		int  id = response.get_value_by_path("weather[0].id");
//		System.out.println(id);
		
		
		/*
		 * webui.enter_text_by_id("customer_firstname", "John");
		 * webui.enter_text_by_id("customer_lastname", "smith");
		 * webui.enter_text_by_id("passwd", "abc1234");
		 * 
		 * webui.select_from_dropdown_by_id("days", "11");
		 * webui.select_from_dropdown_by_id("months", "May");
		 * webui.select_from_dropdown_by_id("years", "1985");
		 */
		
	}

}
