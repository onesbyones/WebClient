package com.log;

import org.testng.Reporter;

/**
 * 
 * @author hqh
 *
 */
public class LogUtils {

	public static void log(String logStr) {
		Reporter.log(logStr, true);
	}
}
