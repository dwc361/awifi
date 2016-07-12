package com.zjhhcsoft.uac.clinet.webapp;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zjhhcsoft.uac.clinet.UserHolder;

public class SessionUserHolder implements UserHolder {
	private static final String LOGIN_USER = "LOGIN_USER";

	@Override
	public Object getUser(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		return httpServletRequest.getSession().getAttribute(LOGIN_USER); // 在session中获得当前登陆对象
	}

	@Override
	public boolean setUser(Object user, ServletRequest request,
			ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		httpServletRequest.getSession().setAttribute(LOGIN_USER, user); // 在session中保存登陆对象
		return false;
	}

	@Override
	public String getUsername(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		return httpServletRequest.getSession().getAttribute(LOGIN_USER) == null ? null
				: httpServletRequest.getSession().getAttribute(LOGIN_USER)
						.toString(); // 获取当前登陆对象的用户名
	}

	@Override
	public void clear(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		httpServletRequest.getSession().removeAttribute(LOGIN_USER);// 在session中清除当前的登陆对象
		httpServletRequest.getSession().invalidate();

	}

}
