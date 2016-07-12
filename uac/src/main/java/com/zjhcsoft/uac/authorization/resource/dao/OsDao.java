package com.zjhcsoft.uac.authorization.resource.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.authorization.resource.entity.Os;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class OsDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryOsPage(Os paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "Os_exp_select_os_list",
				"Os_exp_select_os_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
