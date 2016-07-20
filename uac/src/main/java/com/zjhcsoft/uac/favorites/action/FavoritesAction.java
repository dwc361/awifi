package com.zjhcsoft.uac.favorites.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.roof.exceptions.ApplicationException;
import org.roof.security.BaseUserContext;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.roof.web.user.entity.Staff;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.favorites.entity.Favorites;
import com.zjhcsoft.uac.favorites.service.FavoritesService;

/**
 * 自动生成模版
 */
@Component("uac_favoritesAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class FavoritesAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private FavoritesService favoritesService;

	private Favorites favorites;

	private List<Favorites> favoritesList;

	private DictionaryService dictionaryService;

	private ResourceService resourceService;

	private Long currentPage;

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		String url = super.getRequest().getRequestURI();
		List<org.roof.security.entity.Resource> list = resourceService.findModuleByPath(url.substring(
				url.lastIndexOf("/"), url.indexOf(".")));
		if (list.size() > 0) {
			super.addParameter("module", list.get(0));
		}
		// (Staff)BaseUserContext.getCurrentUser();// 得到当前用户
		// List<Dictionary> demoList = dictionaryService.findByType("字典表类型");
		// super.addParameter("demoList", demoList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		if (favorites == null) {
			favorites = new Favorites();
		}
		favorites.setStaff((Staff) BaseUserContext.getCurrentUser());
		page = favoritesService.queryFavoritesPage(favorites, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/favorites/favorites_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		favorites = new Favorites();
		super.addParameter("favorites_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/favorites/favorites_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		favorites = favoritesService.load(favorites.getId());
		super.addParameter("favorites", favorites);
		super.addParameter("favorites_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/favorites/favorites_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		favorites = favoritesService.load(favorites.getId());
		super.addParameter("favorites", favorites);
		super.addParameter("favorites_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/favorites/favorites_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = favoritesService.load(favorites.getId());
		return JSON;
	}

	/**
	 * 删除实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String delete() throws ApplicationException {
		try {
			favoritesService.delete(favorites);
			result = new Result("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00002");
		}
		return JSON;
	}

	/**
	 * 保存实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String create() throws ApplicationException {
		try {
			if (favorites == null) {
				favorites = new Favorites();
			}
			favorites.setStaff((Staff) BaseUserContext.getCurrentUser());
			if (favoritesService.select(favorites).size() > 0) {
				result = new Result(Result.FAIL, "该地址账号已经收藏!");
				return JSON;
			}
			favoritesService.save(favorites);
			result = new Result("收藏成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	/**
	 * 修改实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String update() throws ApplicationException {
		try {
			if (favorites == null) {
				favorites = new Favorites();
			}
			favoritesService.updateIgnoreNull(favorites);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	@Resource
	public void setFavoritesService(FavoritesService favoritesService) {
		this.favoritesService = favoritesService;
	}

	public FavoritesService getFavoritesService() {
		return favoritesService;
	}

	public Favorites getFavorites() {
		return favorites;
	}

	public void setFavorites(Favorites favorites) {
		this.favorites = favorites;
	}

	public List<Favorites> getFavoritesList() {
		return favoritesList;
	}

	public void setFavoritesList(List<Favorites> favoritesList) {
		this.favoritesList = favoritesList;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@Resource
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
}