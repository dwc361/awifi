package com.awifi.bigscreen.redisCache.impl.DataTransform;

import java.util.ArrayList;
import java.util.Date;
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
		List<Map> result_list = new ArrayList<Map>();
		
		long redis_time = new Date().getTime();
		Object redis_time_object = map.get(AwifiConstants.Redis_Create_Time);
		if(redis_time_object != null) {
			redis_time = (long) redis_time_object;
		}
		
		List<Map> dataList = (List<Map>) map.get(AwifiConstants.Interface_Return_Data);
		if(dataList != null) {
			for (Map m : dataList) {
				Map result_map = new HashMap();
				result_map.put("createTime", redis_time); //插入redis时间
				result_map.put("typeName", m.get("typeName")); //热点类型名称
				result_map.put("hotareaNum", m.get("hotareaNum")); //热点数量
				result_list.add(result_map);
			}
		}
		
		return JSON.toJSONString(result_list);
	}
	
}