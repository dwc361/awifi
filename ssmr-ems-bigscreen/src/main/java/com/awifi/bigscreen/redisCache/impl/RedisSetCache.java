package com.awifi.bigscreen.redisCache.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import reactor.util.Assert;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.data.entity.UserData;
import com.awifi.bigscreen.redisCache.api.AbstractRedisCache;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

/**
 * Redis数据结构：Set(无序集合)
 * @author zhangmm
 */
@Service
public class RedisSetCache extends AbstractRedisCache {
	Logger logger = Logger.getLogger(RedisSetCache.class);

	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 取Set里面所有的数据
	 */
	@Override
	public String readCacheByKey(String key, IDataTransform dataTransform) {
		Long all = redisTemplate.opsForSet().size(key);
		Set<Object> set = redisTemplate.opsForSet().members(key);
		return dataTransform.transform(set);
	}

	/**
	 * 将数据更新到Set里面
	 */
	@Override
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisHashCache:key can't be null");
		Assert.notNull(dataAcquisition, "RedisHashCache:dataAcquisition object can't be null");
		
		Object result = dataAcquisition.selectData(param);
		Assert.notNull(result, "RedisHashCache:result object can't be null");
		Assert.isInstanceOf(Map.class, result, "RedisHashCache:result object not instanceof Map.class");
		
//		Map<String, Object> map = (Map<String, Object>) result;
//		Object data = map.get(AwifiConstants.Interface_Return_Data);
//		Assert.notNull(data, "data can't be null");
		redisTemplate.opsForSet().add(key, result);
	}
	
	
	
	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
