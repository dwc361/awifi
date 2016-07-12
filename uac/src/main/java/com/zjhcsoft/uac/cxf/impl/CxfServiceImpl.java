package com.zjhcsoft.uac.cxf.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.roof.commons.RoofDateUtils;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.dictionary.dao.DictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.SubUserService;
import com.zjhcsoft.uac.account.user.service.UserService;
import com.zjhcsoft.uac.authorization.resource.entity.SysResource;
import com.zjhcsoft.uac.cxf.CxfService;

@WebService
public class CxfServiceImpl implements CxfService {
	private static final Logger LOGGER = Logger.getLogger(CxfServiceImpl.class);
	private DictionaryDao dictionaryDao;
	private RoofDaoSupport roofDaoSupport;
	private UserService userService;
	private SubUserService subUserService;

	public String synchronousUser(String clientXML) {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<root FuncName=\"synchronousUser\">\n");
		StringBuffer body = new StringBuffer();
		List<String> errList = new ArrayList<String>();
		try {
			Document doc = DocumentHelper.parseText(clientXML);
			Element root = doc.getRootElement();
			String funcName = root.attributeValue("FuncName");
			Iterator iterator = root.elements("notelist").iterator();
			while (iterator.hasNext()) {
				Element childElement = (Element) iterator.next();
				Map<String, String> map = new HashMap<String, String>();
				Iterator iterator2 = childElement.elementIterator();
				while (iterator2.hasNext()) {
					Element node = (Element) iterator2.next();
					map.put(node.getName(), node.getTextTrim());
				}
				User user = new User();
				user.setIdNumber(map.get("idNumber"));
				user.setEnabled(true);
				List<User> users = userService.select(user);
				if (users.size() == 0) {
					errList.add("找不到身份证为[" + user.getIdNumber() + "]的主账号!");
					continue;
				} else if (users.size() == 1) {
					user = users.get(0);
				} else {
					errList.add("身份证为[" + user.getIdNumber() + "]存在多个主账号!");
					continue;
				}
				SubUser paramObj = new SubUser();
				try {
					Long operationId = Long.valueOf(map.get("operation"));// 判断操作类型
					Long appId = Long.valueOf(map.get("identify"));
					paramObj.setUsername(map.get("cn"));
					paramObj.setSysResource(new SysResource(appId));
					paramObj.setUser(user);
					List<SubUser> list = subUserService.select(paramObj);
					if (list.size() == 1) {
						paramObj.setId(list.get(0).getId());
					}
					paramObj.setPassword(map.get("userPassword"));
					paramObj.setScope(new Dictionary(Long.valueOf(map.get("scope"))));
					paramObj.setPrivilege(new Dictionary(Long.valueOf(map.get("privilege"))));
					paramObj.setPwdDisableTime(RoofDateUtils.stringToDate(map.get("pwdDisableTime"), "yyyy-MM-dd"));
					// 151:新增;152:修改;153:锁定;154:激活;155:删除
					if (operationId.equals(155L)) {
						subUserService.delete(paramObj);
					} else if (operationId.equals(154L)) {
						subUserService.reuse(paramObj);
					} else if (operationId.equals(153L)) {
						subUserService.delete(paramObj);
					} else if (operationId.equals(152L)) {
						subUserService.updateIgnoreNull(paramObj);
					} else if (operationId.equals(151L)) {
						paramObj.setCreate_date(new Date());
						subUserService.save(paramObj);
					}
				} catch (Exception e) {
					e.printStackTrace();
					errList.add("账号[" + paramObj.getUsername() + "]同步失败!");
					continue;
				}
			}
			LOGGER.info("接口数据加载成功:" + body.toString());
		} catch (Exception e) {
			e.printStackTrace();
			errList.add("接口同步出现异常!");
			LOGGER.info("接口数据加载失败:" + e.getMessage());
		}
		if (errList.size() == 0) {
			body.append("<code>" + CxfConstants.code1 + "</code>\n");
			body.append("<message>" + CxfConstants.getCodeMessage(CxfConstants.code1) + "</message>\n");
		} else {
			body = new StringBuffer();
			body.append("<code>" + CxfConstants.code2 + "</code>\n");
			body.append("<message>" + CxfConstants.getCodeMessage(CxfConstants.code2) + ":" + errList + "</message>\n");
		}
		result.append(body);
		result.append("</root>\n");
		return result.toString();
	}

	public String queryBaseData() {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<root FuncName=\"queryBaseData\">\n");
		StringBuffer body = new StringBuffer();
		try {
			body.append("<operationlist>\n");
			List<Dictionary> operationlist = dictionaryDao.query("ACCOUNT_OP_TYPE", null, null, null);
			for (Dictionary dictionary : operationlist) {
				body.append("<operation>\n" + "<id>" + dictionary.getId() + "</id>\n" + "<name>" + dictionary.getText()
						+ "</name>\n" + "</operation>\n");
			}
			body.append("</operationlist>\n");

			body.append("<requestlist>\n");
			List<Dictionary> requestlist = dictionaryDao.query("SYSTEM_TYPE", null, null, null);
			for (Dictionary dictionary : requestlist) {
				body.append("<request>\n" + "<id>" + dictionary.getId() + "</id>\n" + "<name>" + dictionary.getText()
						+ "</name>\n" + "</request>\n");
			}
			body.append("</requestlist>\n");

			body.append("<regionlist>\n");
			List<Dictionary> regionlist = dictionaryDao.query("REGION", null, null, null);
			for (Dictionary dictionary : regionlist) {
				body.append("<region>\n" + "<id>" + dictionary.getId() + "</id>\n" + "<name>" + dictionary.getText()
						+ "</name>\n" + "</region>\n");
			}
			body.append("</regionlist>\n");

			body.append("<scopelist>\n");
			List<Dictionary> scopelist = dictionaryDao.query("USER_SCOPE", null, null, null);
			for (Dictionary dictionary : scopelist) {
				body.append("<scope>\n" + "<id>" + dictionary.getId() + "</id>\n" + "<name>" + dictionary.getText()
						+ "</name>\n" + "</scope>\n");
			}
			body.append("</scopelist>\n");

			body.append("<privilegelist>\n");
			List<Dictionary> privilegelist = dictionaryDao.query("USER_PRIVILEG", null, null, null);
			for (Dictionary dictionary : privilegelist) {
				body.append("<privilege>\n" + "<id>" + dictionary.getId() + "</id>\n" + "<name>" + dictionary.getText()
						+ "</name>\n" + "</privilege>\n");
			}
			body.append("</privilegelist>\n");

			body.append("<code>" + CxfConstants.code1 + "</code>\n");
			body.append("<message>" + CxfConstants.getCodeMessage(CxfConstants.code1) + "</message>\n");
			LOGGER.info("接口数据加载成功:" + body.toString());
		} catch (Exception e) {
			body = new StringBuffer();
			body.append("<code>" + CxfConstants.code2 + "</code>\n");
			body.append("<message>" + CxfConstants.getCodeMessage(CxfConstants.code2) + "</message>\n");
			e.printStackTrace();
			LOGGER.info("接口数据加载失败:" + e.getMessage());
		}
		result.append(body);
		result.append("</root>\n");
		return result.toString();
	}

	public String queryResourceData(String clientXML) {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<root FuncName=\"queryResourceData\">\n");
		StringBuffer body = new StringBuffer();
		try {
			Document doc = DocumentHelper.parseText(clientXML);
			Element root = doc.getRootElement();
			String funcName = root.attributeValue("FuncName");
			Map<String, String> map = new HashMap<String, String>();
			Iterator iterator = root.elementIterator();
			while (iterator.hasNext()) {
				Element node = (Element) iterator.next();
				map.put(node.getName(), node.getTextTrim());
			}
			String request = map.get("request");
			String region = map.get("region");
			String resource = map.get("resource");
			Dictionary req = roofDaoSupport.load(Dictionary.class, Long.valueOf(request));
			String table = req.getVal().replace("SYSTEM_TYPE", "U");
			Map<String, String> arg = new HashMap<String, String>();
			arg.put("table", table);
			if ("SYSTEM_TYPE_APP,SYSTEM_TYPE_DB,SYSTEM_TYPE_OS".contains(req.getVal())) {
				arg.put("region_id", region);
			}
			arg.put("name", resource);
			List<SysResource> resources = (List<SysResource>) roofDaoSupport.selectForList(
					"SysResource_exp_select_resource", arg);
			for (SysResource sysResource : resources) {
				body.append("<resource>\n" + "<id>" + sysResource.getId() + "</id>\n" + "<name>"
						+ sysResource.getName() + "</name>\n" + "</resource>\n");
			}

			body.append("<code>" + CxfConstants.code1 + "</code>\n");
			body.append("<message>" + CxfConstants.getCodeMessage(CxfConstants.code1) + "</message>\n");
			LOGGER.info("接口数据加载成功:" + body.toString());
		} catch (Exception e) {
			body = new StringBuffer();
			body.append("<code>" + CxfConstants.code2 + "</code>\n");
			body.append("<message>" + CxfConstants.getCodeMessage(CxfConstants.code2) + "</message>\n");
			e.printStackTrace();
			LOGGER.info("接口数据加载失败:" + e.getMessage());
		}
		result.append(body);
		result.append("</root>\n");
		return result.toString();
	}

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setSubUserService(SubUserService subUserService) {
		this.subUserService = subUserService;
	}
}