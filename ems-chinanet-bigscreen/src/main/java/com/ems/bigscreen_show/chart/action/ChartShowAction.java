package com.ems.bigscreen_show.chart.action;

import java.util.List;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ems/bigscreen_show/chartShowAction")
public class ChartShowAction {
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/areaspline_chart")
	public String areaspline_chart() {
		return "/ems/bigscreen_show/chart/areaspline_chart.jsp";
	}
	
	@RequestMapping("/spline_chart")
	public String spline_chart() {
		return "/ems/bigscreen_show/chart/spline_chart.jsp";
	}
	
	@RequestMapping("/many_spline_chart")
	public String many_spline_chart() {
		return "/ems/bigscreen_show/chart/many_spline_chart.jsp";
	}
	
	@RequestMapping("/watch_chart")
	public String watch_chart() {
		return "/ems/bigscreen_show/chart/watch_chart.jsp";
	}
	
	@RequestMapping("/column_chart")
	public String column_chart() {
		return "/ems/bigscreen_show/chart/column_chart.jsp";
	}
	
	@RequestMapping("/mixture_chart")
	public String mixture_chart() {
		return "/ems/bigscreen_show/chart/mixture_chart.jsp";
	}
	
	
	
	@RequestMapping("/e_chart_one")
	public String e_chart_one() {
		return "/ems/bigscreen_show/chart/e_chart_one.jsp";
	}
	
	
	
	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}