package com.awifi.bigscreen.bigscreen_chart_rel.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRel;
import com.awifi.bigscreen.bigscreen_chart_rel.service.api.IBigscreenChartRelService;

@Controller
@RequestMapping("awifi/bigscreen_chart_relAction")
public class BigscreenChartRelAction {
	private IBigscreenChartRelService bigscreenChartRelService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/awifi/bigscreenChartRel/bigscreenChartRel_index.jsp";
	}

	@RequestMapping("/list")
	public String list(BigscreenChartRel bigscreenChartRel, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = bigscreenChartRelService.page(page, bigscreenChartRel);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/awifi/bigscreenChartRel/bigscreenChartRel_list.jsp";
	}
	
	
	@RequestMapping("/create_page")
	public String create_page(Model model) {
		BigscreenChartRel bigscreenChartRel = new BigscreenChartRel();
		model.addAttribute("bigscreenChartRel", bigscreenChartRel);
		this.loadCommon(model);
		return "/awifi/bigscreenChartRel/bigscreenChartRel_create.jsp";
	}
	
	@RequestMapping("/update_page")
	public String update_page(BigscreenChartRel bigscreenChartRel, Model model) {
		bigscreenChartRel = bigscreenChartRelService.load(bigscreenChartRel);
		model.addAttribute("bigscreenChartRel", bigscreenChartRel);
		this.loadCommon(model);
		return "/awifi/bigscreenChartRel/bigscreenChartRel_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(BigscreenChartRel bigscreenChartRel, Model model) {
		bigscreenChartRel = bigscreenChartRelService.load(bigscreenChartRel);
		model.addAttribute("bigscreenChartRel", bigscreenChartRel);
		this.loadCommon(model);
		return "/awifi/bigscreenChartRel/bigscreenChartRel_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(BigscreenChartRel bigscreenChartRel) {
		if (bigscreenChartRel != null) {
			bigscreenChartRelService.save(bigscreenChartRel);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(BigscreenChartRel bigscreenChartRel) {
		if (bigscreenChartRel != null) {
			bigscreenChartRelService.updateIgnoreNull(bigscreenChartRel);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Result delete(BigscreenChartRel bigscreenChartRel) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		bigscreenChartRelService.delete(bigscreenChartRel);
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void setBigscreenChartRelService(
			@Qualifier("bigscreenChartRelService") IBigscreenChartRelService bigscreenChartRelService) {
		this.bigscreenChartRelService = bigscreenChartRelService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
