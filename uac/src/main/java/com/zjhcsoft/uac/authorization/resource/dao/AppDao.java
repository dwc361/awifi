package com.zjhcsoft.uac.authorization.resource.dao;

import java.util.List;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.authorization.resource.entity.App;

/**
 * 自动生成
 */
@Component
public class AppDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryAppPage(App paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "App_exp_select_app_list",
				"App_exp_select_app_count");
		return pageQuery.findByMappedQuery(paramObj);
	}

	@SuppressWarnings("unchecked")
	public List<App> findEnable() {
		return (List<App>) findByMappedQuery("com.zjhcsoft.uac.authorization.resource.dao.AppDao.findEnable");
	}
}
