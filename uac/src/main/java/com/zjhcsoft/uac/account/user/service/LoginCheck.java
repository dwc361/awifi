package com.zjhcsoft.uac.account.user.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.roof.struts2.Result;

import com.zjhcsoft.uac.ldap.util.Person;

public class LoginCheck {

	private List<LoginCheckUnit> checkUnits;

	public Result check(Person person) {
		String message = "";
		for (LoginCheckUnit checkUnit : checkUnits) {
			Result result = checkUnit.check(person);
			if (StringUtils.equals(result.getState(), Result.FAIL)) {
				return result;
			} else {
				if (StringUtils.isNotEmpty(result.getMessage())) {
					message += result.getMessage() + "\n";
				}
			}
		}
		return new Result(message);
	}

	public List<LoginCheckUnit> getCheckUnits() {
		return checkUnits;
	}

	public void setCheckUnits(List<LoginCheckUnit> checkUnits) {
		this.checkUnits = checkUnits;
	}

}
