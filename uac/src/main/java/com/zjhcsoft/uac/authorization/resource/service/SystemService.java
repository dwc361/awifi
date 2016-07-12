package com.zjhcsoft.uac.authorization.resource.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.authorization.resource.dao.SystemDao;
import com.zjhcsoft.uac.authorization.resource.entity.System;

/**
 * 自动生成
 */
@Component
public class SystemService {

	private SystemDao systemDao;

	/**
	 * 列表展示
	 */
	public Page querySystemPage(System paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new System();
		}
		return systemDao.querySystemPage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public System save(System paramObj) throws Exception{
		systemDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public System delete(System paramObj) throws Exception{
		List<System> list = (List<System>) systemDao.findByMappedQuery("System_exp_select_system_list", paramObj);
		for (System system : list) {
			systemDao.delete(system);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<System> select(System paramObj) {
		List<System> list = (List<System>) systemDao.findByMappedQuery("System_exp_select_system_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public System selectObject(System paramObj) {
		List<System> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new System();
		}
	}
	
	/**
	 * 修改数据
	 */
	public System update(System paramObj) throws Exception{
		systemDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public System updateIgnoreNull(System paramObj) throws Exception{
		systemDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public System load(Serializable id) throws Exception{
		System paramObj = (System) systemDao.load(System.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<System> loadAll() throws Exception{
		return (List<System>) systemDao.loadAll(System.class);
	}
	
	public boolean hasNullIp(String ip) {
		System paramObj = new System();
		paramObj.setIp(ip);
		long count = systemDao.findSystemCount(paramObj);
		return count > 0;
	}
	
	public boolean hasOneIp(String ip) {
		System paramObj = new System();
		paramObj.setIp(ip);
		long count = systemDao.findSystemCount(paramObj);
		return count == 1;
	}
	
	public boolean hasOneIp(Long id,String ip) throws Exception {
		System paramObj = this.load(id);
		return hasOneIp(paramObj.getIp());
	}

	@Resource
	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

}
