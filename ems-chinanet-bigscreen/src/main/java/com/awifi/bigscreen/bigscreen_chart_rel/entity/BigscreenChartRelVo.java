package com.awifi.bigscreen.bigscreen_chart_rel.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： e_bigscreen_chart_rel <br/>
 *         描述：e_bigscreen_chart_rel <br/>
 */
public class BigscreenChartRelVo extends BigscreenChartRel {

	private List<BigscreenChartRelVo> bigscreenChartRelList;

	public BigscreenChartRelVo() {
		super();
	}

	public BigscreenChartRelVo(Long id) {
		super();
		this.id = id;
	}

	public List<BigscreenChartRelVo> getBigscreenChartRelList() {
		return bigscreenChartRelList;
	}

	public void setBigscreenChartRelList(List<BigscreenChartRelVo> bigscreenChartRelList) {
		this.bigscreenChartRelList = bigscreenChartRelList;
	}

}
