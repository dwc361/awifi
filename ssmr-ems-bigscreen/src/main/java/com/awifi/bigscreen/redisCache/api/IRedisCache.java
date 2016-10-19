package com.awifi.bigscreen.redisCache.api;

public interface IRedisCache {
	/**
	 * 缓存读
	 * 
	 * @param key
	 * @param dataTransform 读缓存转换器
	 * @return String json格式
	 */
	public abstract String readCacheByKey(String key, IDataTransform dataTransform);
	
	public abstract String readCacheByKey(String key, int count, IDataTransform dataTransform);
	
	public abstract String readCacheByKey(String key, int count, String order, IDataTransform dataTransform);
	
	public abstract String readCacheByKey(String key, double min, double max, IDataTransform dataTransform);
	
	public abstract String readCacheByKey(String key, double min, double max, String order, IDataTransform dataTransform);

	/**
	 * 缓存写
	 * 
	 * @param key
	 * @param dataAcquisition 写缓存转换器
	 */
	public void createOrUpdateCache(String key, IDataAcquisition dataAcquisition, String param);
	
	public void LeftPushListCache(String key, IDataAcquisition dataAcquisition, String param);
	
	public void RightPushListCache(String key, IDataAcquisition dataAcquisition, String param);
}