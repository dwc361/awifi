package com.zjhcsoft.uac.authorization.resource.dao;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.authorization.resource.entity.System;

/**
 * 自动生成
 */
@Component
public class SystemDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page querySystemPage(System paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "System_exp_select_system_list",
				"System_exp_select_system_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
	
	public Long findSystemCount(System paramObj) {
		return (Long) executeByMappedQuery(
				"com.zjhcsoft.uac.account.System.dao.SystemDao.findUserCount",
				paramObj);
	}
}
