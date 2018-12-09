package com.webclient;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author hqh
 * @date 2018年11月24日23:25:41
 *
 */

public class HttpClientDemo {

	private static Pattern SET_VAR_PARENT_PATTERN = Pattern.compile("\\&\\{\\w.*\\}");
	private static Pattern SET_VAR_CHILDREN_PATTERN = Pattern.compile("\\w+:[\\w+\\.?]*\\w+");
	private static Pattern GET_VAR_PARENT_PATTERN = Pattern.compile("\\$\\{\\w*\\}");
	private static Pattern GET_VAR_CHILDREN_PATTERN = Pattern.compile("\\w+");

	public static void main(String[] args) throws ParseException, IOException {
		// String fielPath = "";
		// ArrayList<String> fileList = FileForEach.getFileList(fielPath);
		// System.out.println(fileList);
		// System.out.println("文件数：" + fileList.size());
		//
		// for (String pathStr : fileList) {
		// JSONArray jsonArray = JsonToArray.jsonToArrays(pathStr);
		// CreateHttp httpClient = new CreateHttp(jsonArray);
		// ParamBean pb = httpClient.getParamBean();
		// System.out.println("describe --> " + pb.getDesc());
		// System.out.println("host --> " + pb.getHost());
		// System.out.println("uri --> " + pb.getUri());
		// System.out.println("headers --> " + pb.getHeadersMap());
		// System.out.println("body --> " + pb.getBody());
		// System.out.println("method --> " + pb.getMethod());
		// System.out.println("response --> " + pb.getResponse());
		// System.out.println("expectValue --> " + pb.getExpectValue());
		// System.out.println("actualVlaue --> " + pb.getActualVlaue());
		// System.out.println("variable --> " + pb.getVariable());
		// System.out.println("sql --> " + pb.getSql());
		// }

		// 变量赋值ֵ
		String setVarString = "asdf&{var1:response.data}Sdcasdf&{var2:response.data.list}Sdc&{var3:response.data.list.0}Sdcsdf";
		Matcher setVarParentMatcher = SET_VAR_PARENT_PATTERN.matcher(setVarString);
		while (setVarParentMatcher.find()) {
			String childString = setVarParentMatcher.group();
			System.out.println("匹配到的父串：" + childString);
			Matcher setVarChildreMatcher = SET_VAR_CHILDREN_PATTERN.matcher(childString);
			while (setVarChildreMatcher.find()) {
				System.out.println("匹配到的子串：" + setVarChildreMatcher.group());
			}
		}

		// 取出变量
		String getVarString = "adf${var1}asdfadf${var2}asdf";
		Matcher getVarParentMatcher = GET_VAR_PARENT_PATTERN.matcher(getVarString);
		while (getVarParentMatcher.find()) {
			String childString = getVarParentMatcher.group();
			System.out.println("匹配到的父串：" + childString);
			Matcher getVarChildrenMatcher = GET_VAR_CHILDREN_PATTERN.matcher(childString);
			while (getVarChildrenMatcher.find()) {
				System.out.println("匹配到的子串：" + getVarChildrenMatcher.group());
			}
		}

	}
}
