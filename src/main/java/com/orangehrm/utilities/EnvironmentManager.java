package com.orangehrm.utilities;

import java.util.ResourceBundle;

import com.orangehrm.base.BaseTest;

public class EnvironmentManager {
	
	public static ResourceBundle getURLs() {
		ResourceBundle resource =ResourceBundle.getBundle("config");
		return resource;
	}
	

	public static String getApplicationURL() {
		
		String applicationEnvironment=getURLs().getString("applicationEnvironment");
		String testExecutionMode=getURLs().getString("testExecutionMode");
		
		switch(applicationEnvironment.toLowerCase()) {
		case "local":
			if(testExecutionMode.equalsIgnoreCase("docker")) {
				return getURLs().getString("dockerLocalUrl");
			}else {
				return getURLs().getString("localUrl");
			}
			
		case "live": return getURLs().getString("liveUrl");
		
		default:
            throw new RuntimeException("Invalid environment");
		
		}
		
	}
}
