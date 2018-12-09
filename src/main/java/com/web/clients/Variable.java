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

	private static Pattern SET_VAR_PARENT_PATTERN = Pattern.compile("\\&\\{\\w.*\\}");
	private static Pattern SET_VAR_CHILDREN_PATTERN = Pattern.compile("\\w+:[\\w+\\.?]*\\w+");
	private static Pattern GET_VAR_PARENT_PATTERN = Pattern.compile("\\$\\{\\w*\\}");
	private static Pattern GET_VAR_CHILDREN_PATTERN = Pattern.compile("\\w+");

	/**
	 * 处理变量关系
	 * 
	 * @param paramBean
	 *            参数bean类型
	 * @see 暂时不支持多变量赋值，比如：asdf&{var1:response.data}Sdcasdf&{var2:response.data.
	 *      list}Sdc&{var3:response.data.list.0}Sdcsdf
	 */
	public static void setVariable(ParamBean paramBean, HashMap<String, String> variableMap) {
		JSONObject responseJson = JSONObject.fromObject(paramBean.getResponse());
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
							variableMap.put(varStr[0], responseJson.getString(varList.get(i)));
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
	}

	/**
	 * 获取变量并返回变量替换后的URI
	 * 
	 * @param paramBean
	 */
	public static String getVariable(ParamBean paramBean, HashMap<String, String> variableMap) {
		String uri = new String(paramBean.getUri());
		Matcher getVarParentMatcher = GET_VAR_PARENT_PATTERN.matcher(uri);
		while (getVarParentMatcher.find()) {
			String parentString = getVarParentMatcher.group();
			System.out.println("匹配到的父串：" + parentString);
			Matcher getVarChildrenMatcher = GET_VAR_CHILDREN_PATTERN.matcher(parentString);
			while (getVarChildrenMatcher.find()) {
				String childrenString = getVarChildrenMatcher.group();
				System.out.println("匹配到的子串：" + childrenString);
				String tempStr = variableMap.get(childrenString);
				if (null == tempStr) {
					throw new NullPointerException("不存在的变量{" + childrenString + "}，请检查配置");
				}
				uri = uri.replace(parentString, tempStr);
			}
		}
		return uri;
	}

	public static void main(String[] args) {
		HashMap<String, String> varMap = SingleVariableMap.getVariableMap();
		String jsonStr = "{\"success\":\"true\",\"test\":{\"sex\":\"female\",\"age\":\"11\"},\"data\":[{\"contury\":\"CN\",\"city\":\"SZ\"},{\"contury\":\"US\",\"city\":\"OKC\"}]}";
		// success/test.sex/data.0.contury
		String variableStr = "df&{var2:data.1.contury}";
		String uri = "adf${var1}asdfadf${var2}asdf";
		ParamBean paramBean = new ParamBean();
		paramBean.setResponse(jsonStr);
		paramBean.setVariable(variableStr);
		paramBean.setUri(uri);
		setVariable(paramBean, varMap);

		varMap.put("var1", "CN");

		System.out.println(getVariable(paramBean, varMap));

		// JSONObject json = JSONObject.fromObject(jsonStr);
		// JSONArray jsonArray = (JSONArray) json.get("data");
		// System.out.println(jsonArray);
		// System.out.println(jsonArray.get(0));
		// System.out.println(jsonArray.get(1));
	}
}
