package com.web.clients;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface InterfaceHttpExecute {

	public CloseableHttpResponse executeHttp(ParamBean paramBean);

}
