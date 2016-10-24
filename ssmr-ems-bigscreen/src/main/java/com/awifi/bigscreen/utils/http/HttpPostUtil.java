package com.awifi.bigscreen.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

/**
 * 以Post方式调用Http接口：使用POST方法时，查询字符串在POST信息中单独存在，和HTTP请求一起发送到服务器。
 * 1.POST请求不能被缓存下来
 * 2.POST请求不会保存在浏览器浏览记录中
 * 3.以POST请求的URL无法保存为浏览器书签
 * 4.POST请求没有长度限制
 * @author zhangmm
 */
public class HttpPostUtil {
	private static final Logger logger = Logger.getLogger(HttpPostBodyUtil.class);
	
	public static void main(String[] args) {
		// 发送Http Post请求
		String postStr = HttpPostUtil.post("http://localhost:6144/Home/RequestPostString", "key=123&v=456");
		System.out.println(postStr);
    }
	
	/**
	 * 向指定URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 所代表远程资源的响应结果
	 */
	public static String post(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(connection.getOutputStream());
			
			// 发送请求参数
			out.print(param);
			
			// flush输出流的缓冲
			out.flush();
			
			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送 POST 请求出现异常！" + e.toString());
		} finally {// 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error(ex.toString());
			}
		}
		return result;
	}
}