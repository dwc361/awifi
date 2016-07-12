package com.zjhcsoft.uac.log.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.log.entity.AuthLog;

@Component
public class LogSaveRunable implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(LogSaveRunable.class);
	private RoofDaoSupport roofDaoSupport;
	private BlockingQueue<Object> logQueue;
	private AuthLogService authLogService;

	@Override
	public void run() {
		while (true) {
			Object log = null;
			try {
				log = logQueue.poll(1000L, TimeUnit.MILLISECONDS);
				if (log != null) {
					if (log instanceof AuthLog) {
						authLogService.fillRes((AuthLog) log);
					}
					roofDaoSupport.save(log);
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
	}

	public BlockingQueue<Object> getLogQueue() {
		return logQueue;
	}

	@Resource(name = "logQueue")
	public void setLogQueue(BlockingQueue<Object> logQueue) {
		this.logQueue = logQueue;
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

	@Resource
	public void setAuthLogService(AuthLogService authLogService) {
		this.authLogService = authLogService;
	}

}
