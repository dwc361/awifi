package com.zjhcsoft.uac.account.password.dao;

import java.util.List;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.exceptions.DaoException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;
import com.zjhcsoft.uac.account.password.entity.PasswordPolicyVo;

/**
 * 自动生成
 */
@Component
public class PasswordPolicyDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryPasswordPolicyPage(PasswordPolicy paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page,
				"PasswordPolicy_exp_select_passwordPolicy_list",
				"PasswordPolicy_exp_select_passwordPolicy_count");
		return pageQuery.findByMappedQuery(paramObj);
	}

	@Cacheable(value = "com.zjhcsoft.uac.account.password.dao.PasswordPolicyDao#findbyName", key = "#name")
	public PasswordPolicy findbyName(String name) throws DaoException {
		PasswordPolicy passwordPolicy = new PasswordPolicy();
		passwordPolicy.setName(name);
		return (PasswordPolicy) findByExampleSingle(passwordPolicy);
	}

	public List<PasswordPolicyVo> findAllForVo() {
		return (List<PasswordPolicyVo>) findByMappedQuery("com.zjhcsoft.uac.account.password.dao.PasswordPolicyDao.findAllForVo");

	}
}
