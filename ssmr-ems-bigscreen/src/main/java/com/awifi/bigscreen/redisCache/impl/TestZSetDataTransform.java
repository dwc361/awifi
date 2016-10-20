package com.awifi.bigscreen.redisCache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		List<Map<String, Object>> result_list = new ArrayList<Map<String, Object>>();
		
//		Iterator<Map<String, Object>> iterator = set.iterator();
//		while (iterator.hasNext()) {
//			Map m = iterator.next();
//			Map<String, Object> result_map = new HashMap<String, Object>();
//			result_map.put("data", m.get(AwifiConstants.Interface_Return_Data));
//			result_map.put("score", m.get(AwifiConstants.Redis_ZSet_Score));
//			result_list.add(result_map);
//		}
		
		for (Map<String, Object> m : set) {
			Map<String, Object> result_map = new HashMap<String, Object>();
			result_map.put("data", m.get(AwifiConstants.Interface_Return_Data));
			result_map.put("score", m.get(AwifiConstants.Redis_ZSet_Score));
			result_list.add(result_map);
		}
		return JSON.toJSONString(result_list);
	}
	
}