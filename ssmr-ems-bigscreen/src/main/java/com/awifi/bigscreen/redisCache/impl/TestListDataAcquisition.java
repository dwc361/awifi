package com.awifi.bigscreen.redisCache.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.chart.service.api.IChartService;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;

@Service
public class TestListDataAcquisition implements IDataAcquisition<Map<String, Object>> {

	private IChartService chartService;

	@Override
	public Map<String, Object> selectData(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		long time = new Date().getTime();
		map.put(AwifiConstants.Redis_Create_Time, time);
		List<ChartVo> chartList = chartService.selectForList(null);
		map.put(AwifiConstants.Interface_Return_Data, chartList);
		return map;
	}

	@Autowired
	public void setChartService(IChartService chartService) {
		this.chartService = chartService;
	}
}