package com.orangehrm.tesetcases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.utilities.APIUtilities;
import com.orangehrm.utilities.MyExtentReport;
import com.orangehrm.utilities.RetryAnalyzer;

import io.restassured.response.Response;

public class APItest {

	@Test
	public void verifyUserAPI() {

		SoftAssert soft = new SoftAssert();
		String endPoint = " https://jsonplaceholder.typicode.com/users/1";
		MyExtentReport.logStep("API EndPoint :" + endPoint);
		System.out.println(endPoint);
		MyExtentReport.logStep("sending get request");
		Response response = APIUtilities.sendGetRequest(endPoint);

		// validating status code
		MyExtentReport.logStep("Validatiing status code");
		boolean isStatusCodeValid = APIUtilities.validateStatusCode(response, 200);
		soft.assertTrue(isStatusCodeValid, "Status code in not matched");

		if (isStatusCodeValid) {
			MyExtentReport.APIlogStep("Status code validation passed");
		} else {
			MyExtentReport.APIlogFailure("Status code validation failed");
		}

		// validating response body attributes
		MyExtentReport.logStep("Validatiing username");
		String actual = APIUtilities.getJsonValue(response, "username");
		boolean isUserNameValid = "Bret".equals(actual);
		soft.assertTrue(isUserNameValid, "User Name in not matched");

		if (isUserNameValid) {
			MyExtentReport.APIlogStep("Username validation passed");
		} else {
			MyExtentReport.APIlogFailure("Username validation failed");
		}
		
		soft.assertAll();

	}
}
