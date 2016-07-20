package org.roof.guardmonitor.extension;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.roof.guardmonitor.Status;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.AntPathMatcher;

public class MonitorStatusManager implements InitializingBean {
	private static Logger logger = Logger.getLogger(MonitorStatusManager.class);

	private String filePath = null;
	private static final String STOPPED_KEY = "monitor.stopped";
	private static final String PASSWORD_KEY = "monitor.password";
	private String[] stoppedPatterns = null;
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	private long updateInterval = 1000 * 60;
	private Map<String, String> statusMap = new HashMap<String, String>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String password = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		loadConfig();
	}

	public MonitorStatusManager() {
		statusMap.put("status", Status.STOPPED.toString());
		statusMap.put("lastUpdate", sdf.format(new Date()));
		statusMap.put("updateInterval", updateInterval + "");
	}

	public void loadConfig() {
		Resource resource = new FileSystemResource(filePath);
		InputStream in = null;
		try {
			in = resource.getInputStream();
			Properties properties = new Properties();
			properties.load(in);
			String s = properties.getProperty(STOPPED_KEY);
			if (s != null) {
				stoppedPatterns = s.split(",");
			} else {
				stoppedPatterns = new String[] {};
			}
			password = properties.getProperty(PASSWORD_KEY);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public boolean isStopped(String... paths) {
		for (String pattern : stoppedPatterns) {
			for (String path : paths) {
				if (path != null && antPathMatcher.match(pattern, path)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean validPassword(String password) {
		if (this.password.equals(password)) {
			return true;
		}
		return false;
	}

	public Map<String, String> getStoppedStatusMap() {
		return statusMap;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
