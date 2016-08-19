package com.awifi.bigscreen.redisCache.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.redisCache.DataAcquisition;
import com.awifi.bigscreen.redisCache.DataTransform;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisHashCacheTest extends AbstractJUnit4SpringContextTests {

	private RedisHashCache redisHashCache;
	private Data1Acquisition data1Acquisition;
	private Chart1DataTransform chart1DataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisHashCache.readCacheByKey("testchart", chart1DataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		redisHashCache.createOrUpdateCache("testchart", data1Acquisition, "{'key':'value'}");
	}

	@Autowired
	public void setRedisHashCache(RedisHashCache redisHashCache) {
		this.redisHashCache = redisHashCache;
	}

	@Autowired
	public void setData1Acquisition(Data1Acquisition data1Acquisition) {
		this.data1Acquisition = data1Acquisition;
	}

	@Autowired
	public void setChart1DataTransform(Chart1DataTransform chart1DataTransform) {
		this.chart1DataTransform = chart1DataTransform;
}

}
