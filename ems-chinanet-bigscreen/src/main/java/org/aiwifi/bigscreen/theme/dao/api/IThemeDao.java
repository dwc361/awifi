package org.aiwifi.bigscreen.theme.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.aiwifi.bigscreen.theme.entity.Theme;

public interface IThemeDao extends IDaoSupport {
	Page page(Page page, Theme theme);
}