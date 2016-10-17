package com.awifi.bigscreen.data.entity;

import java.util.Locale;

import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;

/**
 * 大屏架构类型
 */
public enum ProvinceEnum {

	BeiJing("北京", "2", "一层架构", "The First Framework"),
	AnHui("安徽", "3", "二层架构", "The Second Framework"),
	FuJian("福建", "4", "三层架构", "The Third Framework"),
	GanSu("甘肃", "5", "四层架构", "The Forth Framework"),
	GuangDong("广东", "6", "五层架构", "The Fifth Framework"),
	GuangXi("广西", "7", "六层架构", "The Sixth Framework"),
	GuiZhou("贵州", "8", "七层架构", "The SevenTh Framework"),
	HaiNan("海南", "9", "", ""),
	HeBei("河北", "10", "", ""),
	HeNan("河南", "11", "", ""),
	HeiLongJiang("黑龙江", "12", "", ""),
	HuBei("湖北", "13", "", ""),
	HuNan("湖南", "14", "", ""),
	JiLin("吉林", "15", "", ""),
	JiangSu("江苏", "16", "", ""),
	JiangXi("江西", "17", "", ""),
	LiaoNing("辽宁", "18", "", ""),
	NeiMengGu("内蒙古", "19", "", ""),
	NingXia("宁夏", "20", "", ""),
	QingHai("青海", "21", "", ""),
	ShangDong("山东", "22", "", ""),
	ShanXi("山西", "23", "", ""),
	ShaAnXi("陕西", "24", "", ""),
	ShangHai("上海", "25", "", ""),
	SiChuan("四川", "26", "", ""),
	TianJin("天津", "27", "", ""),
	XiZang("西藏", "28", "", ""),
	XinJiang("新疆", "29", "", ""),
	YunNan("云南", "30", "", ""),
	ZheJiang("浙江", "31", "", ""),
	ChongQing("重庆", "32", "", ""),
	XiangGang("香港", "33", "", ""),
	AaoMen("澳门", "34", "", ""),
	TaiWan("台湾", "35", "", "");

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