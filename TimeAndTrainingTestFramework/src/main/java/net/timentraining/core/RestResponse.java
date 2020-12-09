package net.timentraining.core;

import io.restassured.response.Response;

public class RestResponse {
	private Response response;
	
	public RestResponse(Response response) {
		this.response = response;
	}
	
	public <T> T get_value_by_path(String path) {
		T t = response.path(path);
		return t;
	}
	public String get_response_as_string() {
		return response.asString();
	}
	

}
