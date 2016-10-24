package com.awifi.bigscreen.data.entity;

import java.util.Locale;

import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;

/**
 * 省份
 */
public enum ProvinceEnum {

	BeiJing("北京", "2", "北京", "BeiJing"),
	AnHui("安徽", "3", "安徽", "AnHui"),
	FuJian("福建", "4", "福建", "FuJian"),
	GanSu("甘肃", "5", "甘肃", "GanSu"),
	GuangDong("广东", "6", "广东", "GuangDong"),
	GuangXi("广西", "7", "广西", "GuangXi"),
	GuiZhou("贵州", "8", "贵州", "GuiZhou"),
	HaiNan("海南", "9", "海南", "HaiNan"),
	HeBei("河北", "10", "河北", "HeBei"),
	HeNan("河南", "11", "河南", "HeNan"),
	HeiLongJiang("黑龙江", "12", "黑龙江", "HeiLongJiang"),
	HuBei("湖北", "13", "湖北", "HuBei"),
	HuNan("湖南", "14", "湖南", "HuNan"),
	JiLin("吉林", "15", "吉林", "JiLin"),
	JiangSu("江苏", "16", "江苏", "JiangSu"),
	JiangXi("江西", "17", "江西", "JiangXi"),
	LiaoNing("辽宁", "18", "辽宁", "LiaoNing"),
	NeiMengGu("内蒙古", "19", "内蒙古", "NeiMengGu"),
	NingXia("宁夏", "20", "宁夏", "NingXia"),
	QingHai("青海", "21", "青海", "QingHai"),
	ShangDong("山东", "22", "山东", "ShangDong"),
	ShanXi("山西", "23", "山西", "ShanXi"),
	ShaAnXi("陕西", "24", "陕西", "ShaAnXi"),
	ShangHai("上海", "25", "上海", "ShangHai"),
	SiChuan("四川", "26", "四川", "SiChuan"),
	TianJin("天津", "27", "天津", "TianJin"),
	XiZang("西藏", "28", "西藏", "XiZang"),
	XinJiang("新疆", "29", "新疆", "XinJiang"),
	YunNan("云南", "30", "云南", "YunNan"),
	ZheJiang("浙江", "31", "浙江", "ZheJiang"),
	ChongQing("重庆", "32", "重庆", "ChongQing"),
	XiangGang("香港", "33", "香港", "XiangGang"),
	AaoMen("澳门", "34", "澳门", "AaoMen"),
	TaiWan("台湾", "35", "台湾", "TaiWan");

	private String display;
	private String code;
	private String zh_name;
	private String en_name;

	private ProvinceEnum(String display, String code, String zh_name, String en_name) {
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