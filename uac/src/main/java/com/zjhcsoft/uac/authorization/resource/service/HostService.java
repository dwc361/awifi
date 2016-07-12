package com.zjhcsoft.uac.authorization.resource.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import com.zjhcsoft.uac.authorization.resource.entity.Host;
import com.zjhcsoft.uac.authorization.resource.dao.HostDao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class HostService {

	private HostDao hostDao;

	/**
	 * 列表展示
	 */
	public Page queryHostPage(Host paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new Host();
		}
		return hostDao.queryHostPage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public Host save(Host paramObj) throws Exception{
		hostDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public Host delete(Host paramObj) throws Exception{
		List<Host> list = (List<Host>) hostDao.findByMappedQuery("Host_exp_select_host_list", paramObj);
		for (Host host : list) {
			hostDao.delete(host);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<Host> select(Host paramObj) {
		List<Host> list = (List<Host>) hostDao.findByMappedQuery("Host_exp_select_host_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public Host selectObject(Host paramObj) {
		List<Host> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new Host();
		}
	}
	
	/**
	 * 修改数据
	 */
	public Host update(Host paramObj) throws Exception{
		hostDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public Host updateIgnoreNull(Host paramObj) throws Exception{
		hostDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public Host load(Serializable id) throws Exception{
		Host paramObj = (Host) hostDao.load(Host.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<Host> loadAll() throws Exception{
		return (List<Host>) hostDao.loadAll(Host.class);
	}

	@Resource
	public void setHostDao(HostDao hostDao) {
		this.hostDao = hostDao;
	}

}
