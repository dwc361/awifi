package org.roof.commons.init;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class InitFreemarker extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -80078290080500530L;

	private Configuration cfg;

	public void init() {

		// 初始化FreeMarker配置
		// 创建一个Configuration实例
		cfg = new Configuration();
		// 设置FreeMarker的模版文件位置
		cfg.setServletContextForTemplateLoading(getServletContext(), "/");
		cfg.setDefaultEncoding("UTF-8");// 此处解决乱码

	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 建立数据模型
		Map<String, Object> root = new HashMap<String, Object>();
		// 放入对应数据key value
		root.put("basePath", request.getContextPath());

		String url = request.getRequestURI();
		if (url.endsWith("jsp")) {
			String inputArgs = request.getQueryString();// name=管理员&pwd=123
			if (StringUtils.isNotEmpty(inputArgs)) {
				inputArgs = URLDecoder.decode(inputArgs, "UTF-8");// 解决中文乱码
				String[] argsArr = inputArgs.split("&");// name=管理员
				for (int i = 0; i < argsArr.length; i++) {
					String[] args = argsArr[i].split("=");
					if (args.length == 2) {
						root.put(args[0], args[1]);// key = value
					}
				}
			}
		}

		// 取得模版文件
		Template template = cfg.getTemplate(request.getServletPath());

		// 使用text/html MIME-type
		// response.setContentType("text/html; charset=" +
		// template.getEncoding());
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 合并数据模型和模版，并将结果输出到out中
		try {
			template.process(root, out);// 用模板来开发servlet可以只在代码里面加入动态的数据
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new ServletException("处理Template模版中出现错误", e);
		}

	}

}
