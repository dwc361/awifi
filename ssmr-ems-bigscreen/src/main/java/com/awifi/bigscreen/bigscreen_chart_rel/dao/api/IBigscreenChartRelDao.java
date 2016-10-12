package com.awifi.bigscreen.bigscreen_chart_rel.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRel;

public interface IBigscreenChartRelDao extends IDaoSupport {
	Page page(Page page, BigscreenChartRel bigscreenChartRel);
}