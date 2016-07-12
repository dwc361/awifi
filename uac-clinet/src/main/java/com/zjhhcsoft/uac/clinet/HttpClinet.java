package com.zjhhcsoft.uac.clinet;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.log4j.Logger;

public class HttpClinet {

	private static final Logger LOGGER = Logger.getLogger(HttpClinet.class);
	private int retryCount = 3; // 重试次数
	private int connectionTimeout = 500; // 连接超时时间

	public HttpResponse httpRequest(String url) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet httpPost = new HttpGet(url);
		CloseableHttpResponse response = null;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectionTimeout).build();
		try {
			httpPost.setConfig(requestConfig);
			response = httpClient.execute(httpPost);
			return response;
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	private CloseableHttpClient getHttpClient() {
		CloseableHttpClient client = HttpClients
				.custom()
				.setRetryHandler(
						new StandardHttpRequestRetryHandler(retryCount, false))
				.build();
		return client;
	}

}
