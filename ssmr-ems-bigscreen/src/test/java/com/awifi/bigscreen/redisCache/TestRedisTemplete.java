package com.awifi.bigscreen.redisCache;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestRedisTemplete extends AbstractJUnit4SpringContextTests {
	private RedisTemplate redisTemplate;
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void test() {
		String key = "spring";
		ListOperations<String, String> lop = redisTemplate.opsForList();
		RedisSerializer<String> serializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(serializer);
		redisTemplate.setValueSerializer(serializer);
		// rt.setDefaultSerializer(serializer);

		lop.leftPush(key, "aaa");
		lop.leftPush(key, "bbb");
		long size = lop.size(key); // rt.boundListOps(key).size();
		Assert.assertEquals(2, size);
	}

	@Test
	public void test5() {
		ValueOperations<String, String> vop = stringRedisTemplate.opsForValue();
		String key = "string_redis_template";
		
		
		String v = "use StringRedisTemplate set k v";
		vop.set(key, v);
		String value = vop.get(key);
		System.out.println(value);
		Assert.assertEquals(v, value);
	}

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

}
