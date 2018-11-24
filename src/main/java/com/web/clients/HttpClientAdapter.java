package com.web.clients;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import com.web.clientpost.CreateHttpPost;

/**
 * 
 * @author hqh
 * @date 2018年11月24日23:25:41
 *
 */

public class HttpClientAdapter implements InterfaceHttpExecute {

	private InterfaceCreateHttp createHttp;

	private final String POST = "post";
	private final String GET = "get";
	private final String PUT = "put";
	private final String DELETE = "delete";

	public HttpClientAdapter(ParamBean paramBean) {

		if (POST.equalsIgnoreCase(paramBean.getMethod())) {
			this.createHttp = new CreateHttpPost(paramBean.getHost(), paramBean.getUri(), paramBean.getHeadersMap(),
					paramBean.getBody());

		} else if (GET.equalsIgnoreCase(paramBean.getMethod())) {

		} else if (PUT.equalsIgnoreCase(paramBean.getMethod())) {

		} else if (DELETE.equalsIgnoreCase(paramBean.getMethod())) {

		}
	}

	@Override
	public CloseableHttpResponse executeHttp(ParamBean paramBean) {
		CloseableHttpClient httpClient = CreateSingleHttpClient.getInstance();
		CloseableHttpResponse response = null;
		if (POST.equalsIgnoreCase(paramBean.getMethod())) {
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