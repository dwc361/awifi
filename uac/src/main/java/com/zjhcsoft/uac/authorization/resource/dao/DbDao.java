package com.zjhcsoft.uac.authorization.resource.dao;

import org.roof.dataaccess.RoofDaoSupport;
import com.zjhcsoft.uac.authorization.resource.entity.Db;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class DbDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryDbPage(Db paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "Db_exp_select_db_list",
				"Db_exp_select_db_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
