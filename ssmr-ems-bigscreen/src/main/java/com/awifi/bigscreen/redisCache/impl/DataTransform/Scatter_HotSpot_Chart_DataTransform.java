package com.awifi.bigscreen.redisCache.impl.DataTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class Scatter_HotSpot_Chart_DataTransform implements IDataTransform<Map<String, Object>> {

	/**
	 * 把Set里的Map对象转成报表所需要的对象输出
	 */
	@Override
	public String transform(Map<String, Object> map) {
		List<Map<String, Object>> target_list = new ArrayList<Map<String, Object>>();
		//long time = (long) map.get(AwifiConstants.Redis_Create_Time); //插入redis时间
		List<Map> list = (List<Map>) map.get(AwifiConstants.Interface_Return_Data);
		for (Map m : list) {
			Map<String, Object> target_map = new HashMap<String, Object>();
			target_map.put("typeName", m.get("typeName")); //热点类型名称
			target_map.put("hotareaNum", m.get("hotareaNum")); //热点数量
			target_list.add(target_map);
		}
		return JSON.toJSONString(target_list);
	}
	
}