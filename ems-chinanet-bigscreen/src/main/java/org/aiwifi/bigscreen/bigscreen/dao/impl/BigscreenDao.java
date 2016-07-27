package org.aiwifi.bigscreen.bigscreen.dao.impl;

import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.aiwifi.bigscreen.bigscreen.dao.api.IBigscreenDao;
import org.aiwifi.bigscreen.bigscreen.entity.Bigscreen;
@Service
public class BigscreenDao extends AbstractDao implements IBigscreenDao {
	
	private PageQueryFactory<PageQuery> pageQueryFactory;
	
	public Page page(Page page, Bigscreen bigscreen) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectBigscreenPage", "selectBigscreenCount");
		//IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectBigscreenPage", null);
		return pageQuery.select(bigscreen);
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