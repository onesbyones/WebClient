package com.web.clients;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import com.web.clientpost.CreateHttpPost;

public class HttpClientAdapter implements InterfaceHttpExecute {

	private InterfaceCreateHttp createHttp;

	public HttpClientAdapter(ParamBean paramBean) {

		if ("post".equalsIgnoreCase(paramBean.getMethod())) {
			this.createHttp = new CreateHttpPost(paramBean.getHost(), paramBean.getUri(), paramBean.getHeadersMap(),
					paramBean.getBody());

		} else if ("get".equalsIgnoreCase(paramBean.getMethod())) {

		} else if ("put".equalsIgnoreCase(paramBean.getMethod())) {

		} else if ("delete".equalsIgnoreCase(paramBean.getMethod())) {

		}
	}

	@Override
	public CloseableHttpResponse executeHttp(ParamBean paramBean) {
		CloseableHttpClient httpClient = CreateSingleHttpClient.getInstance();
		CloseableHttpResponse response = null;
		if ("post".equalsIgnoreCase(paramBean.getMethod())) {
			try {
				response = httpClient.execute(this.createHttp.httpPost());
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}

		return response;
	}

}
