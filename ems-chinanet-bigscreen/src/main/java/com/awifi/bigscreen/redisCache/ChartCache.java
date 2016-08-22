package com.awifi.bigscreen.redisCache;

public interface ChartCache {
	/**
	 * 缓存读
	 * 
	 * @param key
	 * @param transverter
	 *            读缓存转换器
	 * @return chart json
	 */
	public String readCacheByKey(String key, DataTransform dataTransform);
	
	public String readCacheByKey(String key, int count, DataTransform dataTransform);
	
	public String readCacheByKey(String key, int count, String order, DataTransform dataTransform);
	
	public String readCacheByKey(String key, double min, double max, DataTransform dataTransform);
	
	public String readCacheByKey(String key, double min, double max, String order, DataTransform dataTransform);

	/**
	 * 缓存写
	 * 
	 * @param key
	 * @param transverter
	 *            写缓存转换器
	 */
	public void createOrUpdateCache(String key, DataAcquisition dataAcquisition, String param);
}