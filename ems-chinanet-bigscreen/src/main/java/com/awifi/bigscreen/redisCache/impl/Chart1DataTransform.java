package com.awifi.bigscreen.redisCache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.chart.entity.Chart;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class Chart1DataTransform implements IDataTransform<Map<String, Object>> {

	@Override
	public String transform(Map<String, Object> map) {
		// 把chart的map对象转成 报表所需要的x，y对象输出
		List<Map<String,Object>> target_list = new ArrayList<Map<String,Object>>();
		List<Chart> list = (List<Chart>) map.get("chartData");
		for (Chart chart : list) {
			Map<String, Object> target_map = new HashMap<String, Object>();
			target_map.put("x",chart.getCreate_time().getTime());
			target_map.put("y", chart.getRe_times());
			target_list.add(target_map);
		}
		return JSON.toJSONString(target_list);
	}

}