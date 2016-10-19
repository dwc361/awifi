package com.awifi.bigscreen.redisCache.impl;

import java.util.Map;
import java.util.Set;

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
 * Redis数据结构：ZSet(排序集合)
 * @author zhangmm
 */
@Service
public class RedisZSetCache extends AbstractRedisCache {
	Logger logger = Logger.getLogger(RedisZSetCache.class);

	private RedisTemplate<String, Object> redisTemplate;
	
	private int defaultCount = 12;
	private String defaultOrder = "asc";
	
	/**
	 * 取ZSet里面最新插入的defaultCount个数据
	 */
	@Override
	public String readCacheByKey(String key, IDataTransform dataTransform) {
		return this.readCacheByKey(key, defaultCount, defaultOrder, dataTransform);
	}
	
	/**
	 * 取ZSet里面最新插入的count个数据
	 */
	@Override
	public String readCacheByKey(String key, int count, IDataTransform dataTransform) {
		return this.readCacheByKey(key, count, defaultOrder, dataTransform);
	}
	
	/**
	 * 取ZSet里面最新插入的count个数据，按order排序
	 */
	@Override
	public String readCacheByKey(String key, int count, String order, IDataTransform dataTransform) {
		Long all = redisTemplate.opsForZSet().size(key);
		Set<Object> set;
		if("desc".equals(order)) {
			// zRevRange(key, start, end)：返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素
			set = redisTemplate.opsForZSet().reverseRange(key, all-count, all-1);
			//set = redisTemplate.boundZSetOps(key).reverseRange(all-count, all-1);
		} else {
			// zRange(key, start, end)：返回名称为key的zset（元素已按score从小到大排序）中的index从start到end的所有元素
			set = redisTemplate.opsForZSet().range(key, all-count, all-1);
			//set = redisTemplate.boundZSetOps(key).range(all-count, all-1);
		}
		return dataTransform.transform(set);
	}

	/**
	 * 取ZSet里面score在某个范围内的数据
	 */
	@Override
	public String readCacheByKey(String key, double min, double max, IDataTransform dataTransform) {
		return this.readCacheByKey(key, min, max, defaultOrder, dataTransform);
	}

	/**
	 * 取ZSet里面score在某个范围内的数据，按order排序
	 */
	@Override
	public String readCacheByKey(String key, double min, double max, String order, IDataTransform dataTransform) {
		Set<Object> set;
		if("desc".equals(order)) {
			// zRevRangeByScore(key, min, max)：返回名称为key的zset（元素已按score从大到小排序）中score >= min且score <= max的所有元素
			set = redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
			//set = redisTemplate.boundZSetOps(key).reverseRangeByScore(min, max);
		} else {
			// zRangeByScore(key, min, max)：返回名称为key的zset（元素已按score从小到大排序）中score >= min且score <= max的所有元素
			set = redisTemplate.opsForZSet().rangeByScore(key, min, max);
			//set = redisTemplate.boundZSetOps(key).rangeByScore(min, max);
		}
		return dataTransform.transform(set);
	}

	/**
	 * 将数据更新到ZSet里面
	 */
	@Override
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param) {
		Assert.notNull(key, "RedisZSetCache:key can't be null");
		Assert.notNull(dataAcquisition, "RedisZSetCache:dataAcquisition object can't be null");
		
		Object result = dataAcquisition.selectData(param);
		Assert.notNull(result, "RedisZSetCache:result object can't be null");
		Assert.isInstanceOf(Map.class, result, "RedisZSetCache:result object not instanceof Map.class");
		
		Map<String, Object> map = (Map<String, Object>) result;
		Object chartData = map.get(AwifiConstants.Interface_Return_Data);
		Assert.notNull(chartData, "chartData can't be null");
		String score = String.valueOf(map.get(AwifiConstants.Redis_ZSet_Score));
		Assert.notNull(score, "score can't be null");
		redisTemplate.opsForZSet().add(key, chartData, Double.valueOf(score).doubleValue());
		//redisTemplate.boundZSetOps(key).add(chartData, Double.valueOf(score).doubleValue());
	}
	
	
	
	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
