package org.roof.guardmonitor.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.roof.guardmonitor.MonitorContext;
import org.roof.guardmonitor.MonitorListener;

public class LoggedMonitorListener implements MonitorListener {

	private static final Logger LOGGER = Logger
			.getLogger(LoggedMonitorListener.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void beforExecute(MonitorContext monitorContext) {

	}

	@Override
	public void afterExecute(MonitorContext monitorContext) {
		log(monitorContext);
		if (monitorContext.getSubContexts() != null) {
			for (MonitorContext context : monitorContext.getSubContexts()) {
				afterExecute(context);
			}
		}

	}

	private String formatDate(Date date) {
		String result = sdf.format(date);
		return result;
	}

	private void log(MonitorContext context) {
		LOGGER.info("\n=======================================\n监视器:"
				+ context.getMonitor().getName()
				+ "\n开始时间:"
				+ formatDate(context.getStartTime())
				+ "\n结束时间:"
				+ formatDate(context.getEndTime())
				+ "\n执行结果:"
				+ context.getStatus()
				+ "\n信息:"
				+ context.getMessage()
				+ "\n响应时间:"
				+ (context.getEndTime().getTime() - context.getStartTime()
						.getTime())
				+ "\n=======================================");

	}

}
