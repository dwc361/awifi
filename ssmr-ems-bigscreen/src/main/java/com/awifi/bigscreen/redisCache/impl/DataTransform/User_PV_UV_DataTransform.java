package com.awifi.bigscreen.redisCache.impl.DataTransform;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class User_PV_UV_DataTransform implements IDataTransform<Map<String, Object>> {

	/**
	 * 把Set里的Map对象转成报表所需要的对象输出
	 */
	@Override
	public String transform(Map<String, Object> map) {
		Map result_map = new HashMap();
		
		/**
		 * 插入redis时间
		 */
		long redis_time = new Date().getTime();
		Object redis_time_object = map.get(AwifiConstants.Redis_Create_Time);
		if(redis_time_object != null) {
			redis_time = (long) redis_time_object;
		}
		result_map.put("createTime", redis_time);
		
		/**
		 * 展示数据
		 */
		Map data = (Map) map.get(AwifiConstants.Interface_Return_Data);
		if(data != null) {
			result_map.put("userNum", data.get("userNum")); //该地区用户数量
			result_map.put("merchantNum", data.get("merchantNum")); //该地区商户数量
			result_map.put("PV", data.get("PV")); //该地区PV量
			result_map.put("UV", data.get("UV")); //该地区UV量
		}
		
		return JSON.toJSONString(result_map);
	}
	
}