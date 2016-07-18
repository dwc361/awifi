package org.roof.web.menu.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.roof.web.menu.dao.MenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.resources.entity.Module;
import org.roof.web.user.entity.Staff;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * 
 * @author liuxin
 * 
 */
@Component
public class MenuFilter {

	private SecurityMetadataSource securityMetadataSource;
	private AccessDecisionManager accessDecisionManager;
	private MenuDao menuDao;

	public Menu doFilter(Menu menu) {
		List<Menu> menus = doFilter(menuDao.findMenuByParent(menu.getId(), menu.getMenuType()));
		if (menus != null) {
			for (Menu menu2 : menus) {
				doFilter(menu2);
			}
			menu.setChildren(menus);
		}
		if (CollectionUtils.isEmpty(menu.getChildren()) && menu.getModule() == null) {
			return null;
		}
		return menu;
	}

	public List<Menu> doFilter(List<Menu> menus) {
		List<Menu> result = new ArrayList<Menu>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		for (Menu menu : menus) {
			Module module = menu.getModule();
			if (module == null) {
				result.add(menu);
				continue;
			}
			try {
				String path = menu.getModule().getPath();
				Collection<ConfigAttribute> configAttributes = securityMetadataSource.getAttributes(path);
				accessDecisionManager.decide(authentication, path, configAttributes);
				result.add(menu);
			} catch (InsufficientAuthenticationException e) {
			} catch (AccessDeniedException e) {
			}
		}
		return result;
	}

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	public List<Menu> filterResource(List<Menu> menus, Staff staff) {
		List<org.roof.security.entity.Resource> r = (List<org.roof.security.entity.Resource>) menuDao.selectForList(
				"staff_exp_select_resource_by_staff", staff.getId());
		List<Menu> result = new ArrayList<Menu>();
		for (Menu menuVo : menus) {
			String url = menuVo.getModule().getPath();
			for (org.roof.security.entity.Resource resource : r) {
				String pattern = resource.getPattern();
				if (pathMatcher.match(pattern, url)) {
					result.add(menuVo);
					break;
				}
			}
		}
		return result;
	}

	@Resource(name = "securityMetadataSource")
	public void setSecurityMetadataSource(SecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	@Resource(name = "accessDecisionManager")
	public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
		this.accessDecisionManager = accessDecisionManager;
	}

	@Resource
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}

}
