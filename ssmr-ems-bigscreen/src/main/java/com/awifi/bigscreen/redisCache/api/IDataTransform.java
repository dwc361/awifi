package com.awifi.bigscreen.redisCache.api;

public interface IDataTransform<T> {
	/**
	 * @param data
	 * @return json
	 */
	public abstract String transform(T t);
}