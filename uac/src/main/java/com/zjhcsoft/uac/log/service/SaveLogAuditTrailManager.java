package com.zjhcsoft.uac.log.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.roof.struts2.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.inspektr.audit.AuditActionContext;
import com.github.inspektr.audit.support.AbstractStringAuditTrailManager;
import com.zjhcsoft.uac.ldap.util.Person;

public class SaveLogAuditTrailManager extends AbstractStringAuditTrailManager {

	private LogManager logManager;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void record(AuditActionContext auditActionContext) {
		log.info(toString(auditActionContext));

		if (StringUtils.equals("AUTHENTICATION_SUCCESS",
				auditActionContext.getActionPerformed())) {

			HttpServletRequest httpServletRequest = WebUtils.getRequest();
			String url = getUrl(httpServletRequest);

			String principal = auditActionContext.getPrincipal();
			principal = StringUtils.replaceEach(principal, new String[] { "[",
					"]" }, new String[] { "", "" });
			String[] s = principal.split(":");
			logManager.addAuthLog(auditActionContext
					.getWhenActionWasPerformed(), auditActionContext
					.getClientIpAddress(), url,
					new Person(StringUtils.trim(s[s.length - 1])),
					LogManager.LOGIN_RESULT_SUCCESS, null);
		}

	}

	private String getUrl(HttpServletRequest httpServletRequest) {
		String url = null;
		try {
			url = URLDecoder.decode(httpServletRequest.getQueryString(),
					"UTF-8");
			int i = StringUtils.indexOf(url, "=");
			url = StringUtils.substring(url, i + 1);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		return url;
	}

	public LogManager getLogManager() {
		return logManager;
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

}
