package com.zjhcsoft.uac.favorites.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.favorites.dao.FavoritesDao;
import com.zjhcsoft.uac.favorites.entity.Favorites;

/**
 * 自动生成
 */
@Component
public class FavoritesService {

	private FavoritesDao favoritesDao;

	/**
	 * 查询指定数量的收藏夹
	 * 
	 * @param paramObj
	 * @param maxResults
	 * @return
	 */
	public List<Favorites> queryCountFavorites(Favorites paramObj, int maxResults) {
		return favoritesDao.queryCountFavorites(paramObj, maxResults);
	}

	/**
	 * 列表展示
	 */
	public Page queryFavoritesPage(Favorites paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new Favorites();
		}
		return favoritesDao.queryFavoritesPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public Favorites save(Favorites paramObj) throws Exception {
		favoritesDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public Favorites delete(Favorites paramObj) throws Exception {
		List<Favorites> list = (List<Favorites>) favoritesDao.findByMappedQuery("Favorites_exp_select_favorites_list",
				paramObj);
		for (Favorites favorites : list) {
			favoritesDao.delete(favorites);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<Favorites> select(Favorites paramObj) {
		List<Favorites> list = (List<Favorites>) favoritesDao.findByMappedQuery("Favorites_exp_select_favorites_list",
				paramObj);
		return list;
	}

	/**
	 * 查询数据
	 */
	public Favorites selectObject(Favorites paramObj) {
		List<Favorites> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new Favorites();
		}
	}

	/**
	 * 修改数据
	 */
	public Favorites update(Favorites paramObj) throws Exception {
		favoritesDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public Favorites updateIgnoreNull(Favorites paramObj) throws Exception {
		favoritesDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public Favorites load(Serializable id) throws Exception {
		Favorites paramObj = (Favorites) favoritesDao.load(Favorites.class, id, false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<Favorites> loadAll() throws Exception {
		return (List<Favorites>) favoritesDao.loadAll(Favorites.class);
	}

	@Resource
	public void setFavoritesDao(FavoritesDao favoritesDao) {
		this.favoritesDao = favoritesDao;
	}

}
