package org.roof.web.menu.action;

import javax.annotation.Resource;

import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.menu.dao.MenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuType;
import org.roof.web.menu.service.IMenuService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("menuAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class MenuAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private Menu menu;
	private MenuDao menuDao;
	private IMenuService menuService;

	public String index() {
		result = "/roof-web/web/menu/menu_index.jsp";
		return JSP;
	}

	public String read() {
		Long parentId = this.getParamByName("id", Long.class);
		result = menuService.read(parentId);
		menuService.read(parentId);
		return JSON;
	}

	public String detail() {
		Long id = this.getParamByName("id", Long.class);
		menu = menuDao.load(Menu.class, id);
		super.addParameter("menuTypes", MenuType.values());
		result = "/roof-web/web/menu/menu_detail.jsp";
		return JSP;
	}

	public String create() {
		Long parentId = this.getParamByName("parentId", Long.class);
		if (menu != null) {
			menuService.create(parentId, menu);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String create_page() {
		Long parentId = this.getParamByName("parentId", Long.class);
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		super.addParameter("parentId", parentId);
		super.addParameter("menuTypes", MenuType.values());
		result = "/roof-web/web/menu/menu_create_page.jsp";
		return JSP;
	}

	public String delete() {
		Long id = this.getParamByName("id", Long.class);
		menuService.delete(id);
		result = new Result("删除成功!");
		return JSON;
	}

	public String update() {
		if (menu != null) {
			if (menu.getModule() != null) {
				if (menu.getModule().getId() == null || menu.getModule().getId().longValue() == 0) {
					menu.setModule(null);
				}
			}
			menuDao.update(menu);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String moveTo() {
		Long parentId = this.getParamByName("parentId", Long.class);
		Long id = this.getParamByName("id", Long.class);
		Menu parent = menuDao.load(Menu.class, parentId);
		Menu menu = menuDao.load(Menu.class, id);
		menu.setParent(parent);
		if (menuDao.childrenCount(parent.getId()) == 0) {
			parent.setLeaf(true);
		} else {
			parent.setLeaf(false);
		}
		menuDao.update(parent);
		menuDao.update(menu);
		result = new Result("移动成功!");
		return JSON;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Resource
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Resource
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

}
