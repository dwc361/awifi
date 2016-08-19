package com.awifi.bigscreen.bigscreen_chart_rel.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 模版生成 <br/>
 *         表名： e_bigscreen_chart_rel <br/>
 *         描述：e_bigscreen_chart_rel <br/>
 */
public class BigscreenChartRel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6982884204476281180L;
	// 需要手动添加非默认的serialVersionUID
	protected Long id;// id
	protected Long chart_id;// chart_id
	protected Long screen_id;// screen_id
	protected String target;// target
	protected String chart_path;
	protected String chart_icon;
	

	public BigscreenChartRel() {
		super();
	}
	public BigscreenChartRel(Long id) {
		super();
		this.id = id;
	}

	public BigscreenChartRel(Long chart_id, Long screen_id, String target) {
		super();
		this.chart_id = chart_id;
		this.screen_id = screen_id;
		this.target = target;
	}

	@Id // 主键
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChart_id() {
		return chart_id;
	}

	public void setChart_id(Long chart_id) {
		this.chart_id = chart_id;
	}

	public Long getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(Long screen_id) {
		this.screen_id = screen_id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getChart_path() {
		return chart_path;
	}

	public void setChart_path(String chart_path) {
		this.chart_path = chart_path;
	}

	public String getChart_icon() {
		return chart_icon;
	}

	public void setChart_icon(String chart_icon) {
		this.chart_icon = chart_icon;
	}
}
