package com.web.clients;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import com.log.LogUtils;
import com.web.clientdelete.CreateHttpDelete;
import com.web.clientget.CreateHttpGet;
import com.web.clientpost.CreateHttpPost;
import com.web.clientput.CreateHttpPut;

/**
 * 
 * @author hqh
 *
 */

public class HttpClientAdapter implements InterfaceHttpExecute {

	private InterfaceCreateHttp createHttp;

	private final String POST = "post";
	private final String GET = "get";
	private final String PUT = "put";
	private final String DELETE = "delete";

	/**
	 * 
	 * @param paramBean
	 */
	public HttpClientAdapter(ParamBean paramBean) {

		if (POST.equalsIgnoreCase(paramBean.getMethod())) {
			// @todo 需要对请求URI做变量替换

			this.createHttp = new CreateHttpPost(paramBean.getHost(), paramBean.getUri(), paramBean.getHeadersMap(),
					paramBean.getBody());

		} else if (GET.equalsIgnoreCase(paramBean.getMethod())) {

			this.createHttp = new CreateHttpGet(paramBean.getHost(), paramBean.getUri(), paramBean.getHeadersMap());

		} else if (PUT.equalsIgnoreCase(paramBean.getMethod())) {

			this.createHttp = new CreateHttpPut(paramBean.getHost(), paramBean.getUri(), paramBean.getHeadersMap());

		} else if (DELETE.equalsIgnoreCase(paramBean.getMethod())) {

			this.createHttp = new CreateHttpDelete(paramBean.getHost(), paramBean.getUri(), paramBean.getHeadersMap());
		}
	}

	@Override
	public CloseableHttpResponse executeHttp(ParamBean paramBean) {
		CloseableHttpClient httpClient = CreateSingleHttpClient.getInstance();
		CloseableHttpResponse response = null;

		try {
			if (this.POST.equalsIgnoreCase(paramBean.getMethod())) {
				response = httpClient.execute(this.createHttp.httpPost());
			} else if (this.GET.equalsIgnoreCase(paramBean.getMethod())) {
				response = httpClient.execute(this.createHttp.httpPost());
			} else if (this.PUT.equalsIgnoreCase(paramBean.getMethod())) {
				response = httpClient.execute(this.createHttp.httpPost());
			} else if (this.DELETE.equalsIgnoreCase(paramBean.getMethod())) {
				response = httpClient.execute(this.createHttp.httpPost());
			}

		} catch (Exception e) {
			LogUtils.log("请求异常：" + e.getMessage());
		}

		return response;
	}

}