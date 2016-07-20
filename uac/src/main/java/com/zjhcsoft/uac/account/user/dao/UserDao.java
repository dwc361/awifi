package com.zjhcsoft.uac.account.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.exceptions.DaoException;
import org.roof.web.org.entity.Organization;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.user.entity.User;

@Component
public class UserDao extends RoofDaoSupport {

	public Page findForPage(Page page, User params) {
		if(params != null &&params.getOrg()!= null && params.getOrg().getOrg_id() != null){
			params.setOrgIds(selectOrganizationIdsByParentId(params.getOrg().getOrg_id()));
		}
		PageQuery pageQuery = new PageQuery(page,
				"com.zjhcsoft.uac.account.user.dao.UserDao.findForPage",
				"com.zjhcsoft.uac.account.user.dao.UserDao.findForPage_count");
		return pageQuery.findByMappedQuery(params);
	}

	public User findByUsername(String username) throws DaoException {
		User user = new User();
		user.setUsername(username);
		return (User) findByMappedQuerySingle(
				"com.zjhcsoft.uac.account.user.dao.UserDao.findByUsername",
				user);
	}

	public User findByIdNumber(String idNumber) throws DaoException {
		User user = new User();
		user.setIdNumber(idNumber);
		return (User) findByMappedQuerySingle(
				"com.zjhcsoft.uac.account.user.dao.UserDao.findByIdNumber",
				user);
	}
	
	public User findByTel(String tel) throws DaoException {
		User user = new User();
		user.setUsername(tel);
		return (User) findByMappedQuerySingle(
				"com.zjhcsoft.uac.account.user.dao.UserDao.findForPage",
				user);
	}

	public Long findUserCount(User params) {
		return (Long) executeByMappedQuery(
				"com.zjhcsoft.uac.account.user.dao.UserDao.findUserCount",
				params);
	}

	/**
	 * 查询数据
	 */
	public List<User> select(User paramObj) {
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) super.findByMappedQuery(
				"com.zjhcsoft.uac.account.user.dao.UserDao.findForPage",
				paramObj);
		return list;
	}

	public Organization findOrgByName(String org_name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("org_name", org_name);
		@SuppressWarnings("unchecked")
		List<Organization> orgs = (List<Organization>) this.findByMappedQuery(
				"com.zjhcsoft.uac.account.user.dao.UserDao.findOrgByName",
				params);
		if (orgs != null && orgs.size() > 0) {
			return orgs.get(0);
		}
		return null;
	}
	
	/**
	 * 根据父节点查找出所有子节点
	 * @param org_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> selectOrganizationIdsByParentId(Long org_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("org_id", org_id);
		params.put("usable", true);
		return (List<Long>) this.selectForList("selectOrganizationIdsByParentId",
				params);
	}
}
