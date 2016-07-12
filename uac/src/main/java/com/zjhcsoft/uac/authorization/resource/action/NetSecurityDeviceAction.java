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

import com.zjhcsoft.uac.authorization.resource.entity.NetSecurityDevice;
import com.zjhcsoft.uac.authorization.resource.service.NetSecurityDeviceService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("uac_authorization_system_netSecurityDeviceAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class NetSecurityDeviceAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private NetSecurityDeviceService netSecurityDeviceService;

	private NetSecurityDevice netSecurityDevice;

	private List<NetSecurityDevice> netSecurityDeviceList;
	
	private DictionaryService dictionaryService;
	
	private ResourceService resourceService;
	
	private Long currentPage;
	
	private Date lastTime;

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
		super.addParameter("stateList", stateList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = netSecurityDeviceService.queryNetSecurityDevicePage(netSecurityDevice, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/authorization/resource/netSecurityDevice/netSecurityDevice_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		netSecurityDevice = new NetSecurityDevice();
		super.addParameter("netSecurityDevice_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/netSecurityDevice/netSecurityDevice_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		netSecurityDevice = netSecurityDeviceService.load(netSecurityDevice.getId());
		super.addParameter("netSecurityDevice", netSecurityDevice);
		super.addParameter("netSecurityDevice_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/netSecurityDevice/netSecurityDevice_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		netSecurityDevice = netSecurityDeviceService.load(netSecurityDevice.getId());
		super.addParameter("netSecurityDevice", netSecurityDevice);
		super.addParameter("netSecurityDevice_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/netSecurityDevice/netSecurityDevice_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = netSecurityDeviceService.load(netSecurityDevice.getId());
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
			netSecurityDeviceService.delete(netSecurityDevice);
			result = new Result("删除成功!");
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
			if(netSecurityDevice == null){
				netSecurityDevice = new NetSecurityDevice();
			}
			if (netSecurityDevice.getId() == null) {
				/**
				 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
				 */
				lastTime = sysTime();
				netSecurityDevice.setModifytime(lastTime);
				netSecurityDeviceService.save(netSecurityDevice);
				result = new Result("新增成功!");
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
			if(netSecurityDevice == null){
				netSecurityDevice = new NetSecurityDevice();
			}
			/**
			 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
			 */
			lastTime = sysTime();
			netSecurityDevice.setModifytime(lastTime);
			netSecurityDeviceService.updateIgnoreNull(netSecurityDevice);
			result = new Result("修改成功!");
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
	public void setNetSecurityDeviceService(NetSecurityDeviceService netSecurityDeviceService) {
		this.netSecurityDeviceService = netSecurityDeviceService;
	}
	
	public NetSecurityDeviceService getNetSecurityDeviceService() {
		return netSecurityDeviceService;
	}

	public NetSecurityDevice getNetSecurityDevice() {
		return netSecurityDevice;
	}

	public void setNetSecurityDevice(NetSecurityDevice netSecurityDevice) {
		this.netSecurityDevice = netSecurityDevice;
	}

	public List<NetSecurityDevice> getNetSecurityDeviceList() {
		return netSecurityDeviceList;
	}

	public void setNetSecurityDeviceList(List<NetSecurityDevice> netSecurityDeviceList) {
		this.netSecurityDeviceList = netSecurityDeviceList;
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
}