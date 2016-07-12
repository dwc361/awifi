package com.zjhcsoft.uac.account.user.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.roof.commons.RoofDateUtils;
import org.roof.struts2.Result;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.dao.PasswordPolicyDao;
import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;
import com.zjhcsoft.uac.ldap.util.Person;

@Component
public class PasswordTimelinessLoginCheckUnit implements LoginCheckUnit {

	private static final String PASSWORD_TIME_REMIND = "password_time_remind";

	private static final String PERIOD_OF_VALIDITY = "period_of_validity";

	private PasswordPolicyDao passwordPolicyDao;
	private Long DAY_MILLISECOND = 24 * 60 * 60 * 1000L;
	private static Logger LOGGER = Logger
			.getLogger(PasswordTimelinessLoginCheckUnit.class);

	@Override
	public Result check(Person person) {
		Result result = new Result();
		result.setState(Result.SUCCESS);
		try {
			PasswordPolicy periodPasswordPolicy = passwordPolicyDao
					.findbyName(PERIOD_OF_VALIDITY);
			PasswordPolicy remindPasswordPolicy = passwordPolicyDao
					.findbyName(PASSWORD_TIME_REMIND);
			long periodTime = Long.parseLong(periodPasswordPolicy.getVal())
					* DAY_MILLISECOND;
			long remind = Long.parseLong(remindPasswordPolicy.getVal())
					* DAY_MILLISECOND;
			Date setTime = RoofDateUtils.stringToDate(person.getPwdSetTime(),
					Person.YYYY_MM_DD);
			Date now = new Date();
			if (setTime.getTime() + periodTime < now.getTime()) {
				result = new Result(Result.SUCCESS, "您的密码已经过期请及时设置新密码!");
				return result;
			}
			if (setTime.getTime() + remind < now.getTime()) {
				result = new Result(Result.SUCCESS, "您的密码即将过期请及时设置新密码!");
				return result;
			}
			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			result = new Result(Result.SUCCESS, "系统异常请与管理员联系, 异常信息:[密码时效验证异常]"
					+ e.getMessage());
			return result;
		}
	}

	@Resource
	public void setPasswordPolicyDao(PasswordPolicyDao passwordPolicyDao) {
		this.passwordPolicyDao = passwordPolicyDao;
	}

}
