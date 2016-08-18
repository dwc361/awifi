package com.awifi.bigscreen.bigscreen.action;

import java.util.ArrayList;
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
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.bigscreen.entity.BigScreenHandlebarsVo;
import com.awifi.bigscreen.bigscreen.entity.BigScreenModel;
import com.awifi.bigscreen.bigscreen.entity.Bigscreen;
import com.awifi.bigscreen.bigscreen.entity.BigscreenVo;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRel;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRelVo;
import com.awifi.bigscreen.bigscreen_chart_rel.service.api.IBigscreenChartRelService;
import com.awifi.bigscreen.chart.entity.Chart;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.chart.service.api.IChartService;
import com.awifi.bigscreen.templates.entity.Templates;
import com.awifi.bigscreen.templates.entity.TemplatesVo;
import com.awifi.bigscreen.templates.service.api.ITemplatesService;
import com.awifi.bigscreen.theme.entity.Theme;
import com.awifi.bigscreen.theme.entity.ThemeVo;
import com.awifi.bigscreen.theme.service.api.IThemeService;
import com.awifi.bigscreen.websocket.SystemWebSocketHandler;

@Controller
@RequestMapping("awifi/bigscreenAction")
public class BigscreenAction {
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

	@RequestMapping("/choose")
	public String choose(Model model) {
		Theme theme = new Theme();
		theme.setEnabled("1");
		List<ThemeVo> themes = themeService.selectForList(theme);
		model.addAttribute("themes", themes);
		Templates templates = new Templates();
		templates.setEnabled("1");
		List<TemplatesVo> templatesVos = templatesService.selectForList(templates);
		model.addAttribute("templatesVos", templatesVos);
		this.loadCommon(model);
		return "/awifi/bigscreen/bigscreen_choose.jsp";
	}

	@RequestMapping("/index")
	public String index() {
		return "/awifi/bigscreen/bigscreen_index.jsp";
	}

	@RequestMapping("/preview")
	public String preview(Model model, BigScreenModel big_model) {
		List<BigScreenHandlebarsVo> vos = big_model.getVos();
		List<BigScreenHandlebarsVo> list = new ArrayList<BigScreenHandlebarsVo>();
		for (BigScreenHandlebarsVo bigScreenHandlebarsVo : vos) {
			if (bigScreenHandlebarsVo.getTarget_name() == null) {
				continue;
			}
			BigScreenHandlebarsVo vo = new BigScreenHandlebarsVo(bigScreenHandlebarsVo.getTarget_name(),
					bigScreenHandlebarsVo.getChart_path(), "");
			list.add(vo);
		}
		model.addAttribute("list", JSON.toJSONString(list));
		return big_model.getTemplates().getPath();
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
		Theme theme = new Theme();
		theme.setEnabled("1");
		List<ThemeVo> themes = themeService.selectForList(theme);
		model.addAttribute("themes", themes);
		Templates templates = new Templates();
		templates.setEnabled("1");
		List<TemplatesVo> templatesVos = templatesService.selectForList(templates);
		model.addAttribute("templatesVos", templatesVos);
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
	public String create_page_easyui(Model model, Long templates_id, Long theme_id) {
		List<ChartVo> charts = chartService.selectForList(new Chart("1"));// 所有可用的图表
		model.addAttribute("charts", charts);
		Bigscreen bigscreen = new Bigscreen();
		model.addAttribute("bigscreen", bigscreen);
		this.loadCommon(model);
		Theme theme = new Theme(theme_id);
		ThemeVo themeVo = themeService.load(theme);
		model.addAttribute("theme", themeVo);
		Templates templates = new Templates(templates_id);
		TemplatesVo templates2 = templatesService.load(templates);
		model.addAttribute("templates", templates2);
		return "/awifi/bigscreen/bigscreen_update_easyui.jsp";
	}

	@RequestMapping("/update_page_easyui")
	public String update_page_easyui(Model model, Long id) {
		List<ChartVo> charts = chartService.selectForList(new Chart("1"));// 所有可用的图表
		model.addAttribute("charts", charts);
		Bigscreen bigscreen = new Bigscreen(id);
		BigscreenVo bigvo = bigscreenService.load(bigscreen);
		model.addAttribute("bigscreen", bigvo);
		this.loadCommon(model);
		Theme theme = new Theme(bigvo.getTheme_id());
		ThemeVo themeVo = themeService.load(theme);
		model.addAttribute("theme", themeVo);
		Templates templates = new Templates(bigvo.getTemplate_id());
		TemplatesVo templates2 = templatesService.load(templates);
		model.addAttribute("templates", templates2);

		BigscreenChartRel bigscreenChartRel = new BigscreenChartRel();
		bigscreenChartRel.setScreen_id(bigvo.getId());
		List<BigscreenChartRelVo> reList = bigscreenChartRelService.selectForList(bigscreenChartRel);
		model.addAttribute("reList", JSON.toJSONString(reList));
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
	public @ResponseBody Result create(Bigscreen bigscreen, HttpServletRequest request, Model model,
			BigScreenModel big_model) {
		if (bigscreen == null) {
			bigscreen = new Bigscreen();
		}
		bigscreen.setTemplate_id(big_model.getTemplates().getId());
		bigscreen.setTheme_id(big_model.getTheme().getId());
		bigscreenService.save(bigscreen);
		List<BigScreenHandlebarsVo> vos = big_model.getVos();
		for (BigScreenHandlebarsVo bigScreenHandlebarsVo : vos) {
			if (bigScreenHandlebarsVo.getTarget_name() == null) {
				continue;
			}
			BigscreenChartRel bigscreenChartRel = new BigscreenChartRel(bigScreenHandlebarsVo.getChart_id(),
					bigscreen.getId(), bigScreenHandlebarsVo.getTarget_name());
			bigscreenChartRelService.save(bigscreenChartRel);
		}
		return new Result("保存成功!");
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(HttpServletRequest request, Bigscreen bigscreen, Model model,
			BigScreenModel big_model) {
		if (bigscreen == null) {
			bigscreen = new Bigscreen();
		}
		bigscreen.setTemplate_id(big_model.getTemplates().getId());
		bigscreen.setTheme_id(big_model.getTheme().getId());
		bigscreenService.updateIgnoreNull(bigscreen);//更新
		/**
		 * 删除关联关系
		 */
		BigscreenChartRel rel = new BigscreenChartRel();
		rel.setScreen_id(bigscreen.getId());
		List<BigscreenChartRelVo> reList = bigscreenChartRelService.selectForList(rel);
		for (BigscreenChartRelVo bigscreenChartRelVo : reList) {
			BigscreenChartRel bigscreenChartRel = new BigscreenChartRel(bigscreenChartRelVo.getId());
			bigscreenChartRelService.delete(bigscreenChartRel);
		}
		/**
		 * 重设关联关系
		 */
		List<BigScreenHandlebarsVo> vos = big_model.getVos();
		for (BigScreenHandlebarsVo bigScreenHandlebarsVo : vos) {
			if (bigScreenHandlebarsVo.getTarget_name() == null) {
				continue;
			}
			BigscreenChartRel bigscreenChartRel = new BigscreenChartRel(bigScreenHandlebarsVo.getChart_id(),
					bigscreen.getId(), bigScreenHandlebarsVo.getTarget_name());
			bigscreenChartRelService.save(bigscreenChartRel);
		}
		return new Result("保存成功!");
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
