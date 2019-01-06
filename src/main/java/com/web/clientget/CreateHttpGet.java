package com.web.clientget;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;

import com.web.clients.InterfaceCreateHttp;

public class CreateHttpGet implements InterfaceCreateHttp {

	HttpGet httpGet;

	public CreateHttpGet(String host, String uri, HashMap<String, String> headersMap) {
		this.httpGet = new HttpGet(host + uri);
		if (!headersMap.isEmpty()) {
			for (Map.Entry<String, String> tempMap : headersMap.entrySet()) {
				// System.out.println(tempMap.getKey() + "--> " +
				// tempMap.getValue());
				this.httpGet.addHeader(tempMap.getKey(), tempMap.getValue());
			}
		}
		// 配置请求超时时间
		RequestConfig rc = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(15000)
				.setSocketTimeout(15000).build();
		this.httpGet.setConfig(rc);
	}

	@Override
	public HttpGet httpGet() {
		// TODO Auto-generated method stub
		return this.httpGet;
	}

	@Override
	public HttpPost httpPost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPut httpPut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpDelete httpDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPatch httpPatch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpOptions httpOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpTrace httpTrace() {
		// TODO Auto-generated method stub
		return null;
	}

}
