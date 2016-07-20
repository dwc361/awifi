package com.zjhcsoft.uac;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.dataaccess.Page;
import org.roof.security.BaseUserContext;
import org.roof.security.entity.BaseRole;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.menu.dao.MenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.service.MenuFilter;
import org.roof.web.org.entity.Organization;
import org.roof.web.user.entity.Staff;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.service.PasswordPolicyService;
import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.SubUserService;
import com.zjhcsoft.uac.account.user.service.UserService;
import com.zjhcsoft.uac.authorization.resource.service.AppService;
import com.zjhcsoft.uac.bulletin.service.BulletinService;
import com.zjhcsoft.uac.cxf.SmsService;
import com.zjhcsoft.uac.favorites.entity.Favorites;
import com.zjhcsoft.uac.favorites.service.FavoritesService;
import com.zjhcsoft.uac.log.service.LogManager;
import com.zjhcsoft.uac.message.entity.Message;
import com.zjhcsoft.uac.message.service.MessageService;

/**
 * @author liuxin
 * 
 */
@Component("uacAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class UacAction extends RoofActionSupport {
	private MenuDao menuDao;
	private MenuFilter menuFilter;
	private BulletinService bulletinService;
	private MessageService messageService;
	private FavoritesService favoritesService;
	private AppService appService;
	private SubUserService subUserService;
	private SubUser subUser;
	private Map<String, String> redisMap;

	private User user;
	private DictionaryService dictionaryService;
	private UserDao userDao;

	private PasswordPolicyService passwordPolicyService;

	private UserService userService;
	private LogManager logManager;
	
	private SmsService smsService;

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		List<Dictionary> userScopes = dictionaryService.findByType("USER_SCOPE"); // 账号性质
		super.addParameter("userScopes", userScopes);

		List<Dictionary> userCategorys = dictionaryService.findByType("USER_CATEGORY"); // 用户类别
		super.addParameter("userCategorys", userCategorys);

		List<Dictionary> genders = dictionaryService.findByType("GENDER"); // 性别
		super.addParameter("genders", genders);

	}

	private static final long serialVersionUID = 1L;

	public String goMain() {
		Menu menu = menuDao.load(Menu.class, 1L);
		menuFilter.doFilter(menu);
		// Map<Class<?>, String[]> map = new HashMap<Class<?>, String[]>();
		// map.put(Menu.class, new String[] { "parent" });
		// map.put(Module.class, new String[] { "parent", "baseRole",
		// "parameters", "returnFields" });
		// String s = JSONObject.fromObject(menu, Result.createJsonConfig(map))
		// .toString();
		this.addParameter("menus", menu);
		this.addParameter("currUser", (Staff) BaseUserContext.getCurrentUser());// 得到当前用户;
		//Staff s = menuDao.load(Staff.class, ((Staff) BaseUserContext.getCurrentUser()).getId());
		//this.addParameter("currUser", s);// 得到当前用户;
		result = "/uac/uac_main.jsp";
		return JSP;
	}

	public String loadMenu() {
		Menu menu = menuDao.load(Menu.class, 1L);
		menuFilter.doFilter(menu);
		result = new Result(Result.SUCCESS, menu);
		return JSON;
	}

	public String index() {
		SubUser currentUser = (SubUser) BaseUserContext.getCurrentUser();
		super.addParameter("appList", appService.selectMyself(currentUser.getUser().getId()));
		Favorites farg = new Favorites();
		farg.setStaff(currentUser);
		super.addParameter("favoritesList", favoritesService.queryCountFavorites(farg, 9));
		Message marg = new Message();
		marg.setToStaff(currentUser);
		super.addParameter("messageList", messageService.queryCountMessage(marg, 4));
		super.addParameter("bulletinList", bulletinService.queryCountBulletin(null, 4));
		result = "/uac/uac_index.jsp";
		return JSP;
	}

	public String loadSubUser() {
		subUser.setUser(((SubUser) BaseUserContext.getCurrentUser()).getUser());
		List<SubUser> list = subUserService.selectTure(subUser);
		List<SubUser> r = new ArrayList<SubUser>();
		for (SubUser subUser : list) {
			SubUser s = new SubUser();
			s.setId(subUser.getId());
			s.setName(subUser.getUsername());
			s.setEnabled(subUser.getEnabled());
			r.add(s);
		}
		result = new Result(Result.SUCCESS, "", r);
		return JSON;
	}

	public String putSubUser() {
		String href = (String) super.getParamByName("href");
		String u = ((SubUser) BaseUserContext.getCurrentUser()).getUser().getUsername();
		redisMap.put(u + "_" + href.split("/")[3] + "_login_subuser", subUser.getName());
		redisMap.put(u + "_" + href.split("/")[3] + "_login_userPwd", (String) super.getParamByName("userPwd"));
		result = new Result(Result.SUCCESS, subUser.getName());
		return JSON;
	}

	public String register_page() {
		Long org_id = getParamByName("org_id", Long.class);
		if (org_id == null || org_id == 0L) {
			org_id = 50L;
		}
		Organization org = userDao.load(Organization.class, org_id);

		user = new User();
		user.setOrg(org);
		loadCommonData();
		this.addParameter("org_id", org_id);
		this.addParameter("passwordPolicys", Result.getStr(passwordPolicyService.findForVo()));
		result = "/uac/account/user/user_register.jsp";
		return JSP;
	}

	public String register() throws Exception {
		try {
			if (user == null) {
				result = new Result("数据传输失败!");
				return JSON;
			}
			if (user.getOrg() == null || user.getOrg().getOrg_id() == null) {
				user.setOrg(new Organization(1L));
			}
			user.setPwdSetTime(new Date());
			userService.save(user);
			logManager.addAccountLog(user, new Date(), LogManager.ACCOUNT_OP_TYPE_CREATE, "成功");
		} catch (Exception e) {
			logManager.addAccountLog(user, new Date(), LogManager.ACCOUNT_OP_TYPE_CREATE, "失败");
			throw e;
		}
		result = new Result("注册成功,请登陆以后绑定子账号!");
		return JSON;
	}

	public String update_sms() {
		String dicVal = (String) super.getParamByName("sms_val");
		List<Dictionary> dicList = dictionaryService.findByType("SMS_SWITCH"); // 短信开关
		Dictionary dic = new Dictionary();
		if (dicList.size() > 0) {
			dic = dicList.get(0);
			dic.setVal(dicVal);
			menuDao.updateIgnoreNull(dic);
		}
		if("SMS_SWITCH_Y".equals(dicVal)){
			result = new Result("短信验证已开启!");
		}else if("SMS_SWITCH_N".equals(dicVal)){
			result = new Result("短信验证已关闭!");
		}
		return JSON;
	}
	
	public String update_sms_page() {
		List<Dictionary> dicList = dictionaryService.findByType("SMS_SWITCH"); // 短信开关
		Boolean is_open = false;
		if (dicList.size() > 0) {
			Dictionary dic = dicList.get(0);
			String dicVal = dic.getVal();
			if("SMS_SWITCH_Y".equals(dicVal)){
				is_open = true;
			}else if("SMS_SWITCH_N".equals(dicVal)){
				is_open = false;
			}
		}
		this.addParameter("is_open", is_open);
		result = "/uac/sms_switch_update_page.jsp";
		return JSP;
	}
	
	public String update_user_page() {
		if (user == null) {
			user = new User();
		}
		SubUser u = (SubUser) BaseUserContext.getCurrentUser();
		List<BaseRole> roles = u.getRoles();
		for(BaseRole r: roles){
			if(StringUtils.equals(r.getName(), "地市管理员")  ){
				super.addParameter("isMayor", true);
				user.setOrg(u.getUser().getOrg());
				break;
			}
			super.addParameter("isMayor", false);
		}
		//f(u.getRoles())
		result = "/uac/update_user_page.jsp";
		return JSP;
	}
	
	public String find_sms_page() {
		Page page = createPage();
		if (user == null) {
			user = new User();
		}
		page = smsService.querySmsPage(user, page);
		//page = smsService.findRandomCodeByStaffCode("");
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		Boolean unEditable = getParamByName("unEditable", Boolean.class);
		this.addParameter("unEditable", unEditable);
		this.addParameter("user", user);
		this.addParameter("pageBar", pageBar);
		this.addParameter("page", page);
		result = "/uac/find_user_page.jsp";
		return JSP;
	}
	
	/**
	 * 下载附件
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public void downFile() throws NumberFormatException, Exception {
		String file_name = this.getRequest().getParameter("file_name");
		file_name = file_name.replace("\\.\\./", "").replace("../", "");
		if(StringUtils.isEmpty(file_name)){
			//result = new Result(Result.FAIL, "找不到文件");
			return;
		}
		String root = PropertiesUtil.getPorpertyString("project.upload.file.folder");
		// 1.创建文件夹
		File folder = new File(super.getWebRoot()+"/"+root);
		if (!folder.exists()) {
			folder.mkdirs();
			//result = new Result(Result.FAIL, "找不到文件");
			return ;
		}
		
		File f = new File(folder.getPath()+"/"+file_name);
		if (!f.exists()) {
			//result = new Result(Result.FAIL, "找不到文件");
			return;
		}
		HttpServletResponse response = WebUtils.getResponse();
		OutputStream os = response.getOutputStream();
		response.setHeader("Content-Type", "application/file");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + java.net.URLEncoder.encode(file_name, "UTF-8"));

		int len = 0;
		byte buf[] = new byte[1024];
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(f));
		ServletOutputStream outputStream = response.getOutputStream();
		while ((len = inputStream.read(buf)) != -1) {
			outputStream.write(buf, 0, len);
		}
		inputStream.close();
		outputStream.flush();
		outputStream.close();
		//return STREAM;
	}

	@Resource
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Resource
	public void setMenuFilter(MenuFilter menuFilter) {
		this.menuFilter = menuFilter;
	}

	@Resource
	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	@Resource
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Resource
	public void setFavoritesService(FavoritesService favoritesService) {
		this.favoritesService = favoritesService;
	}

	@Resource
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@Resource
	public void setSubUserService(SubUserService subUserService) {
		this.subUserService = subUserService;
	}

	public SubUser getSubUser() {
		return subUser;
	}

	public void setSubUser(SubUser subUser) {
		this.subUser = subUser;
	}

	@Resource(name = "loginSubUserMap")
	public void setRedisMap(Map<String, String> redisMap) {
		this.redisMap = redisMap;
	}

	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@Resource
	public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
		this.passwordPolicyService = passwordPolicyService;
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

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	@Resource
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

}
