package org.aiwifi.bigscreen.bigscreen.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.aiwifi.bigscreen.bigscreen.entity.Bigscreen;

public interface IBigscreenDao extends IDaoSupport {
	Page page(Page page, Bigscreen bigscreen);
}