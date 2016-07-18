package org.roof.struts2;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONInterceptor;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * url 结尾为.ajax 或者为空的认为是ajax请求
 * 
 * @author liuxin 2011-3-25
 * 
 */
public class RoofJSONInterceptor extends JSONInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		if (!isJsonUri(uri)) {
			return invocation.invoke();
		}
		String result = super.intercept(invocation);
		if (isIE()) {
			WebUtils.getResponse().setContentType("text/html; charset=utf-8");
		}
		return result;
	}

	private boolean isIE() {
		return WebUtils.getRequest().getHeader("USER-AGENT").toLowerCase()
				.indexOf("msie") > 0 ? true : false;
	}

	private boolean isJsonUri(String uri) {
		if (StringUtils.endsWith(uri, ".ajax")
				|| !StringUtils.contains(uri, ".")) {
			return true;
		}
		return false;
	}
}
