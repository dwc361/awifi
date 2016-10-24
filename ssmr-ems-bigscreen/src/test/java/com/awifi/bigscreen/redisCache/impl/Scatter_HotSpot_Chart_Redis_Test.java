package com.awifi.bigscreen.redisCache.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class Scatter_HotSpot_Chart_Redis_Test extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private IRedisCache redisHashCache;
	@Resource(name="scatter_HotSpot_Chart_DataAcquisition")
	private IDataAcquisition acquisition;
	@Resource(name="scatter_HotSpot_Chart_DataTransform")
	private IDataTransform dataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisHashCache.readCacheByKey(AwifiConstants.Redis_Key_Scatter_HotSpot_Chart, dataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		redisHashCache.createOrUpdateCache(AwifiConstants.Redis_Key_Scatter_HotSpot_Chart, acquisition, "{'key':'value'}");
	}
}