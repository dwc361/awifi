package org.roof.struts2;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.log.SysLog;
import org.roof.spring.CurrentSpringContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 异常拦截器
 * <p/>
 * 当请求以.ajax或者空为后缀时将action抛出的异常以json形式返回到前台页面<br/>
 * 当请求以.action为后缀时将action抛出的异常时页面将跳转到/error.jsp页面并且将异常信息打印到页面上
 * <p/>
 * 产生的异常信息会记录到SYS_LOG表中
 * 
 * @author liuxin 2011-3-14
 * 
 */
public class ExceptionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private static RoofDaoSupport roofDaoSupport;

	private static final Logger LOGGER = Logger
			.getLogger(ExceptionInterceptor.class);

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		if (roofDaoSupport == null) {
			roofDaoSupport = CurrentSpringContext.getBean("roofDaoSupport",
					RoofDaoSupport.class);
		}
		String result = null;
		try {
			result = actionInvocation.invoke();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			SysLog sysLog = new SysLog();
			HttpServletRequest request = ServletActionContext.getRequest();
			String uri = request.getRequestURI();
			Object action = actionInvocation.getAction();
			sysLog.setLocation(action.getClass().getName());
			sysLog.setLog_time(new Date());
			sysLog.setAction(actionInvocation.getProxy().getActionName());
			sysLog.setMessage(e.getMessage());
			sysLog.setDetail_message(ExceptionUtils.getStackTrace(e));
			sysLog.setLog_level("error");
			if (request.getQueryString() != null) {
				sysLog.setQuery_string(request.getQueryString());
			}
			if (MapUtils.isNotEmpty(request.getParameterMap())) {
				@SuppressWarnings("unchecked")
				Map<String, Object> m = request.getParameterMap();
				String queryStr = createQueryStr(m);
				queryStr = sysLog.getQuery_string() == null ? queryStr : sysLog
						.getQuery_string() + queryStr;
				sysLog.setQuery_string(queryStr);
			}
			roofDaoSupport.save(sysLog);
			if (isJsonUri(uri)) {
				Result r = new Result(e);
				actionInvocation.getStack().set("result", r);
				result = RoofActionSupport.JSON;
			} else {
				actionInvocation.getStack().set("result", e.getMessage());
				result = RoofActionSupport.ERROR;
			}
		}
		return result;
	}

	private String createQueryStr(Map<String, Object> m) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Object> entry : m.entrySet()) {
			if (entry.getValue().getClass().isArray()) {
				Object[] os = (Object[]) entry.getValue();
				for (Object object : os) {
					createParamStr(entry.getKey(), object, sb);
				}
			} else {
				createParamStr(entry.getKey(), entry.getValue(), sb);
			}
			sb.append("&");
		}
		return sb.toString();
	}

	private void createParamStr(String key, Object val, StringBuffer sb) {
		sb.append(key);
		sb.append("=");
		sb.append(ObjectUtils.toString(val));
	}

	private boolean isJsonUri(String uri) {
		if (StringUtils.endsWith(uri, ".ajax")
				|| !StringUtils.contains(uri, ".")) {
			return true;
		}
		return false;
	}
}
