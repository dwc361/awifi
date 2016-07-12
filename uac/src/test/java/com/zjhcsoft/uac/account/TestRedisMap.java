package com.zjhcsoft.uac.account;

import javax.annotation.Resource;

import org.jasig.cas.ticket.Ticket;
import org.junit.Test;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestRedisMap extends AbstractJUnit4SpringContextTests {
	private DefaultRedisMap<String, Object> redisMap;

	@Test
	public void getKeys() {
		for (String s : redisMap.keySet()) {
			System.out.println(redisMap.get(s));
		}
	}

	@Resource(name = "redisMap")
	public void setRedisMap(DefaultRedisMap<String, Object> redisMap) {
		this.redisMap = redisMap;
	}

}
