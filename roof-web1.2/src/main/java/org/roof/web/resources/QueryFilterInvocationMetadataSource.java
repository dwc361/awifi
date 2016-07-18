package org.roof.web.resources;

import java.util.Collection;

import org.roof.web.resources.entity.QueryFilterResource;

/**
 * 过滤原数据
 * 
 * @author liuxin
 * 
 */
public interface QueryFilterInvocationMetadataSource {
	/**
	 * 根据查询名称获得过滤
	 * 
	 * @param name
	 *            查询名称
	 * @return 过滤列表
	 */
	Collection<QueryFilterResource> getQueryFilterResources(String name, Long[] roleIds);

	/**
	 * 获得所有的过滤条件
	 * 
	 * @return 过滤列表
	 */
	Collection<QueryFilterResource> getAllQueryFilterResources();

}
