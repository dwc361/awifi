package com.awifi.bigscreen.data.entity;

import java.util.Locale;

import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;

/**
 * 设备类型
 */
public enum DeviceTypeEnum {

	BRAS("NAS", "11", "NAS", "BRAS"),
	AC("NAS", "21", "NAS", "AC"),
	FATAP("胖AP", "31", "胖AP", "FATAP"),
	GPON("光猫", "32", "光猫", "GPON"),
	GPON_W("光猫", "33", "光猫", "GPON_W"),
	EPON("光猫", "34", "光猫", "EPON"),
	EPON_W("光猫", "35", "光猫", "EPON_W"),
	TwoIN1("融合终端", "36", "融合终端", "2IN1"),
	ThreeIN1("融合终端", "37", "融合终端", "3IN1"),
	PRO_FITAP("瘦AP", "42", "瘦AP", "PRO_FITAP"),
	HOT_FITAP("瘦AP", "43", "瘦AP", "HOT_FITAP"),
	FITAP("瘦AP", "41", "瘦AP", "FITAP");

	private String display;
	private String code;
	private String zh_name;
	private String en_name;

	private DeviceTypeEnum(String display, String code, String zh_name, String en_name) {
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