package org.roof.web.role.dao;

import java.util.List;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.role.entity.Roles;
import org.springframework.stereotype.Component;

@Component
public class RoleDao extends RoofDaoSupport {
	public Page list(Page page, Roles params) {
		PageQuery pageQuery = new PageQuery(page,
				"org.roof.web.role.dao.RoleDao.list",
				"org.roof.web.role.dao.RoleDao.list_count");
		return pageQuery.findByMappedQuery(params);
	}

	public List<Roles> listVo(Roles params) {
		@SuppressWarnings("unchecked")
		List<Roles> rs = (List<Roles>) findByMappedQuery(
				"org.roof.web.role.dao.RoleDao.listVo", params);
		return rs;
	}
}
