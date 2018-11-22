package com.web.clients;

import java.io.IOException;
import java.text.ParseException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class ResponseDone {

	public static String doneRespone(ParamBean paramBean) throws ParseException, IOException {

		// todo:发起http请求
		HttpClientAdapter httpClientAdapter = new HttpClientAdapter(paramBean);
		CloseableHttpResponse response = httpClientAdapter.executeHttp(paramBean);
		System.out.println("*********************status: *********************");
		System.out.println(response.getStatusLine());
		System.out.println("*********************header: *********************");
		Header[] resHeader = response.getAllHeaders();
		for (Header header : resHeader) {
			System.out.println(header.getName() + " --> " + header.getValue());
		}
		HttpEntity resEntity = response.getEntity();
		System.out.println("*********************response: *********************");
		String responseStr = EntityUtils.toString(resEntity);
		System.out.println(responseStr);
		// todo:对响应校验

		// todo:变量提取，需要放到全局变量，变量不允许重复
		return responseStr;

	}
}
