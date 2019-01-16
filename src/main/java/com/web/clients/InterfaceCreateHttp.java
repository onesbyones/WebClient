package com.web.clients;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;

/**
 * 
 * @author hqh
 *
 */

public interface InterfaceCreateHttp {

	/**
	 * post请求
	 * 
	 * @return HttpPost
	 */
	public HttpPost httpPost();

	/**
	 * get请求
	 * 
	 * @return HttpGet
	 */
	public HttpGet httpGet();

	/**
	 * put请求
	 * 
	 * @return HttpPut
	 */
	public HttpPut httpPut();

	/**
	 * Delete请求
	 * 
	 * @return HttpDelete
	 */
	public HttpDelete httpDelete();

	/**
	 * Patch请求
	 * 
	 * @return HttpPatch
	 */
	public HttpPatch httpPatch();

	/**
	 * Options请求
	 * 
	 * @return HttpOptions
	 */
	public HttpOptions httpOptions();

	/**
	 * Trace请求
	 * 
	 * @return HttpTrace
	 */
	public HttpTrace httpTrace();

}
