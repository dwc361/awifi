package com.awifi.bigscreen.redisCache.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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

	@Override
	public void LeftPushListCache(String key, IDataAcquisition dataAcquisition, String param) {
		// TODO Auto-generated method stub
	}

	@Override
	public void RightPushListCache(String key, IDataAcquisition dataAcquisition, String param) {
		// TODO Auto-generated method stub
	}

}