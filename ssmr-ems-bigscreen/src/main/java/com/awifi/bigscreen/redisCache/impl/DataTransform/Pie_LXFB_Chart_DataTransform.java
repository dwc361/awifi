package com.awifi.bigscreen.redisCache.impl.DataTransform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.data.entity.DeviceTypeEnum;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class Pie_LXFB_Chart_DataTransform implements IDataTransform<Map<String, Object>> {

	/**
	 * 把Set里的Map对象转成报表所需要的对象输出
	 */
	@Override
	public String transform(Map<String, Object> map) {
		Map result_map = new HashMap();
		
		/**
		 * 设备类型的Map
		 */
		Map deviceTypeMap = new HashMap();
		Map dataMap = new HashMap();
		DeviceTypeEnum[] deviceTypeEnum = DeviceTypeEnum.values();
		for (int i = 0; i < deviceTypeEnum.length; i++) {
			deviceTypeMap.put(deviceTypeEnum[i].getCode().toString(), deviceTypeEnum[i].getDisplay().toString());
			dataMap.put(deviceTypeEnum[i].getDisplay().toString(), new BigDecimal(0.0));
		}
		
		/**
		 * 插入redis时间
		 */
		long redis_time = new Date().getTime();
		Object redis_time_object = map.get(AwifiConstants.Redis_Create_Time);
		if(redis_time_object != null) {
			redis_time = (long) redis_time_object;
		}
		result_map.put("createTime", redis_time);
		
		/**
		 * 展示数据
		 */
		List<Map> result_list = new ArrayList<Map>();
		Map data = (Map) map.get(AwifiConstants.Interface_Return_Data);
		if(data != null) {
			Iterator<Entry<String, String>> iterator = data.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry)iterator.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				
				if(deviceTypeMap.get(key) != null) {
					BigDecimal device_num = (BigDecimal) dataMap.get(deviceTypeMap.get(key));
					device_num = device_num.add(new BigDecimal(value.toString()));
					dataMap.put(deviceTypeMap.get(key), device_num);
				}
				
//				if(deviceTypeMap.get(key) != null) {
//					Map m = new HashMap();
//					m.put("deviceName", deviceTypeMap.get(key));
//					m.put("deviceNum", value);
//					result_list.add(m);
//				} else {
//					Map m = new HashMap();
//					m.put("deviceName", "其它");
//					m.put("deviceNum", value);
//					result_list.add(m);
//				}
			}
			
			Iterator<Entry<String, String>> itor = dataMap.entrySet().iterator();
			while (itor.hasNext()) {
				Map.Entry entry = (Map.Entry)itor.next();
				Map m = new HashMap();
				m.put("deviceName", entry.getKey());
				m.put("deviceNum", entry.getValue());
				result_list.add(m);
			}
			
		}
		result_map.put("data", result_list);
		
		return JSON.toJSONString(result_map);
	}
	
}