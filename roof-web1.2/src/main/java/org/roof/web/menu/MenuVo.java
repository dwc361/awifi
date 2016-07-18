package org.roof.web.menu;

import org.roof.web.ZTreeNode;

public class MenuVo extends ZTreeNode {

	public MenuVo() {
		super();
	}

	public MenuVo(String name, String url, String icon) {
		super("", name, "", url, icon);
	}

	public MenuVo(String id, String name, String title, String url, String icon) {
		super(id, name, title, url, icon);
	}

}
