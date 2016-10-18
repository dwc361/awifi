package com.awifi.bigscreen.redisCache.impl.DataTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class Mix_JHL_Chart_DataTransform implements IDataTransform<Set<Map>> {

	/**
	 * 把Set里的Map对象转成报表所需要的对象输出
	 */
	@Override
	public String transform(Set<Map> set) {
		List<Map<String, Object>> target_list = new ArrayList<Map<String, Object>>();
		for (Map m : set) {
			Map<String, Object> target_map = new HashMap<String, Object>();
			target_map.put("createTime", m.get("createTime")); //插入redis时间
			target_map.put("activateNum", m.get("activateNum")); //
			target_map.put("activatePer", m.get("activatePer")); //
			target_list.add(target_map);
		}
		return JSON.toJSONString(target_list);
	}
	
}