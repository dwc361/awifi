package org.roof.guardmonitor;

import org.springframework.beans.factory.InitializingBean;

public class MonitorStarter implements InitializingBean {
	private Monitor monitor;

	public void start() {
		MonitorContext monitorContext = new MonitorContext();
		monitor.execute(monitorContext);
	}

	public Monitor getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.start();
	}

}
