package net.timentraining.core;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

class APIExtentReporter {

	public static void  report_info(RequestSpecification request, String response) {
//		QueryableRequestSpecification queryableRequestSpecification = new 
		QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
		String reportHeader  = queryable.getMethod()+" | "+ queryable.getURI();
//		 ExtentReporter.get_extentTest().createNode(reportHeader).createNode(response);
//		return ExtentReporter.get_extentTest().info(reportHeader).createNode("response", response);
//		 ExtentReporter.get_extentTest().info("Request:\n"+ reportHeader).info("Response:\n"+response);
		 ExtentReporter.get_extentTest().info(MarkupHelper.createLabel("Request:\n"+ reportHeader, ExtentColor.GREY)).info(MarkupHelper.createLabel("Response:\n"+response, ExtentColor.BLUE));
	
	
	}
	

	
}
