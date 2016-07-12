package com.zjhcsoft.uac.account.user.service;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.commons.RoofDateUtils;
import org.roof.commons.RoofIPUtils;
import org.roof.struts2.Result;
import org.roof.struts2.WebUtils;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.log.service.LogManager;

@Component
public class AccountTimeLinessLoginCheckUnit implements LoginCheckUnit {

	private static final Logger LOGGER = Logger
			.getLogger(AccountTimeLinessLoginCheckUnit.class);

	private LogManager logManager;

	@Override
	public Result check(Person person) {
		Result result = new Result();
		result.setState(Result.SUCCESS);
		try {
			if (StringUtils.isBlank(person.getPwdDisableTime())) { // 永久账号
				return result;
			}
			Date disableTime = RoofDateUtils.stringToDate(
					person.getPwdDisableTime(), Person.YYYY_MM_DD);
			if (disableTime.getTime() < new Date().getTime()) {
				result = new Result(Result.FAIL, "您的账号已经过期无法登陆!");
				logManager.addAuthLog(new Date(),
						RoofIPUtils.getIp(WebUtils.getRequest()),
						person.getUrl(), person, LogManager.LOGIN_RESULT_FAIL,
						"LOGIN_FAIL_REASON_OVERDUE");
				return result;
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
			result = new Result(Result.SUCCESS, "系统异常请与管理员联系, 异常信息:[密码时效验证异常]"
					+ e.getMessage());
			return result;
		}
		return result;
	}

	@Resource
	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}
}
