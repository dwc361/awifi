package com.awifi.bigscreen.bigscreen.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.roof.web.user.entity.User;
import org.roof.web.user.service.api.BaseUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.awifi.bigscreen.bigscreen.entity.Bigscreen;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import com.awifi.bigscreen.websocket.SystemWebSocketHandler;

import com.awifi.bigscreen.bigscreen.entity.Bigscreen;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import com.awifi.bigscreen.chart.entity.Chart;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.chart.service.api.IChartService;

@Controller
@RequestMapping("awifi/bigscreenAction")
public class BigscreenAction {
	private IBigscreenService bigscreenService;
	private IDictionaryService dictionaryService;
	private IChartService chartService;

	// 加载页面的通用数据
	private void loadCommon(Model model) {
		List<Dictionary> dicList = dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/awifi/bigscreen/bigscreen_index.jsp";
	}

	@RequestMapping("/list")
	public String list(Bigscreen bigscreen, HttpServletRequest request, Model model) {
		SystemWebSocketHandler hander = new SystemWebSocketHandler();
		hander.sendMessageToUsers(new TextMessage("ssss"));
		Page page = PageUtils.createPage(request);
		page = bigscreenService.page(page, bigscreen);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_list.jsp";
	}

	@RequestMapping("/create_page")
	public String create_page(Model model) {
		Bigscreen bigscreen = new Bigscreen();
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_create.jsp";
	}

	@RequestMapping("/create_page_easyui")
	public String create_page_easyui(Model model) {
		List<ChartVo> charts = chartService.selectForList(new Chart("1"));// 所有可用的图表
		model.addAttribute("charts", charts);
		Bigscreen bigscreen = new Bigscreen();
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_update_easyui.jsp";
	}
	
	@RequestMapping("/create_page_easyui1")
	public String create_page_easyui1(Model model) {
		List<ChartVo> charts = chartService.selectForList(new Chart("1"));// 所有可用的图表
		model.addAttribute("charts", charts);
		Bigscreen bigscreen = new Bigscreen();
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_update_easyui1.jsp";
	}

	@RequestMapping("/update_page")
	public String update_page(Bigscreen bigscreen, Model model) {
		bigscreen = bigscreenService.load(bigscreen);
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_update.jsp";
	}

	@RequestMapping("/bigscreen_update_easyui")
	public String bigscreen_update_easyui(Bigscreen bigscreen, Model model) {
		bigscreen = bigscreenService.load(bigscreen);
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_update_easyui.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(Bigscreen bigscreen, Model model) {
		bigscreen = bigscreenService.load(bigscreen);
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Bigscreen bigscreen,HttpServletRequest request) {
		if (bigscreen != null) {
			User user = (User)BaseUserContext.getCurrentUser(request);
			bigscreen.setCreate_by(user.getUsername());
			bigscreen.setCreate_time(new Date());
			bigscreenService.save(bigscreen);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(HttpServletRequest request, Bigscreen bigscreen) {
		if (bigscreen != null) {
			User user = (User) BaseUserContext.getCurrentUser(request);
			bigscreen.setUpdate_by(user.getUsername());
			bigscreen.setUpdate_time(new Date());
			bigscreenService.updateIgnoreNull(bigscreen);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(Bigscreen bigscreen) {
		// bigscreen.setEnabled("0");
		bigscreenService.updateIgnoreNull(bigscreen);
		return new Result("删除成功!");
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
}
