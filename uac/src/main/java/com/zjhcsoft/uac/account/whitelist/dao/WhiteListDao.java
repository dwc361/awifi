package com.zjhcsoft.uac.account.whitelist.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.account.whitelist.entity.WhiteList;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class WhiteListDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryWhiteListPage(WhiteList paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "WhiteList_exp_select_whiteList_list",
				"WhiteList_exp_select_whiteList_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
