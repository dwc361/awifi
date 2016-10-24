package com.awifi.bigscreen.redisCache.impl;

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
public class TestZSetDataTransform implements IDataTransform<Set<Map<String, Object>>> {

	@Override
	public String transform(Set<Map<String, Object>> set) {
		// 把chart的map对象转成 报表所需要的x，y对象输出
		List<Map> result_list = new ArrayList<Map>();
		
//		Iterator<Map<String, Object>> iterator = set.iterator();
//		while (iterator.hasNext()) {
//			Map map = iterator.next();
//			Map<String, Object> result_map = new HashMap<String, Object>();
//			result_map.put("data", map.get(AwifiConstants.Interface_Return_Data));
//			result_map.put("score", map.get(AwifiConstants.Redis_ZSet_Score));
//			result_list.add(result_map);
//		}
		
//		for (Map<String, Object> map : set) {
//			Map<String, Object> result_map = new HashMap<String, Object>();
//			result_map.put("data", map.get(AwifiConstants.Interface_Return_Data));
//			result_map.put("score", map.get(AwifiConstants.Redis_ZSet_Score));
//			result_list.add(result_map);
//		}
		
		for (Map<String, Object> map : set) {
			Map result_map = new HashMap();
			
			long redis_time = new Date().getTime();
			Object redis_time_object = map.get(AwifiConstants.Redis_ZSet_Score);
			if(redis_time_object != null) {
				redis_time = (long) redis_time_object;
			}
			result_map.put("createTime", redis_time); //插入redis时间
			
			Map data = (Map) map.get(AwifiConstants.Interface_Return_Data);
			if(data != null) {
				result_map.put("x", data.get("x"));
				result_map.put("y", data.get("y"));
			}
			
			result_list.add(result_map);
		}
		
		return JSON.toJSONString(result_list);
	}
	
}