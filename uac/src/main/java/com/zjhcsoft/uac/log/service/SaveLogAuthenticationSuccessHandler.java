package com.zjhcsoft.uac.log.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.roof.commons.RoofIPUtils;
import org.roof.security.BaseUserContext;
import org.roof.web.user.entity.Staff;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class SaveLogAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {
	private LogManager logManager;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		logManager.addLoginLog((Staff) BaseUserContext.getCurrentUser(),
				new Date(), null, LogManager.LOGIN_RESULT_SUCCESS,
				LogManager.LOGIN_TYPE_PASSWORD, null,
				RoofIPUtils.getIp(request));
		super.onAuthenticationSuccess(request, response, authentication);
	}

	public LogManager getLogManager() {
		return logManager;
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

}
