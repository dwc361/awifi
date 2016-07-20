package org.roof.guardmonitor.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.roof.guardmonitor.MonitorContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class HttpMonitorTest extends AbstractJUnit4SpringContextTests {

	private HttpMonitor httpMonitor;

	@Test
	public void testExecute() {
		MonitorContext monitorContext = new MonitorContext();
		monitorContext.setMonitor(httpMonitor);
		httpMonitor.execute(monitorContext);
		System.out.println(monitorContext);
	}

	@Resource(name = "baiduHttpMonitor")
	public void setHttpMonitor(HttpMonitor httpMonitor) {
		this.httpMonitor = httpMonitor;
	}

}
