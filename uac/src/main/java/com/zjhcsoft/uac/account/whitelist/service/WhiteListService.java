package com.zjhcsoft.uac.account.whitelist.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import com.zjhcsoft.uac.account.whitelist.entity.WhiteList;
import com.zjhcsoft.uac.account.whitelist.dao.WhiteListDao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class WhiteListService {

	private WhiteListDao whiteListDao;

	/**
	 * 列表展示
	 */
	public Page queryWhiteListPage(WhiteList paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new WhiteList();
		}
		return whiteListDao.queryWhiteListPage(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public WhiteList save(WhiteList paramObj) throws Exception{
		whiteListDao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public WhiteList delete(WhiteList paramObj) throws Exception{
		List<WhiteList> list = (List<WhiteList>) whiteListDao.findByMappedQuery("WhiteList_exp_select_whiteList_list", paramObj);
		for (WhiteList whiteList : list) {
			whiteListDao.delete(whiteList);
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<WhiteList> select(WhiteList paramObj) {
		List<WhiteList> list = (List<WhiteList>) whiteListDao.findByMappedQuery("WhiteList_exp_select_whiteList_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public WhiteList selectObject(WhiteList paramObj) {
		List<WhiteList> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new WhiteList();
		}
	}
	
	/**
	 * 修改数据
	 */
	public WhiteList update(WhiteList paramObj) throws Exception{
		whiteListDao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public WhiteList updateIgnoreNull(WhiteList paramObj) throws Exception{
		whiteListDao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public WhiteList load(Serializable id) throws Exception{
		WhiteList paramObj = (WhiteList) whiteListDao.load(WhiteList.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<WhiteList> loadAll() throws Exception{
		return (List<WhiteList>) whiteListDao.loadAll(WhiteList.class);
	}

	@Resource
	public void setWhiteListDao(WhiteListDao whiteListDao) {
		this.whiteListDao = whiteListDao;
	}

}
