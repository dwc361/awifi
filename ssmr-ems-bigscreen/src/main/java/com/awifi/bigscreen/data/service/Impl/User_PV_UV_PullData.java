package com.awifi.bigscreen.data.service.Impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.bigscreen.data.entity.CountryEnum;
import com.awifi.bigscreen.data.service.api.IPullData;
import com.awifi.bigscreen.utils.http.HttpUtil;

/**
 * 接口：[用户、商户、PV、UV统计]
 * @author zhangmm
 */
@Service
public class User_PV_UV_PullData implements IPullData<Map>, InitializingBean{
	private Logger log = Logger.getLogger(User_PV_UV_PullData.class);
	
	private static String address = "";
	private static String method = "";
	private static String url = "";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		address = PropertiesUtil.getPorpertyString("DataCenter.address");
		method = PropertiesUtil.getPorpertyString("DataCenter.method.queryUserAndMerchant");
		url = String.format(address, method);
	}

	/**
	 * 判断接口是否通畅
	 */
	public boolean isExist() {
		boolean flag = true;
		try {
			URL url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
		} catch (Exception e) {
			log.error("接口地址：["+url+"]不通:"+e.toString());
			return false;
		}
		return flag;
	}

	public Map Pull() {
		Map map = new HashMap();
		
		/**
		 * 拼接入参
		 */
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("country", CountryEnum.China.getCode());
//		m.put("province", 31);
//		m.put("city", 383);
//		m.put("county", 3289);
//		m.put("globalKey", "");
//		m.put("globalValue", "");
//		m.put("globalStandby", "");
		String json = JSON.toJSONString(m);
		params.put("json", json);
		
		/**
		 * 远程调用
		 */
		String result = null;
		try {
			result = HttpUtil.post(url, params);
			
			/**
			 * 解析结果集
			 */
			JSONObject fjsonObject = JSON.parseObject(result);
			Iterator itor=fjsonObject.entrySet().iterator();
			while(itor.hasNext()) {
				Map.Entry entry = (Map.Entry)itor.next();
				map.put(entry.getKey().toString(), entry.getValue());
			}
		} catch (Throwable e) {
			log.error("接口["+url+"]调用失败:"+e.toString());
		}
		
		return map;
	}
}
