package org.roof.web.user.dao;

import java.util.List;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.log.SysLoginLog;
import org.roof.web.user.entity.Staff;
import org.springframework.stereotype.Component;

@Component
public class StaffDao extends RoofDaoSupport {

	public Long readUserCount(Staff staff) {
		Long l = (Long) this.executeByMappedQuery("org.roof.web.user.dao.StaffDao.readUserCount", staff);
		return l;
	}

	public List<Long> selectOrgIds(Long parId) {
		return (List<Long>) super.selectForList("org_exp_find_children_by_parent_id", parId);
	}

	public Page list(Page page, Staff params) {
		PageQuery pageQuery = new PageQuery(page, "org.roof.web.user.dao.StaffDao.list",
				"org.roof.web.user.dao.StaffDao.list_count");
		return pageQuery.findByMappedQuery(params);
	}

	public List<Staff> list(Staff params) {
		@SuppressWarnings("unchecked")
		List<Staff> staffs = (List<Staff>) this.findByMappedQuery("org.roof.web.user.dao.StaffDao.list", params);
		return staffs;
	}

	public SysLoginLog findLastedLog(SysLoginLog params) {
		List<SysLoginLog> list = (List<SysLoginLog>) this.findByMappedQuery(
				"org.roof.web.user.dao.StaffDao.findLastedLog", 0, 1, params);
		if (list == null || list.size() == 0) {
			return new SysLoginLog();
		}
		return list.get(0);
	}

}
