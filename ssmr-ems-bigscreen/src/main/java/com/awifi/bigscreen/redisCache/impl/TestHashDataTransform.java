package com.awifi.bigscreen.redisCache.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.chart.entity.Chart;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class TestHashDataTransform implements IDataTransform<Map<String, Object>> {

	@Override
	public String transform(Map<String, Object> map) {
		// 把chart的map对象转成 报表所需要的x，y对象输出
		List<Map<String,Object>> result_list = new ArrayList<Map<String,Object>>();
		Object redis_time_object = map.get(AwifiConstants.Redis_Create_Time);
		long redis_time = new Date().getTime();
		if(redis_time_object != null) {
			redis_time = (long) redis_time_object;
		}
		List<Chart> list = (List<Chart>) map.get(AwifiConstants.Interface_Return_Data);
		if(list != null) {
			for (Chart chart : list) {
				Map<String, Object> result_map = new HashMap<String, Object>();
				result_map.put("createTime", redis_time);
				result_map.put("name", chart.getName());
				result_list.add(result_map);
			}
		}
		return JSON.toJSONString(result_list);
	}

}