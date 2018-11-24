package com.web.clients;

import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * 
 * @author hqh
 * @date 2018年11月24日23:25:41
 *
 */

public interface InterfaceHttpExecute {

	/**
	 * 执行请求
	 * 
	 * @param paramBean
	 * @return CloseableHttpResponse
	 */
	public CloseableHttpResponse executeHttp(ParamBean paramBean);

}
