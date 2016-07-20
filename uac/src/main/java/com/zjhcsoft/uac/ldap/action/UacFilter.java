package com.zjhcsoft.uac.ldap.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.roof.commons.EscapeUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.exceptions.ApplicationException;
import org.roof.struts2.WebUtils;

public class UacFilter implements Filter {

	List<String> interceptorRule;// 过滤拦截规则

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (interceptorRule == null) {
			interceptorRule = PropertiesUtil.initPropList(interceptorRule, "interceptorRule");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getRequestURL().toString().replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");// 去除所有空格
		url = EscapeUtils.unescape(url);
		if (url.endsWith("logo_icon.ico")) {// 部分容器登录成功之后会到这个ico,将其直接跳转到后台主页面
			// req.getRequestDispatcher("/uacAction!goMain.action").forward(req, res);
			res.sendRedirect(WebUtils.getFullBasePath() + "uacAction!goMain.action");
			return;
		}
		if (this.passInterceptorRule(url)) {
			boolean argsOk = true;// 参数合法
			// 检验参数合法性
			Map mapParams = req.getParameterMap();
			java.util.Iterator iterator = mapParams.entrySet().iterator();
			for (; iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String name = (String) entry.getKey();
				if(!this.passInterceptorRule(name)) {
					argsOk = false;
					break;
				}
				String[] values = request.getParameterValues(name);
				for (int i = 0; i < values.length; i++) {
					String param = (String) values[i];
					if (!this.passInterceptorRule(param)) {
						argsOk = false;
						break;
					}
				}
			}
			if (argsOk) {
				chain.doFilter(req, res);
			} else {
				req.getRequestDispatcher("/err.jsp").forward(req, res);
				return;
			}
		} else {
			req.getRequestDispatcher("/err.jsp").forward(req, res);
			return;
		}
	}

	@Override
	public void destroy() {

	}

	/**
	 * 拦截器过滤规则
	 * 
	 * @param value
	 * @return
	 */
	private boolean passInterceptorRule(String value) {
		boolean flag = true;
		value = value.toLowerCase();
		for (String rule : interceptorRule) {
			if (value.contains(rule.toLowerCase())) {
				return false;
			}
		}
		return flag;
	}
}
