package org.roof.web.resources.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.dataaccess.RoofDaoSupport;
import org.roof.security.entity.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceDao extends RoofDaoSupport {

	public List<Resource> findModuleByParent(Long parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		if (parentId == null) {
			param.put("lvl", 0);
		}
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) this.findByMappedQuery(
				"org.roof.web.resources.dao.ResourceDao.findModuleByParent",
				param);
		return resources;
	}
	
	@Deprecated
	public List<Resource> findModuleByPath(String path) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("path", path);
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) this.findByMappedQuery(
				"org.roof.web.resources.dao.ResourceDao.findModuleByParent",
				param);
		return resources;
	}

	public List<Resource> findModuleByPath(String[] path) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pathArr", path);
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) this.findByMappedQuery(
				"org.roof.web.resources.dao.ResourceDao.findModuleByParent",
				param);
		return resources;
	}

	public Long childrenCount(Long id) {
		Long result = (Long) this
				.executeByMappedQuery("org.roof.web.resources.dao.ResourceDao.childrenCount", id);
		return result;
	}

}
