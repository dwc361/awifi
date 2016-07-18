package org.roof.web.resources;

import java.util.Collection;

import javax.annotation.Resource;

import org.roof.web.resources.dao.QueryFilterResourceDao;
import org.roof.web.resources.dao.QueryResourceDao;
import org.roof.web.resources.entity.QueryFilterResource;
import org.roof.web.resources.entity.QueryResource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liuxin
 * 
 */
@Component
public class DefaultQueryFilterInvocationMetadataSource implements
		QueryFilterInvocationMetadataSource {
	private QueryResourceDao queryResourceDao;
	private QueryFilterResourceDao queryFilterResourceDao;

	@Override
	@Cacheable(value = "DefaultQueryFilterInvocationMetadataSource#getQueryFilterResources", key = "#identify + T(org.apache.commons.lang3.ArrayUtils).toString(#roleIds)")
	public Collection<QueryFilterResource> getQueryFilterResources(
			String identify, Long[] roleIds) {
		QueryResource queryResource = queryResourceDao.findByIdentify(identify);
		if (queryResource == null) {
			return null;
		}
		return queryFilterResourceDao.findByPath(queryResource.getPath(),
				roleIds);
	}

	@Override
	public Collection<QueryFilterResource> getAllQueryFilterResources() {
		return queryFilterResourceDao.loadAll(QueryFilterResource.class);
	}

	@Resource
	public void setQueryFilterResourceDao(
			QueryFilterResourceDao queryFilterResourceDao) {
		this.queryFilterResourceDao = queryFilterResourceDao;
	}

	@Resource
	public void setQueryResourceDao(QueryResourceDao queryResourceDao) {
		this.queryResourceDao = queryResourceDao;
	}

}
