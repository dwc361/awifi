package org.roof.web.role.action;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.PageUtils;
import org.roof.web.role.dao.RoleDao;
import org.roof.web.role.entity.Roles;
import org.roof.web.role.service.RoleService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("roleAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class RoleAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private Long currentPage;
	private Roles roles;
	private RoleService roleService;
	private RoleDao roleDao;
	private String selSrc;
	private String allSrc;

	public String list() {
		Page page = this.createPage();
		page = roleDao.list(page, roles);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		this.addParameter("page", page);
		this.addParameter("pageBar", pageBar);
		result = "/roof-web/web/role/role_list.jsp";
		return JSP;
	}

	public String list_select() {
		list();
		result = "/roof-web/web/role/role_list_select.jsp";
		return JSP;
	}

	public String create() {
		if (roles != null) {
			roles.setCreate_date(new Date());
			roleService.changeSrc(roles, allSrc, selSrc);
			roleDao.save(roles);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String create_page() {
		result = "/roof-web/web/role/role_create_page.jsp";
		return JSP;
	}

	public String update() {
		if (roles != null) {
			roleService.update(roles, allSrc, selSrc);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String update_page() {
		Long id = this.getParamByName("id", Long.class);
		roles = roleDao.load(Roles.class, id);
		result = "/roof-web/web/role/role_update_page.jsp";
		return JSP;
	}

	public String delete() {
		Long id = this.getParamByName("id", Long.class);
		Roles roles = new Roles();
		roles.setId(id);
		roleDao.delete(roles);
		result = new Result("删除成功!");
		return JSON;
	}

	public String detail() {

		return JSP;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public String getSelSrc() {
		return selSrc;
	}

	public void setSelSrc(String selSrc) {
		this.selSrc = selSrc;
	}

	public String getAllSrc() {
		return allSrc;
	}

	public void setAllSrc(String allSrc) {
		this.allSrc = allSrc;
	}

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}
