package com.zjhcsoft.uac.account.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.log.entity.AuthLog;
import com.zjhcsoft.uac.log.service.AuthLogService;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

	private UserService userService;
	private UserDao userDao;
	private AuthLogService authLogService;

	@Test
	public void testCopyToLdap() throws Exception {
		// CurrentSpringContext.setCurrentContext(this.applicationContext);
		List<User> users = userDao.loadAll(User.class);
		for (User user : users) {
			userService.copyToLdap(user);
		}
	}

	@Test
	public void fill() {
		List<AuthLog> authLogs = userDao.loadAll(AuthLog.class);
		for (AuthLog authLog : authLogs) {
			if(authLog.getRequestUrl() != null) {
				authLogService.fillRes(authLog);
			}
			userDao.update(authLog);
		}
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
	public void setAuthLogService(AuthLogService authLogService) {
		this.authLogService = authLogService;
	}

}
