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
public class Line_YHRZ_Chart_Redis_Test extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private IRedisCache redisZSetCache;
	@Resource(name="line_YHRZ_Chart_DataAcquisition")
	private IDataAcquisition acquisition;
	@Resource(name="line_YHRZ_Chart_DataTransform")
	private IDataTransform dataTransform;
	
	@Test
	public void testReadCacheByKey() {
//		String s = redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Line_YHRZ_Chart, dataTransform);
//		System.out.println(s);
//		String s1 = redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Line_YHRZ_Chart, 6, dataTransform);
//		System.out.println(s1);
		String s2 = redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Line_YHRZ_Chart, 6, "desc", dataTransform);
		System.out.println(s2);
//		String s3 = redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Line_YHRZ_Chart, 1471568532, new Date().getTime(), dataTransform);
//		System.out.println(s3);
	}
	
	@Test
	public void testCreateOrUpdateCache() {
		redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_Line_YHRZ_Chart, acquisition, "{'key':'value'}");
	}
}