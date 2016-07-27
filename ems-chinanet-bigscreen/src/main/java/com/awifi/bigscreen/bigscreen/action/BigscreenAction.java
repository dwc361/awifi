package com.awifi.bigscreen.bigscreen.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import com.awifi.bigscreen.bigscreen.entity.Bigscreen;
import com.awifi.bigscreen.bigscreen.entity.BigscreenVo;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("awifi/bigscreenAction")
public class BigscreenAction {
	private IBigscreenService bigscreenService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/awifi/bigscreen/bigscreen_index.jsp";
	}

	@RequestMapping("/list")
	public String list(Bigscreen bigscreen, HttpServletRequest request, Model model) {
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
	
	@RequestMapping("/update_page")
	public String update_page(Bigscreen bigscreen, Model model) {
		bigscreen = bigscreenService.load(bigscreen);
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(Bigscreen bigscreen, Model model) {
		bigscreen = bigscreenService.load(bigscreen);
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Bigscreen bigscreen) {
		if (bigscreen != null) {
			bigscreenService.save(bigscreen);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(Bigscreen bigscreen) {
		if (bigscreen != null) {
			bigscreenService.updateIgnoreNull(bigscreen);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Result delete(Bigscreen bigscreen) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		bigscreenService.delete(bigscreen);
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void setBigscreenService(
			@Qualifier("bigscreenService") IBigscreenService bigscreenService) {
		this.bigscreenService = bigscreenService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
