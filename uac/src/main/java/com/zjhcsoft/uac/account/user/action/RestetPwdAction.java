package com.zjhcsoft.uac.account.user.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.service.PasswordPolicyService;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.UserService;
import com.zjhcsoft.uac.cxf.SmsService;
import com.zjhcsoft.uac.log.service.LogManager;

@Component("uac_restet_pwdAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class RestetPwdAction extends RoofActionSupport {
	private static final long serialVersionUID = -247530850003055354L;
	private User user;
	private UserService userService;
	
	private SmsService smsService;
	private PasswordPolicyService passwordPolicyService;
	
	private LogManager logManager;

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {


	}

	public String index() {
		result = "/uac/account/user/user_restet_page.jsp";
		return JSP;
	}
	
	public String restet_page() {
		user = userService.findByUsername(user.getUsername());
		this.addParameter("passwordPolicys",
				Result.getStr(passwordPolicyService.findForVo()));
		result = "/uac/account/user/user_restet_pwd_page.jsp";
		return JSP;
	}

	
	
	public String restet_passcardSend() {
		Result r = new Result();
		if(user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getIdNumber())){
			r.setMessage("必填项不能为空");
			r.setState(Result.FAIL);
			result = r;
			return JSON;
		}
		List<User> list = userService.select(user);
		if(list.size()==0){
			r.setMessage("用户信息不存在请联系管理员");
			r.setState(Result.FAIL);
			result = r;
			return JSON;
		}
		User u = list.get(0);
		if(!StringUtils.endsWith(user.getIdNumber(), u.getIdNumber())){
			r.setMessage("用户信息不存在请联系管理员");
			r.setState(Result.FAIL);
			result = r;
			return JSON;
		}
		if (smsService.isoneminute(u.getUsername())) { //一分钟
			r.setMessage("一分钟内已经发送过，请不要重复提交");
			r.setState(Result.FAIL);
			result = r;
			return JSON;
		}
		if (smsService.send(u.getUsername(), u.getTel())) {
			r.setState(Result.SUCCESS);
		} else {
			r.setMessage("短信发送失败，请重试!");
			r.setState(Result.FAIL);
		}
		r.setState(Result.SUCCESS);
		result = r;
		return JSON;
	}
	
	public String restet_update() {
		
		String code = smsService.findRandomCodeByStaffCode(user.getUsername());
		String ipt = super.getParamByName("phonepwd", String.class);
		if (((!"".equals(code)) && (code.equals(ipt))) == false) {
			String msg = "验证码错误或已经过期";
			super.addParameter("msg", msg);
			result = "/uac/account/user/user_restet_page.jsp";
			return JSP;
		}
		user = userService.findByUsername(user.getUsername());
		this.addParameter("passwordPolicys",
				Result.getStr(passwordPolicyService.findForVo()));
		result = "/uac/account/user/user_restet_pwd_page.jsp";
		return JSP;
	}
	
	
	public String update() throws Exception {
		if (user == null) {
			result = new Result("数据传输失败！");
			return JSON;
		}
		try {
			user.setUpdate_time(new Date());
			user.setModifyTime(new Date());
			userService.update(user);
		} catch (Exception e) {
			logManager.addAccountLog(user, new Date(),
					LogManager.ACCOUNT_OP_TYPE_UPDATE, "失败");
			throw e;
		}
		logManager.addAccountLog(user, new Date(),
				LogManager.ACCOUNT_OP_TYPE_UPDATE, "成功");
		result = new Result("保存成功！");
		return JSON;
	}
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@Resource
	public void setPasswordPolicyService(
			PasswordPolicyService passwordPolicyService) {
		this.passwordPolicyService = passwordPolicyService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Resource
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	@Resource
	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

}
