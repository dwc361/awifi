package com.awifi.bigscreen.redisCache.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class TestListDataTransform implements IDataTransform<List<Map<String, Object>>> {

	@Override
	public String transform(List<Map<String, Object>> list) {
		// 把chart的map对象转成 报表所需要的x，y对象输出
		List<List<Map>> result_list = new ArrayList<List<Map>>();
		for (Map<String, Object> map : list) {
			List<Map> mapList = new ArrayList<Map>();
			
			long redis_time = new Date().getTime();
			Object redis_time_object = map.get(AwifiConstants.Redis_ZSet_Score);
			if(redis_time_object != null) {
				redis_time = (long) redis_time_object;
			}
			
			List<ChartVo> chartList = (List<ChartVo>) map.get(AwifiConstants.Interface_Return_Data);
			if(chartList != null) {
				for (ChartVo chart : chartList) {
					Map<String, Object> result_map = new HashMap<String, Object>();
					result_map.put("createTime", redis_time);
					result_map.put("Id", chart.getId());
					result_map.put("Name", chart.getName());
					mapList.add(result_map);
				}
			}
			result_list.add(mapList);
		}
		
		return JSON.toJSONString(result_list);
	}

}