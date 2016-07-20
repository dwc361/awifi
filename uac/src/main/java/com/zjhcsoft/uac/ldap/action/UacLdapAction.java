package com.zjhcsoft.uac.ldap.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.LoginCheck;
import com.zjhcsoft.uac.account.user.service.PasswordLoginCheckUnit;
import com.zjhcsoft.uac.account.user.service.SubUserService;
import com.zjhcsoft.uac.account.user.service.UserIpLoginCheckUnit;
import com.zjhcsoft.uac.account.whitelist.entity.WhiteList;
import com.zjhcsoft.uac.account.whitelist.service.WhiteListService;
import com.zjhcsoft.uac.authorization.resource.entity.App;
import com.zjhcsoft.uac.authorization.resource.entity.SysResource;
import com.zjhcsoft.uac.authorization.resource.service.AppService;
import com.zjhcsoft.uac.cxf.SmsService;
import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.ldap.util.PersonServiceI;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class UacLdapAction extends RoofActionSupport {
	private static final long serialVersionUID = 8254410635003946841L;
	private PersonServiceI ldapUtils;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private LoginCheck loginCheck;
	private AppService appService;
	private SmsService smsService;
	private WhiteListService whiteListService;
	private SubUserService subUserService;
	private PasswordLoginCheckUnit passwordLoginCheckUnit;

	public String login_check() {
		String clientUrl = super.getParamByName("service", String.class);
		Result r = null;
		Person arg = new Person();
		arg.setUrl(clientUrl);// 请求地址
		arg.setCn(super.getParamByName("username", String.class));
		arg.setUserPassword(super.getParamByName("password", String.class));
		r = passwordLoginCheckUnit.check(arg);
		if (StringUtils.equals(r.getState(), Result.FAIL)) { // 密码错误
			result = r;
			return JSON;
		}
		arg = ldapUtils.getPersonList(arg).get(0);
		arg.setUrl(clientUrl);// 请求地址
		Result l = loginCheck.check(arg);
		r.setMessage(l.getMessage());
		if (!StringUtils.equals(l.getState(), Result.SUCCESS)) { // 校验不通过
			r.setState(Result.FAIL);
			result = r;
			return JSON;
		}
		try {
			User user = new User();
			WhiteList whiteList = new WhiteList();
			user.setUsername(super.getParamByName("username", String.class));
			whiteList.setUser(user);
			Result u = userIpLoginCheckUnit.check(null);
			if(whiteListService.select(whiteList).size() == 0 && !StringUtils.equals(u.getState(), Result.SUCCESS)){
				if(smsService.isOpen()){
					String code = smsService.findRandomCodeByStaffCode(arg.getCn());
					String ipt = super.getParamByName("phonepwd", String.class);
					if (((!"".equals(code)) && (code.equals(ipt))) == false) {
						r.setState(Result.FAIL);
						r.setMessage("短信验证码不正确或已经失效!");
						result = r;
						return JSON;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean isFilter = (StringUtils.isNotEmpty(clientUrl) ? true : false);
		Person sub = new Person();
		sub.setParNode(arg.getCn());
		/*User u = new User();
		u.setUsername(arg.getCn());
		SubUser subUser = new SubUser();
		subUser.setUser(u);
		subUser.setSysResource(new SysResource(50L));*/
		//List<SubUser> subs = subUserService.select(subUser);
		List<Person> subs = ldapUtils.getPersonList(sub);
		List<Person> filterList = new ArrayList<Person>();
		//filterList.add(new Person(subs.get(0)));
		if (isFilter) {
			for (Person person : subs) {
				boolean ok = pathMatcher.match(ObjectUtils.toString(person.getUrl()), clientUrl);
				if (ok) {
					App app = appService.findByService(clientUrl);
					if (app != null) {
						person.setNeedPassword(app.getNeedPassword());
						person.setAppName(app.getName());
					}
					filterList.add(person);
				}
			}
		}
		
		r = new Result();
		r.setState(Result.SUCCESS);
		r.setData(filterList);
		result = r;
		return JSON;
	}

	public String login_passcardSend() {
		String clientUrl = super.getParamByName("service", String.class);
		Result r = null;
		Person arg = new Person();
		arg.setUrl(clientUrl);// 请求地址
		arg.setCn(super.getParamByName("username", String.class));
		arg.setUserPassword(super.getParamByName("password", String.class));
		r = passwordLoginCheckUnit.check(arg);
		if (StringUtils.equals(r.getState(), Result.FAIL)) { // 密码错误
			result = r;
			return JSON;
		}
		arg = ldapUtils.getPersonList(arg).get(0);
		arg.setUrl(clientUrl);// 请求地址
		Result l = loginCheck.check(arg);
		r.setMessage(l.getMessage());
		if (!StringUtils.equals(l.getState(), Result.SUCCESS)) { // 校验不通过
			r.setState(Result.FAIL);
			result = r;
			return JSON;
		}
		if (smsService.isoneminute(arg.getCn())) { //一分钟
			r.setMessage("一分钟内已经发送过，请不要重复提交");
			r.setState(Result.FAIL);
			result = r;
			return JSON;
		}

		if (smsService.send(arg.getCn(), arg.getTelephoneNumber())) {
			r.setState(Result.SUCCESS);
		} else {
			r.setMessage("短信发送失败，请重试!");
			r.setState(Result.FAIL);
		}
		result = r;
		return JSON;
	}
	private UserIpLoginCheckUnit userIpLoginCheckUnit;
	//验证是否白名单用户    2014-09-20   yxg
	public String whiteList_check() {
		User user = new User();
		WhiteList whiteList = new WhiteList();
		user.setUsername(super.getParamByName("username", String.class));
		whiteList.setUser(user);
		List<WhiteList> list = whiteListService.select(whiteList);
		if (list.size() == 0){
			Result u = userIpLoginCheckUnit.check(null);
			if (StringUtils.equals(u.getState(), Result.SUCCESS)) { // 通过白名单ip校验
				result = new Result("success");
				return JSON;
			}
			result = new Result("fail");
		}else {
			result = new Result("success");
		}
		return JSON;
	}
		
	@Resource
	public void setLdapUtils(PersonServiceI ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	@Resource
	public void setLoginCheck(LoginCheck loginCheck) {
		this.loginCheck = loginCheck;
	}

	@Resource
	public void setPasswordLoginCheckUnit(PasswordLoginCheckUnit passwordLoginCheckUnit) {
		this.passwordLoginCheckUnit = passwordLoginCheckUnit;
	}

	@Resource
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	@Resource
	public void setWhiteListService(WhiteListService whiteListService) {
		this.whiteListService = whiteListService;
	}

	@Resource
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	@Resource
	public void setUserIpLoginCheckUnit(UserIpLoginCheckUnit userIpLoginCheckUnit) {
		this.userIpLoginCheckUnit = userIpLoginCheckUnit;
	}
	@Resource
	public void setSubUserService(SubUserService subUserService) {
		this.subUserService = subUserService;
	}
	
	
}
