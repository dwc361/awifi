package org.roof.guardmonitor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.roof.guardmonitor.extension.MonitorStatusManager;
import org.roof.guardmonitor.impl.AndStatusMonitorListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MonitorInfo {
	private Map<String, String> statusMap;
	private ObjectMapper mapper = new ObjectMapper();
	private AndStatusMonitorListener statusMonitorListener;
	private MonitorStatusManager monitorStatusManager;

	@RequestMapping("/status")
	@ResponseBody
	public String getStatus() throws JsonProcessingException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String addr = request.getRemoteAddr();
		String host = request.getRemoteHost();
		String xForwardedFor = request.getHeader("X-Forwarded-For");
		String xClientIP = request.getHeader("X-ClientIP");
		if (monitorStatusManager
				.isStopped(addr, host, xForwardedFor, xClientIP)) {
			return mapper.writeValueAsString(monitorStatusManager
					.getStoppedStatusMap());
		}
		return mapper.writeValueAsString(statusMap);
	}

	@RequestMapping(value = "/running/{status}/{password}")
	@ResponseBody
	public String running(@PathVariable boolean status,
			@PathVariable String password) {
		if (!monitorStatusManager.validPassword(password)) {
			return "wrong password";
		}
		statusMonitorListener.setRunning(status);
		return "success";
	}

	@RequestMapping(value = "/rc/{password}")
	@ResponseBody
	public String reloadConifg(@PathVariable String password) {
		if (!monitorStatusManager.validPassword(password)) {
			return "wrong password";
		}
		monitorStatusManager.loadConfig();
		return "success";
	}

	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	@Resource(name = "statusMap")
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}

	@Resource(name = "andStatusMonitorListener")
	public void setStatusMonitorListener(
			AndStatusMonitorListener statusMonitorListener) {
		this.statusMonitorListener = statusMonitorListener;
	}

	@Resource
	public void setMonitorStatusManager(
			MonitorStatusManager monitorStatusManager) {
		this.monitorStatusManager = monitorStatusManager;
	}

}
