package com.awifi.bigscreen.bigscreen.entity;

public class BigScreenHandlebarsVo {
	private String target_name;
	private String templete_name;
	private String json_param;
	private String chart_path;
	private Long chart_id;
	private Long theme_id;

	public BigScreenHandlebarsVo() {
	}

	public BigScreenHandlebarsVo(String target_name, String chart_path, String json_param) {
		this.target_name = target_name;
		this.chart_path = chart_path;
		this.json_param = json_param;
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	public String getTemplete_name() {
		return templete_name;
	}

	public void setTemplete_name(String templete_name) {
		this.templete_name = templete_name;
	}

	public String getJson_param() {
		return json_param;
	}

	public void setJson_param(String json_param) {
		this.json_param = json_param;
	}

	public Long getChart_id() {
		return chart_id;
	}

	public void setChart_id(Long chart_id) {
		this.chart_id = chart_id;
	}

	public Long getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(Long theme_id) {
		this.theme_id = theme_id;
	}

	public String getChart_path() {
		return chart_path;
	}

	public void setChart_path(String chart_path) {
		this.chart_path = chart_path;
	}
}
