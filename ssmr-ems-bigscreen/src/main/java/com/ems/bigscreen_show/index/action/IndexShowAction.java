package com.ems.bigscreen_show.index.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.awifi.bigscreen.bigscreen.entity.BigScreenHandlebarsVo;
import com.awifi.bigscreen.bigscreen.entity.BigScreenModel;
import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;
import com.awifi.bigscreen.bigscreen.entity.BigscreenVo;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRel;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRelVo;
import com.awifi.bigscreen.bigscreen_chart_rel.service.api.IBigscreenChartRelService;
import com.awifi.bigscreen.chart.service.api.IChartService;
import com.awifi.bigscreen.templates.entity.Templates;
import com.awifi.bigscreen.templates.entity.TemplatesVo;
import com.awifi.bigscreen.templates.service.api.ITemplatesService;
import com.awifi.bigscreen.theme.entity.Theme;
import com.awifi.bigscreen.theme.entity.ThemeVo;
import com.awifi.bigscreen.theme.service.api.IThemeService;

@Controller
@RequestMapping("ems/bigscreen_show/indexShowAction")
public class IndexShowAction {
	private IBigscreenService bigscreenService;
	private IDictionaryService dictionaryService;
	private IChartService chartService;
	private IBigscreenChartRelService bigscreenChartRelService;
	private IThemeService themeService;
	private ITemplatesService templatesService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	/**
	 * 大屏一层架构页面展示
	 */
	@RequestMapping("/bigscreen_first_display")
	public String bigscreen_first_display(Model model, HttpServletRequest request) {
		this.loadCommon(model);
		return "";
	}
	
	/**
	 * 大屏二层架构页面展示
	 */
	@RequestMapping("/bigscreen_second_display")
	public String bigscreen_second_display(Model model, HttpServletRequest request) {
		this.loadCommon(model);
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		
		// 加载大屏信息
		BigscreenVo bigscreenVo = new BigscreenVo();
		bigscreenVo.setEnabled("1");
		bigscreenVo.setRe_type(BigscreenReTypeEnum.Second.getCode());
		List<BigscreenVo> bigscreenList = bigscreenService.selectForList(bigscreenVo);
		if(bigscreenList!=null && bigscreenList.size()>0) {
			bigscreenVo = bigscreenList.get(0);
		}
		
		BigscreenChartRel bigscreenChartRel = new BigscreenChartRel();
		bigscreenChartRel.setScreen_id(bigscreenVo.getId());
		List<BigscreenChartRelVo> relList = bigscreenChartRelService.selectForList(bigscreenChartRel);
		for(BigscreenChartRelVo vo : relList) {
			vo.setChart_icon(basePath + vo.getChart_icon());
		}
		
		List<BigScreenHandlebarsVo> list = new ArrayList<BigScreenHandlebarsVo>();
		if(relList != null) {
			for (BigscreenChartRelVo bigscreenChartRelVo : relList) {
				BigScreenHandlebarsVo vo = new BigScreenHandlebarsVo(bigscreenChartRelVo.getTarget(), bigscreenChartRelVo.getChart_path(), "");
				list.add(vo);
			}
		}
		model.addAttribute("list", JSON.toJSONString(list));
		
		// 选用哪一套模板
		TemplatesVo templatesVo = templatesService.load(new Templates(bigscreenVo.getTemplate_id()));
		
		// 选用哪一套主题
		ThemeVo themeVo = themeService.load(new Theme(bigscreenVo.getTheme_id()));
		
		return templatesVo.getPath();
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