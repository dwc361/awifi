package org.roof.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.service.api.IMenuFilter;
import org.roof.web.user.entity.User;
import org.roof.web.user.service.api.BaseUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("mainAction")
public class MainAction {
	private IMenuFilter menuFilter;
	
	@RequestMapping("/goLogin")
	public String goLogin(String errorCode, Model model) {
		model.addAttribute("errorCode", errorCode == null ? "null" : "'" + errorCode + "'");
		return "/login/user_goLogin.jsp";
	}
	
	@RequestMapping("/roof_main")
	public String index(Model model, HttpServletRequest request, HttpSession httpSession) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Menu menu = menuFilter.doFilter(1L, new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
				authentication.getCredentials(), authentication.getAuthorities()));
		model.addAttribute("menus", menu.getChildren());
		return "/ems_common/user_main_auto.jsp";
	}
	
	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request, HttpSession httpSession) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Menu menu = menuFilter.doFilter(1L, new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
				authentication.getCredentials(), authentication.getAuthorities()));
		model.addAttribute("menus", menu.getChildren());
		User user = (User) BaseUserContext.getCurrentUser(request);
		model.addAttribute("user", user);
		//return "/ems_common/user_main_auto.jsp";
		return "/ems_common/main/index_bak.jsp";
	}
	
	@RequestMapping("/websocket_chart_demo")
	public String websocket_chart_demo(Model model, HttpServletRequest request, HttpSession httpSession) {
		return "/ems_common/main/websocket_chart_demo.jsp";
	}
	
	@RequestMapping("/websocket_test")
	public String websocket_test(Model model, HttpServletRequest request, HttpSession httpSession) {
		return "/ems_common/main/websocket_test.jsp";
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping("/tab")
	public String tab(Model model){
		return "/ems_common/main/tab.jsp";
	}

	@Autowired(required = true)
	public void setMenuFilter(@Qualifier("menuFilter") IMenuFilter menuFilter) {
		this.menuFilter = menuFilter;
	}

}
