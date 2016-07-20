package com.zjhcsoft.uac.authorization.resource.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.authorization.resource.entity.Host;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class HostDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryHostPage(Host paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "Host_exp_select_host_list",
				"Host_exp_select_host_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
