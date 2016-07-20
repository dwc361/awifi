package com.zjhcsoft.uac.blj.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.dictionary.dao.DictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.SubUserService;
import com.zjhcsoft.uac.authorization.resource.dao.SystemDao;
import com.zjhcsoft.uac.authorization.resource.entity.Db;
import com.zjhcsoft.uac.authorization.resource.entity.Host;
import com.zjhcsoft.uac.authorization.resource.entity.NetDevice;
import com.zjhcsoft.uac.authorization.resource.entity.System;
import com.zjhcsoft.uac.authorization.resource.service.SystemService;
import com.zjhcsoft.uac.blj.entity.BljLog;
import com.zjhcsoft.uac.blj.util.BljResultUntils;
import com.zjhcsoft.uac.blj.util.BljUntils;
import com.zjhcsoft.uac.blj.util.ClinetSSL;

/**
 * 自动生成
 */
@Component
@Transactional
public class BljService {
	private static final Logger LOGGER = Logger.getLogger(BljService.class);
	public static final long NORMAL_USER_ID = 51L;// 普通用户ID
	private RoofDaoSupport roofDaoSupport;
	public ClinetSSL clinetSSL;
	public BljUntils bljUntils;
	public SubUserService subUserService;
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public SystemService systemService;
	public SystemDao systemDao;
	private DictionaryDao dictionaryDao;
	/**
	 * 保存数据
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BljLog save(BljLog paramObj) throws Exception {
		paramObj.setUuid(UUID.randomUUID().toString());
		paramObj.setLog_time(new Date());
		roofDaoSupport.save(paramObj);
		return paramObj;
	}

	/**
	 * 
	 * @param paramObj
	 * @return
	 */
	public List<BljLog> select(BljLog paramObj) {
		List<BljLog> list = (List<BljLog>) roofDaoSupport.findByMappedQuery(
				"BljLog_exp_select_log", paramObj);
		return list;
	}

	public String operate(Map<String, Object> paramObj, String url) {
		String restr = "";
		try {
			String t = url.substring(url.lastIndexOf("/"));
			t =t.split("/")[1];
			if(ADD.equals(t)){
				restr = result(clinetSSL.sendDataByPost(url, paramObj));
			}else if(UPDATE.equals(t)){
				restr = result(clinetSSL.sendDataByPut(url, paramObj));
			}else if(DELETE.equals(t)){
				restr = result(clinetSSL.sendDataByDel(url, paramObj));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return restr;
	}
	//主账号增加
	public String Useradd(User paramObj) {
		String url = PropertiesUtil.getPorpertyString("blj.user.url")+ADD;

		return operate(bljUntils.Usertomap(paramObj), url);
	}
	//主账号修改
	public String Userupdate(User paramObj) {
		String url = PropertiesUtil.getPorpertyString("blj.user.url")+UPDATE;
		return operate(bljUntils.Usertomap(paramObj), url);
	}
	//主账号增加删除
	public String Userdelete(User paramObj) {
		String url = PropertiesUtil.getPorpertyString("blj.user.url")+DELETE;
		return operate(bljUntils.Usertomap(paramObj,DELETE), url);
	}
	//资源账号ADD
	public String SubUseradd(SubUser paramObj) {
		paramObj = subUserService.load(paramObj.getId());
		String url = PropertiesUtil.getPorpertyString("blj.account.url")+ADD;
		return operate(bljUntils.SubUsertomap(paramObj), url);
	}
	//资源账号UPDATE
	public String SubUserupdate(SubUser paramObj) {
		paramObj = subUserService.load(paramObj.getId());
		String url = PropertiesUtil.getPorpertyString("blj.account.url")+UPDATE;
		return operate(bljUntils.SubUsertomap(paramObj,UPDATE), url);
	}
	//资源账号DELETE
	public String SubUserdelete(SubUser paramObj) {
		paramObj = subUserService.load(paramObj.getId());
		String url = PropertiesUtil.getPorpertyString("blj.account.url")+DELETE;
		return operate(bljUntils.SubUsertomap(paramObj,DELETE), url);
	}
	//资源
	public String Systemadd(System paramObj) throws Exception {
		DictionaryLoad(paramObj);
		String url = PropertiesUtil.getPorpertyString("blj.resource.url")+ADD;
		return operate(bljUntils.Systemtomap(paramObj), url);
	}
	//资源
	public String Systemupdate(System paramObj) {
		DictionaryLoad(paramObj);
		String url = PropertiesUtil.getPorpertyString("blj.resource.url")+UPDATE;
		return operate(bljUntils.Systemtomap(paramObj), url);
	}
	//资源
	public String Systemdelete(System paramObj) throws Exception {
		paramObj = systemService.load(paramObj.getId());//删除的时候只有id
		String url = PropertiesUtil.getPorpertyString("blj.resource.url")+DELETE;
		return operate(bljUntils.Systemtomap(paramObj,DELETE), url);
	}
	//资源服务
	public String Serviceadd(System paramObj) {
		DictionaryLoad(paramObj);
		String url = PropertiesUtil.getPorpertyString("blj.service.url")+ADD;
		return operate(bljUntils.Servicetomap(paramObj), url);
	}
	//资源服务
	public String Serviceupdate(System paramObj) {
		DictionaryLoad(paramObj);
		String url = PropertiesUtil.getPorpertyString("blj.service.url")+UPDATE;
		return operate(bljUntils.Servicetomap(paramObj,UPDATE), url);
	}
	//资源服务
	public String Servicedelete(System paramObj) throws Exception {
		paramObj = systemService.load(paramObj.getId());//删除的时候只有id
		String url = PropertiesUtil.getPorpertyString("blj.service.url")+DELETE;
		return operate(bljUntils.Servicetomap(paramObj,DELETE), url);
	}
	
	public String Accessadd(SubUser paramObj) {
		paramObj = subUserService.load(paramObj.getId());
		String url = PropertiesUtil.getPorpertyString("blj.access.url")+ADD;
		return operate(bljUntils.Acctomap(paramObj), url);
	}
	public String Accessdelete(SubUser paramObj) {
		paramObj = subUserService.load(paramObj.getId());
		String url = PropertiesUtil.getPorpertyString("blj.access.url")+DELETE;
		return operate(bljUntils.Acctomap(paramObj,DELETE), url);
	}
	
	public void DictionaryLoad(System paramObj) {
		Dictionary tyename = dictionaryDao.load(Dictionary.class, paramObj.getTypename().getId());
		paramObj.setTypename(tyename);
		if(paramObj.getGroup()!= null){
			Dictionary group = dictionaryDao.load(Dictionary.class, paramObj.getGroup().getId());
			paramObj.setGroup(group);
		}
		if(paramObj instanceof NetDevice){
			NetDevice h = (NetDevice) paramObj;
			Dictionary Serve_type = dictionaryDao.load(Dictionary.class,h.getServe_type().getId());
			h.setServe_type(Serve_type);
			paramObj = h;
		}else if (paramObj instanceof Host){
			Host h = (Host) paramObj;
			Dictionary Serve_type = dictionaryDao.load(Dictionary.class,h.getServe_type().getId());
			h.setServe_type(Serve_type);
			paramObj = h;
		}else if (paramObj instanceof Db){
			Db h = (Db) paramObj;
			Dictionary DbType = dictionaryDao.load(Dictionary.class,h.getDbType().getId());
			h.setDbType(DbType);
			paramObj = h;
		}
	}

	public String result(Map<String, Object> re) {
		if(re == null){
			return "没有返回报文";
		}
		String code = toString(re.get("code"));
		String msg = toString(re.get("msg"));
		String item = toString(re.get("item"));
		if (code != null && !"".endsWith(code)) {
			Integer c = Integer.valueOf(code);
			if (c == 0) {
				return "success";
			} else if (c == 1) {
				return "用户不存在";
			}else if (c <= 0) {
				return item + BljResultUntils.getCodeMessage(-c);
			}
		}
		return "";
	}

	private String toString(Object o) {
		return StringUtils.strip(ObjectUtils.toString(o, ""));
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

	@Resource
	public void setClinetSSL(ClinetSSL clinetSSL) {
		this.clinetSSL = clinetSSL;
	}

	@Resource
	public void setBljUntils(BljUntils bljUntils) {
		this.bljUntils = bljUntils;
	}
	@Resource
	public void setSubUserService(SubUserService subUserService) {
		this.subUserService = subUserService;
	}
	@Resource
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	@Resource
	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}
	
	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

}
