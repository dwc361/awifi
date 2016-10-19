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
	private IDataAcquisition testHashDataAcquisition;
	@Resource
	private IDataTransform testHashDataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisHashCache.readCacheByKey("Redis_Hash_Test", testHashDataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		redisHashCache.createOrUpdateCache("Redis_Hash_Test", testHashDataAcquisition, "{'key':'value'}");
	}
}