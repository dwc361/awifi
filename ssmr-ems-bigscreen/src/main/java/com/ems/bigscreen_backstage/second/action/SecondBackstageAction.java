package com.ems.bigscreen_backstage.second.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;
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

@Controller
@RequestMapping("ems/bigscreen_backstage/SecondBackstageAction")
public class SecondBackstageAction {
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
	 * 二层架构配置页面(已废弃，但可以访问)
	 */
	@RequestMapping("/bigscreen_second_config")
	public String bigscreen_second_config(Bigscreen bigscreen, HttpServletRequest request, Model model) {
		this.loadCommon(model);
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		List<ChartVo> chartList = chartService.selectForList(new Chart("1"));// 所有可用的图表
		for(ChartVo vo : chartList) {
			vo.setIcon(basePath + vo.getIcon());
		}
		model.addAttribute("chartList", JSON.toJSONString(chartList));
		
		// 加载大屏信息
		BigscreenVo bigscreenVo = new BigscreenVo();
		bigscreenVo.setEnabled("1");
		bigscreenVo.setRe_type(BigscreenReTypeEnum.Second.getCode());
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
		ThemeVo themeVo = themeService.load(new Theme(bigscreenVo.getTheme_id()));
		model.addAttribute("theme", themeVo);
		
		// 选用哪一套模板
		TemplatesVo templatesVo = templatesService.load(new Templates(bigscreenVo.getTemplate_id()));
		model.addAttribute("templates", templatesVo);
		
		return "/ems/bigscreen_backstage/second/second_config.jsp";
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
	
	/**
	 * 二层架构配置数据加载
	 * @param request
	 * @param response
	 * @return json
	 */
	@RequestMapping("/get_bigscreen_second_data")
	public @ResponseBody Result get_bigscreen_second_data(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		List<ChartVo> chartList = chartService.selectForList(new Chart("1"));// 所有可用的图表
		for(ChartVo vo : chartList) {
			vo.setIcon(basePath + vo.getIcon());
		}
		map.put("chartList", chartList);
		
		// 加载大屏信息
		BigscreenVo bigscreenVo = new BigscreenVo();
		bigscreenVo.setEnabled("1");
		bigscreenVo.setRe_type(BigscreenReTypeEnum.Second.getCode());
		List<BigscreenVo> bigscreenList = bigscreenService.selectForList(bigscreenVo);
		if(bigscreenList!=null && bigscreenList.size()>0) {
			bigscreenVo = bigscreenList.get(0);
		}
		map.put("bigscreen", bigscreenVo);
		
		BigscreenChartRel bigscreenChartRel = new BigscreenChartRel();
		bigscreenChartRel.setScreen_id(bigscreenVo.getId());
		List<BigscreenChartRelVo> relList = bigscreenChartRelService.selectForList(bigscreenChartRel);
		for(BigscreenChartRelVo vo : relList) {
			vo.setChart_icon(basePath + vo.getChart_icon());
		}
		map.put("relList", relList);
		
		// 选用哪一套主题
		ThemeVo themeVo = themeService.load(new Theme(bigscreenVo.getTheme_id()));
		map.put("theme", themeVo);
		
		// 选用哪一套模板
		TemplatesVo templatesVo = templatesService.load(new Templates(bigscreenVo.getTemplate_id()));
		map.put("templates", templatesVo);
		
		return new Result("二层架构配置数据加载成功!", map);
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