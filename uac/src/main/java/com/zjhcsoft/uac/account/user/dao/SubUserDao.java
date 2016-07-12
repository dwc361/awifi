package com.zjhcsoft.uac.account.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.exceptions.DaoException;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.SubUserTemp;

/**
 * 自动生成
 */
@Component
public class SubUserDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page querySubUserPage(SubUser paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "SubUser_exp_select_subUser_list", "SubUser_exp_select_subUser_count");
		return pageQuery.findByMappedQuery(paramObj);
	}

	@SuppressWarnings("unchecked")
	public List<SubUser> loadEnable(Long user_id, Long sysResource_id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", user_id);
		param.put("sysResource_id", sysResource_id);
		return (List<SubUser>) findByMappedQuery("com.zjhcsoft.uac.account.user.dao.SubUserDao.loadEnable", param);
	}

	public List<SubUser> loadAllSubs(Long user_id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", user_id);
		return (List<SubUser>) findByMappedQuery("com.zjhcsoft.uac.account.user.dao.SubUserDao.loadAllSubs", param);
	}

	public Long selectSubUserByPar(String sub_code,String par_code) throws DaoException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sub", sub_code);
		param.put("par", par_code);
		return (Long) super.selectForObject("selectSubUserByPar", param);
	}
	
	public Long countBindingSubUser(Long sysResource_id, String subUsername) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sysResource_id", sysResource_id);
		param.put("subUsername", subUsername);
		Long l = (Long) executeByMappedQuery("com.zjhcsoft.uac.account.user.dao.SubUserDao.countBindingSubUser", param);
		return l;
	}

	public SubUserTemp findSubUserTemp(Long sysResource_id, String subUsername) throws DaoException {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("sysResource_id", sysResource_id);
		parameterObject.put("username", subUsername);
		return (SubUserTemp) findByMappedQuerySingle("com.zjhcsoft.uac.account.user.dao.SubUserDao.findSubUserTemp",
				parameterObject);
	}

	public List<SubUser> loadEnable(Long user_id) {
		return loadEnable(user_id, null);
	}
	
	
	public Long findUserCount(Long sysResource_id, String subUsername,Long userid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sysResource_id", sysResource_id);
		param.put("subUsername", subUsername);
		param.put("user_id", userid);
		Long l = (Long) executeByMappedQuery(
				"com.zjhcsoft.uac.account.user.dao.SubUserDao.findSubUserCount",
				param);
		return l;
	}
	
	public Long findUserCount(Long sysResource_id, String subUsername) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sysResource_id", sysResource_id);
		param.put("subUsername", subUsername);
		Long l = (Long) executeByMappedQuery(
				"com.zjhcsoft.uac.account.subuser.dao.SubUserDao.findSubUserCount",
				param);
		return l;
	}

}
