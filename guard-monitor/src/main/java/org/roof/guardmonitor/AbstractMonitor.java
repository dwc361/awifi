package org.roof.guardmonitor;

import java.util.Collection;
import java.util.Date;

public abstract class AbstractMonitor implements Monitor {

	private String name;
	protected Collection<MonitorListener> listeners;

	public AbstractMonitor() {
		super();
	}

	@Override
	public Status execute(MonitorContext monitorContext) {
		synchronized (this) {
			monitorContext.setMonitor(this);
			monitorContext.setStatus(Status.UNKNOWN);
			if (listeners != null) {
				for (MonitorListener listener : listeners) {
					listener.beforExecute(monitorContext);
				}
			}
			monitorContext.setStartTime(new Date());
			try {
				doInTry(monitorContext);
			} catch (Throwable e) {
				monitorContext.setStatus(Status.STOPPED);
				monitorContext.setMessage(e.getMessage());
				monitorContext.setEndTime(new Date());
				return Status.STOPPED;
			} finally {
				doFinally();
			}
			monitorContext.setEndTime(new Date());
			if (listeners != null) {
				for (MonitorListener listener : listeners) {
					listener.afterExecute(monitorContext);
				}
			}
			return monitorContext.getStatus();
		}
	}

	/**
	 * 执行监控逻辑<br />
	 * 设置监控状态 : monitorContext.status<br />
	 * 设置监控信息 : monitorContext.message<br />
	 * 
	 * @param monitorContext
	 * @throws Throwable
	 */
	protected abstract void doInTry(MonitorContext monitorContext)
			throws Throwable;

	protected abstract void doFinally();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<MonitorListener> getListeners() {
		return listeners;
	}

	public void setListeners(Collection<MonitorListener> listeners) {
		this.listeners = listeners;
	}

}