package com.awifi.bigscreen.redisCache.impl;

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
public class TestHashDataAcquisition implements IDataAcquisition<Map<String, Object>> {

	private IChartService chartService;

	@Override
	public Map<String, Object> selectData(String param) {
		List<ChartVo> charts = chartService.selectForList(null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AwifiConstants.Interface_Return_Data, charts);
		return map;
	}

	@Autowired
	public void setChartService(IChartService chartService) {
		this.chartService = chartService;
	}
}