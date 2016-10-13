package com.awifi.bigscreen.bigscreen.entity;

import java.util.Locale;

/**
 * 大屏架构类型
 */
public enum BigscreenReTypeEnum {

	First("一层架构", "First", "一层架构", "The First Framework"),
	Second("二层架构", "Second", "二层架构", "The Second Framework"),
	Third("三层架构", "Third", "三层架构", "The Third Framework"),
	Forth("四层架构", "Forth", "四层架构", "The Forth Framework"),
	Fifth("五层架构", "Fifth", "五层架构", "The Fifth Framework"),
	Sixth("六层架构", "Sixth", "六层架构", "The Sixth Framework"),
	SevenTh("七层架构", "SevenTh", "七层架构", "The SevenTh Framework");

	private String display;
	private String code;
	private String zh_name;
	private String en_name;

	private BigscreenReTypeEnum(String display, String code, String zh_name, String en_name) {
		this.display = display;
		this.code = code;
		this.en_name = en_name;
		this.zh_name = zh_name;
	}

	public String getZh_name() {
		return zh_name;
	}

	public void setZh_name(String zh_name) {
		this.zh_name = zh_name;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public String getDisplay() {
		return display;
	}

	public String getCode() {
		return code;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// 中英文显示转换方法
	public static BigscreenReTypeEnum[] zh_en(BigscreenReTypeEnum[] obj, Locale locale) {
		if (locale != null) {
			if ("en".equals(locale.getLanguage())) {
				for (BigscreenReTypeEnum o : obj) {
					o.setDisplay(o.getEn_name());
				}
			} else {
				for (BigscreenReTypeEnum o : obj) {
					o.setDisplay(o.getZh_name());
				}
			}
		}
		return obj;
	}
}