package com.log;

import org.testng.Reporter;

/**
 * 公共日志类，同时输出到console、接口测试报告中
 * 
 * @author hqh
 *
 */
public class LogUtils {

	public static void log(String logStr) {
		Reporter.log(logStr, true);
	}
}
