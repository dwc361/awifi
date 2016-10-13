package com.awifi.bigscreen.word_fileinfo.impl.dao.impl;

import com.awifi.bigscreen.word_fileinfo.api.dao.api.IWordFileinfoDao;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfo;
import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class WordFileinfoDao extends AbstractDao implements IWordFileinfoDao {
	private PageQueryFactory<PageQuery> pageQueryFactory;
	
	public Page page(Page page, WordFileinfo wordFileinfo) {
		
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectWordFileinfoPage", "selectWordFileinfoCount");
		//IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectWordFileinfoPage", null);
		return pageQuery.select(wordFileinfo);
	}

	@Autowired
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
	
	@Autowired
	public void setPageQueryFactory(
			@Qualifier("pageQueryFactory") PageQueryFactory<PageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

}