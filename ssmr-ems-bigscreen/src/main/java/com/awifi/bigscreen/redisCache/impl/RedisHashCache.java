package com.awifi.bigscreen.redisCache.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import reactor.util.Assert;

import com.awifi.bigscreen.redisCache.api.AbstractRedisCache;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

@Service
public class RedisHashCache extends AbstractRedisCache {
	Logger logger = Logger.getLogger(RedisHashCache.class);

	private RedisTemplate redisTemplate;

	@Override
	public String readCacheByKey(String key, IDataTransform transverter) {
		// get key from data centre cache
		Map<String, Object> map = redisTemplate.opsForHash().entries(key);
		return transverter.transform(map);
	}

	@Override
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param) {
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
