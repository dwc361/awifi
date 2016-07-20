package com.zjhhcsoft.uac.clinet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class UacLogOutFilter implements Filter {

	private String casServerLoginUrl;
	private UserHolder userHolder;

	private static final Logger LOGGER = Logger
			.getLogger(UserThreadLocalFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		if (casServerLoginUrl == null) {
			casServerLoginUrl = filterConfig
					.getInitParameter("casServerLoginUrl");
		}
		try {
			if (userHolder == null) {
				String userHolderTypeName = filterConfig
						.getInitParameter("userHolder");
				Class<?> userHolderType = null;
				userHolderType = Class.forName(userHolderTypeName);
				userHolder = (UserHolder) userHolderType.newInstance();
			}
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		userHolder.clear(request, response);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (!ServerHealth.getInstance().running()) {
			httpServletResponse.sendRedirect(httpServletRequest
					.getContextPath());
		} else {
			httpServletResponse.sendRedirect(casServerLoginUrl);
		}
	}

	public void destroy() {

	}

}
