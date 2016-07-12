package com.zjhhcsoft.uac.clinet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

public class UserThreadLocalFilter implements Filter {

	// private static final String CONST_UAC_USER = "_const_uac_user_";

	private UserLoader userLoader;
	private UserHolder userHolder;
	private static final Logger LOGGER = Logger
			.getLogger(UserThreadLocalFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			if (userLoader == null) {
				String userLoaderName = filterConfig
						.getInitParameter("userLoader");
				Class<?> userLoaderType = null;
				userLoaderType = Class.forName(userLoaderName);
				userLoader = (UserLoader) userLoaderType.newInstance();
			}
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
		// 业务系统是否登陆
		if (userHolder.getUsername(request, response) != null) {
			chain.doFilter(request, response);
			return;
		}
		// CAS是否登陆
		if (AssertionHolder.getAssertion() == null
				|| AssertionHolder.getAssertion().getPrincipal() == null
				|| AssertionHolder.getAssertion().getPrincipal().getName() == null) {
			chain.doFilter(request, response);
			return;
		}
		Assertion assertion = AssertionHolder.getAssertion();
		Object obj = null;
		String username = assertion.getPrincipal().getName();
		Map<String, Object> attributes = assertion.getPrincipal()
				.getAttributes();
		if (attributes != null && !attributes.isEmpty()) {
			attributes.put("username", username);
			obj = attributes;
		} else {
			obj = username;
		}
		Object user = null;
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("CAS登陆用户名为:[" + username + "]");
		}
		if (userHolder.getUser(request, response) == null) {
			user = userLoader.load(obj, request, response);
		}
		try {
			boolean c = userHolder.setUser(user, request, response);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("CAS登陆用户名为:[" + username + "]登陆成功");
			}
			if (!c) {
				chain.doFilter(request, response);
			}
		} finally {
			// userHolder.clear();
		}
	}

	public void destroy() {

	}

	public UserLoader getUserLoader() {
		return userLoader;
	}

	public UserHolder getUserHolder() {
		return userHolder;
	}

	public void setUserLoader(UserLoader userLoader) {
		this.userLoader = userLoader;
	}

	public void setUserHolder(UserHolder userHolder) {
		this.userHolder = userHolder;
	}

}
