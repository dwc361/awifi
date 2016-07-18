package org.roof.guardmonitor;

/**
 * 监视器监听器
 * 
 * @author liuxin
 *
 */
public interface MonitorListener {
	/**
	 * 监视器执行前
	 * 
	 * @param executeContext
	 */
	void beforExecute(MonitorContext monitorContext);

	/**
	 * 监视器执行后
	 * 
	 * @param executeContext
	 */
	void afterExecute(MonitorContext monitorContext);

}
