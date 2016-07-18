package org.roof.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class RoofHttpUtil {
	private static final Logger logger = Logger.getLogger(RoofHttpUtil.class);

	/**
	 * 模拟SSL形式请求 HttpClient连接SSL
	 * 
	 * @param keystore
	 *            如：d:\\tomcat.keystore
	 * @param pwd
	 *            如：123456
	 * @param url
	 *            如：https://localhost:8443/myDemo/Ajax/serivceJ.action
	 */
	public static void ssl(String keystore, String pwd, String url) {
		CloseableHttpClient httpclient = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File(keystore));
			try {
				trustStore.load(instream, pwd.toCharArray());
			} catch (CertificateException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			// 相信自己的CA和所有自签名的证书
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
					.build();
			// 只允许使用TLSv1协议
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			// 创建http请求(get方式)
			HttpGet httpget = new HttpGet(url);
			logger.info("executing request" + httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				logger.info(response.getStatusLine());
				if (entity != null) {
					logger.info("Response content length: " + entity.getContentLength());
					logger.info(EntityUtils.toString(entity));
					EntityUtils.consume(entity);
				}
			} finally {
				response.close();
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (KeyManagementException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (KeyStoreException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得不需要证书的https的HttpClient
	 * 
	 * @return
	 */
	public static CloseableHttpClient getHttpsClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (KeyStoreException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return HttpClients.createDefault();
	}

	/**
	 * 发送 post请求访问并根据传递参数不同返回不同结果
	 * 
	 * @param httpclient
	 *            如果为null，会自动创建默认的httpclient
	 * @param url
	 *            请求的URL
	 * @param postData
	 *            提交请求的参数键值对
	 * @return 响应内容
	 */
	public static String post(CloseableHttpClient httpclient, String url, Map<String, String> postData) {
		// 创建默认的httpClient实例.
		boolean closehttpclient = false;
		if (httpclient == null) {
			httpclient = HttpClients.createDefault();
			closehttpclient = true;
		}
		if (postData == null) {
			postData = new HashMap<String, String>();
		}
		String content = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<String> keys = postData.keySet();
		for (String key : keys) {
			formparams.add(new BasicNameValuePair(key, postData.get(key)));
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			logger.info("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					content = EntityUtils.toString(entity, "UTF-8");
					logger.info("Response post content: " + content);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			// 关闭连接,释放资源
			if (closehttpclient) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	/**
	 * 发送 get请求
	 * 
	 * @param httpclient
	 *            如果为null，会自动创建默认的httpclient
	 * @param url
	 *            请求的URL
	 * @return 响应内容
	 */
	public static String get(CloseableHttpClient httpclient, String url) {
		if (httpclient == null) {
			httpclient = HttpClients.createDefault();
		}
		String content = null;
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			logger.info("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				logger.info(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					logger.info("Response get content length: " + entity.getContentLength());
					// 打印响应内容
					content = EntityUtils.toString(entity);
					logger.info("Response get content: " + content);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	// /**
	// * 上传文件
	// */
	// public void upload() {
	// CloseableHttpClient httpclient = HttpClients.createDefault();
	// try {
	// HttpPost httppost = new
	// HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");
	//
	// FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));
	// StringBody comment = new StringBody("A binary file of some kind",
	// ContentType.TEXT_PLAIN);
	//
	// HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin",
	// bin).addPart("comment", comment)
	// .build();
	//
	// httppost.setEntity(reqEntity);
	//
	// logger.info("executing request " + httppost.getRequestLine());
	// CloseableHttpResponse response = httpclient.execute(httppost);
	// try {
	// logger.info("----------------------------------------");
	// logger.info(response.getStatusLine());
	// HttpEntity resEntity = response.getEntity();
	// if (resEntity != null) {
	// logger.info("Response content length: " +
	// resEntity.getContentLength());
	// }
	// EntityUtils.consume(resEntity);
	// } finally {
	// response.close();
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// httpclient.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
}
