package com.awifi.bigscreen.chart.service.impl;

import java.io.Serializable;
import java.util.List;
import org.roof.roof.dataaccess.api.Page;
import com.awifi.bigscreen.chart.dao.api.IChartDao;
import com.awifi.bigscreen.chart.entity.Chart;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.chart.service.api.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChartService implements IChartService {
	private IChartDao chartDao;

	public Serializable save(Chart chart){
		return chartDao.save(chart);
	}

	public void delete(Chart chart){
		chartDao.delete(chart);
	}
	
	public void deleteByExample(Chart chart){
		chartDao.deleteByExample(chart);
	}

	public void update(Chart chart){
		chartDao.update(chart);
	}
	
	public void updateIgnoreNull(Chart chart){
		chartDao.updateIgnoreNull(chart);
	}
		
	public void updateByExample(Chart chart){
		chartDao.update("updateByExampleChart", chart);
	}

	public ChartVo load(Chart chart){
		return (ChartVo)chartDao.reload(chart);
	}
	
	public ChartVo selectForObject(Chart chart){
		return (ChartVo)chartDao.selectForObject("selectChart",chart);
	}
	
	public List<ChartVo> selectForList(Chart chart){
		return (List<ChartVo>)chartDao.selectForList("selectChart",chart);
	}
	
	public Page page(Page page, Chart chart) {
		return chartDao.page(page, chart);
	}

	@Autowired
	public void setIChartDao(
			@Qualifier("chartDao") IChartDao  chartDao) {
		this.chartDao = chartDao;
	}
	

}
