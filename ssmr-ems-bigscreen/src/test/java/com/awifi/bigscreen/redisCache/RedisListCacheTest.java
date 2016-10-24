package com.awifi.bigscreen.redisCache;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisListCacheTest extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private IRedisCache redisListCache;
	@Resource
	private IDataAcquisition testListDataAcquisition;
	@Resource
	private IDataTransform testListDataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisListCache.readCacheByKey("Redis_List_Test", testListDataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		redisListCache.LeftPushListCache("Redis_List_Test", testListDataAcquisition, "{'key':'value'}");
		redisListCache.RightPushListCache("Redis_List_Test", testListDataAcquisition, "{'key':'value'}");
	}
}