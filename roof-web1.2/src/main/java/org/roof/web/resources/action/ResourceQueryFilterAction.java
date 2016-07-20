package org.roof.web.resources.action;

import javax.annotation.Resource;

import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.resources.dao.ResourceDao;
import org.roof.web.resources.entity.QueryFilterResource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liuxin
 * 
 */
@Component("resource_query_filterAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ResourceQueryFilterAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private ResourceDao resourceDao;

	private QueryFilterResource privilege;

	public String update() {
		if (privilege != null) {
			resourceDao.updateIgnoreNull(privilege);
		}
		result = new Result("保存成功!");
		return JSON;
	}

	public QueryFilterResource getPrivilege() {
		return privilege;
	}

	public void setPrivilege(QueryFilterResource privilege) {
		this.privilege = privilege;
	}

	@Resource
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
}
