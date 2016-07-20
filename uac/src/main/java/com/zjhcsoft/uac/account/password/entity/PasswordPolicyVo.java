package com.zjhcsoft.uac.account.password.entity;

public class PasswordPolicyVo {
	private String name;// 英文表示
	private String val;// 判断值
	private String message;// 提示

	private String unit;// 单位
	private String nameCn;// 中文名
	private String expression; // 表达式

	public PasswordPolicyVo(String name, String val, String message,
			String unit, String nameCn, String expression) {
		super();
		this.name = name;
		this.val = val;
		this.message = message;
		this.unit = unit;
		this.nameCn = nameCn;
		this.expression = expression;
	}

	public String getName() {
		return name;
	}

	public String getVal() {
		return val;
	}

	public String getMessage() {
		return message;
	}

	public String getUnit() {
		return unit;
	}

	public String getNameCn() {
		return nameCn;
	}

	public String getExpression() {
		return expression;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
