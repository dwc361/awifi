package com.zjhcsoft.uac.account.password.service;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.roof.exceptions.DaoException;

import com.zjhcsoft.uac.account.password.dao.PasswordPolicyDao;
import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;

public abstract class AbstractPasswordValidator implements PasswordValidator {
	protected static final Logger LOGGER = Logger
			.getLogger(PasswordValidator.class);
	protected PasswordPolicyDao passwordPolicyDao;

	protected String formatMessage(String pattern, Object... arguments) {
		return MessageFormat.format(pattern, arguments);
	}

	protected PasswordPolicy findPasswordPolicy(String name) {
		try {
			return passwordPolicyDao.findbyName(name);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Resource
	public void setPasswordPolicyDao(PasswordPolicyDao passwordPolicyDao) {
		this.passwordPolicyDao = passwordPolicyDao;
	}

}
