package com.zjhcsoft.uac.account.password.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.roof.exceptions.ApplicationException;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;
import com.zjhcsoft.uac.account.password.service.PasswordPolicyService;

/**
 * 自动生成模版
 */
@Component("uac_account_passwordAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class PasswordPolicyAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private PasswordPolicyService passwordPolicyService;

	private PasswordPolicy passwordPolicy;

	private DictionaryService dictionaryService;

	private ResourceService resourceService;

	private Long currentPage;

	private List<PasswordPolicy> passwordPolicies;

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		String url = super.getRequest().getRequestURI();
		List<org.roof.security.entity.Resource> list = resourceService
				.findModuleByPath(url.substring(url.lastIndexOf("/"),
						url.indexOf(".")));
		if (list.size() > 0) {
			super.addParameter("module", list.get(0));
		}
		// (Staff)BaseUserContext.getCurrentUser();// 得到当前用户
		// List<Dictionary> demoList = dictionaryService.findByType("字典表类型");
		// super.addParameter("demoList", demoList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = passwordPolicyService.queryPasswordPolicyPage(passwordPolicy,
				page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/account/passwordPolicy/passwordPolicy_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		passwordPolicy = new PasswordPolicy();
		super.addParameter("passwordPolicy_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/passwordPolicy/passwordPolicy_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		passwordPolicies = passwordPolicyService.loadAll();
		super.addParameter("passwordPolicies", passwordPolicies);
		super.addParameter("passwordPolicy_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/passwordPolicy/passwordPolicy_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		passwordPolicy = passwordPolicyService.load(passwordPolicy.getId());
		super.addParameter("passwordPolicy", passwordPolicy);
		super.addParameter("passwordPolicy_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/passwordPolicy/passwordPolicy_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = passwordPolicyService.load(passwordPolicy.getId());
		return JSON;
	}

	/**
	 * 删除实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String delete() throws ApplicationException {
		try {
			passwordPolicyService.delete(passwordPolicy);
			result = new Result("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00002");
		}
		return JSON;
	}

	/**
	 * 保存实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String create() throws ApplicationException {
		try {
			if (passwordPolicy == null) {
				passwordPolicy = new PasswordPolicy();
			}
			if (passwordPolicy.getId() == null) {
				passwordPolicyService.save(passwordPolicy);
				result = new Result("新增成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	/**
	 * 修改实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String update() throws ApplicationException {
		try {
			if (passwordPolicies == null) {
				result = new Result("数据传输错误");
			}
			for (PasswordPolicy passwordPolicy : passwordPolicies) {
				passwordPolicyService.updateIgnoreNull(passwordPolicy);
			}
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	public String validPasswordPolicy() {
		String password = this.getParamByName("password", String.class);
		String r = passwordPolicyService.validPasswordPolicy(password);
		return JSON;
	}

	@Resource
	public void setPasswordPolicyService(
			PasswordPolicyService passwordPolicyService) {
		this.passwordPolicyService = passwordPolicyService;
	}

	public PasswordPolicyService getPasswordPolicyService() {
		return passwordPolicyService;
	}

	public PasswordPolicy getPasswordPolicy() {
		return passwordPolicy;
	}

	public void setPasswordPolicy(PasswordPolicy passwordPolicy) {
		this.passwordPolicy = passwordPolicy;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@Resource
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public List<PasswordPolicy> getPasswordPolicies() {
		return passwordPolicies;
	}

	public void setPasswordPolicies(List<PasswordPolicy> passwordPolicies) {
		this.passwordPolicies = passwordPolicies;
	}

}