package com.web.clients;

import java.util.HashMap;

/**
 * 
 * @author hqh
 *
 * @date 2018年12月9日10:55:42
 * 
 * @see 存放所有提取的变量
 */

public class SingleVariableMap {

	private static volatile HashMap<String, String> variableMap;

	private SingleVariableMap() {

	}

	public static HashMap<String, String> getVariableMap() {
		if (null == variableMap) {
			synchronized (HashMap.class) {
				if (null == variableMap) {
					variableMap = new HashMap<>(256);
				}
			}
		}
		return variableMap;
	}

}
