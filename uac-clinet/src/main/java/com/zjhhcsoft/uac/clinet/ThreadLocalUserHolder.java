package com.zjhhcsoft.uac.clinet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ThreadLocalUserHolder implements UserHolder {

	private static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

	public Object getUser(ServletRequest request, ServletResponse response) {
		return threadLocal.get(); // 在ThreadLocal中获得当前登陆对象
	}

	public boolean setUser(final Object user, ServletRequest request,
			ServletResponse response) {
		threadLocal.set(user);// 在ThreadLocal中保存当前登陆用户对象
		return false;
	}

	public void clear(ServletRequest request, ServletResponse response) {
		threadLocal.set(null); // 在ThreadLocal中删除当前登陆对象
	}

	public String getUsername(ServletRequest request, ServletResponse response) {
		return threadLocal.get() == null ? null : threadLocal.get().toString();
	}

}
