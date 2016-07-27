package org.aiwifi.bigscreen.templates.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.aiwifi.bigscreen.templates.entity.Templates;

public interface ITemplatesDao extends IDaoSupport {
	Page page(Page page, Templates templates);
}