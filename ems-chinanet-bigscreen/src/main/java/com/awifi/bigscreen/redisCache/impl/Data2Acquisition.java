package com.awifi.bigscreen.redisCache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.chart.service.api.IChartService;
import com.awifi.bigscreen.data.entity.UserData;
import com.awifi.bigscreen.data.service.api.IPullData;
import com.awifi.bigscreen.redisCache.DataAcquisition;

@Service
public class Data2Acquisition implements DataAcquisition<Map<String, Object>> {

	private IChartService chartService;
	private IPullData<UserData> pullData;

	@Override
	public Map<String, Object> selectData(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserData data = pullData.Pull();
		map.put("chartData", data);
		map.put("score", data.getClock());
		return map;
	}

	@Autowired
	public void setChartService(IChartService chartService) {
		this.chartService = chartService;
	}
	
	@Autowired
	public void setPullData(IPullData pullData) {
		this.pullData = pullData;
	}

	
}
