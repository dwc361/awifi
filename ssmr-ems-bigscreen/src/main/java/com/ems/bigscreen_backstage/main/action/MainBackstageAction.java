package com.ems.bigscreen_backstage.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.awifi.bigscreen.bigscreen.entity.Bigscreen;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import com.awifi.bigscreen.bigscreen_chart_rel.service.api.IBigscreenChartRelService;
import com.awifi.bigscreen.chart.service.api.IChartService;
import com.awifi.bigscreen.templates.service.api.ITemplatesService;
import com.awifi.bigscreen.theme.service.api.IThemeService;
import com.awifi.bigscreen.websocket.handler.SystemWebSocketHandler;

@Controller
@RequestMapping("ems/bigscreen_backstage/MainBackstageAction")
public class MainBackstageAction {
	private IBigscreenService bigscreenService;
	private IDictionaryService dictionaryService;
	private IChartService chartService;
	private IBigscreenChartRelService bigscreenChartRelService;
	private IThemeService themeService;
	private ITemplatesService templatesService;

	// 加载页面的通用数据
	private void loadCommon(Model model) {
		List<Dictionary> dicList = dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}
	
	/**
	 * 后台主页
	 */
	@RequestMapping("/main")
	public String main(Bigscreen bigscreen, HttpServletRequest request, Model model) {
		this.loadCommon(model);
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		
		return "/ems/bigscreen_backstage/main/main.jsp";
	}
	
	/**
	 * 大屏切换到一层架构
	 */
	@RequestMapping("/switch_to_bigscreen_first_display_page")
	public @ResponseBody Result switch_to_bigscreen_first_display_page(HttpServletRequest request) {
		SystemWebSocketHandler hander = new SystemWebSocketHandler();
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/ems/bigscreen_show/indexShowAction/bigscreen_first_display.action";
		hander.sendMessageToUsers(new TextMessage(url));
		return new Result("大屏成功切换到一层架构!");
	}
	
	/**
	 * 大屏切换到二层架构
	 */
	@RequestMapping("/switch_to_bigscreen_second_display_page")
	public @ResponseBody Result switch_to_bigscreen_second_display_page(HttpServletRequest request) {
		SystemWebSocketHandler hander = new SystemWebSocketHandler();
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/ems/bigscreen_show/indexShowAction/bigscreen_second_display.action";
		hander.sendMessageToUsers(new TextMessage(url));
		return new Result("大屏成功切换到二层架构!");
	}
	
	
	
	@Autowired(required = true)
	public void setBigscreenService(@Qualifier("bigscreenService") IBigscreenService bigscreenService) {
		this.bigscreenService = bigscreenService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@Autowired
	public void setChartService(IChartService chartService) {
		this.chartService = chartService;
	}

	@Autowired
	public void setBigscreenChartRelService(IBigscreenChartRelService bigscreenChartRelService) {
		this.bigscreenChartRelService = bigscreenChartRelService;
	}

	@Autowired
	public void setThemeService(IThemeService themeService) {
		this.themeService = themeService;
	}

	@Autowired
	public void setTemplatesService(ITemplatesService templatesService) {
		this.templatesService = templatesService;
	}
}
