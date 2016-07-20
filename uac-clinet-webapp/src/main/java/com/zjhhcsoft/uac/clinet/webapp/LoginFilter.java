package com.zjhhcsoft.uac.clinet.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	private final List<Pattern> patterns = new ArrayList<Pattern>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String patternsStr = filterConfig.getInitParameter("ignorePatterns");
		initPatterns(patternsStr);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession httpSession = httpServletRequest.getSession();

		// 忽略登陆
		String url = httpServletRequest.getRequestURL().toString()
				.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");// 去除所有空格
		if (ignoreUrl(url)) {
			chain.doFilter(request, response);
			return;
		}
		if (httpSession.getAttribute("LOGIN_USER") == null) {
			httpServletRequest.getRequestDispatcher("login.html").forward(
					httpServletRequest, response);
			return;
		}
		chain.doFilter(httpServletRequest, response);
	}

	@Override
	public void destroy() {

	}

	private void initPatterns(String patternsStr) {
		if (patternsStr != null && !patternsStr.trim().equals("")) {
			String[] ps = patternsStr.split(",");
			for (String p : ps) {
				Pattern pat = Pattern.compile(p);
				patterns.add(pat);
			}
		}
	}

	/**
	 * https忽略的文件类型
	 * 
	 * @param url
	 * @return
	 */
	private boolean ignoreUrl(String url) {
		url = url.toLowerCase();
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

}
