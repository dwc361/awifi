package org.roof.web.dictionary;

import org.roof.web.ZTreeNode;

public class DictionaryVo extends ZTreeNode {
	private String val;
	private String description; // 描述

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
