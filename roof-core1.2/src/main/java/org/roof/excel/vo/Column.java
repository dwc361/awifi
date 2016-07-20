package org.roof.excel.vo;

import com.zjhcsoft.xdm.annotation.Node;
import com.zjhcsoft.xdm.annotation.NodeType;
import com.zjhcsoft.xdm.annotation.Xml;
@Xml(name = "col")
public class Column {

	private String dbcol;
	private String xslcol;
	private String title;
	private Boolean unique;// 列数据是否唯一

	public Column() {
	}

	public Column(String dbcol, String xslcol, String title) {
		super();
		this.dbcol = dbcol;
		this.xslcol = xslcol;
		this.title = title;
	}

	@Node(type = NodeType.ATTR, name = "dbcol")
	public String getDbcol() {
		return dbcol;
	}

	@Node(type = NodeType.ATTR, name = "xslcol")
	public String getXslcol() {
		return xslcol;
	}

	@Node(type = NodeType.ATTR, name = "title")
	public String getTitle() {
		return title;
	}

	public void setDbcol(String dbcol) {
		this.dbcol = dbcol;
	}

	public void setXslcol(String xslcol) {
		this.xslcol = xslcol;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

}
