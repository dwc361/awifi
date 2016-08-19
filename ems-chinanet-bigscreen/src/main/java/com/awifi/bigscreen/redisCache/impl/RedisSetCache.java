package com.awifi.bigscreen.redisCache.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.data.entity.UserData;
import com.awifi.bigscreen.redisCache.ChartCache;
import com.awifi.bigscreen.redisCache.DataAcquisition;
import com.awifi.bigscreen.redisCache.DataTransform;

import reactor.util.Assert;

@Service
public class RedisSetCache implements ChartCache {
	Logger logger = Logger.getLogger(RedisSetCache.class);

	private RedisTemplate<String,UserData> redisTemplate;
	
	private int defaultcount = 12;
	
	@Override
	public String readCacheByKey(String key, DataTransform transverter) {
		return this.readCacheByKey(key, defaultcount, transverter);
	}
	
	@Override
	public String readCacheByKey(String key, int count, DataTransform dataTransform) {
		Long all = redisTemplate.opsForZSet().size(key);
		Set<UserData> map = redisTemplate.opsForZSet().range(key, all-count, all-1);
		Iterator<UserData> iterator = map.iterator();
		while (iterator.hasNext()) {
			UserData data = iterator.next();
		}
		return map.toString();
	}

	@Override
	public String readCacheByKey(String key, double min, double max, DataTransform dataTransform) {
		Set<UserData> map = redisTemplate.opsForZSet().rangeByScore(key, min, max);
		Iterator<UserData> iterator = map.iterator();
		while (iterator.hasNext()) {
			UserData data = iterator.next();
		}
		return map.toString();
	}

	@Override
	public void createOrUpdateCache(String key, DataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisHashCache:key not be null");
		Assert.notNull(dataAcquisition, "RedisHashCache:dataAcquisition object not be null");
		Object o = dataAcquisition.selectData(param);
		Assert.notNull(o, "RedisHashCache:result object must not be null");
		Assert.isInstanceOf(Map.class, o, "RedisHashCache:result object instanceof Map.class");
		Map<String, Object> map = (Map<String, Object>) o;
		String score = (String) map.get("score");
		UserData data = (UserData) map.get("chartData");
		Assert.notNull(score, "score not be null");
		redisTemplate.opsForZSet().add(key, data, Double.valueOf(score).doubleValue());
	}
	

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	

}
