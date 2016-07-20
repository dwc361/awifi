package org.roof.web.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.roof.commons.SysConstants;
import org.roof.dataaccess.Page;
import org.roof.safety.service.MD5Util;
import org.roof.security.BaseUserContext;
import org.roof.security.entity.BaseRole;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.PageUtils;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.service.MenuFilter;
import org.roof.web.resources.entity.Module;
import org.roof.web.role.entity.Roles;
import org.roof.web.user.dao.StaffDao;
import org.roof.web.user.entity.Staff;
import org.roof.web.user.service.StaffService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("staffAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class StaffAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private Staff staff = new Staff();
	private StaffDao staffDao;
	private StaffService staffService;
	private Long currentPage;
	private MenuFilter menuFilter;

	public String goLogin() {
		String errorCode = this.getParamByName("errorCode", String.class);
		this.addParameter("errorCode", errorCode == null ? "null" : "'" + errorCode + "'");
		result = "/roof-web/web/user/staff_goLogin.jsp";
		return JSP;
	}

	public String goMain() {
		Menu menu = staffDao.load(Menu.class, 1L);
		menuFilter.doFilter(menu);
		Map<Class<?>, String[]> map = new HashMap<Class<?>, String[]>();
		map.put(Menu.class, new String[] { "parent" });
		map.put(Module.class, new String[] { "parent", "baseRole", "parameters", "returnFields" });
		String s = JSONObject.fromObject(menu, Result.createJsonConfig(map)).toString();
		this.addParameter("menuJson", s);
		result = "/roof-web/web/user/staff_goMain.jsp";
		return JSP;
	}

	/**
	 * 登录验证
	 * 
	 * @return
	 */
	public String login() {
		try {
			if (staffService.isLoginError(staff.getUsername())) {
				result = new Result(Result.FAIL, "30分钟之内登录失败次数过多，请稍后再试");
				return JSON;
			}
			Staff loginUser = staffService.login(staff);
			if (loginUser == null) {
				result = new Result(Result.FAIL,
						"用户名或者密码错误，30分钟之内剩余"
								+ (SysConstants.getAllowableErrorCount() - 1 - staffService.findErrorCount(staff
										.getUsername())) + "次!");
			} else {
				result = new Result("用户登录成功!");
			}
			staffService.saveLoginLog(loginUser, staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON;
	}

	public String list() {
		Page page = this.createPage();
		page = staffDao.list(page, staff);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		this.addParameter("page", page);
		this.addParameter("pageBar", pageBar);
		result = "/roof-web/web/user/staff_list.jsp";
		return JSP;
	}

	public String list_select() {
		Page page = this.createPage();
		page = staffDao.list(page, staff);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		this.addParameter("page", page);
		this.addParameter("pageBar", pageBar);
		this.addParameter("defPwd", MD5Util.getMD5String(SysConstants.DEFAULT_PWD).toUpperCase());
		result = "/roof-web/web/user/staff_list_select.jsp";
		return JSP;
	}

	public String detail() {

		return JSP;
	}

	public String update() {
		if (staff != null) {
			staff.setPassword(null);
			if (staff.getRoles() == null) {
				staff.setRoles(new ArrayList<BaseRole>());
			}
			if (staff.getOrg() == null || staff.getOrg().getOrg_id() == null) {
				staff.setOrg(null);
			}
			staffDao.updateIgnoreNull(staff);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String update_pwd() {
		if (staff != null) {
			staffDao.updateIgnoreNull(staff);
			result = new Result("修改成功!");
		} else {
			result = new Result("修改失败!");
		}
		return JSON;
	}

	public String update_pwd_page() {
		staff = (Staff) BaseUserContext.getCurrentUser();
		result = "/roof-web/web/user/staff_update_pwd_page.jsp";
		return JSP;
	}

	public String update_page() {
		Long id = this.getParamByName("id", Long.class);
		staff = staffDao.load(Staff.class, id);
		List<Roles> roleses = staffDao.loadAll(Roles.class);
		this.addParameter("roleses", roleses);
		result = "/roof-web/web/user/staff_update_page.jsp";
		return JSP;
	}

	public String create() {
		if (staff != null) {
			staff.setCreate_date(new Date());
			staffDao.save(staff);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String delete() {
		Long id = this.getParamByName("id", Long.class);
		Staff staff = new Staff();
		staff.setId(id);
		staffDao.delete(staff);
		result = new Result("删除成功!");
		return JSON;
	}

	public String sameUsername() {
		String username = this.getParamByName("username", String.class);
		Long id = this.getParamByName("id", Long.class);
		result = !staffService.sameUsername(id, username);
		return JSON;
	}

	public String create_page() {
		List<Roles> roleses = staffDao.loadAll(Roles.class);
		this.addParameter("roleses", roleses);
		result = "/roof-web/web/user/staff_create_page.jsp";
		return JSP;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Resource
	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	@Resource
	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}

	@Resource
	public void setMenuFilter(MenuFilter menuFilter) {
		this.menuFilter = menuFilter;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

}
