package org.roof.guardmonitor;

import java.util.Collection;
import java.util.Date;

/**
 * 监视器上下文
 * 
 * @author liuxin
 *
 */
public class MonitorContext {

	private Monitor monitor; // 监视器
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Status status;// 状态
	private String message; // 信息
	private Collection<MonitorContext> subContexts; // 子监视器状态

	public MonitorContext() {
		status = Status.UNKNOWN;
	}

	public Monitor getMonitor() {
		return monitor;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Status getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<MonitorContext> getSubContexts() {
		return subContexts;
	}

	public void setSubContexts(Collection<MonitorContext> subContexts) {
		this.subContexts = subContexts;
	}

	@Override
	public String toString() {
		return "MonitorContext [monitor=" + monitor.getName() + ", startTime="
				+ startTime + ", endTime=" + endTime + ", status=" + status
				+ ", message=" + message + "]";
	}

}
