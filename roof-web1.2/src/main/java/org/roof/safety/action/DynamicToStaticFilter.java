package org.roof.safety.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.roof.commons.PropertiesUtil;
import org.roof.safety.service.MD5Util;

/**
 * 动转静过滤器<br/>
 * 
 * @author hongzc
 * 
 */
public class DynamicToStaticFilter implements Filter {
	private final Log _logger = LogFactory.getLog(this.getClass());

	private static final long serialVersionUID = -2383222327035299546L;

	private static String static_folder = null;// 静态文件存放相对于根目录的路径

	private static String static_extend_name = ".html";// 静态页面后缀名

	private static int refresh_interval = 60;// 刷新频率单位秒 即静态文件的有效时长

	private final int buffersize = 2048;

	private static String request_head_value = "download";// 请求下载的http head参数值

	private static String request_head_name = "requesttype";// 请求下载的http head参数名

	private static List<String> dynamicToStaticUrlList = new ArrayList<String>();// 需要动转静的页面地址

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			if (StringUtils.isEmpty(static_folder)) {
				static_folder = PropertiesUtil.getPorpertyString("staticFolder");
			}
			if (refresh_interval == 0) {
				refresh_interval = Integer.parseInt(PropertiesUtil.getPorpertyString("refreshInterval"));
			}
			if (CollectionUtils.isEmpty(dynamicToStaticUrlList)) {
				String temp = PropertiesUtil.getPorpertyString("dynamic_to_static_url");
				if (!StringUtils.isEmpty(temp)) {
					dynamicToStaticUrlList = Arrays.asList(temp.split(","));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(ex);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getRequestURL().toString();
		if (!this.dynamicToStaticPage(url)) {// 不需要动转静的页面直接忽略
			chain.doFilter(req, res);
		} else {
			String requesttype = req.getHeader(request_head_name);
			if (requesttype != null && requesttype.equals(request_head_value)) {// 请求下载
				chain.doFilter(req, res);
			} else {
				boolean genStatic = true;
				String strParam = "";// 参数暂时先保留
				String strHtmlUrl = static_folder + getHtmlUrl(req.getServletPath(), strParam);
				String strHtmlRealPath = req.getSession().getServletContext().getRealPath(strHtmlUrl);

				File f = new File(strHtmlRealPath);

				if (f.exists()) {// 如果文件已经存在
					long diff = new Date().getTime() - f.lastModified();// 毫秒
					if ((diff / 1000) > refresh_interval) {
						genStatic = true;
					} else {
						genStatic = false;
					}
				}

				if (genStatic) {
					chain.doFilter(req, res);
					try {
						String strRequestUrl = req.getRequestURL() + strParam;
						// String exportPath =
						// request.getSession().getServletContext().getRealPath(static_folder);
						// createStaticPage.sendDataByPost(strRequestUrl,
						// exportPath);
						this.genStaticPage(strRequestUrl, strHtmlRealPath);
					} catch (Exception ex) {
						ex.printStackTrace();
						_logger.error(ex);
					}
				} else {// 定位到静态页面
					req.getRequestDispatcher(strHtmlUrl).forward(req, res);// 转发
					// res.sendRedirect(strHtmlUrl);// 重定向到strHtmlUrl
				}
			}
		}
	}

	/**
	 * 获得html路径
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	protected String getHtmlUrl(String url, String param) {
		String strMD5 = "";
		String extendName = "";
		if (param != null && param.length() > 0) {
			strMD5 = MD5Util.getMD5String(param.getBytes());
		}
		int iPos = url.lastIndexOf(".");
		if (iPos != -1) {
			extendName = url.substring(iPos);
		}
		String htmlUrl = url.substring(0, url.length() - extendName.length()) + strMD5 + static_extend_name;
		return htmlUrl;
	}

	/**
	 * 生成静态资源<br>
	 * 注：可能发生同时生成一个临时文件的情况，但概率很小,即使发生也只是抛个异常，所以不处理这种情况<br>
	 * 该状况发生时机:只有在多个用户生成同一url时，且各个线程都在写临时文件，而临时文件却还未生成的时候才会发生
	 * 
	 * @param request
	 * @param strParam
	 * @param strHtmlRealPath
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	protected void genStaticPage(String strRequestUrl, String strHtmlRealPath) throws Exception {
		long lBegin = System.currentTimeMillis();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {

			URL urlRequest = new URL(strRequestUrl);
			URLConnection uc = urlRequest.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.addRequestProperty(request_head_name, request_head_value);

			bis = new BufferedInputStream(uc.getInputStream());

			File f = new File(strHtmlRealPath);
			File fP = f.getParentFile();
			if (!fP.exists()) {
				fP.mkdirs();
			}
			bos = new BufferedOutputStream(new FileOutputStream(f), buffersize);
			byte[] bs = new byte[buffersize];
			int iRead = 0;
			while ((iRead = bis.read(bs)) != -1) {
				bos.write(bs, 0, iRead);
			}
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
		long lEnd = System.currentTimeMillis();
		_logger.info("生成页面用时: " + (lEnd - lBegin) / 1000 + " 秒");
	}

	/**
	 * 需要动转静的页面路径
	 * 
	 * @param value
	 * @return
	 */
	private boolean dynamicToStaticPage(String value) {
		// value = value.toLowerCase();
		for (String strUrl : dynamicToStaticUrlList) {
			if (value.contains(strUrl)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {

	}
}
