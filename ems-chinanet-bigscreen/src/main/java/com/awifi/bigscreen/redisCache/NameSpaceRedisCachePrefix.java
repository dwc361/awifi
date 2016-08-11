package com.awifi.bigscreen.redisCache;

import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class NameSpaceRedisCachePrefix implements RedisCachePrefix {
	private final RedisSerializer serializer = new StringRedisSerializer();
	private final String delimiter;
	private final String namespace;

	public NameSpaceRedisCachePrefix() {
		this("", ":");
	}

	public NameSpaceRedisCachePrefix(String namespace) {
		this(namespace, ":");
	}

	public NameSpaceRedisCachePrefix(String namespace, String delimiter) {
		if (delimiter == null) {
			delimiter = ":";
		}
		this.delimiter = delimiter;
		this.namespace = namespace;
	}

	@Override
	public byte[] prefix(String cacheName) {
		String r = "";
		if (namespace != null) {
			r = r.concat(namespace).concat(delimiter);
		}
		r = r.concat(cacheName);
		r = r.concat(delimiter);
		return serializer.serialize(r);
	}

}
