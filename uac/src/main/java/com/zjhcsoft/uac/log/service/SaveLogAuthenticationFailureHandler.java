package com.zjhcsoft.uac.log.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.roof.commons.RoofIPUtils;
import org.roof.security.BaseUserContext;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import com.zjhcsoft.uac.account.user.entity.User;

public class SaveLogAuthenticationFailureHandler extends
		ExceptionMappingAuthenticationFailureHandler {
	private LogManager logManager;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		logManager.addLoginLog((User) BaseUserContext.getCurrentUser(),
				new Date(), LogManager.LOGIN_FAIL_REASON_ERROR_PASSWORD,
				LogManager.LOGIN_RESULT_FAIL, LogManager.LOGIN_TYPE_PASSWORD,
				exception.getMessage(), RoofIPUtils.getIp(request));
		super.onAuthenticationFailure(request, response, exception);
	}

	public LogManager getLogManager() {
		return logManager;
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

}
