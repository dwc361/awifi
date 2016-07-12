package com.zjhcsoft.uac.authorization.resource.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.zjhcsoft.uac.account.user.dao.SubUserDao;
import com.zjhcsoft.uac.authorization.resource.dao.AppDao;
import com.zjhcsoft.uac.authorization.resource.entity.App;
import com.zjhcsoft.uac.ldap.util.LdapUtils;

/**
 * 自动生成
 */
@Component
public class AppService {

	private AppDao appDao;
	private AntPathMatcher antPathMatcher = new AntPathMatcher();


	/**
	 * 列表展示
	 */
	public Page queryAppPage(App paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new App();
		}
		return appDao.queryAppPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public App save(App paramObj) throws Exception {
		appDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public App delete(App paramObj) throws Exception {
		List<App> list = (List<App>) appDao.findByMappedQuery(
				"App_exp_select_app_list", paramObj);
		for (App app : list) {
			appDao.delete(app);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<App> select(App paramObj) {
		List<App> list = (List<App>) appDao.findByMappedQuery(
				"App_exp_select_app_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public List<App> selectMyself(Long staffId) {
		List<App> list = (List<App>) appDao.selectForList("App_exp_select_resource_by_staff", staffId);
		return list;
	}

	/**
	 * 查询数据
	 */
	public App selectObject(App paramObj) {
		List<App> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new App();
		}
	}

	/**
	 * 修改数据
	 */
	public App update(App paramObj) throws Exception {
		appDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public App updateIgnoreNull(App paramObj) throws Exception {
		appDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public App load(Serializable id) throws Exception {
		App paramObj = (App) appDao.load(App.class, id, false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<App> loadAll() throws Exception {
		return (List<App>) appDao.loadAll(App.class);
	}

	public App findByService(String service) {
		List<App> apps = appDao.findEnable();
		for (App app : apps) {
			if (antPathMatcher.match(app.getExpression(), service)) {
				return app;
			}
		}
		return null;
	}

	@Resource
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}
	

}
