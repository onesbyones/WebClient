package com.web.clients;

import java.util.HashMap;

/**
 * 
 * @author hqh
 * @date 2018年11月24日23:25:41
 *
 */

public class ParamBean {

	/**
	 * 接口描述
	 */
	private String desc;

	/**
	 * 请求host，可以是ip、域名
	 */
	private String host;

	/**
	 * 请求uri
	 */
	private String uri;

	/**
	 * 请求方法
	 */
	private String method;

	/**
	 * 请求头部
	 */
	private HashMap<String, String> headersMap;

	/**
	 * 请求body，没有则为空。比如："body": ""
	 */
	private String body;

	/**
	 * 请求响应
	 */
	private String response;

	/**
	 * 结果校验：期望值
	 */
	private String expectValue;

	/**
	 * 结果校验：实际值
	 */
	private String actualVlaue;

	/**
	 * 变量提取。如果变量重名则替换原有变量的值
	 */
	private String variable;

	/**
	 * 数据库。暂时定义为JDBC的Uri路径，后边接上sql语句。比如：jdbc:mysql://localhost:3306/RUNOOB?sql=
	 * ""
	 */
	private String sql;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public HashMap<String, String> getHeadersMap() {
		return headersMap;
	}

	public void setHeadersMap(HashMap<String, String> headersMap) {
		this.headersMap = headersMap;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getExpectValue() {
		return expectValue;
	}

	public void setExpectValue(String expectValue) {
		this.expectValue = expectValue;
	}

	public String getActualVlaue() {
		return actualVlaue;
	}

	public void setActualVlaue(String actualVlaue) {
		this.actualVlaue = actualVlaue;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
