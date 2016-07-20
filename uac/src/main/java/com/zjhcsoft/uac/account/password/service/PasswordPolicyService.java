package com.zjhcsoft.uac.account.password.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.dao.PasswordPolicyDao;
import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;
import com.zjhcsoft.uac.account.password.entity.PasswordPolicyVo;

/**
 * 自动生成
 */
@Component
public class PasswordPolicyService {

	private PasswordPolicyDao passwordPolicyDao;
	private List<PasswordValidator> passwordValidators;

	/**
	 * 列表展示
	 */
	public Page queryPasswordPolicyPage(PasswordPolicy paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new PasswordPolicy();
		}
		return passwordPolicyDao.queryPasswordPolicyPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public PasswordPolicy save(PasswordPolicy paramObj) throws Exception {
		passwordPolicyDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public PasswordPolicy delete(PasswordPolicy paramObj) throws Exception {
		List<PasswordPolicy> list = (List<PasswordPolicy>) passwordPolicyDao
				.findByMappedQuery(
						"PasswordPolicy_exp_select_passwordPolicy_list",
						paramObj);
		for (PasswordPolicy passwordPolicy : list) {
			passwordPolicyDao.delete(passwordPolicy);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<PasswordPolicy> select(PasswordPolicy paramObj) {
		List<PasswordPolicy> list = (List<PasswordPolicy>) passwordPolicyDao
				.findByMappedQuery(
						"PasswordPolicy_exp_select_passwordPolicy_list",
						paramObj);
		return list;
	}

	/**
	 * 查询数据
	 */
	public PasswordPolicy selectObject(PasswordPolicy paramObj) {
		List<PasswordPolicy> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new PasswordPolicy();
		}
	}

	/**
	 * 修改数据
	 */
	public PasswordPolicy update(PasswordPolicy paramObj) throws Exception {
		passwordPolicyDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public PasswordPolicy updateIgnoreNull(PasswordPolicy paramObj)
			throws Exception {
		passwordPolicyDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public PasswordPolicy load(Serializable id) throws Exception {
		PasswordPolicy paramObj = (PasswordPolicy) passwordPolicyDao.load(
				PasswordPolicy.class, id, false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<PasswordPolicy> loadAll() throws Exception {
		return (List<PasswordPolicy>) passwordPolicyDao
				.loadAll(PasswordPolicy.class);
	}

	/**
	 * 密码策略验证
	 * 
	 * @param password
	 *            密码
	 * @return
	 */
	public String validPasswordPolicy(String password) {
		String result = "";
		for (PasswordValidator passwordValidator : passwordValidators) {
			String s = passwordValidator.validate(password);
			if (s != null) {
				result += s;
			}
		}
		return result;
	}

	public List<PasswordPolicyVo> findForVo() {
		List<PasswordPolicyVo> passwordPolicyVos = passwordPolicyDao
				.findAllForVo();
		for (PasswordPolicyVo passwordPolicyVo : passwordPolicyVos) {
			passwordPolicyVo
					.setExpression(MessageFormat.format(
							passwordPolicyVo.getExpression(),
							passwordPolicyVo.getVal()));
			passwordPolicyVo.setMessage(MessageFormat.format(
					passwordPolicyVo.getMessage(), passwordPolicyVo.getVal(),
					passwordPolicyVo.getUnit()));
		}
		return passwordPolicyVos;
	}

	@Resource
	public void setPasswordPolicyDao(PasswordPolicyDao passwordPolicyDao) {
		this.passwordPolicyDao = passwordPolicyDao;
	}

	public List<PasswordValidator> getPasswordValidators() {
		return passwordValidators;
	}

	@Resource(name = "passwordValidators")
	public void setPasswordValidators(List<PasswordValidator> passwordValidators) {
		this.passwordValidators = passwordValidators;
	}

}
