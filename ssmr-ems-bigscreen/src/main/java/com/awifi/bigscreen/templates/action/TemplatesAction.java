package com.awifi.bigscreen.templates.action;

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

import com.awifi.bigscreen.templates.entity.Templates;
import com.awifi.bigscreen.templates.entity.TemplatesVo;
import com.awifi.bigscreen.templates.service.api.ITemplatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("awifi/templatesAction")
public class TemplatesAction {
	private ITemplatesService templatesService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model) {
		List<Dictionary> dicList = dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
		List<Dictionary> enableds = dictionaryService.findByType("enabled");
		model.addAttribute("enableds", enableds);
	}

	@RequestMapping("/index")
	public String index() {
		return "/awifi/templates/templates_index.jsp";
	}

	@RequestMapping("/list")
	public String list(Templates templates, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = templatesService.page(page, templates);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/awifi/templates/templates_list.jsp";
	}

	@RequestMapping("/create_page")
	public String create_page(Model model) {
		Templates templates = new Templates();
		model.addAttribute("templates", templates);
		this.loadCommon(model);
		return "/awifi/templates/templates_create.jsp";
	}

	@RequestMapping("/update_page")
	public String update_page(Templates templates, Model model) {
		templates = templatesService.load(templates);
		model.addAttribute("templates", templates);
		this.loadCommon(model);
		return "/awifi/templates/templates_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(Templates templates, Model model) {
		templates = templatesService.load(templates);
		model.addAttribute("templates", templates);
		this.loadCommon(model);
		return "/awifi/templates/templates_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Templates templates,HttpServletRequest request) {
		if (templates != null) {
			User user = (User)BaseUserContext.getCurrentUser(request);
			templates.setCreate_by(user.getUsername());
			templates.setCreate_time(new Date());
			templatesService.save(templates);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(Templates templates,HttpServletRequest request) {
		if (templates != null) {
			User user = (User)BaseUserContext.getCurrentUser(request);
			templates.setUpdate_by(user.getUsername());
			templates.setUpdate_time(new Date());
			templatesService.updateIgnoreNull(templates);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(Templates templates) {
		templates.setEnabled("0");
		templatesService.updateIgnoreNull(templates);
		return new Result("禁用成功!");
	}

	@Autowired(required = true)
	public void setTemplatesService(@Qualifier("templatesService") ITemplatesService templatesService) {
		this.templatesService = templatesService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
