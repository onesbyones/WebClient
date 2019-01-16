package com.web.clients;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 * @author hqh
 *
 */

public class CreateSingleHttpClient {

	private static volatile CloseableHttpClient httpClient;

	private CreateSingleHttpClient() {
	}

	public static CloseableHttpClient getInstance() {
		if (null == httpClient) {
			synchronized (CloseableHttpClient.class) {
				if (null == httpClient) {
					httpClient = HttpClients.createDefault();
				}
			}
		}
		return httpClient;
	}
}
