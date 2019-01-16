package com.web.clients;

import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * 
 * @author hqh
 *
 */

public interface InterfaceHttpExecute {

	/**
	 * 
	 * @param paramBean
	 * @return CloseableHttpResponse
	 */
	public CloseableHttpResponse executeHttp(ParamBean paramBean);

}
