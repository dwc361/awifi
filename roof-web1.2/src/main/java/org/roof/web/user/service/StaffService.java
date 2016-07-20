package org.roof.web.user.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.commons.RoofIPUtils;
import org.roof.commons.SysConstants;
import org.roof.exceptions.DaoException;
import org.roof.log.SysLoginLog;
import org.roof.security.entity.BaseRole;
import org.roof.struts2.WebUtils;
import org.roof.web.user.dao.StaffDao;
import org.roof.web.user.entity.Staff;
import org.springframework.stereotype.Component;

@Component
public class StaffService {
	private StaffDao staffDao;

	/**
	 * 按登录账号找到最新的成功登录日志
	 * 
	 * @param username
	 * @return
	 */
	public SysLoginLog findLastedLog(String username) {
		SysLoginLog s = new SysLoginLog();
		s.setUsername(username);
		s.setSts(1L);
		return staffDao.findLastedLog(s);
	}

	public Boolean sameUsername(Long id, String username) {
		Staff staff = new Staff();
		staff.setUsername(username);
		Long l = staffDao.readUserCount(staff);
		if (l == 0) {
			return false;
		}
		if (id != null && id != 0L) {
			staff = staffDao.load(Staff.class, id);
			if (StringUtils.equals(username, staff.getUsername())) {
				return false;
			}
		}
		return true;
	}

	public boolean isAdmin(Staff user) {
		boolean flg = false;
		List<BaseRole> roles = user.getRoles();
		for (BaseRole baseRole : roles) {
			if (Long.valueOf(PropertiesUtil.getPorpertyString("core.superadmin")).equals(baseRole.getId())) {
				flg = true;
				break;
			}
		}
		return flg;
	}

	public void saveLoginLog(Staff dbFind, Staff formSubmit) {
		SysLoginLog sysLoginLog = new SysLoginLog();
		sysLoginLog.setUsername(formSubmit.getUsername());
		sysLoginLog.setIp(RoofIPUtils.getIp(WebUtils.getRequest()));
		sysLoginLog.setLog_time(new Date());
		if (dbFind == null) {
			sysLoginLog.setSts(0L);
			sysLoginLog.setMessage("用户名或者密码错误，30分钟之内剩余"
					+ (SysConstants.getAllowableErrorCount() - 1 - this.findErrorCount(formSubmit.getUsername()))
					+ "次!");
		} else {
			sysLoginLog.setSts(1L);
			sysLoginLog.setMessage("用户登录成功!");
			sysLoginLog.setStaff_name(dbFind.getName());
		}
		staffDao.save(sysLoginLog);
		if (sysLoginLog.getSts().equals(1L)) {// 用户登录成功!
			Long count = dbFind.getLogin_count();
			count = ((count == null) ? 0L : count);
			dbFind.setLogin_count(count + 1);
			dbFind.setSysLoginLog(sysLoginLog);
			staffDao.updateIgnoreNull(dbFind);
		}
	}

	public Staff login(Staff paramObj) throws DaoException {
		if (paramObj == null) {
			return null;
		}
		Staff result = (Staff) staffDao.findByExampleSingle(paramObj);
		// Staff result = (Staff)
		// staffDao.selectForObject("staff_exp_select_user_login_data",
		// paramObj);
		return result;
	}

	public boolean isLoginError(String staff_no) {
		return (this.findErrorCount(staff_no) >= SysConstants.getAllowableErrorCount());
	}

	public int findErrorCount(String staff_no) {
		Object obj = staffDao.selectForObject("staff_exp_select_user_login_error_count", staff_no);
		return Integer.parseInt(obj.toString());
	}

	public List<Staff> list(Staff params) {
		return staffDao.list(params);
	}

	@Resource
	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

}
