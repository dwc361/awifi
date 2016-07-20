package org.roof.struts2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.NoParameters;

/**
 * 将请求参数放入名为args的Map, action中 可以使用 getParamsByName(String name)方法取得
 * 
 * 此拦截器必须在 {@link ParametersInterceptor} 之前
 * 
 * @author liuxin 2011-3-14
 * 
 */
public class ParameterInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (!(action instanceof NoParameters)) {
			Map<String, Object> params = initParams();
			PropertyUtils.setProperty(action, "params", params);
		}
		return invocation.invoke();
	}

	private Map<String, Object> initParams() {
		Map<String, Object> args = ActionContext.getContext().getParameters();
		Map<String, Object> params = new HashMap<String, Object>();
		Iterator<Entry<String, Object>> iterator = args.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			Object val = entry.getValue();
			if (val == null) {
				continue;
			}
			if (val.getClass().isArray()) {
				Object[] os = (Object[]) val;
				if (os.length == 1) {
					params.put(entry.getKey(), os[0]);
				}
			} else {
				params.put(entry.getKey(), val);
			}
		}
		return params;
	}

	protected Map<String, Object> retrieveParameters(ActionContext ac) {
		return ac.getParameters();
	}

}
