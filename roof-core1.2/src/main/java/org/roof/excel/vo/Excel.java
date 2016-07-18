package org.roof.excel.vo;

public class Excel {

	private String xslcol;// excel列

	private String table;// 表名

	private String dbcol;// 数据库表名

	private Boolean unique;// 列数据是否唯一

	private String subcol;// 子列名

	private String parcol;// 父列名

	public String getDbcol() {
		return dbcol;
	}

	public void setDbcol(String dbCol) {
		this.dbcol = dbCol;
	}

	public String getParcol() {
		return parcol;
	}

	public void setParcol(String parCol) {
		this.parcol = parCol;
	}

	public String getSubcol() {
		return subcol;
	}

	public void setSubcol(String subCol) {
		this.subcol = subCol;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String tableName) {
		this.table = tableName;
	}

	public String getXslcol() {
		return xslcol;
	}

	public void setXslcol(String xslCol) {
		this.xslcol = xslCol;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

}
