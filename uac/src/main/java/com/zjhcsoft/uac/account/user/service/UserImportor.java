package com.zjhcsoft.uac.account.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.org.entity.Organization;
import org.springframework.stereotype.Component;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.IExcelReader;
import com.zjhcsoft.exceldb.support.impl.PoiExcelReader;
import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.User;

@Component
public class UserImportor {

	private static final long DIC_USER_SCOPE_TEMP_ID = 52L;
	private static final long USER_CATEGORY_DIANXIN_ID = 55L;

	private static final long DIC_FEMALE_ID = 57L;
	private static final long DIC_MALE_ID = 58L;
	private static final Logger LOGGER = Logger.getLogger(UserImportor.class);
	private UserService userService;
	private UserDao userDao;

	public void exc(File file, XslDb xslDb) {
		IExcelReader excelReader;
		InputStream in = null;
		int row = xslDb.getIgnore();
		try {
			in = new FileInputStream(file);

			excelReader = new PoiExcelReader(in, xslDb);
			while (excelReader.hasNext()) {
				row++;
				try {
					Map<String, Object> map = excelReader.next();
					User user = createUser(map, row);
					if (user != null) {
						userService.save(user);
					}
				} catch (Exception e) {
					LOGGER.error("导入第[" + row + "]行出现异常", e);
				}
			}
		} catch (FileNotFoundException e1) {
			LOGGER.error(e1.getMessage(), e1);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	public User createUser(Map<String, Object> map, int row) {
		User user = new User();
		user.setName(toString(map.get("name")));
		user.setUsername(toString(map.get("username")));
		if (StringUtils.equals(toString(map.get("gender")), "F")) {
			user.setGender(new Dictionary(DIC_MALE_ID));
		} else if (StringUtils.equals(toString(map.get("gender")), "T")) {
			user.setGender(new Dictionary(DIC_FEMALE_ID));
		} else {
			user.setGender(new Dictionary(DIC_MALE_ID));
		}
		user.setIdNumber(toString(map.get("idNumber")));
		user.setTel(toString(map.get("tel")));
		if (StringUtils.isBlank(user.getTel())) {
			return null;
		}
		if (StringUtils.isBlank(user.getIdNumber())) {
			return null;
		}
		if (StringUtils.isBlank(user.getUsername())) {
			return null;
		}

		if (userService.hasIdNumber(user.getIdNumber())) {
			LOGGER.warn("[" + row + "]行[身份证]已经存在:" + map);
			return null;
		}
		if (userService.hasUserName(user.getUsername())) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.warn("[" + row + "]行[用户名]已经存在:" + map);
			}
			return null;
		}
		user.setScope(new Dictionary(DIC_USER_SCOPE_TEMP_ID));
		user.setCategory(new Dictionary(USER_CATEGORY_DIANXIN_ID));

		Organization org = getOrg(ObjectUtils.toString(
				map.get("REGION_NAMEFROMCHANNE"), ""));
		user.setOrg(org);
		if (StringUtils.isEmpty(user.getIdNumber())) {
			user.setPassword(user.getUsername());
		} else {
			user.setPassword(StringUtils.substring(user.getIdNumber(), user
					.getIdNumber().length() - 7,
					user.getIdNumber().length() - 1));
		}
		user.setCreate_date(new Date());
		return user;
	}

	private Organization getOrg(String org_name) {
		if (StringUtils.isEmpty(org_name)) {
			return null;
		}
		org_name = StringUtils.substring(org_name, 0, 2);
		return userDao.findOrgByName(org_name);
	}

	private String toString(Object o) {
		return StringUtils.strip(ObjectUtils.toString(o, ""));
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
