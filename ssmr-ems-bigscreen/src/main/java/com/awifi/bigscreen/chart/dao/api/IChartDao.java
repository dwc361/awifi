package com.awifi.bigscreen.chart.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import com.awifi.bigscreen.chart.entity.Chart;

public interface IChartDao extends IDaoSupport {
	Page page(Page page, Chart chart);
}