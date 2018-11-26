package com.web.clients;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Variable {

	private static HashMap<String, String> varMap;
	private static Pattern SET_VAR_PARENT_PATTERN = Pattern.compile("\\&\\{\\w.*\\}");
	private static Pattern SET_VAR_CHILDREN_PATTERN = Pattern.compile("\\w+:[\\w+\\.?]*\\w+");

	static {
		varMap = new HashMap<>(256);
	}

	/**
	 * 从响应中赋值变量
	 * 
	 * @param response
	 *            http响应
	 */
	public static HashMap<String, String> setVariable(String regex, String response) {
		// 响应转换成JSONObject对象
		JSONObject responseJson = JSONObject.fromObject(response);
		// 给变量赋值
		Matcher setVarParentMatcher = SET_VAR_PARENT_PATTERN.matcher(regex);
		while (setVarParentMatcher.find()) {
			String childString = setVarParentMatcher.group();
			System.out.println("匹配到的父串：" + childString);
			Matcher setVarChildreMatcher = SET_VAR_CHILDREN_PATTERN.matcher(childString);
			while (setVarChildreMatcher.find()) {
				String regexStr = setVarChildreMatcher.group();
				System.out.println("匹配到的子串：" + regexStr);
				String[] varStr = regexStr.split(":");
				String[] varArray = varStr[1].split(".");
				for (String tempStr : varArray) {
					// 遍历需要给变量赋值的key
					if (responseJson.getString(tempStr) instanceof String) {
						// 如果是个字符串，则直接加入到varMap
						varMap.put(varStr[0], responseJson.getString(tempStr));
					} else if (responseJson.getJSONObject(tempStr) instanceof JSONObject) {
						// 如果是个JSONObject
						JSONObject jsonObject = responseJson.getJSONObject(tempStr);
						// 把JSONObject转换成String

					} else if (responseJson.getJSONArray(tempStr) instanceof JSONArray) {
						// 如果是个JSONArray
					} else if (Integer.valueOf(tempStr) instanceof Integer) {
						// 先转换成数值类型再做判断
					}
				}
			}
		}

		return varMap;
	}

}
