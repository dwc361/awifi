package com.awifi.bigscreen.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * 以PostBody方式调用Http接口
 * 
 * @author zhangmm
 */
public class HttpPostBodyUtil {
	private static final Logger logger = Logger.getLogger(HttpPostBodyUtil.class);
	
	public static void main(String[] args) {
		String url = "http://192.168.24.26:8080/iot-esb-webapp/httpServlets/Service001?SystemCode=33.1001";
		String requestXml = "<root><service/><params><QfaImpAddion><batchId>2</batchId><batchCount>100</batchCount><priKey>123</priKey><transType>新增</transType><bookTypeCode>123456</bookTypeCode><assetNumber></assetNumber><tagNumber></tagNumber><description>描述</description><categorySeg1></categorySeg1><categorySeg2></categorySeg2><keywordSeg1></keywordSeg1><keywordSeg2></keywordSeg2><keywordSeg3>资产小类</keywordSeg3><keywordSeg4></keywordSeg4><assetType></assetType><manufacturerName></manufacturerName><serialNumber></serialNumber><modelNumber></modelNumber><fixedAssetsUnits></fixedAssetsUnits><unitOfMeasure></unitOfMeasure><datePlacedInService>2016-03-01</datePlacedInService><lifeInYears>2016</lifeInYears><fixedAssetsCost>1423435</fixedAssetsCost><deprnReserve></deprnReserve><attribute30></attribute30><ytdDeprn></ytdDeprn><salvageValue></salvageValue><amortizeFlag></amortizeFlag><assignedToNumber>1243432</assignedToNumber><locationSeg1>地点编码</locationSeg1><locationSeg2>123</locationSeg2><locationSeg3>地点组合</locationSeg3><costCenter>124234</costCenter><expenseAccount>12423423</expenseAccount><projectSegment>12434234</projectSegment><productCode>234234</productCode><assetContext></assetContext><attribute1>1223423</attribute1><attribute2>顶顶顶顶</attribute2><attribute3>1243423</attribute3><attribute4>23423423</attribute4><attribute5></attribute5><attribute6></attribute6><attribute7></attribute7><attribute8></attribute8><attribute9></attribute9><attribute10></attribute10><attribute11></attribute11><attribute12></attribute12><attribute13></attribute13><attribute14>1434534534</attribute14><attribute15></attribute15><attribute16></attribute16><attribute17></attribute17><attribute18></attribute18><attribute19></attribute19><attribute20></attribute20><attribute21></attribute21><attribute22></attribute22><attribute23></attribute23><attribute24></attribute24><attribute25></attribute25><attribute26></attribute26><attribute27></attribute27><attribute28></attribute28><attribute29></attribute29><sourceSystem>BPM</sourceSystem></QfaImpAddion></params></root>";
        
		// 用Http Post方式模拟Webservice发送请求
		String xmlPostStr = HttpPostBodyUtil.postBody(url, null, requestXml);
		System.out.println(xmlPostStr);
    }
	
	/**
	 * 用Http Post方式模拟WebService发送请求
	 * 
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param requestXml 请求报文，格式为xml的字符串
	 * @return 所代表远程资源的响应结果
	 */
	public static String postBody(String url, String param, String requestXml) {
		String responseXml = "";
		try {
			// 拼接请求地址
			String urlNameString = url;
			if(param!=null && !"".equals(param)) {
				urlNameString += "?" + param;
			}
        	
        	// 创建Http Request(内部使用HttpURLConnection)
			ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(urlNameString), HttpMethod.POST);
			
			// 设置请求头的内容类型头和内容编码
			request.getHeaders().set("Content-Type", "application/xml;charset=UTF-8");
			
			// 以UTF-8编码写出请求内容体
			request.getBody().write(requestXml.getBytes("UTF-8"));
			
			// 发送请求并得到响应
	        ClientHttpResponse response = request.execute();
	        
	        // 得到响应体的内容 
	        InputStream is = response.getBody();
	        
	        // 把IO流转化成字符串
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				responseXml += line;
			}
		} catch (IOException | URISyntaxException e) {
			logger.error("发送 POST 请求出现异常！" + e.toString());
		}
		return responseXml;
	}
}