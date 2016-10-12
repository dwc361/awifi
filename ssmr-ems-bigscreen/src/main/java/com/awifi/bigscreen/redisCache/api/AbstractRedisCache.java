package com.awifi.bigscreen.redisCache.api;

public abstract class AbstractRedisCache implements IRedisCache {

	@Override
	public String readCacheByKey(String key, IDataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readCacheByKey(String key, int count, IDataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readCacheByKey(String key, int count, String order, IDataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readCacheByKey(String key, double min, double max, IDataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readCacheByKey(String key, double min, double max, String order, IDataTransform dataTransform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param) {
		// TODO Auto-generated method stub
	}

}