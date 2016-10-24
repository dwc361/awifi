package com.awifi.bigscreen.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

/**
 * 以Get方式调用Http接口：使用GET方法时，查询字符串（键值对）被附加在URL地址后面一起发送到服务器。
 * 1.GET请求能够被缓存
 * 2.GET请求会保存在浏览器的浏览记录中
 * 3.以GET请求的URL能够保存为浏览器书签
 * 4.GET请求有长度限制
 * 5.GET请求主要用以获取数据
 * @author zhangmm
 */
public class HttpGetUtil {
	private static final Logger logger = Logger.getLogger(HttpPostBodyUtil.class);
	
	public static void main(String[] args) {
		// 发送Http Get请求
		String getStr = HttpGetUtil.get("http://localhost:6144/Home/RequestString", "key=123&value=456");
		System.out.println(getStr);
    }
	
	/**
	 * 向指定URL 发送GET方法的请求
	 * 
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 所代表远程资源的响应结果
	 */
	public static String get(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			// 建立实际的连接
			connection.connect();
			
			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送GET请求出现异常！" + e.toString());
			e.printStackTrace();
		} finally {// 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				logger.error(e2.toString());
				e2.printStackTrace();
			}
		}
		return result;
	}
}