package com.awifi.bigscreen.templates.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import com.awifi.bigscreen.templates.entity.Templates;

public interface ITemplatesDao extends IDaoSupport {
	Page page(Page page, Templates templates);
}