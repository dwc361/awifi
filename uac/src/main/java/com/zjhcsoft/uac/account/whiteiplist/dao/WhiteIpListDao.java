package com.zjhcsoft.uac.account.whiteiplist.dao;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.whiteiplist.entity.WhiteIpList;

/**
 * 自动生成
 */
@Component
public class WhiteIpListDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryWhiteIpListPage(WhiteIpList paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "WhiteList_exp_select_whiteipList_list",
				"WhiteList_exp_select_whiteipList_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
