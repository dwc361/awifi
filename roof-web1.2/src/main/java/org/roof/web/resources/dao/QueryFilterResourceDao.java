package org.roof.web.resources.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.resources.entity.QueryFilterResource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liuxin
 * 
 */
@Component
public class QueryFilterResourceDao extends RoofDaoSupport {
	/**
	 * 获取指定角色下查询的过滤
	 * 
	 * @param path
	 *            查询的路径
	 * @param roleIds
	 *            角色列表
	 * @return 过滤列表
	 */
	public List<QueryFilterResource> findByPath(String path, Long[] roleIds) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("path", path);
		parameterObject.put("roleIds", roleIds);
		@SuppressWarnings("unchecked")
		List<QueryFilterResource> result = (List<QueryFilterResource>) findByMappedQuery(
				"org.roof.web.resources.dao.QueryFilterResourceDao.findByPath",
				parameterObject);
		return result;
	}
}
