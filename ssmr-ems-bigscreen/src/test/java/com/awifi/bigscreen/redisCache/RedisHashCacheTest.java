package com.awifi.bigscreen.redisCache;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisHashCacheTest extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private IRedisCache redisHashCache;
	@Resource
	private IDataAcquisition data1Acquisition;
	@Resource
	private IDataTransform chart1DataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisHashCache.readCacheByKey("testchart", chart1DataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		redisHashCache.createOrUpdateCache("testchart", data1Acquisition, "{'key':'value'}");
	}
}