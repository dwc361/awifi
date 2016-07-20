package com.zjhcsoft.uac.authorization.resource.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import com.zjhcsoft.uac.authorization.resource.entity.NetDevice;
import com.zjhcsoft.uac.authorization.resource.dao.NetDeviceDao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class NetDeviceService {

	private NetDeviceDao netDeviceDao;

	/**
	 * 列表展示
	 */
	public Page queryNetDevicePage(NetDevice paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new NetDevice();
		}
		return netDeviceDao.queryNetDevicePage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public NetDevice save(NetDevice paramObj) throws Exception{
		netDeviceDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public NetDevice delete(NetDevice paramObj) throws Exception{
		List<NetDevice> list = (List<NetDevice>) netDeviceDao.findByMappedQuery("NetDevice_exp_select_netDevice_list", paramObj);
		for (NetDevice netDevice : list) {
			netDeviceDao.delete(netDevice);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<NetDevice> select(NetDevice paramObj) {
		List<NetDevice> list = (List<NetDevice>) netDeviceDao.findByMappedQuery("NetDevice_exp_select_netDevice_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public NetDevice selectObject(NetDevice paramObj) {
		List<NetDevice> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new NetDevice();
		}
	}
	
	/**
	 * 修改数据
	 */
	public NetDevice update(NetDevice paramObj) throws Exception{
		netDeviceDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public NetDevice updateIgnoreNull(NetDevice paramObj) throws Exception{
		netDeviceDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public NetDevice load(Serializable id) throws Exception{
		NetDevice paramObj = (NetDevice) netDeviceDao.load(NetDevice.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<NetDevice> loadAll() throws Exception{
		return (List<NetDevice>) netDeviceDao.loadAll(NetDevice.class);
	}

	@Resource
	public void setNetDeviceDao(NetDeviceDao netDeviceDao) {
		this.netDeviceDao = netDeviceDao;
	}

}
