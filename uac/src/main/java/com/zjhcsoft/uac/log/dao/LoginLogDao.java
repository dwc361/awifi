package com.zjhcsoft.uac.log.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.log.entity.LoginLog;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class LoginLogDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryLoginLogPage(LoginLog paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "LoginLog_exp_select_loginLog_list",
				"LoginLog_exp_select_loginLog_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
