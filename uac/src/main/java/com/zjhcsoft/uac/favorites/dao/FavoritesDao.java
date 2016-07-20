package com.zjhcsoft.uac.favorites.dao;

import java.util.List;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.favorites.entity.Favorites;

/**
 * 自动生成
 */
@Component
public class FavoritesDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryFavoritesPage(Favorites paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "Favorites_exp_select_favorites_list",
				"Favorites_exp_select_favorites_count");
		return pageQuery.findByMappedQuery(paramObj);
	}

	public List<Favorites> queryCountFavorites(Favorites paramObj, int maxResults) {
		return (List<Favorites>) super
				.findByMappedQuery("Favorites_exp_select_favorites_list", 0, maxResults, paramObj);
	}
}
