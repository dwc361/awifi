package org.roof.web.menu.service;

import java.util.List;

import javax.annotation.Resource;

import org.roof.web.menu.MenuVo;
import org.roof.web.menu.dao.MenuDao;
import org.roof.web.menu.entity.Menu;

public interface IMenuService {

	public abstract Menu selectByModule(List list);

	public abstract List<MenuVo> read(Long parentId);

	/**
	 * 创建一个菜单
	 * 
	 * @return
	 */
	public abstract Menu create(Long parentId, Menu menu);

	/**
	 * 删除一个菜单
	 * 
	 * @param id
	 */
	public abstract void delete(Long id);

	@Resource
	public abstract void setMenuDao(MenuDao menuDao);

}