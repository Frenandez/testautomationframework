package net.timentraining.core;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequest {
	
	private static String baseURI;
	private static String contentType;
	private static String acceptType;
	private static Map<String,String> headers = new HashMap<String, String>();
	
	
	private RequestSpecification requestSpecification;
	
	

	
	private void construct_requestspec() {
		requestSpecification = null;
		requestSpecification = RestAssured.given(new RequestSpecBuilder().build());
		if(baseURI == null) {
			throw new RuntimeException("Base URI required");
		}else {
			requestSpecification.baseUri(getBaseURI());
		}
		
		if(contentType !=null) {
			requestSpecification.contentType(getContentType());
		}
		
		if(acceptType !=null) {
			requestSpecification.accept(getAcceptType());
		}
		
		if(headers.size() == 0) {
			requestSpecification.headers(getHeaders());
		}
	}
	
	private void construct_requestspec(Map<String,?> queryParameters) {
		construct_requestspec();
		requestSpecification.queryParams(queryParameters);
	}
	
	@SuppressWarnings("unused")
	private void construct_requestspec(Object body) {
		construct_requestspec();
		requestSpecification.body(body);
	}
	
	
	
	@SuppressWarnings("unused")
	private void set_queryparameters(Map<String,?> params) {
		requestSpecification.queryParams(params);
	}
	

	
	
	public String getBaseURI() {
		return baseURI;
	}






	public static void setBaseURI(String baseURI) {
		RestRequest.baseURI = baseURI;
	}






	public Map<String, ?> getHeaders() {
		return headers;
	}

	public static void setHeaders(Map<String, String> headers) {
		RestRequest.headers = headers;
	}
//	public void setHeaders(String key, String value) {
//		this.headers.put(key, value);
//	}

	public String getContentType() {
		return contentType;
	}






	public static void setContentType(String contentType) {
		RestRequest.contentType = contentType;
	}






	public String getAcceptType() {
		return acceptType;
	}






	public static void setAcceptType(String acceptType) {
		RestRequest.acceptType = acceptType;
	}



	


	//GET
	public RestResponse get(String uri) {
		construct_requestspec();
		RestResponse response =  new RestResponse(requestSpecification.get(uri));
		APIExtentReporter.report_info(requestSpecification, response.get_response_as_string());
		return response;
	}
	
	public RestResponse get(String uri, String querParamKey, String queryParamvalue) {
		
		Map<String,String> q = new HashMap<String, String>();
		q.put(querParamKey, queryParamvalue);
		construct_requestspec(q);
		Response response = requestSpecification.get(uri);
		APIExtentReporter.report_info(requestSpecification, response.asString());
		return new RestResponse(response);
		
	}
	
	
	
	
	

}
