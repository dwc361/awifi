package com.zjhcsoft.uac.authorization.resource.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.roof.exceptions.ApplicationException;
import org.roof.dataaccess.Page;
import org.roof.security.BaseUserContext;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.roof.web.dictionary.entity.Dictionary;

import com.zjhcsoft.uac.authorization.resource.entity.NetDevice;
import com.zjhcsoft.uac.authorization.resource.service.NetDeviceService;
import com.zjhcsoft.uac.authorization.resource.service.SystemService;
import com.zjhcsoft.uac.blj.service.BljService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("uac_authorization_system_netDeviceAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class NetDeviceAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private NetDeviceService netDeviceService;

	private NetDevice netDevice;

	private List<NetDevice> netDeviceList;
	
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
		page = netDeviceService.queryNetDevicePage(netDevice, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/authorization/resource/netDevice/netDevice_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		netDevice = new NetDevice();
		super.addParameter("netDevice_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/netDevice/netDevice_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		netDevice = netDeviceService.load(netDevice.getId());
		super.addParameter("netDevice", netDevice);
		super.addParameter("netDevice_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/netDevice/netDevice_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		netDevice = netDeviceService.load(netDevice.getId());
		super.addParameter("netDevice", netDevice);
		super.addParameter("netDevice_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/netDevice/netDevice_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = netDeviceService.load(netDevice.getId());
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
			re = bljService.Servicedelete(netDevice);
			if(systemService.hasOneIp(netDevice.getId(),netDevice.getIp())){
				re =re+ bljService.Systemdelete(netDevice);
			}
			netDeviceService.delete(netDevice);
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
			if(netDevice == null){
				netDevice = new NetDevice();
			}
			if (netDevice.getId() == null) {
				/**
				 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
				 */
				lastTime = sysTime();
				netDevice.setModifytime(lastTime);
				netDeviceService.save(netDevice);
				String re = "";
				if(systemService.hasOneIp(netDevice.getIp())){
					re = bljService.Systemadd(netDevice);
				}
				re =re+ bljService.Serviceadd(netDevice);
				result = new Result("新增成功!"+re);
			}
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
			if(netDevice == null){
				netDevice = new NetDevice();
			}
			/**
			 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
			 */
			lastTime = sysTime();
			netDevice.setModifytime(lastTime);
			netDeviceService.updateIgnoreNull(netDevice);
			String re = "";
			re = bljService.Systemupdate(netDevice);
			re = re + bljService.Serviceupdate(netDevice);
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
	  /*SimpleDateFormat style = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = style.format(date);
		try {
			lastTime = style.parse(now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return date;
	}
	@Resource
	public void setNetDeviceService(NetDeviceService netDeviceService) {
		this.netDeviceService = netDeviceService;
	}
	
	public NetDeviceService getNetDeviceService() {
		return netDeviceService;
	}

	public NetDevice getNetDevice() {
		return netDevice;
	}

	public void setNetDevice(NetDevice netDevice) {
		this.netDevice = netDevice;
	}

	public List<NetDevice> getNetDeviceList() {
		return netDeviceList;
	}

	public void setNetDeviceList(List<NetDevice> netDeviceList) {
		this.netDeviceList = netDeviceList;
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