package com.awifi.bigscreen.redisCache;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.awifi.bigscreen.redisCache.impl.Chart1DataTransform;
import com.awifi.bigscreen.redisCache.impl.Data2Acquisition;
import com.awifi.bigscreen.redisCache.impl.RedisSetCache;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisSetCacheTest extends AbstractJUnit4SpringContextTests {

	private RedisSetCache redisSetCache;
	private Data2Acquisition data2Acquisition;
	private Chart1DataTransform chart1DataTransform;

	@Test
	public void testReadCacheByKey() {
		String s = redisSetCache.readCacheByKey("testsetchart",1471568532,1471568535, chart1DataTransform);
		System.out.println(s);
	}

	@Test
	public void testCreateOrUpdateCache() {
		redisSetCache.createOrUpdateCache("testsetchart", data2Acquisition, "{'key':'value'}");
	}

	
	
	
	

	@Autowired
	public void setChart1DataTransform(Chart1DataTransform chart1DataTransform) {
		this.chart1DataTransform = chart1DataTransform;
	}
	@Autowired
	public void setRedisSetCache(RedisSetCache redisSetCache) {
		this.redisSetCache = redisSetCache;
	}

	@Autowired
	public void setData2Acquisition(Data2Acquisition data2Acquisition) {
		this.data2Acquisition = data2Acquisition;
	}

}
