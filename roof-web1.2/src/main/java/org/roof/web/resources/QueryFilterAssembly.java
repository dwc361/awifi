package org.roof.web.resources;

import java.util.Collection;

import org.roof.web.resources.entity.QueryFilterResource;

/**
 * 过滤条件组装
 * 
 * @author liuxin
 * 
 */
public interface QueryFilterAssembly {
	/**
	 * 组装条件
	 * 
	 * @param queryFilterResources
	 *            过滤条件列表
	 * @return 组装后的Sql条件
	 */
	String assemble(Collection<QueryFilterResource> queryFilterResources);

}
