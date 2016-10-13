package com.awifi.bigscreen.word_fileinfo.api.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfo;

public interface IWordFileinfoDao extends IDaoSupport {
	Page page(Page page, WordFileinfo wordFileinfo);
}