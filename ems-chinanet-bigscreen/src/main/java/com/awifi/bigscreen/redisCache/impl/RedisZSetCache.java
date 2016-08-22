package com.awifi.bigscreen.redisCache.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import reactor.util.Assert;

import com.awifi.bigscreen.redisCache.ChartCache;
import com.awifi.bigscreen.redisCache.DataAcquisition;
import com.awifi.bigscreen.redisCache.DataTransform;

@Service
public class RedisZSetCache implements ChartCache {
	Logger logger = Logger.getLogger(RedisZSetCache.class);

	private RedisTemplate<String, Map> redisTemplate;
	
	private int defaultCount = 12;
	private String defaultOrder = "asc";
	
	@Override
	public String readCacheByKey(String key, DataTransform dataTransform) {
		return this.readCacheByKey(key, defaultCount, defaultOrder, dataTransform);
	}
	
	@Override
	public String readCacheByKey(String key, int count, DataTransform dataTransform) {
		return this.readCacheByKey(key, count, defaultOrder, dataTransform);
	}

	@Override
	public String readCacheByKey(String key, double min, double max, DataTransform dataTransform) {
		return this.readCacheByKey(key, min, max, defaultOrder, dataTransform);
	}
	
	@Override
	public String readCacheByKey(String key, int count, String order, DataTransform dataTransform) { // 取ZSet里面最新插入的count个数据
		Long all = redisTemplate.opsForZSet().size(key);
		Map map = new HashMap();
		Set<Map> set;
		if("desc".equals(order)) {
			set = redisTemplate.opsForZSet().reverseRange(key, all-count, all-1); // zRevRange(key, start, end)：返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素
		} else {
			set = redisTemplate.opsForZSet().range(key, all-count, all-1); // zRange(key, start, end)：返回名称为key的zset（元素已按score从小到大排序）中的index从start到end的所有元素
		}
		map.put("chartData", set);
		Iterator<Map> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map data = iterator.next();
		}
		return dataTransform.transform(map);
	}

	@Override
	public String readCacheByKey(String key, double min, double max, String order, DataTransform dataTransform) { // 取ZSet里面score在某个范围内的数据
		Map map = new HashMap();
		Set<Map> set;
		if("desc".equals(order)) {
			set = redisTemplate.opsForZSet().reverseRangeByScore(key, min, max); // zRevRangeByScore(key, min, max)：返回名称为key的zset（元素已按score从大到小排序）中score >= min且score <= max的所有元素
		} else {
			set = redisTemplate.opsForZSet().rangeByScore(key, min, max); // zRangeByScore(key, min, max)：返回名称为key的zset（元素已按score从小到大排序）中score >= min且score <= max的所有元素
		}
		map.put("chartData", set);
		Iterator<Map> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map data = iterator.next();
		}
		return dataTransform.transform(map);
	}

	@Override
	public void createOrUpdateCache(String key, DataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisHashCache:key not be null");
		Assert.notNull(dataAcquisition, "RedisHashCache:dataAcquisition object not be null");
		
		Object o = dataAcquisition.selectData(param);
		Assert.notNull(o, "RedisHashCache:result object must not be null");
		Assert.isInstanceOf(Map.class, o, "RedisHashCache:result object instanceof Map.class");
		
		Map<String, Object> map = (Map<String, Object>) o;
		List<Map> list = (List<Map>) map.get("chartData");
		for (Map m : list) {
			String score = String.valueOf(m.get("score"));
			Assert.notNull(score, "score not be null");
			redisTemplate.opsForZSet().add(key, m, Double.valueOf(score).doubleValue());
		}
	}
	

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
