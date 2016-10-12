package com.awifi.bigscreen.redisCache.api;

public interface IDataAcquisition<T> {
	/**
	 * @param String
	 * @return data
	 */
	public abstract T selectData(String param);
}