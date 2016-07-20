package com.zjhcsoft.uac.log.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.log.entity.AccountLog;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class AccountLogDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryAccountLogPage(AccountLog paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "AccountLog_exp_select_accountLog_list",
				"AccountLog_exp_select_accountLog_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
