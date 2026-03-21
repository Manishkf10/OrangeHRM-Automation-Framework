package com.orangehrm.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIUtilities {

	
	public static Response sendGetRequest(String endPoint) {
		return RestAssured.get(endPoint);
	}
	public static Response sendPostRequest(String endPoint,String payLoad) {
		return RestAssured.given().header("Content-Type","application/json")
							.body(payLoad)
							.post();
	}
	public static boolean validateStatusCode(Response reponse,int statusCode) {
		return reponse.getStatusCode()==statusCode;
	}
	public static String getJsonValue(Response response, String value) {
		return response.jsonPath().getString(value);
	}
	
	
}
