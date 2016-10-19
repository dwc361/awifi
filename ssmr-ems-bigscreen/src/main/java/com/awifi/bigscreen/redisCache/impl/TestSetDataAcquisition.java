package com.awifi.bigscreen.redisCache.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.chart.service.api.IChartService;
import com.awifi.bigscreen.data.entity.UserData;
import com.awifi.bigscreen.data.service.api.IPullData;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;

@Service
public class TestSetDataAcquisition implements IDataAcquisition<Map<String, Object>> {
	private IChartService chartService;
	
	@Resource(name="userPullData")
	private IPullData<UserData> pullData;

	@Override
	public Map<String, Object> selectData(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		long time = new Date().getTime();
		UserData data = pullData.Pull();
		map.put(AwifiConstants.Interface_Return_Data, data);
		map.put(AwifiConstants.Redis_ZSet_Score, time);
		//map.put(AwifiConstants.Redis_ZSet_Score, data.getClock());
		return map;
	}

	@Autowired
	public void setChartService(IChartService chartService) {
		this.chartService = chartService;
	}
}