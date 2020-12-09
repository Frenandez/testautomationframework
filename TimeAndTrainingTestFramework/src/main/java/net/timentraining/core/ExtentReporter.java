package net.timentraining.core;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentHtmlReporterConfiguration;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {
	private static ExtentReports extentReports;
	public static ThreadLocal<ExtentTest> extentTestGroup = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	public static void init(){
		String path = System.getProperty("user.dir")+"/target/test-automation-report.html";
		extentReports = new ExtentReports();
		ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(new File(path));
		ExtentHtmlReporterConfiguration config =extentHtmlReporter.config();
		config.setDocumentTitle("TestAutomation Report");
		config.setTheme(Theme.STANDARD);
		
		
		extentReports.attachReporter(extentHtmlReporter);

	}
	


	
	public static void create_test_group(String testName) {
		System.out.println("test group created: "+ testName);
		extentTestGroup.set(extentReports.createTest(testName));
	}
	public static void create_test_(String testMethodName) {
		extentTest.set(extentTestGroup.get().createNode(testMethodName));
	} 
	
	
	public static void reportInfo(String msg) {
		extentTest.get().info(msg);
	}
	public static void reportError(String msg) {
		extentTest.get().error(msg);
	}
	public static void reportError(String msg,String screenshotImagePath) {
		extentTest.get().error(msg);
	}
	public static void reportPass(String msg) {
		extentTest.get().pass(msg);
	}
	public static void reportFail(String msg) {
		extentTest.get().fail(msg);
	}
	public static void reportFail(String msg, String screenshotImagePath) {
		try {
			extentTest.get().fail(msg).addScreenCaptureFromPath(screenshotImagePath);
			Assert.fail(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ExtentTest get_extentTest() {
		return extentTest.get();
	}
	
	public static void save() {
		extentReports.flush();
	}

}
