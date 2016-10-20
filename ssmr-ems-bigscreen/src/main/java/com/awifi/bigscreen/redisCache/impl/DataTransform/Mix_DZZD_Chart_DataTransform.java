package com.awifi.bigscreen.redisCache.impl.DataTransform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class Mix_DZZD_Chart_DataTransform implements IDataTransform<Set<Map<String, Object>>> {

	/**
	 * 把Set里的Map对象转成报表所需要的对象输出
	 */
	@Override
	public String transform(Set<Map<String, Object>> set) {
		List<Map> result_list = new ArrayList<Map>();
		for (Map<String, Object> map : set) {
			Map<String, Object> result_map = new HashMap<String, Object>();
			Object redis_time_object = map.get(AwifiConstants.Redis_ZSet_Score);
			long redis_time = new Date().getTime();
			if(redis_time_object != null) {
				redis_time = (long) redis_time_object;
			}
			result_map.put("createTime", redis_time); //插入redis时间
			Map data = (Map) map.get(AwifiConstants.Interface_Return_Data);
			if(data != null) {
				result_map.put("onlineNum", data.get("onlineNum")); //在线设备数量
				result_map.put("offlineNum", data.get("offlineNum")); //离线设备数量
			}
			result_list.add(result_map);
		}
		return JSON.toJSONString(result_list);
	}
	
}