package com.awifi.bigscreen.redisCache.impl.DataTransform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class Mix_JHL_Chart_DataTransform implements IDataTransform<Set<Map<String, Object>>> {

	/**
	 * 把Set里的Map对象转成报表所需要的对象输出
	 */
	@Override
	public String transform(Set<Map<String, Object>> set) {
		List<Map> result_list = new ArrayList<Map>();
		for (Map<String, Object> map : set) {
			Map result_map = new HashMap();
			
			/**
			 * 插入redis时间
			 */
			long redis_time = new Date().getTime();
			Object redis_time_object = map.get(AwifiConstants.Redis_ZSet_Score);
			if(redis_time_object != null) {
				redis_time = (long) redis_time_object;
			}
			result_map.put("createTime", redis_time);
			
			/**
			 * 展示数据
			 */
			Map data = (Map) map.get(AwifiConstants.Interface_Return_Data);
			if(data != null) {
				String activatePer = data.get("activatePer").toString();
				String beforePer = activatePer.substring(0, activatePer.indexOf("%"));
				result_map.put("activatePer", beforePer); //胖AP激活率
				result_map.put("activateNum", data.get("activateNum")); //胖AP激活数量
				//result_map.put("activatePer", data.get("activatePer")); //胖AP激活率
//				String activateNum = data.get("activateNum").toString();
//				BigDecimal showNum = new BigDecimal(activateNum).divide(new BigDecimal(100));
//				result_map.put("activateNum", showNum); //胖AP激活数量
			}
			
			result_list.add(result_map);
		}
		
		return JSON.toJSONString(result_list);
	}
	
}