package com.awifi.bigscreen.redisCache.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import reactor.util.Assert;

import com.awifi.bigscreen.data.entity.UserData;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

@Service
public class RedisSetCache implements IRedisCache {
	Logger logger = Logger.getLogger(RedisSetCache.class);

	private RedisTemplate<String,UserData> redisTemplate;
	
	private int defaultcount = 12;
	
	@Override
	public String readCacheByKey(String key, IDataTransform transverter) {
		return this.readCacheByKey(key, defaultcount, transverter);
	}
	
	@Override
	public String readCacheByKey(String key, int count, IDataTransform dataTransform) {
		Long all = redisTemplate.opsForZSet().size(key);
		Set<UserData> map = redisTemplate.opsForZSet().range(key, all-count, all-1);
		Iterator<UserData> iterator = map.iterator();
		while (iterator.hasNext()) {
			UserData data = iterator.next();
		}
		return map.toString();
	}

	@Override
	public String readCacheByKey(String key, double min, double max, IDataTransform dataTransform) {
		Set<UserData> map = redisTemplate.opsForZSet().rangeByScore(key, min, max);
		Iterator<UserData> iterator = map.iterator();
		while (iterator.hasNext()) {
			UserData data = iterator.next();
		}
		return map.toString();
	}
	
	@Override
	public String readCacheByKey(String key, int count, String order, IDataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readCacheByKey(String key, double min, double max, String order, IDataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param) {
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
