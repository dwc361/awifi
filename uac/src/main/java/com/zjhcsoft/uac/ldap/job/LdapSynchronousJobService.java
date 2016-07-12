package com.zjhcsoft.uac.ldap.job;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.roof.commons.RoofDateUtils;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.log.SysLog;
import org.roof.spring.CurrentSpringContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class LdapSynchronousJobService extends QuartzJobBean {
	private static final Logger LOGGER = Logger.getLogger(LdapSynchronousJobService.class);
	private LdapSynchronousService ldapSynchronousService;
	private RoofDaoSupport roofDaoSupport;

	protected void executeInternal(JobExecutionContext cxt) throws JobExecutionException {
		Lock lock = new ReentrantLock();
		try {
			lock.lock();
			Date start = new Date();
			if (ldapSynchronousService == null) {
				ldapSynchronousService = (LdapSynchronousService) CurrentSpringContext
						.getBean("ldapSynchronousService");
			}
			if (roofDaoSupport == null) {
				roofDaoSupport = (RoofDaoSupport) CurrentSpringContext.getBean("roofDaoSupport");
			}
			List<String> result = ldapSynchronousService.DbToLdap();
			Date end = new Date();
			Long[] arr = RoofDateUtils.getTimeSpan(end, start);

			SysLog log = new SysLog();
			log.setLog_time(end);
			log.setLog_level("INFO");
			log.setLocation("com.zjhcsoft.uac.ldap.job.LdapSynchronousJobService.executeInternal");
			log.setMessage("本次执行结果报告：" + result + ",耗时：" + arr[0] + "天" + arr[1] + "小时" + arr[2] + "分钟" + arr[3] + "秒");
			log.setAction("LDAP数据同步定时器");
			roofDaoSupport.save(log);

			LOGGER.info(log.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		} finally {
			lock.unlock();
		}
		LOGGER.info("下一次执行时间：" + RoofDateUtils.dateToString(cxt.getNextFireTime()));
	}

}
