package com.zjhcsoft.uac.authorization.resource.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import com.zjhcsoft.uac.authorization.resource.entity.Os;
import com.zjhcsoft.uac.authorization.resource.dao.OsDao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class OsService {

	private OsDao osDao;

	/**
	 * 列表展示
	 */
	public Page queryOsPage(Os paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new Os();
		}
		return osDao.queryOsPage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public Os save(Os paramObj) throws Exception{
		osDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public Os delete(Os paramObj) throws Exception{
		List<Os> list = (List<Os>) osDao.findByMappedQuery("Os_exp_select_os_list", paramObj);
		for (Os os : list) {
			osDao.delete(os);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<Os> select(Os paramObj) {
		List<Os> list = (List<Os>) osDao.findByMappedQuery("Os_exp_select_os_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public Os selectObject(Os paramObj) {
		List<Os> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new Os();
		}
	}
	
	/**
	 * 修改数据
	 */
	public Os update(Os paramObj) throws Exception{
		osDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public Os updateIgnoreNull(Os paramObj) throws Exception{
		osDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public Os load(Serializable id) throws Exception{
		Os paramObj = (Os) osDao.load(Os.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<Os> loadAll() throws Exception{
		return (List<Os>) osDao.loadAll(Os.class);
	}

	@Resource
	public void setOsDao(OsDao osDao) {
		this.osDao = osDao;
	}

}
