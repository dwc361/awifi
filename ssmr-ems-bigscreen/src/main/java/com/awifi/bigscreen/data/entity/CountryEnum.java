package com.awifi.bigscreen.data.entity;

import java.util.Locale;

import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;

/**
 * 国家
 */
public enum CountryEnum {

	China("中国", "1", "中国", "China");

	private String display;
	private String code;
	private String zh_name;
	private String en_name;

	private CountryEnum(String display, String code, String zh_name, String en_name) {
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