package net.timentraining.core;

public class TestBaseSettings {
	
	static String BROWSER="chrome";//chrome, ie,firefox
	static long WEBDRIVER_IMPLICIT_TIMEOUT=30;
	static long WEBDRIVER_EXPLICIT_TIMEOUT=60;
	static boolean API_TEST_ONLY = false;
	

	
	public static void set_api_test_only() {
		API_TEST_ONLY = true;
	}
	public static void set_browser_chrome() {
		BROWSER="chrome";
	}
	public static void set_browser_ie() {
		BROWSER="ie";
	}

}
