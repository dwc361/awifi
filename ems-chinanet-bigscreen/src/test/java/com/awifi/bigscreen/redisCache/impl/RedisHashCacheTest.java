package com.awifi.bigscreen.redisCache.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.redisCache.ChartCache;
import com.awifi.bigscreen.redisCache.DataAcquisition;
import com.awifi.bigscreen.redisCache.DataTransform;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisHashCacheTest extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private ChartCache redisHashCache;
	@Resource
	private DataAcquisition data1Acquisition;
	@Resource
	private DataTransform chart1DataTransform;

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