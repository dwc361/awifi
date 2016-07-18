package org.roof.struts2;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.roof.commons.Assert;
import org.roof.commons.RoofStringUtils;
import org.roof.dataaccess.Page;
import org.roof.exceptions.FrameworkException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * ActionSupport替代
 * </p>
 * 
 * 包括请求获取参数 获取session request response 等功能
 * 
 * @author liuxin
 */
public class RoofActionSupport extends ActionSupport {

	private static final long serialVersionUID = -6932197474774957107L;

	public static final String JSON = "json";
	public static final String FREEMARKER = "freemarker";
	public static final String JSP = "jsp";
	public static final String ACTION = "action";
	public static final String REDIRECT = "redirect";
	public static final String STREAM = "stream";
	public static final String CHAIN = "chain";

	protected String reqParams;
	protected Map<String, Object> jsonParams;
	protected Map<String, Object> params;
	protected Object result;
	protected static final Logger logger = Logger.getLogger(RoofActionSupport.class);

	/**
	 * 转换成JSON的格式
	 * 
	 * @param ids
	 *            1,2,3
	 * @return
	 */
	protected String toJSON(String ids) {
		if (StringUtils.isEmpty(ids)) {
			return "";
		}
		StringBuffer sf = new StringBuffer("[");
		String[] arr = ids.split(",");
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i];
			if (StringUtils.isEmpty(str.trim())) {
				continue;
			}
			sf.append("{");
			sf.append("\"id\":" + str.trim());
			sf.append("},");
		}
		sf.deleteCharAt(sf.length() - 1);
		sf.append("]");
		return sf.toString();
	}

	/**
	 * 取得json请求参数列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getJsonParamsMap() {
		if (jsonParams == null) {
			jsonParams = new HashMap<String, Object>();
			JSONObject jsonObject = JSONObject.fromObject(reqParams);
			this.jsonParams = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		}
		return jsonParams;
	}

	/**
	 * 将参数返回数组
	 * 
	 * @param key
	 * @return
	 */
	protected String[] getParamArrByName(String key) {
		Object obj = this.getParamByName(key);
		if (obj == null) {
			return new String[0];
		}
		String[] strArr = new String[1];
		if (obj instanceof String) {
			strArr[0] = obj.toString();
		} else {
			strArr = (String[]) obj;
		}
		return strArr;
	}

	/**
	 * /** 取得请求参数列表
	 * 
	 * @return
	 */
	protected Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		map.putAll(WebUtils.getValueStack().getContext());
		map.putAll(getJsonParamsMap());
		return map;
	}

	/**
	 * @see #getArgByName(String, Class)
	 */
	protected Object getParamByName(String name) {
		return this.getParamByName(name, Object.class);
	}

	/**
	 * <p>
	 * 获得请求参数,前台页面可以以2种方式传递参数 ,例如:
	 * </p>
	 * 
	 * <pre>
	 * 1.url?arg1=name&arg2=name2
	 * 2.url?requestArgs={arg1:name,arg2:name2}
	 * </pre>
	 * 
	 * 会优先查找第一种方式
	 * 
	 * @param <T>
	 * @param name
	 *            参数名
	 * @param clazz
	 *            参数类型
	 * @return 页面请求参数
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getParamByName(String name, Class<T> clazz) {
		Assert.hasText(name);
		Assert.isInstanceOf(Class.class, clazz);
		if (params == null) {
			return null;
		}
		Object val = params.get(name);
		if (val == null) {
			val = WebUtils.getValueStack().findValue(name, clazz);
		}
		if (val != null) {
			val = typeConvert(clazz, val);
			return (T) val;
		}
		return null;
	}

	private Object typeConvert(Class<?> cls, Object result) {
		if (!isSimple(cls)) {
			return result;
		}
		ConvertUtils.register(new LongConverter(null), Long.class);  
	    ConvertUtils.register(new ShortConverter(null), Short.class);  
	    ConvertUtils.register(new IntegerConverter(null), Integer.class);  
	    ConvertUtils.register(new DoubleConverter(null), Double.class);  
	    ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		return ConvertUtils.convert(result.toString(), cls);
	}

	public static boolean isSimple(Class<?> cls) {
		if (cls.isPrimitive())
			return true;
		if (cls == String.class)
			return true;
		if (cls == Short.class)
			return true;
		if (cls == Integer.class)
			return true;
		if (cls == Long.class)
			return true;
		if (cls == Double.class)
			return true;
		if (cls == Float.class)
			return true;
		if (cls == Boolean.class)
			return true;
		if (cls == Byte.class)
			return true;
		if (cls == Character.class)
			return true;
		return false;
	}

	/**
	 * 默认前缀为类名首字母改为小写
	 * 
	 * @see RoofActionSupport#getBean(String, Object)
	 * 
	 */
	protected void getBean(Object target) throws FrameworkException {
		String name = target.getClass().getSimpleName();
		name = RoofStringUtils.firstLowerCase(name);
		getBean(name, target);
	}

	/**
	 * 从请求参数中,获取bean
	 * 
	 * @param name
	 *            前缀, name==null 或者 name="" 则直接从参数中copy属性
	 * @param target
	 */
	protected void getBean(String name, Object target) throws FrameworkException {
		if (copyProperties(name, getParamsMap(), target))
			return;
	}

	private boolean copyProperties(String name, Map<String, Object> source, Object target) throws FrameworkException {
		if (source == null) {
			return false;
		}
		try {
			if (StringUtils.isBlank(name)) {
				BeanUtils.copyProperties(target, source);
				return true;
			}
			name = name + ".";
			Map<String, Object> map = new HashMap<String, Object>();
			for (Entry<String, Object> entry : source.entrySet()) {
				if (StringUtils.startsWith(entry.getKey(), name)) {
					String key = StringUtils.substring(entry.getKey(), name.length());
					map.put(key, entry.getValue());
				}
			}
			if (map.size() == 0) {
				return false;
			}
			BeanUtils.copyProperties(target, map);
		} catch (IllegalAccessException e) {
			throw new FrameworkException(e);
		} catch (InvocationTargetException e) {
			throw new FrameworkException(e);
		}
		return true;
	}

	protected void addParameter(String key, Object value) {
		WebUtils.addParameter(key, value);
	}

	/**
	 * 获取带前缀的分页对象如:page1.currentPage=1
	 * 
	 * @param prefix
	 *            前缀
	 * @return 分页对象
	 */
	protected Page cratePage(String prefix) {
		Page page = new Page();
		if (StringUtils.isBlank(prefix)) {
			prefix = "";
		} else {
			prefix += ".";
		}
		Integer rowCount = this.getParamByName(prefix + "rowCount", Integer.class);
		Integer start = this.getParamByName(prefix + "start", Integer.class);
		Integer limit = this.getParamByName(prefix + "limit", Integer.class);
		Integer currentPage = this.getParamByName(prefix + "currentPage", Integer.class);
		if (limit == null || limit == 0) {
			limit = (int) Page.DEFAULT_LIMIT;
		}
		if (rowCount != null) {
			page.setTotal(rowCount.longValue());
		}
		if (start != null) {
			page.setStart(start.longValue());
		}
		if (limit != null) {
			page.setLimit(limit.longValue());
		}
		if (currentPage != null) {
			page.setCurrentPage(currentPage.longValue());
		}
		return page;

	}

	/**
	 * 获取分页
	 * 
	 * @return 分页对象
	 */
	protected Page createPage() {
		return cratePage(null);
	}

	/**
	 * 获得请求里面的参数字符串
	 * 
	 * @param request
	 * @return ?name=aaa&id=1
	 */
	protected String getParamString(HttpServletRequest request) {
		Map mapParam = request.getParameterMap();
		StringBuffer sb = new StringBuffer("?");
		Iterator iterator = mapParam.entrySet().iterator();
		for (; iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String name = (String) entry.getKey();
			String[] values = request.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				if (StringUtils.isEmpty(values[i])) {
					continue;
				}
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

	/**
	 * 获得页面的根目录
	 * 
	 * @return 返回字符串中包含最后一个反斜杠 <br/>
	 *         如： E:/proj/WebRoot/
	 */
	protected String getWebRoot() {
		String webroot = WebUtils.getServletContext().getRealPath("/").replaceAll("\\\\", "/");
		return webroot;
	}

	protected String getFullBasePath() {
		HttpServletRequest request = WebUtils.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		return basePath;
	}

	/**
	 * Gets the Map of HttpSession values when in a servlet environment or a
	 * generic session map otherwise.
	 * 
	 * @returnthe Map of HttpSession values when in a servlet environment or a
	 *            generic session map otherwise.
	 */
	protected Map<String, Object> getSessionMap() {
		return WebUtils.getSessionMap();
	}

	protected HttpServletRequest getRequest() {
		return WebUtils.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return WebUtils.getResponse();
	}

	@SuppressWarnings("deprecation")
	protected HttpSession getSession() {
		return WebUtils.getSession();
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getResult() {
		return result;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
