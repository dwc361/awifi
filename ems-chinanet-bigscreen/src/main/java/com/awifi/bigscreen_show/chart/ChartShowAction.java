package com.awifi.bigscreen_show.chart;

import java.util.List;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("awifi/bigscreen_show/chartShowAction")
public class ChartShowAction {
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/areaspline_chart")
	public String areaspline_chart() {
		return "/awifi/bigscreen_show/chart/areaspline_chart.jsp";
	}
	
	@RequestMapping("/spline_chart")
	public String spline_chart() {
		return "/awifi/bigscreen_show/chart/spline_chart.jsp";
	}
	
	@RequestMapping("/watch_chart")
	public String watch_chart() {
		return "/awifi/bigscreen_show/chart/watch_chart.jsp";
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}