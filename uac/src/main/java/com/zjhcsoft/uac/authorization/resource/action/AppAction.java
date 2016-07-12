package com.zjhcsoft.uac.authorization.resource.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.exceptions.ApplicationException;
import org.roof.dataaccess.Page;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.roof.web.dictionary.entity.Dictionary;

import com.zjhcsoft.uac.authorization.resource.entity.App;
import com.zjhcsoft.uac.authorization.resource.service.AppService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("uac_authorization_appAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AppAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private AppService appService;

	private App app;

	private List<App> appList;

	private DictionaryService dictionaryService;

	private ResourceService resourceService;

	private Long currentPage;

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		String url = super.getRequest().getRequestURI();
		List<org.roof.security.entity.Resource> list = resourceService.findModuleByPath(url.substring(
				url.lastIndexOf("/"), url.indexOf(".")));
		if (list.size() > 0) {
			super.addParameter("module", list.get(0));
		}
		// (Staff)BaseUserContext.getCurrentUser();// 得到当前用户
		List<Dictionary> regionList = dictionaryService.findByType("REGION");
		super.addParameter("regionList", regionList);
		List<Dictionary> stateList = dictionaryService.findByType("STATE");
		super.addParameter("stateList", stateList);
		super.addParameter("currentPage", currentPage);
	}

	public String index() {
		loadCommonData();
		result = "/uac/authorization/resource/app/app_index.jsp";
		return JSP;
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = appService.queryAppPage(app, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/authorization/resource/app/app_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		app = new App();
		super.addParameter("app_paramString", super.getParamString(WebUtils.getRequest()));
		super.addParameter("app_region_id", super.getParamByName("app_region_id"));
		loadCommonData();
		result = "/uac/authorization/resource/app/app_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		app = appService.load(app.getId());
		super.addParameter("app", app);
		super.addParameter("app_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/app/app_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		app = appService.load(app.getId());
		super.addParameter("app", app);
		super.addParameter("app_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/app/app_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = appService.load(app.getId());
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
			appService.delete(app);
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
			if (app == null) {
				app = new App();
			}
			appService.save(app);
			result = new Result("新增成功!");
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
			if (app == null) {
				app = new App();
			}
			appService.updateIgnoreNull(app);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	@Resource
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public AppService getAppService() {
		return appService;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
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