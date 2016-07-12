package com.zjhcsoft.uac.account.user.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.roof.commons.RoofIPUtils;
import org.roof.struts2.Result;
import org.roof.struts2.WebUtils;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.dao.PasswordPolicyDao;
import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;
import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.log.dao.AuthLogDao;
import com.zjhcsoft.uac.log.service.LogManager;

@Component
public class PasswordContinuousErrorLoginCheckUnit implements LoginCheckUnit {

	private static final String CONTINUOUS_ERROR = "continuous_error";
	private static final String UNFREEZE_TIME = "unfreeze_time";
	private static final Logger LOGGER = Logger
			.getLogger(PasswordContinuousErrorLoginCheckUnit.class);
	private Long MINUTE_MILLISECOND = 60 * 1000L;

	private AuthLogDao authLogDao;
	private PasswordPolicyDao passwordPolicyDao;
	private LogManager logManager;

	@Override
	public Result check(Person person) {
		Result result = null;
		try {
			PasswordPolicy continuousErrorPasswordPolicy = passwordPolicyDao
					.findbyName(CONTINUOUS_ERROR);
			int continuousErrorTimes = Integer
					.parseInt(continuousErrorPasswordPolicy.getVal());
			PasswordPolicy unfreezeTimePasswordPolicy = passwordPolicyDao
					.findbyName(UNFREEZE_TIME);
			Date end = new Date();
			Date start = new Date(end.getTime() - MINUTE_MILLISECOND
					* Long.parseLong(unfreezeTimePasswordPolicy.getVal()));
			long times = authLogDao.continuousErrorTimes(start, end,
					person.getCn());
			if (times >= continuousErrorTimes) {
				logManager.addAuthLog(new Date(),
						RoofIPUtils.getIp(WebUtils.getRequest()),
						person.getUrl(), person, LogManager.LOGIN_RESULT_FAIL,
						"LOGIN_FAIL_REASON_CONTINUOUS_ERROR");
				result = new Result(Result.FAIL, "密码连续错误次数过多,请"
						+ unfreezeTimePasswordPolicy.getVal()
						+ unfreezeTimePasswordPolicy.getUnit() + "后再试");
				return result;
			}
			result = new Result();
			result.setState(Result.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			result = new Result(Result.SUCCESS,
					"系统异常请与管理员联系, 异常信息:[密码错误次数验证异常]" + e.getMessage());
			return result;
		}
	}

	@Resource
	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	@Resource
	public void setAuthLogDao(AuthLogDao authLogDao) {
		this.authLogDao = authLogDao;
	}

	@Resource
	public void setPasswordPolicyDao(PasswordPolicyDao passwordPolicyDao) {
		this.passwordPolicyDao = passwordPolicyDao;
	}

}
