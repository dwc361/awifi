package org.roof.excel.vo;

/**
 * 实体配置类
 */
public class Field {

	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 字段类型
	 */
	private String fieldType;
	
	/**
	 * 数据库类型
	 */
	private String dbType;
	
	/**
	 * 字段显示
	 */
	private String fieldDisplay;
	
	/**
	 * 属性句柄
	 */
	private String stateMent;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getStateMent() {
		return stateMent;
	}

	public void setStateMent(String stateMent) {
		this.stateMent = stateMent;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldDisplay() {
		return fieldDisplay;
	}

	public void setFieldDisplay(String fieldDisplay) {
		this.fieldDisplay = fieldDisplay;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}


}
