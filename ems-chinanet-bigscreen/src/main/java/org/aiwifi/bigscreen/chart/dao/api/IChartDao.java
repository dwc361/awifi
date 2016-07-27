package org.aiwifi.bigscreen.chart.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.aiwifi.bigscreen.chart.entity.Chart;

public interface IChartDao extends IDaoSupport {
	Page page(Page page, Chart chart);
}