package com.zjhcsoft.uac.blj.action;

import java.util.List;

import javax.annotation.Resource;

import org.roof.commons.PropertiesUtil;
import org.roof.security.BaseUserContext;
import org.roof.struts2.RoofActionSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.blj.entity.BljLog;
import com.zjhcsoft.uac.blj.service.BljService;


@Component("uac_bljAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class BljAction extends RoofActionSupport {
	private static final long serialVersionUID = -247530850003055354L;
	private BljService bljService;
	
	public String index() {
		SubUser currentUser = (SubUser) BaseUserContext.getCurrentUser();
		User u = currentUser.getUser();
		BljLog log = new BljLog();
		String uuid = "";
		log.setStaff_code(u.getUsername());
		log.setStaff_id(u.getId());
		List<BljLog> logList = bljService.select(log);
		if(logList.size()==0){
			try {
				uuid = bljService.save(log).getUuid();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			uuid = logList.get(0).getUuid();
		}
		
		String url = PropertiesUtil.getPorpertyString("blj.url");
		url=url+"?token="+uuid+"&appAcctId="+u.getUsername()+"&flag=1&typeid=2";
		result = url;
		return REDIRECT;
	}
	
	public String audit() {
		BljLog log = new BljLog();
		String uuid = "";
		String username = "audit";
		Long id = 293147L;
		log.setStaff_code(username);
		log.setStaff_id(id);
		List<BljLog> logList = bljService.select(log);
		if(logList.size()==0){
			try {
				uuid = bljService.save(log).getUuid();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			uuid = logList.get(0).getUuid();
		}
		
		String url = PropertiesUtil.getPorpertyString("blj.url");
		url=url+"?token="+uuid+"&appAcctId="+username+"&flag=1&typeid=2";
		result = url;
		return REDIRECT;
	}
	
	
	public String accessToken() {
		SubUser currentUser = (SubUser) BaseUserContext.getCurrentUser();
		User u = currentUser.getUser();
		BljLog log = new BljLog();
		String uuid = "";
		log.setStaff_code(u.getUsername());
		log.setStaff_id(u.getId());
		List<BljLog> logList = bljService.select(log);
		if(logList.size()==0){
			try {
				uuid = bljService.save(log).getUuid();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			uuid = logList.get(0).getUuid();
		}
		
		String url = PropertiesUtil.getPorpertyString("bljlogin.url");
		url=url+"?token="+uuid+"&appAcctId="+u.getUsername();
		result = url;
		return REDIRECT;
	}
	
	@Resource
	public void setBljService(BljService bljService) {
		this.bljService = bljService;
	}
	

}
