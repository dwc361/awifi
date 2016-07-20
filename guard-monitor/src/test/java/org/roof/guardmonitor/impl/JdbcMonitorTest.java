package org.roof.guardmonitor.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.roof.guardmonitor.Monitor;
import org.roof.guardmonitor.MonitorContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class JdbcMonitorTest extends AbstractJUnit4SpringContextTests {
	private Monitor monitor;

	@Test
	public void testExecute() {
		MonitorContext monitorContext = new MonitorContext();
		monitor.execute(monitorContext);
		System.out.println(monitorContext);
	}

	@Resource(name = "uacJdbcMonitor")
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

}
