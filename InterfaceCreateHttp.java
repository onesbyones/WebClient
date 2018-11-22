package com.web.clients;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;

public interface InterfaceCreateHttp {

	public HttpPost httpPost();

	public HttpGet httpGet();

	public HttpPut httpPut();

	public HttpDelete httpDelete();

	public HttpPatch httpPatch();

	public HttpOptions httpOptions();

	public HttpTrace httpTrace();

}
