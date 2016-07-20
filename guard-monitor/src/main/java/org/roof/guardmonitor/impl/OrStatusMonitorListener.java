package org.roof.guardmonitor.impl;

import org.roof.guardmonitor.AbstractStatusMonitorListener;
import org.roof.guardmonitor.MonitorContext;
import org.roof.guardmonitor.Status;

/**
 * 监视器状态监听器<br />
 * 根据子监视器的状态,修改父监视器的状态<br />
 * 所有子监视器都停止的情况下为停止
 * 
 * @author liuxin
 *
 */
public class OrStatusMonitorListener extends AbstractStatusMonitorListener {

	protected Status getStatus(MonitorContext monitorContext) {
		if (monitorContext.getSubContexts() == null) {
			return monitorContext.getStatus();
		} else {
			for (MonitorContext context : monitorContext.getSubContexts()) {
				if (getStatus(context) == Status.RUNNING) {
					return Status.RUNNING;
				}
			}
			return Status.STOPPED;

		}
	}

}
