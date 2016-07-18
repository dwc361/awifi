package org.roof.guardmonitor;

import java.util.Collection;

/**
 * 监视器
 * 
 * @author liuxin
 *
 */
public interface Monitor {
	/**
	 * 获得监视器名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 设置监视器名称
	 */
	void setName(String name);

	/**
	 * 添加监听器
	 * 
	 * @return
	 */
	Collection<MonitorListener> getListeners();

	/**
	 * 删除监听器
	 * 
	 * @param listeners
	 */
	void setListeners(Collection<MonitorListener> listeners);

	/**
	 * 执行监视器
	 * 
	 * @return
	 */
	Status execute(MonitorContext monitorContext);

}
