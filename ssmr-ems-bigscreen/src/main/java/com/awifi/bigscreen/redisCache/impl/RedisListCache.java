package com.awifi.bigscreen.redisCache.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.AbstractRedisCache;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;

import reactor.util.Assert;

/**
 * Redis数据结构：List(双向链表)
 * @author zhangmm
 */
@Service
public class RedisListCache extends AbstractRedisCache {
	Logger logger = Logger.getLogger(this.getClass());

	private RedisTemplate redisTemplate;
	
	private int defaultCount = 12;
	
	/**
	 * 取List里面最新插入的defaultCount个数据
	 */
	@Override
	public String readCacheByKey(String key, IDataTransform dataTransform) {
		return this.readCacheByKey(key, defaultCount, dataTransform);
	}
	
	/**
	 * 取List里面最新插入的count个数据
	 */
	@Override
	public String readCacheByKey(String key, int count, IDataTransform dataTransform) {
		Long all = redisTemplate.opsForList().size(key);
		List list = redisTemplate.opsForList().range(key, all-count, all-1);
		return dataTransform.transform(list);
	}

	/**
	 * 将数据更新到List左侧-Left Push
	 */
	@Override
	public void LeftPushListCache(String key, IDataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisListCache:key can't be null");
		Assert.notNull(dataAcquisition, "RedisListCache:dataAcquisition object can't be null");
		
		Object result = dataAcquisition.selectData(param);
		Assert.notNull(result, "RedisListCache:result object can't be null");
		Assert.isInstanceOf(Map.class, result, "RedisListCache:result object not instanceof Map.class");
		
		Map<String, Object> map = (Map<String, Object>) result;
		Object data = map.get(AwifiConstants.Interface_Return_Data);
		Assert.notNull(data, "data can't be null");
		redisTemplate.opsForList().leftPush(key, data);
	}
	
	/**
	 * 将数据更新到List右侧-Right Push
	 */
	@Override
	public void RightPushListCache(String key, IDataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisListCache:key can't be null");
		Assert.notNull(dataAcquisition, "RedisListCache:dataAcquisition object can't be null");
		
		Object result = dataAcquisition.selectData(param);
		Assert.notNull(result, "RedisListCache:result object can't be null");
		Assert.isInstanceOf(Map.class, result, "RedisListCache:result object not instanceof Map.class");
		
		Map<String, Object> map = (Map<String, Object>) result;
		Object data = map.get(AwifiConstants.Interface_Return_Data);
		Assert.notNull(data, "data can't be null");
		redisTemplate.opsForList().rightPush(key, data);
	}
	
	
	
	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
