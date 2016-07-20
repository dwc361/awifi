package com.zjhcsoft.uac.authorization.resource.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.authorization.resource.entity.NetDevice;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class NetDeviceDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryNetDevicePage(NetDevice paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "NetDevice_exp_select_netDevice_list",
				"NetDevice_exp_select_netDevice_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
