package org.roof.web.resources.action;

import java.util.List;

import org.roof.security.entity.Resource;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.resources.ResourcesUtils;
import org.roof.web.resources.dao.ResourceDao;
import org.roof.web.resources.entity.Module;
import org.roof.web.resources.entity.Privilege;
import org.roof.web.resources.entity.QueryFilterResource;
import org.roof.web.resources.entity.QueryResource;
import org.roof.web.resources.service.ResourceService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("resourceAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ResourceAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;
	private ResourceService resourceService;
	private ResourceDao resourceDao;
	private Module module;
	private Privilege privilege;
	private ResourcesUtils resourcesUtils;

	/**
	 * 资源管理首页面
	 * 
	 * @return
	 */
	public String index() {
		result = "/roof-web/web/resources/resource_index.jsp";
		return JSP;
	}

	/**
	 * 根据父节点ID加载资源列表页面
	 * 
	 * @return
	 */
	public String list() {
		Long parentId = this.getParamByName("parentId", Long.class);
		Resource resource = resourceDao.load(Resource.class,
				parentId == null ? 1L : parentId);
		List<Resource> resources = resourceDao.findModuleByParent(parentId);
		this.addParameter("resources", resources);
		this.addParameter("resource", resource);
		result = "/roof-web/web/resources/resource_list.jsp";
		return JSP;
	}

	/**
	 * 资源详情页面
	 * 
	 * @return
	 */
	public String detail() {
		Long id = this.getParamByName("id", Long.class);
		Resource resource = resourceDao.load(Resource.class, id);
		this.addParameter("resource", resource);
		if (resource instanceof Module) {
			result = "/roof-web/web/resources/resource_module_detail.jsp";
		}
		if (resource instanceof Privilege) {
			result = "/roof-web/web/resources/resource_resource_detail.jsp";
		}
		if (resource instanceof QueryResource) {
			result  = "/roof-web/web/resources/resource_query_detail.jsp";
		}
		if (resource instanceof QueryFilterResource) {
			result = "/roof-web/web/resources/resource_queryfilter_detail.jsp";
		}
		return JSP;
	}

	/**
	 * 创建一个新的资源
	 * 
	 * @return
	 */
	public String create() {
		Long parentId = this.getParamByName("parentId", Long.class);
		String type = this.getParamByName("type", String.class);
		resourceService.create(parentId, type);
		result = new Result(Result.SUCCESS, "新增成功!");
		return JSON;
	}

	public String delete() {
		Long id = this.getParamByName("id", Long.class);
		resourceService.delete(id);
		result = new Result("删除成功!");
		return JSON;
	}

	public String updateModule() {
		Integer initBasic = this.getParamByName("initBasic", Integer.class);
		if (module != null) {
			resourceDao.updateIgnoreNull(module);
			if (module.getLeaf() != null && initBasic != null
					&& module.getLeaf() && initBasic == 1) {
				resourcesUtils.initBasicOperation(module);
				module.setLeaf(false);
				resourceDao.updateIgnoreNull(module);
			}
		}
		result = new Result("保存成功!");
		return JSON;
	}

	public String updatePrivilege() {
		if (privilege != null) {
			resourceDao.updateIgnoreNull(privilege);
		}
		result = new Result("保存成功!");
		return JSON;
	}

	/**
	 * 根据父节点ID加载资源列表页面
	 * 
	 * @return
	 */
	public String read() {
		Long parentId = this.getParamByName("id", Long.class);
		Long roleId = this.getParamByName("roleId", Long.class);
		if (roleId == null || roleId.longValue() == 0) {
			result = resourceService.read(parentId);
		} else {
			result = resourceService.readByRole(parentId, roleId);
		}
		return JSON;
	}

	@javax.annotation.Resource
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@javax.annotation.Resource
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	@javax.annotation.Resource
	public void setResourcesUtils(ResourcesUtils resourcesUtils) {
		this.resourcesUtils = resourcesUtils;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

}
