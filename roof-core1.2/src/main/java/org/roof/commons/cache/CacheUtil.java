package org.roof.commons.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

/**
 * 缓存操作工具<br />
 * 默认缓存为 roofcache
 * 使用spring管理的缓存（spring-cache.xml）
 * org.springframework.cache.ehcache.EhCacheCacheManager
 */
@Deprecated
public class CacheUtil {

	private static Logger logger = Logger.getLogger(CacheUtil.class);
	private static net.sf.ehcache.CacheManager CACHEMANAGER = null;

	/*
	 * 修改名称
	 */
	private static String modifyName(String cacheName) {
		if (cacheName.indexOf("?") != -1) {
			cacheName = cacheName.replaceAll("\\?", "W_W_W");
		}
		if (cacheName.indexOf("*") != -1) {
			cacheName = cacheName.replaceAll("\\*", "I_I_I");
		}
		if (cacheName.indexOf("\\") != -1) {
			cacheName = cacheName.replaceAll("\\\\", "U_U_U");
		}
		if (cacheName.indexOf("/") != -1) {
			cacheName = cacheName.replaceAll("/", "O_O_O");
		}
		return cacheName;
	}

	static {
//		InputStream inputStream = null;
//		try {
//			inputStream = logger.getClass().getClassLoader().getResourceAsStream(
//					"org/seek/roof/commons/cache/ehcache.xml");
//			CACHEMANAGER = CacheManager.create(inputStream);
//			logger.debug("new CacheManager [org/seek/roof/cache/ehcache.xml]");
//		} catch (CacheException e) {
//			logger.error(e);
//		} finally {
//			try {
//				if (inputStream != null) {
//					inputStream.close();
//					inputStream = null;
//				}
//			} catch (IOException e) {
//				logger.error(e);
//			}
//		}

	}

	/**
	 * 缓存时候存在
	 * 
	 * @param cacheName
	 *            缓存的名称
	 * @return 存在true 不存在 false
	 */
	public static boolean exists(String cacheName) {
		cacheName = modifyName(cacheName);
		return CACHEMANAGER.cacheExists(cacheName);
	}

	/**
	 * 删除缓存
	 * 
	 * @param cacheName
	 *            缓存的名称
	 */
	public static void removeCache(String cacheName) {
		cacheName = modifyName(cacheName);
		if (exists(cacheName)) {
			CACHEMANAGER.removeCache(cacheName);
			logger.debug("removeCache [" + cacheName + "]");
		}
	}

	/**
	 * 得到指定缓存 <br />
	 * 如果缓存不存在则新建
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @return 缓存对象
	 */
	public static Cache getCache(String cacheName) {
		cacheName = modifyName(cacheName);
		if (!exists(cacheName)) {
			addCache(cacheName);
			logger.debug("cache[" + cacheName + "] is null,create a new cache");
		}
		Cache cache = CACHEMANAGER.getCache(cacheName);
		return cache;
	}

	/**
	 * 添加指定名称的缓存<br />
	 * 如果该缓存已经存在则不新建直接返回
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @return 添加的缓存
	 */
	public static Cache addCache(String cacheName) {
		cacheName = modifyName(cacheName);
		if (exists(cacheName)) {
			return getCache(cacheName);
		}
		CACHEMANAGER.addCache(cacheName);
		Cache cache = CACHEMANAGER.getCache(cacheName);
		logger.debug("add cache[" + cacheName + "] with default configuration");
		return cache;
	}

	/**
	 * 在指定的缓存下添加缓存元素
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param key
	 *            缓存元素的键
	 * @param object
	 *            缓存元素的值
	 */
	public static void put(String cacheName, Object key, Object object) {
		Cache cache = getCache(cacheName);
		cache.put(new Element(key, object));
		logger.debug("add cache[" + cacheName + "] key[" + key + "] value[" + object + "]");
	}

	/**
	 * 在默认的缓存中添加元素,默认的缓存名为:roofcache
	 * 
	 * @param key
	 *            缓存元素的键
	 * @param object
	 *            缓存元素的值
	 */
	public static void putDefault(Object key, Object object) {
		put("roofcache", key, object);
	}

	/**
	 * 得到默认缓存下的指定元素的值
	 * 
	 * @param key
	 *            缓存元素的键
	 * 
	 * @return 缓存元素的值,不存在则为null
	 */
	public static Object getDefault(Object key) {
		return get("roofcache", key);
	}

	/**
	 * 得到默认缓存下的指定元素(泛型)
	 * 
	 * @param key
	 *            缓存元素的键
	 * @param clazz
	 *            缓存元素的值的类型
	 * 
	 * @return 缓存元素的值,不存在则为null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDefault(Object key, Class<T> clazz) {
		return (T) get("roofcache", key);
	}

	/**
	 * 得到指定缓存下元素的值
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param key
	 *            缓存元素的键
	 * 
	 * @return 缓存元素的值 不存在则返回null
	 */
	public static Object get(String cacheName, Object key) {
		Element element = getCache(cacheName).get(key);
		Object value = null;
		if (element != null) {
			value = element.getObjectValue();
		}
		logger.debug("get cache[" + cacheName + "] key[" + key + "] value[" + value + "]");
		return value;
	}

	/**
	 * 得到制定缓存下元素的值(泛型)
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param key
	 *            缓存元素的键
	 * @param clazz
	 *            缓存元素的值的类型
	 * 
	 * @return 缓存元素的值 不存在则返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, Object key, Class<T> clazz) {
		return (T) get(cacheName, key);
	}

	/**
	 * 删除指定缓存下的指定的元素
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param key
	 *            缓存元素的键
	 */
	public static void remove(String cacheName, Object key) {
		getCache(cacheName).remove(key);
	}

	/**
	 * 删除默认缓存下指定元素, 默认缓存名称为 roofcache
	 * 
	 * @param key
	 *            元素的键
	 */
	public static void removeDefault(Object key) {
		remove("roofcache", key);
	}

	public static List<?> getKeysWithExpiryCheck(String cacheName) {
		return getCache(cacheName).getKeysWithExpiryCheck();
	}

	/**
	 * 得到所有缓存的名称
	 * 
	 * @return 缓存名称的数组
	 */
	public static String[] getCacheNames() {
		return CACHEMANAGER.getCacheNames();
	}

	/**
	 * 得到所有的缓存
	 * 
	 * @return 又有缓存的List
	 */
	public static List<Cache> getCacheList() {
		String[] cacheNames = getCacheNames();
		List<Cache> list = new ArrayList<Cache>();
		for (int i = 0; i < cacheNames.length; i++) {
			list.add(getCache(cacheNames[i]));
		}
		return list;
	}

	/**
	 * 关闭缓存
	 */
	public static void shutdown() {
		CACHEMANAGER.shutdown();
	}

}
