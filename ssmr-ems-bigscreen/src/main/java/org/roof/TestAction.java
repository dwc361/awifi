package org.roof;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.roof.spring.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.bigscreen.data.service.api.IPullData;

@Controller
@RequestMapping("testAction")
public class TestAction {
	
	@Resource(name="mix_JHL_Chart_PullData")
	private IPullData mix_JHL_Chart_PullData;
	@Resource(name="scatter_HotSpot_Chart_PullData")
	private IPullData scatter_HotSpot_Chart_PullData;
	@Resource(name="mix_NAS_Chart_PullData")
	private IPullData mix_NAS_Chart_PullData;
	@Resource(name="mix_DZZD_Chart_PullData")
	private IPullData mix_DZZD_Chart_PullData;
	
	/**
	 * 测试
	 */
	@RequestMapping("/test")
	public @ResponseBody Result test(HttpServletRequest request, Model model) {
		Map map = new HashMap();
		map.put("Mix_JHL_Chart", mix_JHL_Chart_PullData.Pull());
		map.put("Scatter_HotSpot_Chart", scatter_HotSpot_Chart_PullData.Pull());
		map.put("Mix_NAS_Chart", mix_NAS_Chart_PullData.Pull());
		map.put("Mix_DZZD_Chart", mix_DZZD_Chart_PullData.Pull());
		return new Result("成功!", map);
	}
}