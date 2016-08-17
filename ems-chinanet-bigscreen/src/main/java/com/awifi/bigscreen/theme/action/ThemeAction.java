package com.awifi.bigscreen.theme.action;

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

import com.awifi.bigscreen.theme.entity.Theme;
import com.awifi.bigscreen.theme.entity.ThemeVo;
import com.awifi.bigscreen.theme.service.api.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("awifi/themeAction")
public class ThemeAction {
	private IThemeService themeService;
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
		return "/awifi/theme/theme_index.jsp";
	}

	@RequestMapping("/list")
	public String list(Theme theme, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = themeService.page(page, theme);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/awifi/theme/theme_list.jsp";
	}

	@RequestMapping("/create_page")
	public String create_page(Model model) {
		Theme theme = new Theme();
		model.addAttribute("theme", theme);
		this.loadCommon(model);
		return "/awifi/theme/theme_create.jsp";
	}

	@RequestMapping("/update_page")
	public String update_page(Theme theme, Model model) {
		theme = themeService.load(theme);
		model.addAttribute("theme", theme);
		this.loadCommon(model);
		return "/awifi/theme/theme_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(Theme theme, Model model) {
		theme = themeService.load(theme);
		model.addAttribute("theme", theme);
		this.loadCommon(model);
		return "/awifi/theme/theme_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Theme theme,HttpServletRequest request) {
		if (theme != null) {
			User user = (User)BaseUserContext.getCurrentUser(request);
			theme.setCreate_by(user.getUsername());
			theme.setCreate_time(new Date());
			themeService.save(theme);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(Theme theme,HttpServletRequest request) {
		if (theme != null) {
			User user = (User)BaseUserContext.getCurrentUser(request);
			theme.setCreate_by(user.getUsername());
			theme.setCreate_time(new Date());
			themeService.updateIgnoreNull(theme);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(Theme theme) {
		theme.setEnabled("0");
		themeService.updateIgnoreNull(theme);
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void setThemeService(@Qualifier("themeService") IThemeService themeService) {
		this.themeService = themeService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
