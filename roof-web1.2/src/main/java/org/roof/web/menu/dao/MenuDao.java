package org.roof.web.menu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuType;
import org.springframework.stereotype.Component;

@Component
public class MenuDao extends RoofDaoSupport {

	public List<Menu> findMenuByParent(Long parentId, MenuType menuType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		param.put("menuType", menuType);
		if (parentId == null) {
			param.put("lvl", 0);
		}
		@SuppressWarnings("unchecked")
		List<Menu> menus = (List<Menu>) this.findByMappedQuery("org.roof.web.menu.dao.MenuDao.findMenuByParent", param);
		return menus;
	}

	public Long childrenCount(Long parentId) {
		Long result = (Long) this.executeByMappedQuery("org.roof.web.menu.dao.MenuDao.childrenCount", parentId);
		return result;
	}

}
