package org.roof.guardmonitor.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.log4j.Logger;
import org.roof.guardmonitor.AbstractMonitor;
import org.roof.guardmonitor.MonitorContext;
import org.roof.guardmonitor.Status;

/**
 * HTTP监控
 * 
 * @author liuxin
 *
 */
public class HttpMonitor extends AbstractMonitor {

	private int retryCount = 3; // 重试次数
	private int connectionTimeout = 1000; // 连接超时时间
	private String url;
	private HttpResponse response = null;

	private static final Logger LOGGER = Logger.getLogger(HttpMonitor.class);

	@Override
	protected void doInTry(MonitorContext monitorContext) throws Throwable {
		response = httpRequest(url);
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			monitorContext.setStatus(Status.STOPPED);
		} else {
			monitorContext.setStatus(Status.RUNNING);
		}
		monitorContext.setMessage(statusLine.getReasonPhrase());

	}

	@Override
	protected void doFinally() {

	}

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

	public int getRetryCount() {
		return retryCount;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
