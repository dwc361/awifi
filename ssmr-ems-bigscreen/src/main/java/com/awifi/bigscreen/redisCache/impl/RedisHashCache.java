package com.awifi.bigscreen.redisCache.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.redisCache.api.AbstractRedisCache;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

import reactor.util.Assert;

@Service
public class RedisHashCache extends AbstractRedisCache {
	Logger logger = Logger.getLogger(RedisHashCache.class);

	private RedisTemplate redisTemplate;

	@Override
	public String readCacheByKey(String key, IDataTransform transverter) {
		Map<String, Object> map = redisTemplate.opsForHash().entries(key);
		return transverter.transform(map);
	}

	@Override
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisHashCache:key can't be null");
		Assert.notNull(dataAcquisition, "RedisHashCache:dataAcquisition object can't be null");
		
		Object result = dataAcquisition.selectData(param);
		Assert.notNull(result, "RedisHashCache:result object can't be null");
		Assert.isInstanceOf(Map.class, result, "RedisHashCache:result object not instanceof Map.class");
		
		Map<String, Object> map = (Map<String, Object>) result;
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	
	
	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
