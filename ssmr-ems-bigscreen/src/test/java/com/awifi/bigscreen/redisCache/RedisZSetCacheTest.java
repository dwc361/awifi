package com.awifi.bigscreen.redisCache;

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
	private IDataAcquisition testZSetDataAcquisition;
	@Resource
	private IDataTransform testZSetDataTransform;
	
	@Test
	public void testReadCacheByKey() {
//		String s = redisZSetCache.readCacheByKey("Redis_ZSet_Test", testZSetDataTransform);
//		System.out.println(s);
		String s1 = redisZSetCache.readCacheByKey("Redis_ZSet_Test", 6, testZSetDataTransform);
		System.out.println(s1);
//		String s2 = redisZSetCache.readCacheByKey("Redis_ZSet_Test", 6, "desc", testZSetDataTransform);
//		System.out.println(s2);
//		String s3 = redisZSetCache.readCacheByKey("Redis_ZSet_Test", 1471568532, new Date().getTime(), testZSetDataTransform);
//		System.out.println(s3);
	}
	
	@Test
	public void testCreateOrUpdateCache() {
		redisZSetCache.createOrUpdateCache("Redis_ZSet_Test", testZSetDataAcquisition, "{'key':'value'}");
	}
}