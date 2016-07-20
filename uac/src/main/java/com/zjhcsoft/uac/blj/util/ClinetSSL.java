package com.zjhcsoft.uac.blj.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.roof.commons.Assert;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.log.SysLog;
import org.springframework.stereotype.Component;

/*
 * 
 */
@Component
public class ClinetSSL {
	private CloseableHttpClient client;
	private ObjectMapper mapper;
	private BljUntils bljUntils;
	private RoofDaoSupport roofDaoSupport;
	private static final Logger LOGGER = Logger.getLogger(ClinetSSL.class);
	public CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (KeyStoreException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return HttpClients.createDefault();

	}

	public Map<String, Object> sendDataByPost(String url,
			Map<String, Object> xmlData) throws ParseException, IOException {
		Assert.notNull(url, "url must is not null");
		if (client == null) {
			client = createSSLClientDefault();
		}
		mapper = new ObjectMapper();
		HttpPost post = new HttpPost(url);
		String date = JSONObject.fromObject(xmlData).toString();
		//System.out.println(date);
		String copy = date;
		date = bljUntils.Encrypt(date);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("JSON", date));
		// 添加参数
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entityRep = response.getEntity();
		SysLog log = new SysLog();
		log.setLog_time(new Date());
		log.setAction("堡垒机数据新增");
		log.setLocation(url);
		log.setQuery_string(copy);
		if (entityRep != null) {
			try{
				String s = EntityUtils.toString(response.getEntity(), "UTF-8");
				log.setLog_level("INFO");
				log.setMessage(s);
				Map statusMap = (Map) mapper.readValue(s, HashMap.class);
				post.abort();
				roofDaoSupport.save(log);
				return statusMap;
			}catch(Exception e){
				log.setLog_level("error");
				log.setMessage(e.getMessage());
			}
			roofDaoSupport.save(log);
		}
		log.setLog_level("entityRep==null");
		roofDaoSupport.save(log);
		return null;
	}

	public Map<String, Object> sendDataByPut(String url,
			Map<String, Object> xmlData) throws ParseException, IOException {
		Assert.notNull(url, "url must is not null");
		if (client == null) {
			client = createSSLClientDefault();
		}
		mapper = new ObjectMapper();
		HttpPut post = new HttpPut(url);
		String date = JSONObject.fromObject(xmlData).toString();
		String copy = date;
		date = bljUntils.Encrypt(date);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("JSON", date));
		// 添加参数
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entityRep = response.getEntity();
		SysLog log = new SysLog();
		log.setLog_time(new Date());
		log.setAction("堡垒机数据更新");
		log.setLocation(url);
		log.setQuery_string(copy);
		if (entityRep != null) {
			try{
				String s = EntityUtils.toString(response.getEntity(), "UTF-8");
				log.setLog_level("INFO");
				log.setMessage(s);
				Map statusMap = (Map) mapper.readValue(s, HashMap.class);
				post.abort();
				roofDaoSupport.save(log);
				return statusMap;
			}catch(Exception e){
				log.setLog_level("error");
				log.setMessage(e.getMessage());
			}
			roofDaoSupport.save(log);
		}
		log.setLog_level("entityRep==null");
		roofDaoSupport.save(log);
		return null;
	}

	public Map<String, Object> sendDataByDel(String url,
			Map<String, Object> xmlData) throws ParseException, IOException {
		Assert.notNull(url, "url must is not null");
		if (client == null) {
			client = createSSLClientDefault();
		}
		mapper = new ObjectMapper();
		String date = JSONObject.fromObject(xmlData).toString();
		String copy = date;
		date = bljUntils.Encrypt(date);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("JSON", date));
		// 添加参数
		MyHttpDelete post = new MyHttpDelete(url);
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entityRep = response.getEntity();
		SysLog log = new SysLog();
		log.setLog_time(new Date());
		log.setAction("堡垒机数据删除");
		log.setLocation(url);
		log.setQuery_string(copy);
		if (entityRep != null) {
			try{
				String s = EntityUtils.toString(response.getEntity(), "UTF-8");
				log.setLog_level("INFO");
				log.setMessage(s);
				Map statusMap = (Map) mapper.readValue(s, HashMap.class);
				post.abort();
				roofDaoSupport.save(log);
				return statusMap;
			}catch(Exception e){
				log.setLog_level("error");
				log.setMessage(e.getMessage());
			}
			roofDaoSupport.save(log);
		}
		log.setLog_level("entityRep==null");
		roofDaoSupport.save(log);
		return null;
	}

	public Map<String, Object> sendDataByPost(String url)
			throws ParseException, IOException {
		Assert.notNull(url, "url must is not null");
		if (client == null) {
			client = createSSLClientDefault();
		}
		mapper = new ObjectMapper();
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entityRep = response.getEntity();
		if (entityRep != null) {
			String s = EntityUtils.toString(response.getEntity(), "UTF-8");
			Map statusMap = (Map) mapper.readValue(s, HashMap.class);
			post.abort();
			return statusMap;
		}
		return null;
	}

	@Resource
	public void setBljUntils(BljUntils bljUntils) {
		this.bljUntils = bljUntils;
	}
	
	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}


}