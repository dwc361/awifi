package com.zjhcsoft.uac.account.whiteiplist.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.whiteiplist.dao.WhiteIpListDao;
import com.zjhcsoft.uac.account.whiteiplist.entity.WhiteIpList;

/**
 * 自动生成
 */
@Component
public class WhiteIpListService {

	private WhiteIpListDao whiteIpListDao;

	/**
	 * 列表展示
	 */
	public Page queryWhiteIpListPage(WhiteIpList paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new WhiteIpList();
		}
		return whiteIpListDao.queryWhiteIpListPage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public WhiteIpList save(WhiteIpList paramObj) throws Exception{
		whiteIpListDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public WhiteIpList delete(WhiteIpList paramObj) throws Exception{
		List<WhiteIpList> list = (List<WhiteIpList>) whiteIpListDao.findByMappedQuery("WhiteList_exp_select_whiteipList_list", paramObj);
		for (WhiteIpList whiteList : list) {
			whiteIpListDao.delete(whiteList);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<WhiteIpList> select(WhiteIpList paramObj) {
		List<WhiteIpList> list = (List<WhiteIpList>) whiteIpListDao.findByMappedQuery("WhiteList_exp_select_whiteipList_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public WhiteIpList selectObject(WhiteIpList paramObj) {
		List<WhiteIpList> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new WhiteIpList();
		}
	}
	
	/**
	 * 修改数据
	 */
	public WhiteIpList update(WhiteIpList paramObj) throws Exception{
		whiteIpListDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public WhiteIpList updateIgnoreNull(WhiteIpList paramObj) throws Exception{
		whiteIpListDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public WhiteIpList load(Serializable id) throws Exception{
		WhiteIpList paramObj = (WhiteIpList) whiteIpListDao.load(WhiteIpList.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<WhiteIpList> loadAll() throws Exception{
		return (List<WhiteIpList>) whiteIpListDao.loadAll(WhiteIpList.class);
	}

	@Resource
	public void setWhiteIpListDao(WhiteIpListDao whiteIpListDao) {
		this.whiteIpListDao = whiteIpListDao;
	}
}
