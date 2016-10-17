package com.awifi.bigscreen.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;

import com.alibaba.fastjson.JSON;


public class HttpUtil {
	private static Logger log = Logger.getLogger(HttpUtil.class);
	
	/**
	 * http请求post提交
	 */
	public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost post = postForm(url, params);
		String body = invoke(httpclient, post);
		httpclient.getConnectionManager().shutdown();
		return body;
	}
	
	/**
	 * http请求get提交
	 */
	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		String body = invoke(httpclient, get);
		httpclient.getConnectionManager().shutdown();
		return body;
	}
	
	/**
	 * 请求参数解析，存入Enity
	 */
	private static HttpPost postForm(String url, Map<String, String> params){
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		Set<String> keySet = params.keySet();
		for(String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		
		try {
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return httpost;
	}
		
	/**
	 * 执行调用
	 */
	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);
		return body;
	}
	
	/**
	 * 发送请求
	 */
	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
		log.info("execute post...");
		HttpResponse response = null;
		
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 解析返回结果
	 */
	private static String paseResponse(HttpResponse response) {
		log.info("response status: " + response.getStatusLine());
		
		HttpEntity entity = response.getEntity();
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);
		String body = null;
		try {
			body = EntityUtils.toString(entity);
			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return body;
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("province", 31);
//		map.put("city", 383);
//		map.put("county", 3289);
		map.put("globalKey", "");
		map.put("globalValue", "");
		map.put("globalStandby", "");
		String json = JSON.toJSONString(map);
		System.out.println(JSON.toJSONString(map));
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("json", json);
		String result = HttpUtil.post("http://192.168.36.8:8080/tsdb-statistics-service/deviceBigScreen/queryHotareaDistributionByArea.do", params);  
		log.info(result);
		System.out.println(result);
	}
}
