package com.ems.bigscreen_show.data.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

@Controller
@RequestMapping("ems/bigscreen_show/dataShowAction")
public class DataShowAction {
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}
	
	@RequestMapping("/areaspline_chart_data")
	public @ResponseBody Result areaspline_chart_data(String x_json, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-origin", "*"); // 允许ajax跨域调用
		
		if (x_json != null) {
			Random generator = new Random();
			
			com.alibaba.fastjson.JSONArray fjsArry = com.alibaba.fastjson.JSON.parseArray(x_json);
			Object[] objects = fjsArry.toArray();
			List<Map> dataList = new ArrayList<Map>();
			for(Object object : objects) {
				Map map = new HashMap();
				map.put('x', object);
				map.put('y', generator.nextDouble());
				map.put('a', generator.nextDouble());
				map.put('b', generator.nextDouble());
				map.put('c', generator.nextDouble());
//				map.put('a', generator.nextInt(10000));
//				map.put('b', generator.nextInt(10000));
//				map.put('c', generator.nextInt(10000));
				
				dataList.add(map);
			}
			return new Result("保存成功!", dataList);
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	
	
	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
