package com.zjhcsoft.uac.authorization.resource.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import com.zjhcsoft.uac.authorization.resource.entity.Db;
import com.zjhcsoft.uac.authorization.resource.dao.DbDao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class DbService {

	private DbDao dbDao;

	/**
	 * 列表展示
	 */
	public Page queryDbPage(Db paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new Db();
		}
		return dbDao.queryDbPage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public Db save(Db paramObj) throws Exception{
		dbDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public Db delete(Db paramObj) throws Exception{
		List<Db> list = (List<Db>) dbDao.findByMappedQuery("Db_exp_select_db_list", paramObj);
		for (Db db : list) {
			dbDao.delete(db);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<Db> select(Db paramObj) {
		List<Db> list = (List<Db>) dbDao.findByMappedQuery("Db_exp_select_db_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public Db selectObject(Db paramObj) {
		List<Db> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new Db();
		}
	}
	
	/**
	 * 修改数据
	 */
	public Db update(Db paramObj) throws Exception{
		dbDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public Db updateIgnoreNull(Db paramObj) throws Exception{
		dbDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public Db load(Serializable id) throws Exception{
		Db paramObj = (Db) dbDao.load(Db.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<Db> loadAll() throws Exception{
		return (List<Db>) dbDao.loadAll(Db.class);
	}

	@Resource
	public void setDbDao(DbDao dbDao) {
		this.dbDao = dbDao;
	}

}
