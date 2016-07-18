package org.roof.web.menu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.web.menu.MenuVo;
import org.roof.web.menu.dao.MenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.resources.entity.Module;
import org.springframework.stereotype.Component;

@Component
public class MenuService implements IMenuService {
	private MenuDao menuDao;

	/* (non-Javadoc)
	 * @see org.roof.web.menu.service.IMenuService#selectByModule(java.util.List)
	 */
	@Override
	public Menu selectByModule(List list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modules", list);
		Menu m = new Menu();
		List<Menu> menus = (List<Menu>) menuDao.findByMappedQuery(
				"org.roof.web.menu.service.MenuService.selectByModule", map);
		if (menus.size() > 0) {
			m = menus.get(0);
		}
		return m;
	}

	/* (non-Javadoc)
	 * @see org.roof.web.menu.service.IMenuService#read(java.lang.Long)
	 */
	@Override
	public List<MenuVo> read(Long parentId) {
		List<Menu> menus = menuDao.findMenuByParent(parentId, null);
		List<MenuVo> result = new ArrayList<MenuVo>();
		for (Menu menu : menus) {
			MenuVo menuVo = new MenuVo();
			copyProperties(menu, menuVo);
			result.add(menuVo);
		}
		return result;
	}

	private void copyProperties(Menu menu, MenuVo menuVo) {
		menuVo.setId(menu.getId().toString());
		menuVo.setName(menu.getName());
		menuVo.setLeaf(menu.getLeaf());
		Module module = menu.getModule();
		menuVo.setTitle((module == null) ? menu.getName() : module.getPath());
	}

	/* (non-Javadoc)
	 * @see org.roof.web.menu.service.IMenuService#create(java.lang.Long, org.roof.web.menu.entity.Menu)
	 */
	@Override
	public Menu create(Long parentId, Menu menu) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		if (menu.getModule() != null && menu.getModule().getId() == null) {
			menu.setModule(null);
		}
		Menu parent = menuDao.load(Menu.class, parentId);
		if (parent.getLeaf() != null && parent.getLeaf()) {
			parent.setLeaf(false);
			menuDao.update(parent);
		}
		menu.setParent(parent);
		menu.setLvl(parent.getLvl() + 1);
		menu.setLeaf(true);
		menuDao.save(menu);
		return menu;
	}

	/* (non-Javadoc)
	 * @see org.roof.web.menu.service.IMenuService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		Menu menu = menuDao.load(Menu.class, id);
		Menu parent = (Menu) menu.getParent();
		Long count = menuDao.childrenCount(parent.getId());
		if (count == 1) {
			parent.setLeaf(true);
			menuDao.update(parent);
		}
		menu.setId(id);
		menuDao.delete(menu);
	}

	/* (non-Javadoc)
	 * @see org.roof.web.menu.service.IMenuService#setMenuDao(org.roof.web.menu.dao.MenuDao)
	 */
	@Override
	@Resource
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

}
