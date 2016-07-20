package com.zjhhcsoft.uac.clinet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 用户上下文
 * 
 * @author liuxin
 *
 */
public interface UserHolder {

	public Object getUser(ServletRequest request, ServletResponse response);

	/**
	 * 登陆系统
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return 是否继续过滤器
	 */
	public boolean setUser(final Object user, ServletRequest request,
			ServletResponse response);

	public String getUsername(ServletRequest request, ServletResponse response);

	public void clear(ServletRequest request, ServletResponse response);

}
