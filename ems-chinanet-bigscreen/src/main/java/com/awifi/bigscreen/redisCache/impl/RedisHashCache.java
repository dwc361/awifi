package com.awifi.bigscreen.redisCache.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.redisCache.ChartCache;
import com.awifi.bigscreen.redisCache.DataAcquisition;
import com.awifi.bigscreen.redisCache.DataTransform;

import reactor.util.Assert;

@Service
public class RedisHashCache implements ChartCache {
	Logger logger = Logger.getLogger(RedisHashCache.class);

	private RedisTemplate redisTemplate;

	@Override
	public String readCacheByKey(String key, DataTransform transverter) {
		// get key from data centre cache
		Map<String, Object> map = redisTemplate.opsForHash().entries(key);
		return transverter.transform(map);
	}
	
	@Override
	public String readCacheByKey(String key, int count, DataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readCacheByKey(String key, double min, double max, DataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String readCacheByKey(String key, int count, String order, DataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readCacheByKey(String key, double min, double max, String order, DataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrUpdateCache(String key, DataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisHashCache:key not be null");
		Assert.notNull(dataAcquisition, "RedisHashCache:dataAcquisition object not be null");
		// get data from centre cache
		Object o = dataAcquisition.selectData(param);
		Assert.notNull(o, "RedisHashCache:result object must not be null");
		Assert.isInstanceOf(Map.class, o, "RedisHashCache:result object instanceof Map.class");
		Map<String, Object> map = (Map<String, Object>) o;
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
