package org.roof.web.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.org.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrgDao extends RoofDaoSupport {

	public List<Organization> findOrgByParent(Long parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		if (parentId == null) {
			param.put("lvl", 0);
		}
		param.put("usable", true);
		@SuppressWarnings("unchecked")
		List<Organization> orgs = (List<Organization>) this.findByMappedQuery(
				"org.roof.web.org.dao.OrgDao.findOrgByParent", param);
		return orgs;
	}

	/**
	 * 逻辑删除
	 * 
	 * @param org
	 */
	public void disable(Organization org) {
		org.setUsable(false);
		update(org);
	}

	public Long childrenCount(Long parentId) {
		Long result = (Long) this.executeByMappedQuery(
				"org.roof.web.org.dao.OrgDao.childrenCount", parentId);
		return result;
	}

}
