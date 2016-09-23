package com.ems.bigscreen_backstage.main.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.awifi.bigscreen.templates.entity.TemplatesVo;
import com.awifi.bigscreen.templates.service.api.ITemplatesService;
import com.awifi.bigscreen.theme.entity.ThemeVo;
import com.awifi.bigscreen.theme.service.api.IThemeService;

@Controller
@RequestMapping("ems/bigscreen_backstage/MainBackstageAction")
public class MainBackstageAction {
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
	
	/**
	 * 后台主页
	 */
	@RequestMapping("/main")
	public String main(Bigscreen bigscreen, HttpServletRequest request, Model model) {
		this.loadCommon(model);
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		List<ChartVo> chartList = chartService.selectForList(new Chart("1"));// 所有可用的图表
		for(ChartVo vo : chartList) {
			vo.setIcon(basePath + vo.getIcon());
		}
		model.addAttribute("chartList", JSON.toJSONString(chartList));
		
		// 加载大屏信息
		//BigscreenVo bigscreenVo = bigscreenService.load(new Bigscreen(8L));
		BigscreenVo bigscreenVo = new BigscreenVo();
		bigscreenVo.setEnabled("1");
		List<BigscreenVo> bigscreenList = bigscreenService.selectForList(bigscreenVo);
		if(bigscreenList!=null && bigscreenList.size()>0) {
			bigscreenVo = bigscreenList.get(0);
		}
		model.addAttribute("bigscreen", bigscreenVo);
		
		BigscreenChartRel bigscreenChartRel = new BigscreenChartRel();
		bigscreenChartRel.setScreen_id(bigscreenVo.getId());
		List<BigscreenChartRelVo> relList = bigscreenChartRelService.selectForList(bigscreenChartRel);
		for(BigscreenChartRelVo vo : relList) {
			vo.setChart_icon(basePath + vo.getChart_icon());
		}
		model.addAttribute("relList", JSON.toJSONString(relList));
		
		// 选用哪一套主题
		//ThemeVo themeVo = themeService.load(new Theme(1L));
		ThemeVo themeVo = new ThemeVo();
		themeVo.setEnabled("1");
		List<ThemeVo> themeList = themeService.selectForList(themeVo);
		if(themeList!=null && themeList.size()>0) {
			themeVo = themeList.get(0);
		}
		model.addAttribute("theme", themeVo);
		
		// 选用哪一套模板
		//TemplatesVo templatesVo = templatesService.load(new Templates(4L));
		TemplatesVo templatesVo = new TemplatesVo();
		templatesVo.setEnabled("1");
		List<TemplatesVo> templatesList = templatesService.selectForList(templatesVo);
		if(templatesList!=null && templatesList.size()>0) {
			templatesVo = templatesList.get(0);
		}
		model.addAttribute("templates", templatesVo);
		
		return "/ems/bigscreen_backstage/main/main.jsp";
	}

	/**
	 * 预览
	 */
	@RequestMapping("/preview")
	public String preview(Model model, BigScreenModel big_model) {
		List<BigScreenHandlebarsVo> vos = big_model.getVos();
		List<BigScreenHandlebarsVo> list = new ArrayList<BigScreenHandlebarsVo>();
		if(vos != null) {
			for (BigScreenHandlebarsVo bigScreenHandlebarsVo : vos) {
				if (bigScreenHandlebarsVo.getTarget_name() == null) {
					continue;
				}
				BigScreenHandlebarsVo vo = new BigScreenHandlebarsVo(bigScreenHandlebarsVo.getTarget_name(), bigScreenHandlebarsVo.getChart_path(), "");
				list.add(vo);
			}
		}
		model.addAttribute("list", JSON.toJSONString(list));
		return big_model.getTemplates().getPath();
	}

	/**
	 * 配置保存
	 */
	@RequestMapping("/save")
	public @ResponseBody Result save(HttpServletRequest request, Bigscreen bigscreen, Model model, BigScreenModel big_model) {
		/**
		 * 更新
		 */
		if (bigscreen == null) {
			bigscreen = new Bigscreen();
		}
		bigscreen.setTemplate_id(big_model.getTemplates().getId());
		bigscreen.setTheme_id(big_model.getTheme().getId());
		bigscreenService.updateIgnoreNull(bigscreen);
		
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
