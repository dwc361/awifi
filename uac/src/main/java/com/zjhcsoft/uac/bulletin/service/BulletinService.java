package com.zjhcsoft.uac.bulletin.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import com.zjhcsoft.uac.bulletin.entity.Bulletin;
import com.zjhcsoft.uac.bulletin.dao.BulletinDao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class BulletinService {

	private BulletinDao bulletinDao;

	/**
	 * 查询指定数量的公告
	 * 
	 * @param paramObj
	 * @param maxResults
	 * @return
	 */
	public List<Bulletin> queryCountBulletin(Bulletin paramObj, int maxResults) {
		return bulletinDao.queryCountBulletin(paramObj, maxResults);
	}

	/**
	 * 列表展示
	 */
	public Page queryBulletinPage(Bulletin paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new Bulletin();
		}
		return bulletinDao.queryBulletinPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public Bulletin save(Bulletin paramObj) throws Exception {
		bulletinDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public Bulletin delete(Bulletin paramObj) throws Exception {
		List<Bulletin> list = (List<Bulletin>) bulletinDao.findByMappedQuery("Bulletin_exp_select_bulletin_list",
				paramObj);
		for (Bulletin bulletin : list) {
			bulletinDao.delete(bulletin);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<Bulletin> select(Bulletin paramObj) {
		List<Bulletin> list = (List<Bulletin>) bulletinDao.findByMappedQuery("Bulletin_exp_select_bulletin_list",
				paramObj);
		return list;
	}

	/**
	 * 查询数据
	 */
	public Bulletin selectObject(Bulletin paramObj) {
		List<Bulletin> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new Bulletin();
		}
	}

	/**
	 * 修改数据
	 */
	public Bulletin update(Bulletin paramObj) throws Exception {
		bulletinDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public Bulletin updateIgnoreNull(Bulletin paramObj) throws Exception {
		bulletinDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public Bulletin load(Serializable id) throws Exception {
		Bulletin paramObj = (Bulletin) bulletinDao.load(Bulletin.class, id, false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<Bulletin> loadAll() throws Exception {
		return (List<Bulletin>) bulletinDao.loadAll(Bulletin.class);
	}

	@Resource
	public void setBulletinDao(BulletinDao bulletinDao) {
		this.bulletinDao = bulletinDao;
	}

}
