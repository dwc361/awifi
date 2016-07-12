package com.zjhcsoft.uac.account.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.exceptions.ApplicationException;
import org.roof.exceptions.DaoException;
import org.roof.web.dictionary.dao.DictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.org.entity.Organization;
import org.springframework.stereotype.Component;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.IExcelReader;
import com.zjhcsoft.exceldb.support.impl.PoiExcelReader;
import com.zjhcsoft.uac.account.user.dao.SubUserDao;
import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.ErrorUser;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.authorization.resource.dao.DbDao;
import com.zjhcsoft.uac.authorization.resource.dao.HostDao;
import com.zjhcsoft.uac.authorization.resource.dao.NetDeviceDao;
import com.zjhcsoft.uac.authorization.resource.dao.NetSecurityDeviceDao;
import com.zjhcsoft.uac.authorization.resource.dao.OsDao;
import com.zjhcsoft.uac.authorization.resource.entity.App;
import com.zjhcsoft.uac.authorization.resource.entity.Db;
import com.zjhcsoft.uac.authorization.resource.entity.Host;
import com.zjhcsoft.uac.authorization.resource.entity.NetDevice;
import com.zjhcsoft.uac.authorization.resource.entity.SysResource;
import com.zjhcsoft.uac.authorization.resource.service.DbService;
import com.zjhcsoft.uac.authorization.resource.service.HostService;
import com.zjhcsoft.uac.authorization.resource.service.NetDeviceService;
import com.zjhcsoft.uac.authorization.resource.service.NetSecurityDeviceService;
import com.zjhcsoft.uac.authorization.resource.service.OsService;
import com.zjhcsoft.uac.blj.service.BljService;

@Component
public class AccountImportor {
	private static final long DIC_USER_SCOPE_PAN_ID = 52L;
	private static final long DIC_USER_SCOPE_TEMP_ID = 53L;
	private static final long USER_CATEGORY_DIANXIN_ID = 55L;
	private static final long USER_CATEGORY_HAOBAI_ID = 96000L;
	private static final long USER_PRIVILEG_ADMIN_ID = 61L;
	private static final long USER_PRIVILEG_USER_ID = 62L;

	private static final long DIC_FEMALE_ID = 58L;
	private static final long DIC_MALE_ID = 57L;
	
	private static final String SERVE_TYPE = "SERVE_TYPE";
	private static final String SYSTEM_CATEGORY = "SYSTEM_CATEGORY";
	private static final String DB_TYPE = "DB_TYPE";
	
	private static final Logger LOGGER = Logger.getLogger(AccountImportor.class);
	private UserService userService;
	private UserDao userDao;
	private SubUserService subuserService;

	private HostDao hostDao;
	private HostService hostService;
	private DbDao dbDao;
	private DbService dbService;
	private OsDao osDao;
	private OsService osService;
	private NetDeviceDao netdevDao;
	private NetDeviceService netDeviceService;
	private NetSecurityDeviceDao netSecurityDeviceDao;
	private NetSecurityDeviceService netSecurityDeviceService;
	private SubUserDao subUserDao;
	
	private DictionaryDao dictionaryDao;
	
	public BljService bljService;

	public void exc(File file, XslDb xslDb, Long sysResource_id) {
		IExcelReader excelReader;
		List<Map<String, Object>> tmbtx = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> errortel = new ArrayList<Map<String, Object>>();
		InputStream in = null;
		int cc = 0;
		int row = xslDb.getIgnore();
		try {
			in = new FileInputStream(file);
			excelReader = new PoiExcelReader(in, xslDb);
			while (excelReader.hasNext()) {
				row++;
				try {
					Map<String, Object> map = excelReader.next();
					if ("综合资源系统".equals(toString(map.get("ip")))) {
						sysResource_id = 251L;
					} else if ("综合网络激活系统".equals(toString(map.get("ip")))) {
						sysResource_id = 65000L;
					} else if ("服务开通系统".equals(toString(map.get("ip")))) {
						sysResource_id = 203L;
					} else if ("服务保障".equals(toString(map.get("ip")))) {
						sysResource_id = 1250L;
					} else if ("施工调度".equals(toString(map.get("ip")))) {
						sysResource_id = 65350L;
					} else if ("CRM".equals(toString(map.get("ip")))) {
						sysResource_id = 200L;
					} else if ("BSN".equals(toString(map.get("ip")))) {
						sysResource_id = 65150L;
					} else if ("划小系统".equals(toString(map.get("ip")))) {
						sysResource_id = 201L;
					} else if ("维系系统".equals(toString(map.get("ip")))) {
						sysResource_id = 65250L;
					} else if ("10000客服".equals(toString(map.get("ip")))) {
						sysResource_id = 69201L;
					}else {

					}
					if(sysResource_id == 0L){
						continue;
					}
					User user = createUser(map, row, true);
					if (user != null) {
						userService.save(user);
						SubUser subuser = createSubUser(map, row,sysResource_id);
						if (subuser != null) {
							cc++;
							subuserService.save(subuser);
						}
					} else {
						if (userService.hasIdNumber(toString(map.get("idNumber")).toLowerCase())) {
							SubUser subuser = createSubUser(map, row,
									sysResource_id);
							if (subuser != null) {
								cc++;
								subuserService.save(subuser);
							}
						} else {
							BigDecimal db = new BigDecimal(toString(map.get("tel")));
							String tel = db.toPlainString();
							if (userService.hasUserName(tel)) {
								errortel.add(map);
							}
							LOGGER.error("导入第[" + row + "]没有导入");
						}
					}
				} catch (Exception e) {
					LOGGER.error("导入第[" + row + "]行出现异常", e);
				}
			}
		} catch (FileNotFoundException e1) {
			LOGGER.error(e1.getMessage(), e1);
		} finally {
			System.out.println("ffffffffffffffffffffff===" + cc);
			System.out.println("eeeeeeeeeeeeeeeeeeeeee===" + errortel.size());
			for (Map<String, Object> m : tmbtx) {
				ErrorUser erroruser = createErrorUser(m, sysResource_id,"同身份证不同姓名");
				if (erroruser != null) {
					userDao.save(erroruser);
				}
			}
			for (Map<String, Object> m : errortel) {
				ErrorUser erroruser = createErrorUser(m, sysResource_id,"手机号码已经存在");
				if (erroruser != null) {
					userDao.save(erroruser);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	// 资源账号导入
	public void exc2(Map<String, Object> map, String type, int row) {
		Long sysResource_id = null;
		List<Map<String, Object>> tmbtx = new ArrayList<Map<String, Object>>();
		try {
			if ("Host".endsWith(type)) {
				Host host = new Host();
				host.setIp(toString(map.get("ip")).trim());
				host.setPort(substring(toString(map.get("port")).trim()));
				List<Host> list = hostService.select(host);
				if (list.size() != 0) {
					for (int i = 0; i < list.size(); i++) {
						if (toString(list.get(i).getPort()).equals(substring(toString(map.get("port")).trim()))) {
							sysResource_id = list.get(i).getId();
							continue;
						}
					}
				} else {
					LOGGER.error("第[" + row + "]资源还没有导入"
							+ toString(map.get("ip")).trim());
				}
			} else if ( "Db".endsWith(type)) {
				Db db = new Db();
				db.setIp(toString(map.get("ip")).trim());
				db.setPort(substring(toString(map.get("port")).trim()));
				db.setDb_name(toString(map.get("host_name")).trim());
				List<Db> list = dbService.select(db);
				if (list.size() != 0) {
					for (int i = 0; i < list.size(); i++) {
						if (toString(list.get(i).getPort()).trim().equals(substring(toString(map.get("port")).trim()))) {
							sysResource_id = list.get(i).getId();
						}
					}
				} else {
					LOGGER.error("第[" + row + "]资源还没有导入");
				}
			} else if ("netDevice".endsWith(type)) {
				NetDevice netDevice = new NetDevice();
				netDevice.setIp(toString(map.get("ip")).trim());
				netDevice.setPort(substring(toString(map.get("port")).trim()));
				List<NetDevice> list = netDeviceService.select(netDevice);
				if (list.size() != 0) {
					for (int i = 0; i < list.size(); i++) {
						if (toString(list.get(i).getPort()).equals(substring(toString(map.get("port"))))) {
							sysResource_id = list.get(i).getId();
						}
					}
				} else {
					LOGGER.error("第[" + row + "]资源还没有导入");
				}
			}
			User user = createUser(map, row, true);
			if (user != null) {
				userService.save(user);
				SubUser subuser = createSubUser(map, row, sysResource_id);
				if (subuser != null) {
					subuserService.save(subuser);
				}
			} else {
				if (userService.hasIdNumber(toString(map.get("idNumber")).toLowerCase())) {
					SubUser subuser = createSubUser(map, row, sysResource_id);
					if (subuser != null) {
						subuserService.save(subuser);
					}else {
						updateSubUser(map, row, sysResource_id);
					}
				} else {
					tmbtx.add(map);
					LOGGER.error("第[" + row + "]身份证不匹配");
				}
			}
		} catch (Exception e) {
			LOGGER.error("导入第[" + row + "]行出现异常", e);

		} finally {
			for (Map<String, Object> m : tmbtx) {
				ErrorUser erroruser = createErrorUser(m, sysResource_id,
						"同手机不同身份证");
				if (erroruser != null) {
					userDao.save(erroruser);
				}
			}

		}
	}

	/**
	 * 堡垒机系统资源导入
	 * 
	 * @param file
	 * @param xslDb
	 */
	public void sysresource(File file, XslDb xslDb, String type) {
		IExcelReader excelReader;
		InputStream in = null;
		Long appid = 69150L;
		int row = xslDb.getIgnore();
		try {
			in = new FileInputStream(file);
			excelReader = new PoiExcelReader(in, xslDb);
			while (excelReader.hasNext()) {
				row++;
				try {
					Map<String, Object> map = excelReader.next();
					type = toString(map.get("sys_type")).trim();
					if (map.size() != 0) {
						if ("Host".equals(type)) {
							Host host = new Host();
							App app = new App();
							host.setIp(toString(map.get("ip")));
							host.setPort(substring(toString(map.get("port"))));
							String name = toString(map.get("host_name"));
							host.setHost_name(name);
							Host list = hostService.selectObject(host);
							host.setApp(app);
							String name2 = toString(map.get("sys_name"))+"_"+host.getIp()+":"+host.getPort();
							host.setName(name2);
							host.setState(new Dictionary(107L));
							Long group =Long.valueOf(toString(map.get("group")).trim());
							host.setGroup(new Dictionary(group));
							app.setId(appid);
							if (map.get("db_type") != null) {
								String tmp = toString(map.get("db_type")).trim().toUpperCase();
								host.setHost_type(tmp);
								host.setServe_name(tmp);
								if(getDic(SERVE_TYPE, tmp) != null){
									host.setServe_type(getDic(SERVE_TYPE, tmp));
								}
							}
							if (map.get("os_type") != null) {
								String tmp = toString(map.get("os_type")).trim();
								if(getDic(SYSTEM_CATEGORY, tmp) != null){
									host.setTypename(getDic(SYSTEM_CATEGORY, tmp));
								}
							}
							if (list.getId() == null) {
								hostDao.save(host);
								bljService.Systemadd(host);
								bljService.Serviceadd(host);
								exc2(map, type, row);
							} else {
								if (list.getHost_type() == null&& map.get("db_type") != null) {
									host.setId(list.getId());
									hostService.updateIgnoreNull(host);
								}
								exc2(map, type, row);
							}
						} else if ("Db".equals(type)) {
							Db db = new Db();
							App app = new App();
							db.setIp(toString(map.get("ip")).trim());
							db.setPort(substring(toString(map.get("port")).trim()));
							db.setDb_name(toString(map.get("host_name")));
							Db list = dbService.selectObject(db);
							String name = toString(map.get("sys_name"))+"_"+db.getIp()+":"+db.getPort();
							db.setName(toString(name));
							db.setState(new Dictionary(107L));
							app.setId(appid);
							db.setApp(app);
							Long group =Long.valueOf(toString(map.get("group")).trim());
							db.setGroup(new Dictionary(group));
							if (map.get("db_type") != null) {
								String tmp = toString(map.get("db_type")).trim().toUpperCase();
								if(getDic(DB_TYPE, tmp) != null){
									db.setDbType(getDic(DB_TYPE, tmp));
								}
							}
							if (map.get("os_type") != null) {
								String tmp = toString(map.get("os_type")).trim();
								if(getDic(SYSTEM_CATEGORY, tmp) != null){
									db.setTypename(getDic(SYSTEM_CATEGORY, tmp));
								}
							}
							if (list.getId() == null) {
								dbDao.save(db);
								bljService.Systemadd(db);
								bljService.Serviceadd(db);
								exc2(map, type, row);
							} else {
								if (list.getDbType() == null&& map.get("db_type") != null) {
									db.setId(list.getId());
									dbService.updateIgnoreNull(db);
								}
								exc2(map, type, row);
							}
						} else if ("netDevice".equals(type)) {
							NetDevice netDevice = new NetDevice();
							App app = new App();
							netDevice.setIp(toString(map.get("ip")));
							netDevice.setPort(substring(toString(map.get("port"))));
							NetDevice list = netDeviceService.selectObject(netDevice);
							String name = toString(map.get("host_name"))+"_"+netDevice.getIp()+":"+netDevice.getPort();
							netDevice.setName(name);
							//netDevice.setName((toString(map.get("host_name"))));
							netDevice.setState(new Dictionary(107L));
							app.setId(appid);
							netDevice.setApp(app);
							Long group =Long.valueOf(toString(map.get("group")).trim());
							netDevice.setGroup(new Dictionary(group));
							if (map.get("db_type") != null) {
								String tmp = toString(map.get("db_type")).trim().toUpperCase();
								netDevice.setServe_name(tmp);
								if(getDic(SERVE_TYPE, tmp) != null){
									netDevice.setServe_type(getDic(SERVE_TYPE, tmp));
								}
							}
							if (map.get("os_type") != null) {
								String tmp = toString(map.get("os_type")).trim();
								if(getDic(SYSTEM_CATEGORY, tmp) != null){
									netDevice.setTypename(getDic(SYSTEM_CATEGORY, tmp));
								}
							}
							if (list.getId() == null) {
								netdevDao.save(netDevice);
								bljService.Systemadd(netDevice);
								bljService.Serviceadd(netDevice);
								exc2(map, type, row);
							} else {
								exc2(map, type, row);
							}
						}
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

	public String substring(String t) {
		String s = t;
		if (t.indexOf(".") != -1) {
			String[] m = t.split("\\.");
			s = m[0];
		}
		return s;
	}

	public User createUser(Map<String, Object> map, int row, boolean is_id) {
		User user = new User();
		user.setName(toString(map.get("name")));

		BigDecimal db = new BigDecimal(toString(map.get("tel")));
		String tel = db.toPlainString();

		user.setUsername(tel);
		// user.setUsername(toString(map.get("username")));
		if (StringUtils.equals(toString(map.get("gender")), "F")) {
			user.setGender(new Dictionary(DIC_MALE_ID));
		} else if (StringUtils.equals(toString(map.get("gender")), "M")) {
			user.setGender(new Dictionary(DIC_FEMALE_ID));
		} else {
			user.setGender(new Dictionary(DIC_MALE_ID));
		}
		user.setIdNumber(toString(map.get("idNumber")).toLowerCase());
		user.setTel(tel);
		user.setMail(toString(map.get("mail")));
		if (StringUtils.isBlank(user.getTel())) {
			return null;
		}
		if (is_id && StringUtils.isBlank(user.getIdNumber())) {
			return null;
		}
		if (StringUtils.isBlank(user.getUsername())) {
			return null;
		}

		if (is_id && userService.hasIdNumber(user.getIdNumber())) {
			LOGGER.warn("[" + row + "]行[身份证]已经存在:" + map);
			return null;
		}
		if (userService.hasUserName(user.getUsername())) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.warn("[" + row + "]行[用户名]已经存在:" + map);
			}
			return null;
		}

		// 账号性质
		if (StringUtils.equals(toString(map.get("scope")), "长期")) {
			user.setScope(new Dictionary(DIC_USER_SCOPE_PAN_ID));
		} else if (StringUtils.equals(toString(map.get("scope")), "临时")) {
			user.setScope(new Dictionary(DIC_USER_SCOPE_TEMP_ID));
		} else {
			user.setScope(new Dictionary(DIC_USER_SCOPE_TEMP_ID));
		}
		// 用户类别
		if (StringUtils.equals(toString(map.get("category")), "电信")) {
			user.setCategory(new Dictionary(USER_CATEGORY_DIANXIN_ID));
		} else if (StringUtils.equals(toString(map.get("category")), "号百")) {
			user.setCategory(new Dictionary(USER_CATEGORY_HAOBAI_ID));
		} else {
			// user.setCategory(new Dictionary(USER_CATEGORY_DIANXIN_ID));
		}
		Organization org = getOrg(ObjectUtils.toString(
				map.get("REGION_NAMEFROMCHANNE"), ""));
		// Long temorg =
		// Long.valueOf(substring(toString(map.get("REGION_NAMEFROMCHANNE")))) ;
		user.setOrg(org);
		if (StringUtils.isEmpty(user.getIdNumber())) {
			user.setPassword(user.getUsername());
		} else {
			user.setPassword(StringUtils.substring(user.getIdNumber(), user
					.getIdNumber().length() - 8, user.getIdNumber().length()));
		}
		user.setCreate_date(new Date());
		user.setModifyTime(new Date());
		return user;
	}

	/**
	 * 
	 * @param map
	 * @param row
	 * @param sysResource_id
	 *            对应系统id
	 * @return
	 */
	public SubUser createSubUser(Map<String, Object> map, int row,
			Long sysResource_id) {
		SubUser subuser = new SubUser();

		if (isNum(toString(map.get("username")))) {
			BigDecimal db = new BigDecimal(toString(map.get("username")));
			String username = db.toPlainString();
			subuser.setUsername(substring(username));
		} else {
			subuser.setUsername(toString(map.get("username")));
		}

		subuser.setPassword(toString(map.get("password")).trim());
		if (StringUtils.isBlank(subuser.getUsername())) {
			return null;
		}

		// 账号性质
		if (StringUtils.equals(toString(map.get("scope")), "长期")) {
			subuser.setScope(new Dictionary(DIC_USER_SCOPE_PAN_ID));
		} else if (StringUtils.equals(toString(map.get("gender")), "临时")) {
			subuser.setScope(new Dictionary(DIC_USER_SCOPE_TEMP_ID));
		} else {
			subuser.setScope(new Dictionary(DIC_USER_SCOPE_PAN_ID));
		}
		// 权限类别
		if (StringUtils.equals(toString(map.get("privilege")), "管理员")) {
			subuser.setPrivilege(new Dictionary(USER_PRIVILEG_ADMIN_ID));
		} else if (StringUtils.equals(toString(map.get("privilege")), "普通用户")) {
			subuser.setPrivilege(new Dictionary(USER_PRIVILEG_USER_ID));
		} else {
			subuser.setPrivilege(new Dictionary(USER_PRIVILEG_USER_ID));
		}

		try {
			User u = userDao.findByIdNumber(toString(map.get("idNumber"))
					.toLowerCase());
			subuser.setUser(u);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		SysResource sysResource = (SysResource) subUserDao.load(
				SysResource.class, sysResource_id, false);
		if (sysResource == null) {
			return null;
		}
		subuser.setSysResource(sysResource);
		subuser.setCreate_date(new Date());
		subuser.setModifyTime(new Date());
		if (subuserService.hasSubUser(sysResource_id, subuser.getUsername(),
				subuser.getUser().getId())) {
			return null;
		}

		return subuser;
	}
	
	
	/**
	 * 
	 * @param map
	 * @param row
	 * @param sysResource_id
	 *            对应系统id
	 * @return
	 */
	public void updateSubUser(Map<String, Object> map, int row,Long sysResource_id) {
		String username = null;
		if (isNum(toString(map.get("username")))) {
			BigDecimal db = new BigDecimal(toString(map.get("username")));
			String username1 = db.toPlainString();
			username = substring(username1);
		} else {
			username = toString(map.get("username"));
		}

		String pwd = toString(map.get("password")).trim();
		if (StringUtils.isBlank(pwd)||StringUtils.isBlank(username)) {
			return;
		}
		String id = toString(map.get("idNumber")).toLowerCase();
		String hql = "from SubUser t where t.sysResource.id = ? and t.username = ? and t.user.idNumber = ? and t.enabled = true  order by id"; //
		@SuppressWarnings("unchecked")
		List<SubUser> users = (List<SubUser>) userDao.findForList(hql,sysResource_id, username, id);
		for (SubUser u : users) {
			u.setPassword(pwd);
			u.setModifyTime(new Date());
			try {
				subuserService.updateIgnoreNull(u);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public ErrorUser createErrorUser(Map<String, Object> map,
			Long sysResource_id, String error) {
		ErrorUser erroruser = new ErrorUser();
		erroruser.setName(toString(map.get("name")));
		// erroruser.setUsername(toString(map.get("username")));
		if (isNum(toString(map.get("username")))) {
			BigDecimal db = new BigDecimal(toString(map.get("username")));
			String username = db.toPlainString();
			erroruser.setUsername(substring(username));
		} else {
			BigDecimal db = new BigDecimal(toString(map.get("username")));
			String username = db.toPlainString();
			erroruser.setUsername(substring(username));
		}

		BigDecimal db = new BigDecimal(toString(map.get("tel")));
		String tel = db.toPlainString();
		erroruser.setTel(tel);

		if (StringUtils.equals(toString(map.get("gender")), "F")) {
			erroruser.setGender("男");
		} else if (StringUtils.equals(toString(map.get("gender")), "M")) {
			erroruser.setGender("女");
		} else {
			erroruser.setGender("男");
		}
		erroruser.setIdNumber(toString(map.get("idNumber")).toLowerCase());

		erroruser.setMail(toString(map.get("mail")));

		// 账号性质
		if (StringUtils.equals(toString(map.get("scope")), "长期")) {
			erroruser.setScope("长期");
		} else if (StringUtils.equals(toString(map.get("gender")), "临时")) {
			erroruser.setScope("临时");
		} else {
			erroruser.setScope("长期");
		}
		// 权限类别
		if (StringUtils.equals(toString(map.get("privilege")), "管理员")) {
			erroruser.setPrivilege("管理员");
		} else if (StringUtils.equals(toString(map.get("privilege")), "普通用户")) {
			erroruser.setPrivilege("普通用户");
		} else {
			erroruser.setPrivilege("普通用户");
		}
		erroruser.setCategory(toString(map.get("category")));

		erroruser.setCompany(toString(map.get("REGION_NAMEFROMCHANNE")));
		SysResource sysResource = (SysResource) subUserDao.load(
				SysResource.class, sysResource_id, false);
		if (sysResource != null) {
			String dic = new Date().toString() + " " + sysResource.getName();
			erroruser.setDescription(dic);
		}
		erroruser.setError(error);
		return erroruser;
	}

	private Organization getOrg(String org_name) {
		if (StringUtils.isEmpty(org_name)) {
			return null;
		}
		org_name = StringUtils.substring(org_name, 0, 2);
		return userDao.findOrgByName(org_name);
	}
	
	private Dictionary getDic(String type,String text ) throws Exception{
		if (StringUtils.isEmpty(type)||StringUtils.isEmpty(text)) {
			return null;
		}
		return dictionaryDao.loadByTypeText(type, text);
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

	@Resource
	public void setSubUserDao(SubUserDao subUserDao) {
		this.subUserDao = subUserDao;
	}

	@Resource
	public void setSubuserService(SubUserService subuserService) {
		this.subuserService = subuserService;
	}

	@Resource
	public void setHostDao(HostDao hostDao) {
		this.hostDao = hostDao;
	}

	@Resource
	public void setDbDao(DbDao dbDao) {
		this.dbDao = dbDao;
	}

	@Resource
	public void setOsDao(OsDao osDao) {
		this.osDao = osDao;
	}

	@Resource
	public void setNetdevDao(NetDeviceDao netdevDao) {
		this.netdevDao = netdevDao;
	}

	@Resource
	public void setNetSecurityDeviceDao(
			NetSecurityDeviceDao netSecurityDeviceDao) {
		this.netSecurityDeviceDao = netSecurityDeviceDao;
	}

	@Resource
	public void setHostService(HostService hostService) {
		this.hostService = hostService;
	}

	@Resource
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}

	@Resource
	public void setOsService(OsService osService) {
		this.osService = osService;
	}

	@Resource
	public void setNetDeviceService(NetDeviceService netDeviceService) {
		this.netDeviceService = netDeviceService;
	}

	@Resource
	public void setNetSecurityDeviceService(
			NetSecurityDeviceService netSecurityDeviceService) {
		this.netSecurityDeviceService = netSecurityDeviceService;
	}

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	
	@Resource
	public void setBljService(BljService bljService) {
		this.bljService = bljService;
	}

}
