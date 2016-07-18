package org.roof.guardmonitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 监视器状态监听器<br />
 * 根据子监视器的状态,修改父监视器的状态
 * 
 * @author liuxin
 *
 */
public abstract class AbstractStatusMonitorListener implements MonitorListener {

	private Map<String, String> statusMap;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private boolean running = true;
	private long updateInterval = 1000 * 60;

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void beforExecute(MonitorContext monitorContext) {

	}

	@Override
	public void afterExecute(MonitorContext monitorContext) {
		Status result = getStatus(monitorContext);
		monitorContext.setStatus(result);
		statusMap.put("status", result.toString());
		if (!running) {
			statusMap.put("status", Status.STOPPED.toString());
		}
		statusMap.put("lastUpdate", formatDate(new Date()));
		statusMap.put("updateInterval", updateInterval + "");

	}

	protected abstract Status getStatus(MonitorContext monitorContext);

	private String formatDate(Date date) {
		String result = sdf.format(date);
		return result;
	}

	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}

}
