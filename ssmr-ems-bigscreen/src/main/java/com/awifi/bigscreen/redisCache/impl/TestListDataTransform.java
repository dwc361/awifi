package com.awifi.bigscreen.redisCache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class TestListDataTransform implements IDataTransform<List<List<ChartVo>>> {

	@Override
	public String transform(List<List<ChartVo>> list) {
		// 把chart的map对象转成 报表所需要的x，y对象输出
		List<List<Map<String, Object>>> target_list = new ArrayList<List<Map<String, Object>>>();
		for (List<ChartVo> chartList : list) {
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			for (ChartVo chart : chartList) {
				Map<String, Object> target_map = new HashMap<String, Object>();
				target_map.put("x", chart.getId());
				target_map.put("y", chart.getName());
				mapList.add(target_map);
			}
			target_list.add(mapList);
		}
		
		return JSON.toJSONString(target_list);
	}

}