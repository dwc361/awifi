package com.zjhcsoft.uac.log.service;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.exceptions.ApplicationException;
import org.roof.exceptions.DaoException;
import org.roof.security.BaseUserContext;
import org.roof.web.dictionary.dao.DictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.user.entity.Staff;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.log.entity.AccountLog;
import com.zjhcsoft.uac.log.entity.AuthLog;
import com.zjhcsoft.uac.log.entity.LoginLog;

@Component
public class LogManager implements InitializingBean {

	private static final Logger LOGGER = Logger.getLogger(LogManager.class);

	public static final Long ACCOUNT_OP_TYPE_CREATE = 151L;
	public static final Long ACCOUNT_OP_TYPE_UPDATE = 152L;
	public static final Long ACCOUNT_OP_TYPE_ACTIVE = 154L;
	public static final Long ACCOUNT_OP_TYPE_LOCK = 153L;
	public static final Long ACCOUNT_OP_TYPE_DELETE = 155L;

	public static final String LOGIN_TYPE_PASSWORD = "LOGIN_TYPE_PASSWORD";// 静态密码认证
	public static final String LOGIN_TYPE_CERT = "LOGIN_TYPE_CERT";// 证书认证

	public static final String LOGIN_RESULT_FAIL = "LOGIN_RESULT_FAIL"; // 失败
	public static final String LOGIN_RESULT_SUCCESS = "LOGIN_RESULT_SUCCESS";// 成功

	public static final String LOGIN_FAIL_REASON_ERROR_PASSWORD = "LOGIN_FAIL_REASON_ERROR_PASSWORD";// 错误密码

	private BlockingQueue<Object> logQueue;

	private int saveThreadSize;

	private TaskExecutor taskExecutor;

	private LogSaveRunable logSaveRunable;

	private DictionaryDao dictionaryDao;
	private UserDao userDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		for (int i = 0; i < saveThreadSize; i++) {
			taskExecutor.execute(logSaveRunable);
			LOGGER.info("日志保存线程[" + i + "]启动");
		}
	}

	/**
	 * 添加账号操作日志
	 * 
	 * @param opStaff
	 *            被操作的账号
	 * @param op_time
	 *            操作时间
	 * @param opType
	 *            操作类型 ACCOUNT_OP 开头的常量
	 * @param op_result
	 *            操作结果
	 */
	public void addAccountLog(Staff opStaff, Date op_time, Long opType,
			String op_result) {
		AccountLog accountLog = new AccountLog();
		accountLog.setOp_result(op_result);
		accountLog.setOp_time(op_time);
		accountLog.setOpStaff(opStaff);
		
		
		accountLog.setOpType(new Dictionary(opType));
		accountLog.setUser((Staff) BaseUserContext.getCurrentUser());
		try {
			logQueue.offer(accountLog, 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("账号操作日志入队超时", e);
		}
	}

	/**
	 * 添加登陆日志
	 * 
	 * @param user
	 *            登陆用户
	 * @param login_time
	 *            登陆时间
	 * @param loginFailReason
	 *            失败原因
	 * @param loginResult
	 *            登陆结果
	 * @param loginType
	 *            登陆类型
	 */
	public void addLoginLog(Staff user, Date login_time,
			String loginFailReason, String loginResult, String loginType,
			String loginFailDetails, String ip) {
		LoginLog loginLog = new LoginLog();
		loginLog.setIp(ip);
		loginLog.setLogin_time(login_time);
		loginLog.setUser(user);
		loginLog.setLoginFailDetails(loginFailDetails);
		try {
			if (StringUtils.isNotEmpty(loginFailReason)) {
				loginLog.setLoginFailReason(dictionaryDao.load(
						"LOGIN_FAIL_REASON", loginFailReason));
			}
			if (StringUtils.isNotEmpty(loginResult)) {
				loginLog.setLoginResult(dictionaryDao.load("LOGIN_RESULT",
						loginResult));
			}
			if (StringUtils.isNotEmpty(loginType)) {
				loginLog.setLoginType(dictionaryDao.load("LOGIN_TYPE",
						loginType));
			}
			logQueue.offer(loginLog, 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("登陆日志日志入队超时", e);
		} catch (ApplicationException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void addLoginLog(Person person, Date login_time,
			String loginFailReason, String loginResult, String loginType,
			String loginFailDetails, String ip) {
		try {
			User user = userDao.findByUsername(person.getCn());
			addLoginLog(user, login_time, loginFailReason, loginResult,
					loginType, loginFailDetails, ip);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 记录认证日志
	 * 
	 * @param auth_time
	 *            认证时间
	 * @param ip
	 *            认证ip
	 * @param sysResource
	 *            请求系统
	 * @param staff
	 *            用户
	 * @param loginResult
	 *            认证结果
	 * @param loginFailReason
	 *            认证失败原因
	 */
	public void addAuthLog(Date auth_time, String ip, String requestUrl,
			Staff staff, String loginResult, String loginFailReason) {
		AuthLog authLog = new AuthLog();
		authLog.setIp(ip);
		authLog.setRequestUrl(requestUrl);
		authLog.setStaff(staff);
		authLog.setAuth_time(auth_time);
		try {
			if (StringUtils.isNotEmpty(loginResult)) {
				authLog.setLoginResult(dictionaryDao.load("LOGIN_RESULT",
						loginResult));
			}
			if (StringUtils.isNotEmpty(loginFailReason)) {
				authLog.setLoginFailReason(dictionaryDao.load(
						"LOGIN_FAIL_REASON", loginFailReason));
			}
			logQueue.offer(authLog, 1000, TimeUnit.MILLISECONDS);
		} catch (ApplicationException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error("认证日志日志入队超时", e);
		}

	}

	public void addAuthLog(Date auth_time, String ip, String requestUrl,
			Person person, String loginResult, String loginFailReason) {
		try {
			User user = userDao.findByUsername(person.getCn());
			addAuthLog(auth_time, ip, requestUrl, user, loginResult,
					loginFailReason);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	public BlockingQueue<Object> getLogQueue() {
		return logQueue;
	}

	@Resource(name = "logQueue")
	public void setLogQueue(BlockingQueue<Object> logQueue) {
		this.logQueue = logQueue;
	}

	public int getSaveThreadSize() {
		return saveThreadSize;
	}

	@Value(value = "1")
	public void setSaveThreadSize(int saveThreadSize) {
		this.saveThreadSize = saveThreadSize;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	@Resource(name = "taskExecutor")
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public LogSaveRunable getLogSaveRunable() {
		return logSaveRunable;
	}

	@Resource
	public void setLogSaveRunable(LogSaveRunable logSaveRunable) {
		this.logSaveRunable = logSaveRunable;
	}

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
