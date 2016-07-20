package com.zjhhcsoft.uac.clinet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 用户加载器
 * 
 * @author liuxin
 * 
 */
public interface UserLoader {
	/**
	 * 更具用户名加载用户
	 * 
	 * @param username
	 *            用户名
	 * @return 用户对象
	 */
	Object load(Object username, ServletRequest request,
			ServletResponse response);
}
