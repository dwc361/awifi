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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;

@Deprecated
public class GreenChannelFilter implements Filter {
	private String url;
	private String loginUrl;
	private UserHolder userHolder;
	private static final Logger LOGGER = Logger
			.getLogger(GreenChannelFilter.class);
	private ServerHealth serverHealth;

	public void init(FilterConfig filterConfig) throws ServletException {

		if (url == null) {
			url = filterConfig.getInitParameter("url");
		}
		serverHealth = ServerHealth.newInstance(url);
		if (loginUrl == null) {
			loginUrl = filterConfig.getInitParameter("loginUrl");
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

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpSession session = request.getSession(false);
		final Assertion assertion = session != null ? (Assertion) session
				.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : null;
		if (assertion != null) {
			filterChain.doFilter(request, response);
			return;
		}
		if (userHolder.getUsername(request, response) != null) {
			session.setAttribute(
					AbstractCasFilter.CONST_CAS_ASSERTION,
					new AssertionImpl(userHolder.getUsername(request, response)));
		}

		if (!serverHealth.running()
				&& !loginUrl.endsWith(request.getRequestURI())) {
			response.sendRedirect(loginUrl);
			return;
		}
		filterChain.doFilter(request, response);

	}

	public void destroy() {

	}

	public String getUrl() {
		return url;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public UserHolder getUserHolder() {
		return userHolder;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setUserHolder(UserHolder userHolder) {
		this.userHolder = userHolder;
	}

}
