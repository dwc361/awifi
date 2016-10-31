package com.ems.bigscreen_show.data.action;

import java.util.Date;
import java.util.HashMap;
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
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Mix_DZZD_Chart, 6, "asc", mix_DZZD_Chart_DataTransform);
	}

	/**
	 * 4.[NAS设备状态统计]
	 * @return json
	 */
	@Resource
	private IDataTransform mix_NAS_Chart_DataTransform;
	@RequestMapping(value="/mix_nas_data", produces="application/json; charset=utf-8")
    public @ResponseBody String mix_nas_data() {
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Mix_NAS_Chart, 6, "asc", mix_NAS_Chart_DataTransform);
    }

	/**
	 * 5.[胖ap激活率统计]
	 * @return json
	 */
	@Resource
	private IDataTransform mix_JHL_Chart_DataTransform;
	@RequestMapping(value="/mix_jhl_data", produces="application/json; charset=utf-8")
    public @ResponseBody String mix_jhl_data() {
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_Mix_JHL_Chart, 6, "asc", mix_JHL_Chart_DataTransform);
    }

	/**
	 * 6.[设备类型分布]
	 * @return json
	 */
	@Resource
	private IDataTransform pie_LXFB_Chart_DataTransform;
	@RequestMapping(value="/pie_lxfb_data", produces="application/json; charset=utf-8")
    public @ResponseBody String pie_lxfb_data() {
		return redisHashCache.readCacheByKey(AwifiConstants.Redis_Key_Pie_LXFB_Chart, pie_LXFB_Chart_DataTransform);
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
	public @ResponseBody String areaspline_chart_data(String countStr, HttpServletRequest request, HttpServletResponse response) {
		int count = 1;
		if(countStr!=null && !"".equals(countStr)) {
			count = Integer.valueOf(countStr);
		}
		return redisZSetCache.readCacheByKey(AwifiConstants.Redis_Key_User_Click, count, "asc", user_Click_DataTransform);
	}
	
	
	
	@RequestMapping("/test")
	public @ResponseBody Result test(HttpServletRequest request, HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-origin", "*"); // 允许ajax跨域调用
		Map map = new HashMap();
		long time = new Date().getTime();
		map.put("createTime", time);
		Random generator = new Random();
		map.put("userClickNum", generator.nextInt(10000)); //用户点击量
		return new Result("查询成功!", map);
	}
	
	
	
	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
