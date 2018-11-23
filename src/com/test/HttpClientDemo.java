package com.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientDemo {
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

		// 变量赋值
		// String setVarString = "asdf&{var1:response.data}Sdqqcasdf&{var2:response.data.list}Sdc";
		// Pattern pPattern = Pattern.compile("\\&\\{\\w.*\\}");
		// Matcher pMatcher = pPattern.matcher(setVarString);
		// while (pMatcher.find()) {
		// String childString = pMatcher.group();
		// System.out.println("匹配到的父串：" + childString);
		// Pattern cPattern = Pattern.compile("\\w+:[\\w+\\.?]*\\w+");
		// Matcher cMatcher = cPattern.matcher(childString);
		// while (cMatcher.find()) {
		// System.out.println("匹配到的子串：" + cMatcher.group());
		// }
		// }

		// 取出变量
		String getVarString = "adf${var1}asdfadf${var2}asdf";
		Pattern pPattern = Pattern.compile("\\$\\{\\w*\\}");
		Matcher pMatcher = pPattern.matcher(getVarString);
		while (pMatcher.find()) {
			String childString = pMatcher.group();
			System.out.println("匹配到的父串：" + childString);
			Pattern cPattern = Pattern.compile("\\w+");
			Matcher cMatcher = cPattern.matcher(childString);
			while (cMatcher.find()) {
				System.out.println("匹配到的子串：" + cMatcher.group());
			}
		}

	}
}
