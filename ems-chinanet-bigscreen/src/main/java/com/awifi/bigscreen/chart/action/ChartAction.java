package com.awifi.bigscreen.chart.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import com.awifi.bigscreen.chart.entity.Chart;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.chart.service.api.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("awifi/chartAction")
public class ChartAction {
	private IChartService chartService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/awifi/chart/chart_index.jsp";
	}

	@RequestMapping("/list")
	public String list(Chart chart, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = chartService.page(page, chart);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/awifi/chart/chart_list.jsp";
	}
	
	
	@RequestMapping("/create_page")
	public String create_page(Model model) {
		Chart chart = new Chart();
		model.addAttribute("chart", chart);
		this.loadCommon(model);
		return "/awifi/chart/chart_create.jsp";
	}
	
	@RequestMapping("/update_page")
	public String update_page(Chart chart, Model model) {
		chart = chartService.load(chart);
		model.addAttribute("chart", chart);
		this.loadCommon(model);
		return "/awifi/chart/chart_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(Chart chart, Model model) {
		chart = chartService.load(chart);
		model.addAttribute("chart", chart);
		this.loadCommon(model);
		return "/awifi/chart/chart_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Chart chart) {
		if (chart != null) {
			chartService.save(chart);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(Chart chart) {
		if (chart != null) {
			chartService.updateIgnoreNull(chart);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Result delete(Chart chart) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		chartService.delete(chart);
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void setChartService(
			@Qualifier("chartService") IChartService chartService) {
		this.chartService = chartService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
