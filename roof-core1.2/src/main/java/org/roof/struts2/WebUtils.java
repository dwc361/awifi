package org.roof.struts2;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 获取session request response 的工具
 * 
 * @author liuxin
 * 
 */
public class WebUtils {
	/**
	 * Gets the Map of HttpSession values when in a servlet environment or a
	 * generic session map otherwise.
	 * 
	 * @returnthe Map of HttpSession values when in a servlet environment or a
	 *            generic session map otherwise.
	 */
	public static Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}

	public static ActionContext getActionContext() {
		return ActionContext.getContext();
	}

	public static HttpServletRequest getRequest() {
		ActionContext actionContext = getActionContext();
		if (actionContext == null) {
			return null;
		}
		return (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	}

	public static ServletContext getServletContext() {
		ActionContext actionContext = getActionContext();
		if (actionContext == null) {
			return null;
		}
		return (ServletContext) actionContext.get(ServletActionContext.SERVLET_CONTEXT);
	}

	public static HttpServletResponse getResponse() {
		ActionContext actionContext = getActionContext();
		if (actionContext == null) {
			return null;
		}
		return (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	}

	public static ValueStack getValueStack() {
		ActionContext actionContext = getActionContext();
		if (actionContext == null) {
			return null;
		}
		return actionContext.getValueStack();
	}

	public static String getWebRoot() {
		String webroot = WebUtils.getServletContext().getRealPath("/").replaceAll("\\\\", "/");
		return webroot;
	}

	public static String getFullBasePath() {
		HttpServletRequest request = WebUtils.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		return basePath;
	}

	/**
	 * 添加值到当前线程的Request中
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void addParameter(String key, Object value) {
		getValueStack().set(key, value);
	}

	@Deprecated
	public static HttpSession getSession() {
		return (HttpSession) ActionContext.getContext().get(ServletActionContext.SESSION);
	}
}
