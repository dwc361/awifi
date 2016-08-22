package com.awifi.bigscreen.redisCache.impl;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisZSetCacheTest extends AbstractJUnit4SpringContextTests {

	private RedisZSetCache redisZSetCache;
	private Data3Acquisition data3Acquisition;
	private Chart3DataTransform chart3DataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisZSetCache.readCacheByKey("test_zset_chart", 1471568532, new Date().getTime(), chart3DataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		//redisZSetCache.createOrUpdateCache("test_zset_chart", data3Acquisition, "{'key':'value'}");
	}

	
	
	
	

	@Autowired
	public void setChart3DataTransform(Chart3DataTransform chart3DataTransform) {
		this.chart3DataTransform = chart3DataTransform;
	}
	@Autowired
	public void setRedisZSetCache(RedisZSetCache redisZSetCache) {
		this.redisZSetCache = redisZSetCache;
	}

	@Autowired
	public void setData3Acquisition(Data3Acquisition data3Acquisition) {
		this.data3Acquisition = data3Acquisition;
	}

}
