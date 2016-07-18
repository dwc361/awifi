package org.roof.safety.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.roof.commons.EscapeUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.exceptions.ApplicationException;
import org.springframework.util.AntPathMatcher;

/**
 * 初始化
 * 
 * org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter
 * 
 */
public class RoofApplicationFilter implements Filter {

	private final Log _logger = LogFactory.getLog(this.getClass());

	private static List<String> interceptorRule = new ArrayList<String>();// 过滤拦截规则

	private static List<String> httpsUrlList = new ArrayList<String>();// 需要https的路径集合

	private static List<String> ignoreUrlType = new ArrayList<String>();// https忽略转换的文件类型

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			
			if (this.antiLeech(req, res)) {
				req.getRequestDispatcher("/common/images/err.jpg").forward(req, res);
				return;
			}
			String url = req.getRequestURL().toString().replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");// 去除所有空格
			url = EscapeUtils.unescape(url);
			if (this.passInterceptorRule(url)) {
				boolean argsOk = true;// 参数合法，过滤器不检查参数
//				// 检验参数合法性
				Map mapParams = req.getParameterMap();
				java.util.Iterator iterator = mapParams.entrySet().iterator();
				for (; iterator.hasNext();) {
					Map.Entry entry = (Map.Entry) iterator.next();
					String name = (String) entry.getKey();
					String[] values = request.getParameterValues(name);
					for (int i = 0; i < values.length; i++) {
						String param = (String) values[i];
						// _logger.info(name + "=" + param);
						if (!this.passInterceptorRule(param)) {
							argsOk = false;
							break;
						}
					}
				}
				if (argsOk) {
					String httpsOpen = PropertiesUtil.getPorpertyString("httpsOpen");
					if (Boolean.valueOf(httpsOpen)) {// add http https
						url = addHttpsUrl(chain, req, res, url);
					} else {
						chain.doFilter(req, res);
					}
					_logger.info("访问地址：" + url);
				} else {
					_logger.info("[" + mapParams + "]，参数非法...");
					throw ApplicationException.newInstance("BL00005");
				}
			} else {
				_logger.info("[" + url + "]，请求非法...");
				throw ApplicationException.newInstance("BL00005");
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			throw new ServletException(e.getMessage());
		} finally {

		}
	}

	/**
	 * 添加URL为http或者https
	 * @param chain
	 * @param req
	 * @param res
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private String addHttpsUrl(FilterChain chain, HttpServletRequest req, HttpServletResponse res, String url)
			throws IOException, ServletException {
		if (!this.ignoreUrl(url)) {
			if (url.startsWith("http://") && this.needHttpsUrl(url)) {
				url = url.replaceAll("http://", "https://").replaceAll(":" + req.getServerPort(),
						":" + PropertiesUtil.getPorpertyString("httpsPort"));
				res.sendRedirect(url);
			} else if (url.startsWith("https://") && !this.needHttpsUrl(url)) {
				url = url.replaceAll("https://", "http://").replaceAll(":" + req.getServerPort(),
						":" + PropertiesUtil.getPorpertyString("httpPort"));
				res.sendRedirect(url);
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
		return url;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		try {
			if (CollectionUtils.isEmpty(interceptorRule)) {
				interceptorRule = PropertiesUtil.initPropList(interceptorRule, "interceptorRule");
			}
			if (CollectionUtils.isEmpty(httpsUrlList)) {
				httpsUrlList = PropertiesUtil.initPropList(httpsUrlList, "httpsUrl");
			}
			if (CollectionUtils.isEmpty(ignoreUrlType)) {
				ignoreUrlType = PropertiesUtil.initPropList(ignoreUrlType, "ignoreUrlType");
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
	}

	/**
	 * 拦截器过滤规则
	 * 
	 * @param value
	 * @return
	 */
	private boolean passInterceptorRule(String value) {
		boolean flag = true;
		value = value.toLowerCase();
		for (String rule : interceptorRule) {
			if (value.contains(rule)) {
				return false;
			}
		}
		return flag;
	}

	/**
	 * 需要https的路径
	 * 
	 * @param value
	 * @return
	 */
	private boolean needHttpsUrl(String value) {
		boolean flag = false;
		for (String rule : httpsUrlList) {
			if (pathMatcher.match(rule, value)) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * https忽略的文件类型
	 * 
	 * @param url
	 * @return
	 */
	private boolean ignoreUrl(String url) {
		url = url.toLowerCase();
		for (String ignore : ignoreUrlType) {
			if (pathMatcher.match(ignore.toLowerCase(), url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 防盗链 <br/>
	 * 如果 链接地址来自其他网站，则返回错误图片
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean antiLeech(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// 禁止缓存
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragrma", "no-cache");
		response.setDateHeader("Expires", 0);

		// 链接来源地址
		String referer = request.getHeader("referer");
		String sourceUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();

		String head = request.getServerName();
		String tail = request.getContextPath();

		boolean isError = (referer == null || !referer.contains(sourceUrl));
		if (referer == null) {
			isError = false;
		}
		if ((referer != null) && referer.contains(head) && referer.contains(tail)) {// 考虑http和https的情况
			isError = false;
		}
		_logger.info("referer:" + referer + ",sourceUrl:" + sourceUrl + ",isError:" + isError);
		return isError;
	}
}
