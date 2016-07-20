package com.zjhcsoft.uac.account.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.roof.commons.RoofDateUtils;
import org.roof.security.BaseUserContext;
import org.roof.struts2.Result;
import org.roof.struts2.WebUtils;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.password.dao.PasswordPolicyDao;
import com.zjhcsoft.uac.account.password.entity.PasswordPolicy;
import com.zjhcsoft.uac.account.whiteiplist.entity.WhiteIpList;
import com.zjhcsoft.uac.account.whiteiplist.service.WhiteIpListService;
import com.zjhcsoft.uac.ldap.util.Person;

@Component
public class UserIpLoginCheckUnit implements LoginCheckUnit {
	private static Logger LOGGER = Logger.getLogger(UserIpLoginCheckUnit.class);
	private WhiteIpListService whiteIpListService;

	@Override
	public Result check(Person person) {
		Result result = new Result();
		HttpServletRequest request = WebUtils.getRequest();
		result.setState(Result.FAIL);
		try {
			String ip = getIpAddr(request);
			//System.out.println(ip);
			WhiteIpList whiteip = new WhiteIpList();
			//whiteip.setWhiteIP(ip);
			List<WhiteIpList> whiteIpList = whiteIpListService.select(whiteip);
			List<Pattern> patterns = new ArrayList<Pattern>();
			for (WhiteIpList w : whiteIpList) {
				 Pattern pat = Pattern.compile(w.getWhiteIP());
				 patterns.add(pat);
			}
			if(ignoreip(ip,patterns)){
				result.setState(Result.SUCCESS);
				return result;
			}
			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			result = new Result(Result.SUCCESS, "系统异常请与管理员联系, 异常信息:[ip段]"
					+ e.getMessage());
			return result;
		}
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}
	
	private boolean ignoreip(String ip,List<Pattern> list)
	  {
	    for (Pattern pattern :list) {
	      Matcher matcher = pattern.matcher(ip);
	      if (matcher.matches()) {
	        return true;
	      }
	    }
	    return false;
	  }

	@Resource
	public void setWhiteIpListService(WhiteIpListService whiteIpListService) {
		this.whiteIpListService = whiteIpListService;
	}

}
