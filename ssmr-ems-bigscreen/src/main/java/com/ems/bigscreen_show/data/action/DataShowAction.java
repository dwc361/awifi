package com.ems.bigscreen_show.data.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.roof.spring.Result;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.bigscreen.AwifiConstants;
<<<<<<< HEAD
=======
import com.awifi.bigscreen.data.service.api.IPullData;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
>>>>>>> 24a626b0617cf97fe4f61054f2c06fea9b1e6453
import com.awifi.bigscreen.redisCache.api.IDataTransform;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

@Controller
@RequestMapping("ems/bigscreen_show/dataShowAction")
public class DataShowAction {
	private IDictionaryService dictionaryService;
	@Resource
	private IRedisCache redisZSetCache;
	@Resource
	private IRedisCache redisHashCache;

	/**
	 * 1.[全省设备排名]
	 * @return json
	 */
	@Resource
	private IDataTransform funnel_SBPM_Chart_DataTransform;
	@RequestMapping(value="/funnel_sbpm_data", produces="application/json; charset=utf-8")
    public @ResponseBody String funnel_sbpm_data() {
		return redisHashCache.readCacheByKey(AwifiConstants.Redis_Key_Funnel_SBPM_Chart, funnel_SBPM_Chart_DataTransform);
    }

	/**
	 * 2.[用户认证状态]
	 * @return json
	 */
	@Resource
	private IDataTransform line_YHRZ_Chart_DataTransform;
	@RequestMapping(value="/line_yhrz_data", produces="application/json; charset=utf-8")
    public @ResponseBody String line_yhrz_data() {
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Line_YHRZ_Chart, line_YHRZ_Chart_DataTransform);
    }

	/**
	 * 3.[定制终端设备状态统计]
	 * @return json
	 */
	@Resource
	private IDataTransform mix_DZZD_Chart_DataTransform;
	@RequestMapping(value="/mix_dzzd_data", produces="application/json; charset=utf-8")
	public @ResponseBody String mix_dzzd_data() {
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Mix_DZZD_Chart, 6, "desc", mix_DZZD_Chart_DataTransform);
	}

	/**
	 * 4.[NAS设备状态统计]
	 * @return json
	 */
	@Resource
	private IDataTransform mix_NAS_Chart_DataTransform;
	@RequestMapping(value="/mix_nas_data", produces="application/json; charset=utf-8")
    public @ResponseBody String mix_nas_data() {
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Mix_NAS_Chart, 6, "desc", mix_NAS_Chart_DataTransform);
    }

	/**
	 * 5.[胖ap激活率统计]
	 * @return json
	 */
	@Resource
	private IDataTransform mix_JHL_Chart_DataTransform;
	@RequestMapping(value="/mix_jhl_data", produces="application/json; charset=utf-8")
    public @ResponseBody String mix_jhl_data() {
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Mix_JHL_Chart, 6, "desc", mix_JHL_Chart_DataTransform);
    }

	/**
	 * 6.[设备类型分布]
	 * @return json
	 */
	@Resource
	private IDataTransform pie_LXFB_Chart_DataTransform;
	@RequestMapping(value="/pie_lxfb_data", produces="application/json; charset=utf-8")
    public @ResponseBody String pie_lxfg_data() {
		return redisHashCache.readCacheByKey(AwifiConstants.Redis_Key_Pie_LXFB_Chart, 6, "desc", pie_LXFB_Chart_DataTransform);
    }

	/**
	 * 7.[爱wifi热点类型分布]
	 * @return json
	 */
	@Resource
	private IDataTransform scatter_HotSpot_Chart_DataTransform;
	@RequestMapping(value="/scatter_hotspot_data", produces="application/json; charset=utf-8")
    public @ResponseBody String scatter_hotspot_data() {
		return redisHashCache.readCacheByKey(AwifiConstants.Redis_Key_Scatter_HotSpot_Chart, scatter_HotSpot_Chart_DataTransform);
    }

	/**
	 * 8.[用户、商户、PV、UV统计]
	 * @return json
	 */
	@Resource
	private IDataTransform user_PV_UV_DataTransform;
	@RequestMapping(value="/user_pv_uv_data", produces="application/json; charset=utf-8")
    public @ResponseBody String user_pv_uv_data() {
		return redisHashCache.readCacheByKey(AwifiConstants.Redis_Key_User_PV_UV, user_PV_UV_DataTransform);
    }
<<<<<<< HEAD



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
				map.put('y', generator.nextInt(10000));
				map.put('a', generator.nextDouble());
				map.put('b', generator.nextDouble()+1);
				map.put('c', generator.nextDouble()+2);
//				map.put('a', generator.nextInt(10000));
//				map.put('b', generator.nextInt(10000));
//				map.put('c', generator.nextInt(10000));

				dataList.add(map);
			}
			return new Result("保存成功!", dataList);
		} else {
			return new Result("数据传输失败!");
		}
=======
	
	/**
	 * [平台用户点击量]
	 * @param x_json
	 * @param request
	 * @param response
	 * @return json
	 */
	@Resource
	private IDataTransform user_Click_DataTransform;
	@RequestMapping(value="/areaspline_chart_data", produces="application/json; charset=utf-8")
	public @ResponseBody String areaspline_chart_data(HttpServletRequest request, HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-origin", "*"); // 允许ajax跨域调用
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_User_Click, 6, "asc", user_Click_DataTransform);
	}
	@Resource
	private IPullData user_Click_PullData;
	@Resource
	private IDataAcquisition user_Click_DataAcquisition;
	@RequestMapping("/areaspline_chart_show_add_one_data")
	public @ResponseBody Result areaspline_chart_show_add_one_data(HttpServletRequest request, HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-origin", "*"); // 允许ajax跨域调用
		
		Map map = new HashMap();
		long time = new Date().getTime();
		map.put("createTime", time);
//		UserData data = (UserData) user_Click_PullData.Pull();
//		if(data != null) {
//			map.put("userClickNum", data.getValue()); //用户点击量
//		}
		Random generator = new Random();
		map.put("userClickNum", generator.nextInt(10000)); //用户点击量
		
		//redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_User_Click, user_Click_DataAcquisition, "{'key':'value'}");
		
		return new Result("保存成功!", map);
>>>>>>> 24a626b0617cf97fe4f61054f2c06fea9b1e6453
	}
	
	
	
	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
