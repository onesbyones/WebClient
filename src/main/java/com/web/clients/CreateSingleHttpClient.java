package com.web.clients;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 * @author hqh
 * @date 2018年11月24日23:25:41
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
