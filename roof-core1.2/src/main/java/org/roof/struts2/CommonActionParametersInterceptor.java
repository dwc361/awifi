package org.roof.struts2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.spring.CurrentSpringContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * action通用数据加载拦截器
 * 
 * @author liuxin 2011-3-25
 * 
 */
public class CommonActionParametersInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private static String paramStr;
	private static List<ICommonActionParameters> actionParameters;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		initParametersList();
		for (ICommonActionParameters cap : actionParameters) {
			this.addParams(cap.getParameters());
		}
		return invocation.invoke();
	}

	private List<ICommonActionParameters> initParametersList()
			throws IOException {
		if (StringUtils.isBlank(paramStr)) {
			paramStr = (String) PropertiesUtil
					.getPorjectProperty("project.action.commonActionParameters");
		}
		if (actionParameters == null) {
			actionParameters = new ArrayList<ICommonActionParameters>();
			String[] params = StringUtils.split(paramStr, ",");
			for (String param : params) {
				ICommonActionParameters ap = (ICommonActionParameters) CurrentSpringContext
						.getBean(param);
				actionParameters.add(ap);
			}
		}
		return actionParameters;
	}

	private void addParam(String key, Object value) {
		ActionContext.getContext().getValueStack().set(key, value);
	}

	public void addParams(Map<String, Object> values) {
		for (Entry<String, Object> entry : values.entrySet()) {
			this.addParam(entry.getKey(), entry.getValue());
		}
	}

}
