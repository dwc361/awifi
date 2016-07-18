package org.roof.guardmonitor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.roof.guardmonitor.AbstractMonitor;
import org.roof.guardmonitor.Monitor;
import org.roof.guardmonitor.MonitorContext;

/**
 * 监视器组
 * 
 * @author liuxin
 *
 */
public class GroupedMonitor extends AbstractMonitor {
	private List<Monitor> monitors;

	@Override
	protected void doInTry(MonitorContext monitorContext) throws Throwable {
		Collection<MonitorContext> contexts = new ArrayList<MonitorContext>();
		for (Monitor monitor : monitors) {
			MonitorContext context = new MonitorContext();
			monitor.execute(context);
			contexts.add(context);
		}
		monitorContext.setSubContexts(contexts);
	}

	@Override
	protected void doFinally() {

	}

	public List<Monitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
	}

}
