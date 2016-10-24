package com.awifi.bigscreen.redisCache.impl.DataAcquisition;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.data.entity.UserData;
import com.awifi.bigscreen.data.service.api.IPullData;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;

@Service
public class Pie_LXFB_Chart_DataAcquisition implements IDataAcquisition<Map<String, Object>> {
	
	@Resource(name="pie_LXFB_Chart_PullData")
	private IPullData<UserData> pullData;
	
	@Override
	public Map<String, Object> selectData(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		long time = new Date().getTime();
		Object data = pullData.Pull();
		map.put(AwifiConstants.Interface_Return_Data, data);
		map.put(AwifiConstants.Redis_Create_Time, time);
		return map;
	}
}