package org.roof.commons;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 对Cookie的操作
 * 
 * @author liuxin
 * 
 */
public class CookieUtils {
	private static final Logger LOGGER = Logger.getLogger(CookieUtils.class);

	private static int MAXAGE = -1;// 如果设置为负值的话，则为浏览器进程Cookie(内存中保存)，关闭浏览器就失效

	public static final void remove(String key, HttpServletRequest request,
			HttpServletResponse response) {
		remove(key, null, request, response);
	}

	/**
	 * 删除Cookie
	 * 
	 * @param key
	 *            Cookie 的名称
	 * @param request
	 *            HttpServletRequest
	 */
	public static final void remove(String key, String path,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = getCookie(key, request);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove Cookie:[" + cookie.getPath()
					+ cookie.getName() + ", value:" + cookie.getValue()
					+ ", MaxAge:" + cookie.getMaxAge() + ", path:"
					+ cookie.getPath() + "]");

		}
		cookie.setValue(null);
		cookie.setMaxAge(0);
		cookie.setPath(path == null ? getPath(request) : path);
		response.addCookie(cookie);
	}

	/**
	 * 设置Cookies
	 * 
	 * @param key
	 * @param value
	 * @param response
	 */
	public static final void setCookie(String key, String value,
			HttpServletRequest request, HttpServletResponse response) {
		setCookie(key, value, null, null, request, response);
	}

	/**
	 * 设置Cookie
	 * 
	 * @param key
	 * @param value
	 * @param maxAge
	 * @param response
	 */
	public static final void setCookie(String key, String value,
			Integer maxAge, HttpServletRequest request,
			HttpServletResponse response) {
		setCookie(key, value, null, maxAge, request, response);

	}

	/**
	 * 设置Cookie
	 * 
	 * @param key
	 *            Cookie 的名称
	 * @param value
	 *            Cookie 的值
	 * @param response
	 *            HttpServletResponse
	 */
	public static final void setCookie(String key, String value, String path,
			Integer maxAge, HttpServletRequest request,
			HttpServletResponse response) {
		Assert.notNull(response);
		Assert.hasText(key);
		Assert.hasText(value);

		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge == null ? MAXAGE : maxAge);
		cookie.setPath(path == null ? getPath(request) : path);
		response.addCookie(cookie);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Add Cookie:[" + cookie.getPath() + cookie.getName()
					+ ", value:" + cookie.getValue() + ", MaxAge:"
					+ cookie.getMaxAge() + ", path:" + cookie.getPath() + "]");

		}
	}

	/**
	 * 得到指定Cookie
	 * 
	 * @param name
	 *            Cookie 的名称
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	public static final Cookie getCookie(String name, HttpServletRequest request) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * 得到制定Cookie的值
	 * 
	 * @param name
	 *            Cookie 的名称
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	public static final String getValue(String name, HttpServletRequest request) {
		Cookie cookie = getCookie(name, request);
		return ((cookie == null) ? null : cookie.getValue());
	}

	private static String getPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return (path == null || path.length() == 0) ? getPath(request) : path;
	}

}
