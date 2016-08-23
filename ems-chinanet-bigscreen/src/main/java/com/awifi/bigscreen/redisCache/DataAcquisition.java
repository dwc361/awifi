package com.awifi.bigscreen.redisCache;

public interface DataAcquisition<T> {
	/**
	 * @param String
	 * @return data
	 */
	public T selectData(String param);
}