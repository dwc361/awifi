package com.awifi.bigscreen.redisCache;

public interface DataAcquisition<T> {
	public T selectData(String param);
}
