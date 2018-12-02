package com.web.clients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author hqh
 * @date 2018年12月2日22:23:20
 *
 */

public class Variable {

	private static HashMap<String, String> varMap;
	private static Pattern SET_VAR_PARENT_PATTERN = Pattern.compile("\\&\\{\\w.*\\}");
	private static Pattern SET_VAR_CHILDREN_PATTERN = Pattern.compile("\\w+:[\\w+\\.?]*\\w+");

	static {
		varMap = new HashMap<>(256);
	}

	/**
	 * 处理变量关系
	 * 
	 * @param paramBean
	 *            参数bean类型
	 * @see 暂时不支持多变量赋值，比如：asdf&{var1:response.data}Sdcasdf&{var2:response.data.
	 *      list}Sdc&{var3:response.data.list.0}Sdcsdf
	 */
	public static HashMap<String, String> setVariableMap(ParamBean paramBean) {
		// 变成jsonObject
		JSONObject responseJson = JSONObject.fromObject(paramBean.getResponse());
		// 给变量赋值
		Matcher setVarParentMatcher = SET_VAR_PARENT_PATTERN.matcher(paramBean.getVariable());
		while (setVarParentMatcher.find()) {
			String childString = setVarParentMatcher.group();
			System.out.println("匹配到的父串：" + childString);
			Matcher setVarChildreMatcher = SET_VAR_CHILDREN_PATTERN.matcher(childString);
			while (setVarChildreMatcher.find()) {
				String regexStr = setVarChildreMatcher.group();
				System.out.println("匹配到的子串：" + regexStr);
				String[] varStr = regexStr.split(":");
				List<String> varList = new ArrayList<String>(Arrays.asList(varStr[1].split("\\.")));
				for (int i = 0; i < varList.size(); i++) {
					if (varList.get(i).matches("\\d*")) {
						continue;
					} else {
						if (!responseJson.getString(varList.get(i)).contains("{")
								&& !responseJson.getString(varList.get(i)).contains("[")) {
							// 如果是个String则直接赋值给变量
							varMap.put(varStr[0], responseJson.getString(varList.get(i)));
						} else if (responseJson.getString(varList.get(i)).contains("[")
								|| responseJson.getString(varList.get(i)).contains("]")) {
							// 如果是JSONArray
							JSONArray jsonArray = responseJson.getJSONArray(varList.get(i));
							responseJson = jsonArray.getJSONObject(Integer.valueOf(varList.get(i + 1)));
						} else if (!responseJson.getString(varList.get(i)).contains("[")
								&& responseJson.getJSONObject(varList.get(i)).toString().contains("{")) {
							// 如果是JSONObject
							responseJson = responseJson.getJSONObject(varList.get(i));
						}
					}
				}
			}
		}

		return varMap;
	}

	public static void main(String[] args) {
		String jsonStr = "{\"success\":\"true\",\"test\":{\"sex\":\"female\",\"age\":\"11\"},\"data\":[{\"contury\":\"CN\",\"city\":\"SZ\"},{\"contury\":\"US\",\"city\":\"OKC\"}]}";
		// success/test.sex/data.0.contury
		String variableStr = "df&{var2:data.1.contury}";
		ParamBean paramBean = new ParamBean();
		paramBean.setResponse(jsonStr);
		paramBean.setVariable(variableStr);

		System.out.println(setVariableMap(paramBean));

		// JSONObject json = JSONObject.fromObject(jsonStr);
		// JSONArray jsonArray = (JSONArray) json.get("data");
		// System.out.println(jsonArray);
		// System.out.println(jsonArray.get(0));
		// System.out.println(jsonArray.get(1));
	}
}
