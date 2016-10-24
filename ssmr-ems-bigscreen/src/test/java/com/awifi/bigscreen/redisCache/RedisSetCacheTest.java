package com.awifi.bigscreen.redisCache;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.impl.RedisSetCache;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisSetCacheTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private RedisSetCache redisSetCache;
	@Resource
	private IDataAcquisition testSetDataAcquisition;
	@Resource
	private IDataTransform testSetDataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisSetCache.readCacheByKey("Redis_Set_Test", testSetDataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		redisSetCache.createOrUpdateCache("Redis_Set_Test", testSetDataAcquisition, "{'key':'value'}");
	}
}