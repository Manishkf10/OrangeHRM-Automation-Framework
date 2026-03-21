package com.orangehrm.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogsManager {

	public static Logger getLogs(Class<?> clazz ) {
		return LogManager.getLogger(clazz);
	}
}
