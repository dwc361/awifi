package com.awifi.bigscreen.redisCache;

public interface DataTransform<T> {
	/**
	 * @param data
	 * @return json
	 */
	public String transform(T t);
}