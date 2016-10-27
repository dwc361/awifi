package com.awifi.bigscreen.redisCache.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;

@Service
public class TestSetDataAcquisition implements IDataAcquisition<Map<String, Object>> {
	@Override
	public Map<String, Object> selectData(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		Random generator = new Random();
		long time = new Date().getTime();
		map.put(AwifiConstants.Redis_Create_Time, time);
		Map chartData = new HashMap();
		chartData.put("x", time);
		chartData.put("y", generator.nextInt(10000));
		chartData.put("a", generator.nextDouble());
		chartData.put("b", generator.nextDouble()+1);
		chartData.put("c", generator.nextDouble()+2);
		map.put(AwifiConstants.Interface_Return_Data, chartData);
		return map;
	}
}