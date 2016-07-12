package com.zjhcsoft.uac.authorization.resource.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.authorization.resource.entity.NetSecurityDevice;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class NetSecurityDeviceDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryNetSecurityDevicePage(NetSecurityDevice paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "NetSecurityDevice_exp_select_netSecurityDevice_list",
				"NetSecurityDevice_exp_select_netSecurityDevice_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
