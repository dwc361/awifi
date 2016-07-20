package org.roof.web.resources.action;

import java.util.Iterator;

import javax.annotation.Resource;

import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.resources.SqlReplacement;
import org.roof.web.resources.dao.ResourceDao;
import org.roof.web.resources.entity.QueryResource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liuxin
 * 
 */
@Component("resource_queryAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ResourceQueryAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private QueryResource privilege;
	private ResourceDao resourceDao;
	private SqlReplacement sqlReplacement;

	public String update() {
		if (privilege != null) {
			resourceDao.updateIgnoreNull(privilege);
		}
		result = new Result("保存成功!");
		return JSON;
	}

	public String statementSelectPage() {
		@SuppressWarnings("rawtypes")
		Iterator mappedStatementNames = sqlReplacement.getMapClientImpl().delegate
				.getMappedStatementNames();
		addParameter("mappedStatementNames", mappedStatementNames);
		result = "/roof-web/web/resources/resource_mapped_statement_selected.jsp";
		return JSP;
	}

	public QueryResource getPrivilege() {
		return privilege;
	}

	public void setPrivilege(QueryResource privilege) {
		this.privilege = privilege;
	}

	@Resource
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	@Resource
	public void setSqlReplacement(SqlReplacement sqlReplacement) {
		this.sqlReplacement = sqlReplacement;
	}
}
