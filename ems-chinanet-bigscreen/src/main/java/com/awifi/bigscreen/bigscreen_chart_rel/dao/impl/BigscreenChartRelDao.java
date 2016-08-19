package com.awifi.bigscreen.bigscreen_chart_rel.dao.impl;

import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.bigscreen_chart_rel.dao.api.IBigscreenChartRelDao;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRel;
@Service
public class BigscreenChartRelDao extends AbstractDao implements IBigscreenChartRelDao {
	
	private PageQueryFactory<PageQuery> pageQueryFactory;
	
	public Page page(Page page, BigscreenChartRel bigscreenChartRel) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectBigscreenChartRelPage", "selectBigscreenChartRelCount");
		//IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectBigscreenChartRelPage", null);
		return pageQuery.select(bigscreenChartRel);
	}
	
	@Autowired
	public void setPageQueryFactory(
			@Qualifier("pageQueryFactory") PageQueryFactory<PageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}
	
	@Autowired
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}