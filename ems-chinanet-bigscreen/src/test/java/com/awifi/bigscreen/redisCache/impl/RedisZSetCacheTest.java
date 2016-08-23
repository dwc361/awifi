package com.awifi.bigscreen.redisCache.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisZSetCacheTest extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private IRedisCache redisZSetCache;
	@Resource
	private IDataAcquisition data3Acquisition;
	@Resource
	private IDataTransform chart3DataTransform;
	
	@Test
	public void testReadCacheByKey() {
		String s = redisZSetCache.readCacheByKey("test_zset_chart", chart3DataTransform);
		String s1 = redisZSetCache.readCacheByKey("test_zset_chart", 6, chart3DataTransform);
		String s2 = redisZSetCache.readCacheByKey("test_zset_chart", 6, "desc", chart3DataTransform);
		String s3 = redisZSetCache.readCacheByKey("test_zset_chart", 1471568532, new Date().getTime(), chart3DataTransform);
		System.out.println(s);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
	}
	
	@Test
	public void testCreateOrUpdateCache() {
		redisZSetCache.createOrUpdateCache("test_zset_chart", data3Acquisition, "{'key':'value'}");
	}
}