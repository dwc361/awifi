package com.awifi.bigscreen.data.service.Impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.awifi.bigscreen.data.service.api.IPullData;
import com.awifi.bigscreen.utils.http.HttpUtil;

/**
 * 接口：[设备类型分布]
 * @author zhangmm
 */
@Service
public class Pie_LXFB_Chart_PullData implements IPullData<List<Map>>, InitializingBean{
	private Logger log = Logger.getLogger(Pie_LXFB_Chart_PullData.class);
	
	private static String address = "";
	private static String method = "";
	private static String url = "";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		address = PropertiesUtil.getPorpertyString("DataCenter.address");
		method = PropertiesUtil.getPorpertyString("DataCenter.method.queryDeviceType");
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
		} catch (Exception e) {
			log.error("接口地址：["+url+"]不通:"+e.toString());
			return false;
		}
		return flag;
	}

	public List<Map> Pull() {
		List<Map> list = new ArrayList();
		
		/**
		 * 拼接入参
		 */
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("province", 31);
//		m.put("city", 383);
//		m.put("county", 3289);
		m.put("globalKey", "");
		m.put("globalValue", "");
		m.put("globalStandby", "");
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
			//JSONObject fjsonObject = JSON.parseObject(result);
			JSONArray fjsonArray = JSON.parseArray(result);
			Object[] objects = fjsonArray.toArray();
			for(Object obj : objects) {
				list.add((Map) obj);
			}
		} catch (Throwable e) {
			log.error("接口["+url+"]调用失败:"+e.toString());
		}
		
		return list;
	}
}
