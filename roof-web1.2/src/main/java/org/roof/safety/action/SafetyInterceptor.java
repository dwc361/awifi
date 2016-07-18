package org.roof.safety.action;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.roof.exceptions.ApplicationException;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.springframework.web.util.HtmlUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.NoParameters;

/**
 * 登录拦截器
 * <p/>
 * 未登录的用户会前往登录界面
 * 
 * 
 */
public class SafetyInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(SafetyInterceptor.class);

	private static List<String> legalIp;// 允许访问的IP段

	@Override
	public void init() {
		try {
			if (legalIp == null) {
				legalIp = PropertiesUtil.initPropList(legalIp, "legalIp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	@Override
	public String intercept(ActionInvocation actionInvocation)
			throws ApplicationException {
		String result = null;
		try {
			// 检验IP段
			HttpServletRequest request = WebUtils.getRequest();
			String url = request.getRequestURL().toString();
			logger.info("访问地址：" + url);
			if (!this.passIp(url)) {
				throw ApplicationException.newInstance("BL00004");
			}

			// 检验参数合法性
			Object action = actionInvocation.getAction();
			ActionContext ac = actionInvocation.getInvocationContext();
			if (!(action instanceof NoParameters)) {
				final Map<String, Object> parameters = ac.getParameters();
				for (Entry<String, Object> paramEntry : parameters.entrySet()) {
					Object val = paramEntry.getValue();
					if (!(val instanceof String[])) {
						continue;
					}
					String[] valArray = (String[]) val;
					for (int i = 0; i < valArray.length; i++) {
						String valStr = valArray[i];
						String escped = HtmlUtils.htmlEscape(valStr);
						if (!StringUtils.equals(escped, valStr)) {
							valArray[i] = escped;
							if (logger.isInfoEnabled()) {
								logger.info("访问地址:[" + url + "] 参数["
										+ paramEntry.getKey() + "]被HTML转义!");
							}
						}
					}
					paramEntry.setValue(valArray);
				}
			}

			result = actionInvocation.invoke();

		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e);
			actionInvocation.getStack().set("result", e.getExceptionCode());
			actionInvocation.getStack().set("tip",
					ApplicationException.getErrorMsg(e.getExceptionCode()));
			result = RoofActionSupport.ERROR;
		} catch (MissingResourceException e) {
			e.printStackTrace();
			logger.error(e);
			String message = ApplicationException
					.getErrorMsg(ApplicationException.DEFAULT_EXCEPTION_CODE);
			message = MessageFormat
					.format(message, new Object[] { e.getKey() });
			actionInvocation.getStack().set("result",
					ApplicationException.DEFAULT_EXCEPTION_CODE);
			actionInvocation.getStack().set("tip", message);
			result = RoofActionSupport.ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return result;
	}

	/**
	 * 允许访问的IP段
	 * 
	 * @param value
	 * @return
	 * @throws ApplicationException
	 */
	private boolean passIp(String value) throws ApplicationException {
		for (String ip : legalIp) {
			if (value.contains(ip)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得查询参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getParamString(HttpServletRequest request) {
		Map mapParam = request.getParameterMap();
		StringBuffer sb = new StringBuffer("?");
		Iterator iterator = mapParam.entrySet().iterator();
		for (; iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String name = (String) entry.getKey();
			String[] values = request.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				sb.append(name);
				sb.append("=");
				sb.append(values[i]);
				sb.append("&");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		String strParam = sb.toString();
		return strParam;
	}
}
