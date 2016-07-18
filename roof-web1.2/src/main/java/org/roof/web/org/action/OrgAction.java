package org.roof.web.org.action;

import javax.annotation.Resource;

import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.org.dao.OrgDao;
import org.roof.web.org.entity.Organization;
import org.roof.web.org.service.OrgService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("orgAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class OrgAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private OrgDao orgDao;
	private OrgService orgService;
	private Organization org;

	public String index() {
		result = "/roof-web/web/org/org_index.jsp";
		return JSP;
	}

	public String read() {
		Long parentId = this.getParamByName("id", Long.class);
		result = orgService.read(parentId);
		return JSON;
	}

	public String detail() {
		Long id = this.getParamByName("id", Long.class);
		org = orgDao.load(Organization.class, id);
		result = "/roof-web/web/org/org_detail.jsp";
		return JSP;
	}

	public String create() {
		Long parentId = this.getParamByName("parentId", Long.class);
		if (org != null) {
			orgService.create(parentId, org);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String create_page() {
		Long parentId = this.getParamByName("parentId", Long.class);
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		this.addParameter("parentId", parentId);
		result = "/roof-web/web/org/org_create_page.jsp";
		return JSP;
	}

	public String delete() {
		Long id = this.getParamByName("id", Long.class);
		orgService.delete(id);
		result = new Result("删除成功!");
		return JSON;
	}

	public String update() {
		if (org != null) {
			orgDao.updateIgnoreNull(org);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String query() {
		if (org != null) {
			result = orgDao.findByExample(org);
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	@Resource
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	@Resource
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

}
