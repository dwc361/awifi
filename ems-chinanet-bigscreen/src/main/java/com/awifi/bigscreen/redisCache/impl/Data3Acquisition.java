package com.awifi.bigscreen.redisCache.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.awifi.bigscreen.redisCache.DataAcquisition;

@Service
public class Data3Acquisition implements DataAcquisition<Map<String, Object>> {

	@Override
	public Map<String, Object> selectData(String param) {
		Random generator = new Random();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map> list = new ArrayList<Map>();
		for(int i=0; i<12; i++) {
			Map m = new HashMap();
			long time = new Date().getTime();
			m.put("score", time);
			m.put("x", time);
			m.put("y", generator.nextInt(10000));
			m.put("a", generator.nextDouble());
			m.put("b", generator.nextDouble()+1);
			m.put("c", generator.nextDouble()+2);
			list.add(m);
		}
		map.put("chartData", list);
		return map;
	}
}
