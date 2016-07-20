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

import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.blj.entity.BljLog;
import com.zjhcsoft.uac.blj.service.BljService;
import com.zjhcsoft.uac.cxf.CxfBljService;


@WebService
public class BljServiceImplImpl implements CxfBljService {
	private static final Logger LOGGER = Logger.getLogger(BljServiceImplImpl.class);
	private BljService bljService;
	private RoofDaoSupport  roofDaoSupport;


	public String CheckAiuapTokenSoap(String xml) {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<USERRSP>\n");
		StringBuffer body = new StringBuffer();
		List<String> errList = new ArrayList<String>();
		User u = null;
		BljLog bljLog = new BljLog();
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement(); // 获取根节点
			Map<String, String> map = new HashMap<String, String>();
			Iterator iter = root.elements("HEAD").iterator();
			while (iter.hasNext()) {
				Element childElement = (Element) iter.next();
				Iterator iterator2 = childElement.elementIterator();
				while (iterator2.hasNext()) {
					Element node = (Element) iterator2.next();
					map.put(node.getName(), node.getTextTrim());
				}		
			}
			Iterator bodyiter = root.elements("BODY").iterator();
			while (bodyiter.hasNext()) {
				Element childElement = (Element) bodyiter.next();
				Iterator iterator2 = childElement.elementIterator();
				while (iterator2.hasNext()) {
					Element node = (Element) iterator2.next();
					map.put(node.getName(), node.getTextTrim());
				}		
			}
			bljLog.setStaff_code(map.get("APPACCTID"));
			bljLog.setUuid(map.get("TOKEN"));
			List<BljLog> bloglist = bljService.select(bljLog);
			if(bloglist.size()==0){
				errList.add("票据验证失败[" + bljLog.getStaff_code() + "]!");
			}else{
				u = roofDaoSupport.load(User.class,bloglist.get(0).getStaff_id());
			}
			//(Staff) BaseUserContext.getCurrentUser();
			LOGGER.info("接口数据加载成功:" + body.toString());
		} catch (Exception e) {
			e.printStackTrace();
			errList.add("接口同步出现异常!");
			LOGGER.info("接口数据加载失败:" + e.getMessage());
		}
		if (errList.size() == 0) {
			String appacctid = u!=null?u.getUsername():"";
			body.append("<HEAD>\n");
			body.append("<code>" +BljConstants.getCodeMessage(BljConstants.code3)+ "</code>\n");
			body.append("<SID>" + BljConstants.getCodeMessage(BljConstants.code4) + "</SID>\n");
			body.append("<TIMESTAMP>" + RoofDateUtils.dateToString(new Date(),"yyyyMMDDHHmmss") + "</TIMESTAMP>\n");
			body.append("<SERVICEID>" + BljConstants.SERVICEID + "</SERVICEID>\n");
			body.append("</HEAD>\n");
			body.append("<BODY>\n");
			body.append("<RSP>" + BljConstants.sussces + "</RSP>\n");
			body.append("<MAINACCTID>" + bljLog.getStaff_code() + "</MAINACCTID>\n");
			body.append("<APPACCTID>" +appacctid+"</APPACCTID>\n");
			body.append("</BODY>\n");
		} else {
			body = new StringBuffer();
			body.append("<HEAD>\n");
			body.append("<code>" +BljConstants.getCodeMessage(BljConstants.code3)+ "</code>\n");
			body.append("<SID>" + BljConstants.getCodeMessage(BljConstants.code4) + "</SID>\n");
			body.append("<TIMESTAMP>" + RoofDateUtils.dateToString(new Date(),"yyyyMMDDHHmmss") + "</TIMESTAMP>\n");
			body.append("<SERVICEID>" + BljConstants.SERVICEID + "</SERVICEID>\n");
			body.append("</HEAD>\n");
			body.append("<BODY>\n");
			body.append("<RSP>" + BljConstants.error + "</RSP>\n");
			body.append("<MAINACCTID>" + bljLog.getStaff_code() + "</MAINACCTID>\n");
			body.append("<APPACCTID>" + "</APPACCTID>\n");
			body.append("</BODY>\n");
		}
		result.append(body);
		result.append("</USERRSP>\n");
		return result.toString();
	}

	@Resource
	public void setBljService(BljService bljService) {
		this.bljService = bljService;
	}
	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}



}