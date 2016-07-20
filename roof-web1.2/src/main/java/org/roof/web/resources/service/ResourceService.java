package org.roof.web.resources.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.roof.security.entity.BaseRole;
import org.roof.security.entity.Resource;
import org.roof.web.resources.ResourceVo;
import org.roof.web.resources.dao.ResourceDao;
import org.roof.web.resources.entity.Module;
import org.roof.web.resources.entity.Privilege;
import org.roof.web.resources.entity.QueryFilterResource;
import org.roof.web.resources.entity.QueryResource;
import org.springframework.stereotype.Component;

@Component
public class ResourceService {
	private static final String RESOURCE_TYPE_ICON_RESOURCE = "roof-web/web/resources/images/R.png";
	private static final String RESOURCE_TYPE_ICON_MODULE = "roof-web/web/resources/images/M.png";
	private static final String RESOURCE_TYPE_ICON_QUERY = "roof-web/web/resources/images/Q.png";
	private static final String RESOURCE_TYPE_ICON_QUERYFILTER = "roof-web/web/resources/images/F.png";
	private static final String RESOURCE_TYPE_ICON_GROUP = "roof-web/web/resources/images/G.png";

	public static final String RESOURCE_TYPE_MODULE = "module";
	public static final String RESOURCE_TYPE_PRIVILEGE = "privilege";
	public static final String RESOURCE_TYPE_QUERY = "query";
	public static final String RESOURCE_TYPE_QUERYFILTER = "queryfilter";
	private ResourceDao resourceDao;
	
	@Deprecated
	public List<Resource> findModuleByPath(String path) {
		return resourceDao.findModuleByPath(path);
	}
	
	public List<Resource> findModuleByPath(String[] pathArr) {
		return resourceDao.findModuleByPath(pathArr);
	}

	public List<ResourceVo> read(Long parentId) {
		List<Resource> resources = resourceDao.findModuleByParent(parentId);
		List<ResourceVo> result = new ArrayList<ResourceVo>();
		for (Resource resource : resources) {
			ResourceVo resourceVo = new ResourceVo();
			copyProperties(resource, resourceVo);
			result.add(resourceVo);
		}
		return result;
	}

	public List<ResourceVo> readByRole(Long parentId, Long roleId) {
		List<ResourceVo> resourceVos = read(parentId);
		BaseRole baseRole = resourceDao.load(BaseRole.class, roleId);
		for (Resource resource : baseRole.getResources()) {
			for (ResourceVo resourceVo : resourceVos) {
				if (StringUtils.equals(resourceVo.getId(), resource.getId().toString())) {
					resourceVo.setChecked(true);
				}
			}
		}
		return resourceVos;
	}

	public void copyProperties(Resource resource, ResourceVo resourceVo) {
		if (resource instanceof Module) {
			resourceVo.setType(RESOURCE_TYPE_MODULE);
			resourceVo.setIcon(RESOURCE_TYPE_ICON_MODULE);
			Module module = (Module) resource;
			resourceVo.setLeaf(module.getLeaf());
		}
		if (resource instanceof Privilege) {
			resourceVo.setType(RESOURCE_TYPE_PRIVILEGE);
			resourceVo.setIcon(RESOURCE_TYPE_ICON_RESOURCE);
			Privilege privilege = (Privilege) resource;
			resourceVo.setLeaf(privilege.getLeaf());
		}
		if (resource instanceof QueryResource) {
			resourceVo.setType(RESOURCE_TYPE_QUERY);
			resourceVo.setIcon(RESOURCE_TYPE_ICON_QUERY);
			QueryResource queryResource = (QueryResource) resource;
			resourceVo.setLeaf(queryResource.getLeaf());
		}

		if (resource instanceof QueryFilterResource) {
			resourceVo.setType(RESOURCE_TYPE_QUERYFILTER);
			resourceVo.setIcon(RESOURCE_TYPE_ICON_QUERYFILTER);
			QueryFilterResource queryFilterResource = (QueryFilterResource) resource;
			resourceVo.setLeaf(queryFilterResource.getLeaf());
		}
		resourceVo.setId(resource.getId().toString());
		resourceVo.setName(resource.getName());
		resourceVo.setTitle(resource.getPattern());
	}

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	public Resource create(Long parentId, String type) {
		Module module = null;
		Module parent = resourceDao.load(Module.class, parentId);
		if (StringUtils.equals(RESOURCE_TYPE_MODULE, type)) {
			module = new Module();
		}
		if (StringUtils.equals(RESOURCE_TYPE_PRIVILEGE, type)) {
			module = new Privilege();
		}
		if (StringUtils.equals(RESOURCE_TYPE_QUERY, type)) {
			module = new QueryResource();
		}
		if (StringUtils.equals(RESOURCE_TYPE_QUERYFILTER, type)) {
			module = new QueryFilterResource();
		}
		if (parent.getLeaf()) {
			parent.setLeaf(false);
			resourceDao.update(parent);
		}
		module.setName("未命名");
		module.setLvl(parent.getLvl() + 1);
		module.setParent(parent);
		module.setLeaf(true);
		resourceDao.save(module);
		return module;
	}

	/**
	 * 更新一个资源
	 * 
	 * @param resource
	 * @return
	 */
	public Resource update(Resource resource) {
		return resource;
	}

	/**
	 * 删除一个资源
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		Module resource = resourceDao.load(Module.class, id);
		Module parent = (Module) resource.getParent();
		Long count = resourceDao.childrenCount(parent.getId());
		if (count == 1) {
			parent.setLeaf(true);
			resourceDao.update(parent);
		}
		resource.setId(id);
		resourceDao.delete(resource);
	}

	@javax.annotation.Resource
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

}
