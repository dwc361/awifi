package com.zjhcsoft.uac.account.user.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.roof.commons.RoofIPUtils;
import org.roof.struts2.Result;
import org.roof.struts2.WebUtils;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.ldap.util.LdapUtils;
import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.log.service.LogManager;

@Component
public class PasswordLoginCheckUnit implements LoginCheckUnit {

	private LogManager logManager;

	private LdapUtils ldapUtils;

	@Override
	public Result check(Person person) {
		String url = person.getUrl();
		person.setUrl(null);
		List<Person> list = ldapUtils.getPersonList(person);
		if (list.size() == 0) {
			logManager.addAuthLog(new Date(),
					RoofIPUtils.getIp(WebUtils.getRequest()), url, person,
					LogManager.LOGIN_RESULT_FAIL,
					"LOGIN_FAIL_REASON_ERROR_PASSWORD");
			return new Result(Result.FAIL, "用户名不存在!发邮件到 gsdx4a@163.com");
		}
		boolean check = ldapUtils.authenticate(person);
		if (check) {
			return new Result(Result.SUCCESS, "登陆成功!");
		}
		logManager.addAuthLog(new Date(),
				RoofIPUtils.getIp(WebUtils.getRequest()), url, person,
				LogManager.LOGIN_RESULT_FAIL,
				"LOGIN_FAIL_REASON_ERROR_PASSWORD");
		return new Result(Result.FAIL, "密码错误!如果忘记密码发邮件到 gsdx4a@163.com");
	}

	@Resource
	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	@Resource
	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

}
