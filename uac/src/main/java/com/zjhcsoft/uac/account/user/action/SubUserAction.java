package com.zjhcsoft.uac.account.user.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.roof.exceptions.ApplicationException;
import org.roof.security.BaseUserContext;
import org.roof.security.entity.BaseRole;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.SubUserService;
import com.zjhcsoft.uac.authorization.resource.dao.AppDao;
import com.zjhcsoft.uac.authorization.resource.entity.App;
import com.zjhcsoft.uac.blj.service.BljService;
import com.zjhcsoft.uac.ldap.util.LdapUtils;
import com.zjhcsoft.uac.ldap.util.PersonServiceI;
import com.zjhcsoft.uac.log.service.LogManager;

/**
 * 自动生成模版
 */
@Component("uac_account_subUserAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SubUserAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private SubUserService subUserService;

	private SubUser subUser;

	private List<SubUser> subUserList;

	private DictionaryService dictionaryService;

	private ResourceService resourceService;

	private Long currentPage;
	private User user;
	private UserDao userDao;
	private List<SubUser> subUsers;

	private LogManager logManager;
	private BljService bljService;
	private AppDao appDao;
	
	private PersonServiceI ldapUtils;

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
		List<Dictionary> userScopes = dictionaryService
				.findByType("USER_SCOPE"); // 账号性质
		super.addParameter("userScopes", userScopes);
		List<Dictionary> userPrivilegs = dictionaryService
				.findByType("USER_PRIVILEG"); // 账号类型
		super.addParameter("userPrivilegs", userPrivilegs);
		List<Dictionary> regionList = dictionaryService.findByType("REGION");
		super.addParameter("regionList", regionList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = subUserService.querySubUserPage(subUser, page);
		if (subUser.getUser().getId() != null) {
			user = userDao.load(User.class, subUser.getUser().getId());
		}
		/**
		 * 获取资源类型 2014-09-15 yxg
		 */
		for (int i = 0; i < page.getDataList().size(); i++) {
			if (((List<SubUser>) page.getDataList()).get(i).getSysResource().getResourcetype() == null) {
				String className = org.hibernate.Hibernate.getClass(((List<SubUser>) page.getDataList()).get(i).getSysResource()).getSimpleName();
				((List<SubUser>) page.getDataList()).get(i).getSysResource().setResourcetype(className);
			}
		}

		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/account/user/subUser/subUser_list.jsp";
		return JSP;
	}

	/**
	 * 查看自己的账号信息
	 * 
	 * @return
	 */
	public String list_myself() {
		SubUser loginStaff = (SubUser) BaseUserContext.getCurrentUser();
		User loginUser = new User();
		loginUser.setId(loginStaff.getUser().getId());
		List<User> mainUsers = userDao.select(loginUser);
		SubUser sub = new SubUser();
		sub.setUser(loginUser);
		List<SubUser> subUsers = subUserService.select(sub);
		super.addParameter("mainUsers", mainUsers);
		super.addParameter("subUsers", subUsers);
		loadCommonData();
		result = "/uac/account/user/subUser/subUser_list_myself.jsp";
		return JSP;
	}

	/**
	 * 账号权限审计
	 * 
	 * @return
	 */
	public String list_sub_user_log() {
		Page page = super.createPage();
		page = subUserService.querySubUserPage(subUser, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/account/user/subUser/subUser_list_log.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		String user_id = this.getParamByName("user_id", String.class);
		subUser = new SubUser();
		super.addParameter("subUser_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		addParameter("user_id", user_id);
		result = "/uac/account/user/subUser/subUser_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		String user_id = this.getParamByName("user_id", String.class);
		subUser = subUserService.load(subUser.getId());
		String pwd = subUser.getPassword();
		if(pwd != null){
			String subpwd = ldapUtils.decodeEncryptStringKey(pwd);
			super.addParameter("subpwd", subpwd);
		}
		super.addParameter("subUser", subUser);
		if(subUser.getSysResource() != null){
			String currType = subUser.getSysResource().toString();
			currType = currType.substring(currType.lastIndexOf(".") + 1, currType.lastIndexOf("@"));
			super.addParameter("currType", currType);
			super.addParameter("isApp", "App".equals(currType));
		}
		super.addParameter("subUser_paramString", super.getParamString(WebUtils.getRequest()));
		addParameter("user_id", user_id);
		loadCommonData();
		result = "/uac/account/user/subUser/subUser_update.jsp";
		return JSP;
	}

	public String update_page_myself() throws Exception {
		this.addParameter("flag", "myself");
		return update_page();
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		subUser = subUserService.load(subUser.getId());
		super.addParameter("subUser", subUser);
		super.addParameter("subUser_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/user/subUser/subUser_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = subUserService.load(subUser.getId());
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
			subUser.setModifyTime(new Date());
			subUserService.delete(subUser);
			logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_LOCK, "成功");
			result = new Result("账号禁用成功!");
		} catch (Exception e) {
			logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_LOCK, "失败");
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00002");
		}
		return JSON;
	}

	/**
	 * 激活
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String reuse() throws ApplicationException {
		try {
			subUser.setModifyTime(new Date());
			subUserService.reuse(subUser);
			logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_ACTIVE, "成功");
			result = new Result("账号启用成功!");
		} catch (Exception e) {
			logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_ACTIVE, "失败");
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
			if (subUser == null) {
				subUser = new SubUser();
			}
			subUser.setCreate_date(new Date());
			subUser.setModifyTime(new Date());
			if (subUser.getId() == null) {
					subUserService.save(subUser);
					logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_CREATE, "成功");
					result = new Result("success","创建成功!",subUser.getId());
			}
		} catch (Exception e) {
			logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_CREATE, "失败");
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
			if (subUser == null) {
				subUser = new SubUser();
			}
			boolean old = false;
			boolean now = false;
			String href = (String) super.getParamByName("blj");
			if(href != null && !"".equals(href)){
				if (subUser.getId() != null) {
					SubUser sub = new SubUser();
					sub = subUserService.load(subUser.getId());
					for (BaseRole r : sub.getRoles()) {
						if (r != null && r.getId() != 51L) {
							old = true;
							break;
						}
					}
					for (BaseRole r : subUser.getRoles()) {
						if (r != null && r.getId() != 51L) {
							now = true;
							break;
						}
					}
				}
			}
			subUserService.updateIgnoreNull(subUser);
			String re ="";
			if (!old && now ) {
				re = bljService.Useradd(subUser.getUser());
			} else if(old && !now ) {
				re = bljService.Userdelete(subUser.getUser());
			}
			result = new Result("success", "修改成功!"+re, subUser.getId());
			logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_UPDATE, "成功"+re);
		} catch (Exception e) {
			logManager.addAccountLog(subUser, new Date(), LogManager.ACCOUNT_OP_TYPE_UPDATE, "失败");
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	/**
	 * 子账号绑定
	 * 
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String binding() {
		subUser = (SubUser) BaseUserContext.getCurrentUser();
		User u = subUser.getUser();
		Long sysResource_id = getParamByName("sysResource_id", Long.class);
		String username = getParamByName("username", String.class);
		String scode = getParamByName("scode", String.class);
		try {
			subUserService.VerifyScode(sysResource_id,username,scode,u);
		} catch (Exception e) {
			result = new Result(Result.FAIL, e.getMessage());
			return JSON;
		}
		result = new Result("绑定成功!");

		return JSON;
	}

	/**
	 * 子账号绑定页面
	 * 
	 * @return
	 */
	public String binding_page() {
		subUser = (SubUser) BaseUserContext.getCurrentUser();
		List<App> apps = appDao.findEnable();
		this.addParameter("apps", apps);
		result = "/uac/account/user/subUser/subUser_binding.jsp";
		return JSP;
	}

	/**
	 * 验证码发送
	 * 
	 * @return
	 */
	public String binding_scode() {
		subUser = (SubUser) BaseUserContext.getCurrentUser();
		User u = subUser.getUser();
		Long sysResource_id = getParamByName("sysResource_id", Long.class);
		String username = getParamByName("username", String.class);
		try {
			subUserService.genScode(sysResource_id, username,u);
		} catch (Exception e) {
			result = new Result(Result.FAIL, e.getMessage());
			return JSON;
		}
		result = new Result("验证码发送成功!");
		return JSON;
	}

	@Resource
	public void setSubUserService(SubUserService subUserService) {
		this.subUserService = subUserService;
	}

	public SubUserService getSubUserService() {
		return subUserService;
	}

	public SubUser getSubUser() {
		return subUser;
	}

	public void setSubUser(SubUser subUser) {
		this.subUser = subUser;
	}

	public List<SubUser> getSubUserList() {
		return subUserList;
	}

	public void setSubUserList(List<SubUser> subUserList) {
		this.subUserList = subUserList;
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

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	@Resource
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SubUser> getSubUsers() {
		return subUsers;
	}

	public void setSubUsers(List<SubUser> subUsers) {
		this.subUsers = subUsers;
	}

	@Resource
	public void setBljService(BljService bljService) {
		this.bljService = bljService;
	}
	
	@Resource
	public void setLdapUtils(PersonServiceI ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

}