package com.zjhcsoft.uac.authorization.resource.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.roof.exceptions.ApplicationException;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.authorization.resource.entity.Host;
import com.zjhcsoft.uac.authorization.resource.service.HostService;
import com.zjhcsoft.uac.authorization.resource.service.SystemService;
import com.zjhcsoft.uac.blj.service.BljService;

/**
 * 自动生成模版
 */
@Component("uac_authorization_system_hostAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class HostAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private HostService hostService;

	private Host host;

	private List<Host> hostList;
	
	private DictionaryService dictionaryService;
	
	private ResourceService resourceService;
	
	private Long currentPage;
	
	private Date lastTime;
	private BljService bljService;
	private SystemService systemService;
	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		String url = super.getRequest().getRequestURI();
		List<org.roof.security.entity.Resource> list = resourceService.findModuleByPath(url.substring(url.lastIndexOf("/"),url.indexOf(".")));
		if (list.size() > 0) {
			super.addParameter("module", list.get(0));
		}
		// (Staff)BaseUserContext.getCurrentUser();// 得到当前用户
		List<Dictionary> stateList = dictionaryService.findByType("STATE");
		List<Dictionary> servetypeList = dictionaryService.findByType("SERVE_TYPE");
		List<Dictionary> typenameList = dictionaryService.findByType("SYSTEM_CATEGORY");
		super.addParameter("typenameList", typenameList);
		super.addParameter("stateList", stateList);
		super.addParameter("servetypeList", servetypeList);
		super.addParameter("currentPage", currentPage);
		List<Dictionary> regionList = dictionaryService.findByType("REGION");
		super.addParameter("regionList", regionList);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = hostService.queryHostPage(host, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/authorization/resource/host/host_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		host = new Host();
		super.addParameter("host_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/host/host_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		host = hostService.load(host.getId());
		super.addParameter("host", host);
		super.addParameter("host_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/host/host_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		host = hostService.load(host.getId());
		super.addParameter("host", host);
		super.addParameter("host_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/host/host_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = hostService.load(host.getId());
		return JSON;
	}

	/**
	 * 删除实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String delete() throws ApplicationException {
		try {
			String re = "";
			re = bljService.Servicedelete(host);
			if(systemService.hasOneIp(host.getId(),host.getIp())){
				re =re+ bljService.Systemdelete(host);
			}
			hostService.delete(host);
			result = new Result("删除成功!"+re);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00002");
		}
		return JSON;
	}

	/**
	 * 保存实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String create() throws ApplicationException {
		try {
			if(host == null){
				host = new Host();
			}
			/**
			 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
			 */
			lastTime = sysTime();
			host.setModifytime(lastTime);
			hostService.save(host);
			String re = "";
			if(systemService.hasOneIp(host.getIp())){
				re = bljService.Systemadd(host);
			}
			re =re+ bljService.Serviceadd(host);
			result = new Result("新增成功!"+re);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	/**
	 * 修改实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String update() throws ApplicationException {
		try {
			if(host == null){
				host = new Host();
			}
			/**
			 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
			 */
			lastTime = sysTime();
			host.setModifytime(lastTime);
			hostService.updateIgnoreNull(host);
			String re = "";
			//re = bljService.Systemadd(host);
			//re =re+ bljService.Serviceadd(host);
			re = bljService.Systemupdate(host);
			re =re+ bljService.Serviceupdate(host);
			result = new Result("修改成功!"+re);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}
	/**
	 * 添加人：yxg 添加时间：2014-08-26 添加原因：获取系统当前时间
	 */
	public Date sysTime() {
		Date date = new Date();
		return date;
	}
	@Resource
	public void setHostService(HostService hostService) {
		this.hostService = hostService;
	}
	
	public HostService getHostService() {
		return hostService;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<Host> getHostList() {
		return hostList;
	}

	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}
	
	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}
	
	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@Resource
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	@Resource
	public void setBljService(BljService bljService) {
		this.bljService = bljService;
	}
	
	@Resource
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
}