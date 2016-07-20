package com.zjhcsoft.uac.bulletin.dao;

import java.util.List;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.bulletin.entity.Bulletin;

/**
 * 自动生成
 */
@Component
public class BulletinDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryBulletinPage(Bulletin paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "Bulletin_exp_select_bulletin_list",
				"Bulletin_exp_select_bulletin_count");
		return pageQuery.findByMappedQuery(paramObj);
	}

	public List<Bulletin> queryCountBulletin(Bulletin paramObj, int maxResults) {
		return (List<Bulletin>) super.findByMappedQuery("Bulletin_exp_select_bulletin_list", 0, maxResults, paramObj);
	}
}
