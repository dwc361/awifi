package com.zjhcsoft.uac.authorization.resource.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import com.zjhcsoft.uac.authorization.resource.entity.NetSecurityDevice;
import com.zjhcsoft.uac.authorization.resource.dao.NetSecurityDeviceDao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class NetSecurityDeviceService {

	private NetSecurityDeviceDao netSecurityDeviceDao;

	/**
	 * 列表展示
	 */
	public Page queryNetSecurityDevicePage(NetSecurityDevice paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new NetSecurityDevice();
		}
		return netSecurityDeviceDao.queryNetSecurityDevicePage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public NetSecurityDevice save(NetSecurityDevice paramObj) throws Exception{
		netSecurityDeviceDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public NetSecurityDevice delete(NetSecurityDevice paramObj) throws Exception{
		List<NetSecurityDevice> list = (List<NetSecurityDevice>) netSecurityDeviceDao.findByMappedQuery("NetSecurityDevice_exp_select_netSecurityDevice_list", paramObj);
		for (NetSecurityDevice netSecurityDevice : list) {
			netSecurityDeviceDao.delete(netSecurityDevice);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<NetSecurityDevice> select(NetSecurityDevice paramObj) {
		List<NetSecurityDevice> list = (List<NetSecurityDevice>) netSecurityDeviceDao.findByMappedQuery("NetSecurityDevice_exp_select_netSecurityDevice_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public NetSecurityDevice selectObject(NetSecurityDevice paramObj) {
		List<NetSecurityDevice> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new NetSecurityDevice();
		}
	}
	
	/**
	 * 修改数据
	 */
	public NetSecurityDevice update(NetSecurityDevice paramObj) throws Exception{
		netSecurityDeviceDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public NetSecurityDevice updateIgnoreNull(NetSecurityDevice paramObj) throws Exception{
		netSecurityDeviceDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public NetSecurityDevice load(Serializable id) throws Exception{
		NetSecurityDevice paramObj = (NetSecurityDevice) netSecurityDeviceDao.load(NetSecurityDevice.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<NetSecurityDevice> loadAll() throws Exception{
		return (List<NetSecurityDevice>) netSecurityDeviceDao.loadAll(NetSecurityDevice.class);
	}

	@Resource
	public void setNetSecurityDeviceDao(NetSecurityDeviceDao netSecurityDeviceDao) {
		this.netSecurityDeviceDao = netSecurityDeviceDao;
	}

}
