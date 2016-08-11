package com.awifi.bigscreen.redisCache;

import java.util.Map;

public interface DataTransform {
	/**
	 * 
	 * @param data
	 * @return json
	 */
	public String transform(Map<String, Object> data);
}
