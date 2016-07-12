package com.zjhcsoft.uac.privilege.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.PageUtils;
import org.roof.web.resources.service.ResourceService;
import org.roof.web.role.dao.RoleDao;
import org.roof.web.role.entity.Roles;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.user.dao.SubUserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.authorization.resource.entity.App;
import com.zjhcsoft.uac.authorization.resource.entity.SysResource;

@Component(value = "uac_privilegeAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class PrivilegeAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private SubUserDao subUserDao;
	private SubUser subUser;
	private RoleDao roleDao;
	private ResourceService resourceService;

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		String url = super.getRequest().getRequestURI();
		List<org.roof.security.entity.Resource> list = resourceService
				.findModuleByPath(url.substring(url.lastIndexOf("/"),
						url.indexOf(".")));
		if (list.size() > 0) {
			super.addParameter("module", list.get(0));
		}
	}

	public String list() {
		Page page = createPage();
		SysResource sysResource = null;
		if (subUser == null) {
			subUser = new SubUser();
		}
		if (subUser.getSysResource() == null) {
			sysResource = new SysResource();
			subUser.setSysResource(sysResource);
		} else {
			sysResource = subUser.getSysResource();
		}
		sysResource.setId(App.SELF_ID);
		page = subUserDao.querySubUserPage(subUser, page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		this.addParameter("pageBar", pageBar);
		addParameter("page", page);
		loadCommonData();
		result = "/uac/privilege/privilege_list.jsp";
		return JSP;
	}

	public String update_page() {
		subUser = (SubUser) subUserDao.reload(subUser);
		List<Roles> roleses = roleDao.loadAll(Roles.class);
		this.addParameter("roleses", roleses);
		result = "/uac/privilege/privilege_update.jsp";
		return JSP;
	}

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Resource
	public void setSubUserDao(SubUserDao subUserDao) {
		this.subUserDao = subUserDao;
	}

	@Resource
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public SubUser getSubUser() {
		return subUser;
	}

	public void setSubUser(SubUser subUser) {
		this.subUser = subUser;
	}

}
