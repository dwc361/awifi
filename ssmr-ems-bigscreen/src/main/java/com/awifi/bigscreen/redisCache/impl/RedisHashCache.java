package com.awifi.bigscreen.redisCache.impl;

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
 * Redis数据结构：Hash类型
 * @author zhangmm
 */
@Service
public class RedisHashCache extends AbstractRedisCache {
	Logger logger = Logger.getLogger(RedisHashCache.class);

	private RedisTemplate redisTemplate;

	/**
	 * 取Hash里面相应key对应的数据
	 */
	@Override
	public String readCacheByKey(String key, IDataTransform transverter) {
		Map<String, Object> map = redisTemplate.opsForHash().entries(key);
		//Map<String, Object> map = redisTemplate.boundHashOps(key).entries();
		return transverter.transform(map);
	}

	/**
	 * 将数据更新到Hash里面
	 */
	@Override
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisHashCache:key can't be null");
		Assert.notNull(dataAcquisition, "RedisHashCache:dataAcquisition object can't be null");
		
		Object result = dataAcquisition.selectData(param);
		Assert.notNull(result, "RedisHashCache:result object can't be null");
		Assert.isInstanceOf(Map.class, result, "RedisHashCache:result object not instanceof Map.class");
		
		Map<String, Object> map = (Map<String, Object>) result;
		//Object data = map.get(AwifiConstants.Interface_Return_Data);
		//Assert.notNull(data, "data can't be null");
		redisTemplate.opsForHash().putAll(key, map);
		//redisTemplate.boundHashOps(key).putAll(map);
	}
	
	
	
	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}