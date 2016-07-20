package com.zjhhcsoft.uac.clinet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerHealth {

	private String url;
	private Date nextUpdateTime = new Date();
	private String status = "STOPPED";
	private ObjectMapper mapper = new ObjectMapper();

	private HttpClinet httpClinet = new HttpClinet();

	private static ServerHealth INSTANCE;

	public static synchronized ServerHealth newInstance(String url) {
		if (INSTANCE == null) {
			INSTANCE = new ServerHealth(url);
		}
		return INSTANCE;
	}

	public static ServerHealth getInstance() {
		return INSTANCE;
	}

	private ServerHealth(String url) {
		super();
		this.url = url;
	}

	public boolean running() {
		synchronized (this) {
			updateStatus();
		}
		return !"STOPPED".equals(status);
	}

	private void updateStatus() {
		if (nextUpdateTime.getTime() < new Date().getTime()) {
			try {
				HttpResponse httpResponse = httpClinet.httpRequest(url);
				String s = EntityUtils.toString(httpResponse.getEntity(),
						"UTF-8");
				@SuppressWarnings("unchecked")
				Map<String, String> statusMap = mapper.readValue(s,
						HashMap.class);
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				dateFormat.parse(statusMap.get("lastUpdate"));
				nextUpdateTime = new Date(
						new Date().getTime()
								+ Long.parseLong(statusMap
										.get("updateInterval") == null ? "60000"
										: statusMap.get("updateInterval")));
				this.status = statusMap.get("status");
			} catch (Throwable e) {
				nextUpdateTime = new Date(new Date().getTime() + 1000 * 60);
				this.status = "STOPPED";
			}
		}
	}

}
