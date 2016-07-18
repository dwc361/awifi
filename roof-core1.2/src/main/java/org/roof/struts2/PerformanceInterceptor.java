package org.roof.struts2;

import java.util.Date;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.log.SysPerformance;
import org.roof.spring.CurrentSpringContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 性能拦截器
 * <p/>
 * 记录每个Action执行的时间<br/>
 * 放置在拦截器栈底的时候记录为Action的执行时间 <br/>
 * 放置在拦截器栈顶的时候记录为整个请求的执行时间 <br/>
 * 
 * @author liuxin 2011-9-19
 * @version 1.0 PerformanceInterceptor.java liuxin 2011-9-19
 */
public class PerformanceInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static RoofDaoSupport roofDaoSupport;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		if (roofDaoSupport == null) {
			roofDaoSupport = CurrentSpringContext.getBean("roofDaoSupport",
					RoofDaoSupport.class);
		}
		String result = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		SysPerformance sysPerformance = new SysPerformance();
		sysPerformance.setAction(actionInvocation.getProxy().getActionName());

		String[] queryStr = new String[request.getParameterMap().size()];
		int i = 0;
		for (Object o : request.getParameterMap().entrySet()) {
			@SuppressWarnings("unchecked")
			Entry<String, Object> entry = (Entry<String, Object>) o;
			String[] vals = (String[]) entry.getValue();
			queryStr[i] = entry.getKey() + "="
					+ StringUtils.join(vals, entry.getKey() + "=");
			i++;
		}
		sysPerformance.setQuery_string(StringUtils.join(queryStr, "&"));
		sysPerformance.setLog_time(new Date());
		long start = System.currentTimeMillis();
		try {
			result = actionInvocation.invoke();
		} finally {
			sysPerformance.setExecute_time(System.currentTimeMillis() - start);
			roofDaoSupport.save(sysPerformance);
		}
		return result;
	}
}
