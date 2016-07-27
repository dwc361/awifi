package com.awifi.bigscreen.chart.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： e_chart <br/>
 *         描述：e_chart <br/>
 */
public class ChartVo extends Chart {

	private List<ChartVo> chartList;

	public ChartVo() {
		super();
	}

	public ChartVo(Long id) {
		super();
		this.id = id;
	}

	public List<ChartVo> getChartList() {
		return chartList;
	}

	public void setChartList(List<ChartVo> chartList) {
		this.chartList = chartList;
	}

}
