package com.awifi.bigscreen.redisCache.impl.DataAcquisition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.data.service.api.IPullData;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;

import reactor.util.Assert;

@Service
public class Scatter_HotSpot_Chart_DataAcquisition implements IDataAcquisition<Map<String, Object>> {
	
	@Resource(name="scatter_HotSpot_Chart_PullData")
	private IPullData<List<Map>> pullData;
	
	@Override
	public Map<String, Object> selectData(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		long time = new Date().getTime();
		Object data = pullData.Pull();
		Assert.notNull(data, "data can't be null");
		map.put(AwifiConstants.Interface_Return_Data, data);
		map.put(AwifiConstants.Redis_Create_Time, time);
		return map;
	}
}