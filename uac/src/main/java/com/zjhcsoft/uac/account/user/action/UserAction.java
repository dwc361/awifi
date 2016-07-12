package com.zjhcsoft.uac.account.user.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.roof.dataaccess.Page;
import org.roof.security.BaseUserContext;
import org.roof.security.entity.BaseUser;
import org.roof.spring.CurrentSpringContext;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.org.entity.Organization;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.service.PasswordPolicyService;
import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.UserService;
import com.zjhcsoft.uac.ldap.job.LdapSynchronousService;
import com.zjhcsoft.uac.ldap.util.LdapUtils;
import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.log.service.LogManager;

@Component("uac_account_userAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class UserAction extends RoofActionSupport {
	private static final long serialVersionUID = -247530850003055354L;
	private User user;
	private DictionaryService dictionaryService;
	private UserDao userDao;
	private UserService userService;
	private List<User> users;
	private LogManager logManager;
	private LdapUtils ldapUtils;

	private PasswordPolicyService passwordPolicyService;
	
	private LdapSynchronousService ldapSynchronousService;

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		List<Dictionary> userScopes = dictionaryService
				.findByType("USER_SCOPE"); // 账号性质
		super.addParameter("userScopes", userScopes);

		List<Dictionary> userCategorys = dictionaryService
				.findByType("USER_CATEGORY"); // 用户类别
		super.addParameter("userCategorys", userCategorys);

		List<Dictionary> genders = dictionaryService.findByType("GENDER"); // 性别
		super.addParameter("genders", genders);

	}

	public String index() {
		result = "/uac/account/user/user_index.jsp";
		return JSP;
	}

	public String list() {
		Page page = createPage();
		if (user == null) {
			user = new User();
		}
		page = userDao.findForPage(page, user);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		Boolean unEditable = getParamByName("unEditable", Boolean.class);
		this.addParameter("unEditable", unEditable);
		this.addParameter("user", user);
		this.addParameter("pageBar", pageBar);
		this.addParameter("page", page);
		result = "/uac/account/user/user_list.jsp";
		return JSP;
	}
	
	public String user_list() {
		Page page = createPage();
		if (user == null) {
			user = new User();
		}
		Boolean isMayor = getParamByName("isMayor", Boolean.class);
		page = userDao.findForPage(page, user);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		Boolean unEditable = getParamByName("unEditable", Boolean.class);
		this.addParameter("unEditable", unEditable);
		this.addParameter("user", user);
		this.addParameter("pageBar", pageBar);
		this.addParameter("isMayor", isMayor);
		this.addParameter("page", page);
		result = "/uac/update_user_page.jsp";
		return JSP;
	}
	

	public String create() throws Exception {
		try {
			if (user == null) {
				result = new Result("数据传输失败！");
				return JSON;
			}
			if (user.getOrg() == null || user.getOrg().getOrg_id() == null) {
				user.setOrg(new Organization(1L));
			}
			user.setModifyTime(new Date());
			user.setPwdSetTime(new Date());
			userService.save(user);
			logManager.addAccountLog(user, new Date(),
					LogManager.ACCOUNT_OP_TYPE_CREATE, "成功");
		} catch (Exception e) {
			logManager.addAccountLog(user, new Date(),
					LogManager.ACCOUNT_OP_TYPE_CREATE, "失败");
			throw e;
		}
		result = new Result("保存成功！");
		return JSON;
	}

	/**
	 * 用户名是否被占用
	 * 
	 * @return
	 */
	public String validateUsername() {
		String username = this.getParamByName("username", String.class);
		result = !userService.hasUserName(username);// 用户名是否已经被占用 true:有相同用户名
		return JSON;
	}

	/**
	 * 身份证号是否被占用
	 * 
	 * @return
	 */
	public String validateIdNumber() {
		String idNumber = this.getParamByName("idNumber", String.class);
		result = !userService.hasIdNumber(idNumber);// 身份证号是否已经被占用 true:有相同 身份证号
		return JSON;
	}

	public String validateOldPassword() {
		user.setPassword(ldapUtils.encodePwdSHA(user.getPassword()));
		result = (userDao.select(user).size() == 1);
		return JSON;
	}

	public String create_page() {
		Long org_id = getParamByName("org_id", Long.class);
		if (org_id == null || org_id == 0L) {
			org_id = 50L;
		}
		Organization org = userDao.load(Organization.class, org_id);

		user = new User();
		user.setOrg(org);
		loadCommonData();
		this.addParameter("org_id", org_id);
		this.addParameter("passwordPolicys",
				Result.getStr(passwordPolicyService.findForVo()));
		result = "/uac/account/user/user_create_page.jsp";
		return JSP;
	}

	public String update() throws Exception {
		if (user == null) {
			result = new Result("数据传输失败！");
			return JSON;
		}
		try {
			user.setUpdate_time(new Date());
			user.setModifyTime(new Date());
			userService.update(user);
		} catch (Exception e) {
			logManager.addAccountLog(user, new Date(),
					LogManager.ACCOUNT_OP_TYPE_UPDATE, "失败");
			throw e;
		}
		logManager.addAccountLog(user, new Date(),
				LogManager.ACCOUNT_OP_TYPE_UPDATE, "成功");
		result = new Result("保存成功！");
		return JSON;
	}
	
	
	public String update_user() throws Exception {
		if (user == null) {
			result = new Result("数据传输失败！");
			return JSON;
		}
		try {
			if (StringUtils.isNotEmpty(user.getPassword())) {
				userService.reset_pwd(user);
			}else{
				user.setUpdate_time(new Date());
				userService.update_username(user);
			}
		} catch (Exception e) {
			logManager.addAccountLog(user, new Date(),
					LogManager.ACCOUNT_OP_TYPE_UPDATE, "失败");
			throw e;
		}
		logManager.addAccountLog(user, new Date(),
				LogManager.ACCOUNT_OP_TYPE_UPDATE, "成功");
		result = new Result("保存成功！");
		return JSON;
	}
	
	public String updateError(){
		if (user == null) {
			result = new Result("数据传输失败！");
			return JSON;
		}
		if (ldapSynchronousService == null) {
			ldapSynchronousService = (LdapSynchronousService) CurrentSpringContext
					.getBean("ldapSynchronousService");
		}
		try {
			ldapSynchronousService.DbToLdap(user);
			user = userDao.load(User.class, user.getId());
			Person p = new Person();
			p.setParNode(user.getUsername());
			List<Person> plist = ldapUtils.getPersonList(p);
			String Hql = "from SubUser t where t.enabled = true and t.user.id =? and t.username = ? and t.cn = ? ";
			String mess = "";
			int i = 0;
			for(Person pp:plist){
				List<SubUser> subs = (List<SubUser>) userDao.findForList(Hql,user.getId(),pp.getSn(),pp.getCn());
				if(subs.size()<=0){
					i++;
					ldapUtils.removeOnePerson(pp);
				}
				if(i>0){
					mess = "ladp删除--"+i;
				}
				System.out.println(pp.getSn()+"==========="+pp.getCn());
			}
			result = new Result("修改成功！"+mess);
		}catch(Exception e) {
			result = new Result("修改失败！");
		}
		
		return JSON;
	}

	public String update_page() {
		loadCommonData();
		user = (User) userDao.reload(user);
		result = "/uac/account/user/user_update_page.jsp";
		return JSP;
	}

	public String update_page_myself() {
		this.addParameter("flag", "myself");
		return update_page();
	}

	public String update_pwd_page() {
		BaseUser baseUser = (BaseUser) BaseUserContext.getCurrentUser();
		if (baseUser instanceof SubUser) {
			SubUser loginStaff = (SubUser) baseUser;
			this.addParameter("user", loginStaff.getUser());

		}
		if (baseUser instanceof User) {
			this.addParameter("user", baseUser);
		}
		this.addParameter("passwordPolicys",
				Result.getStr(passwordPolicyService.findForVo()));
		result = "/uac/account/user/user_update_pwd_page.jsp";
		return JSP;
	}

	public String delete() throws Exception {
		if (user == null || user.getId() == null) {
			result = new Result("数据传输失败！");
			return JSON;
		}
		try {
			user.setModifyTime(new Date());
			userService.delete(user);
		} catch (Exception e) {
			logManager.addAccountLog(user, new Date(),
					LogManager.ACCOUNT_OP_TYPE_LOCK, "失败");
			result = new Result("账号停用失败！");
			return JSON;
		}
		logManager.addAccountLog(user, new Date(),
				LogManager.ACCOUNT_OP_TYPE_LOCK, "成功");
		result = new Result("账号已停用！");
		return JSON;
	}

	public String reuse() throws Exception {

		if (user == null || user.getId() == null) {
			result = new Result("数据传输失败！");
			return JSON;
		}
		try {
			user.setModifyTime(new Date());
			userService.reuse(user);
		} catch (Exception e) {
			logManager.addAccountLog(user, new Date(),
					LogManager.ACCOUNT_OP_TYPE_ACTIVE, "失败");
			throw e;
		}
		logManager.addAccountLog(user, new Date(),
				LogManager.ACCOUNT_OP_TYPE_ACTIVE, "成功");
		result = new Result("账号已启用！");
		return JSON;

	}

	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	@Resource
	public void setPasswordPolicyService(
			PasswordPolicyService passwordPolicyService) {
		this.passwordPolicyService = passwordPolicyService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Resource
	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

}
