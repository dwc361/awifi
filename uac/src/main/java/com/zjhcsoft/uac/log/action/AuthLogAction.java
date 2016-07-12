package com.zjhcsoft.uac.log.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
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

import com.zjhcsoft.uac.log.entity.AuthLog;
import com.zjhcsoft.uac.log.service.AuthLogService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("uac_log_authLogAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AuthLogAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private AuthLogService authLogService;

	private AuthLog authLog;

	private List<AuthLog> authLogList;
	
	private DictionaryService dictionaryService;
	
	private ResourceService resourceService;
	
	private Long currentPage;
	private static final Logger LOGGER = Logger
			.getLogger(AuthLogAction.class);
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
		List<Dictionary> loginFailReasonList = dictionaryService.findByType("LOGIN_FAIL_REASON");
		super.addParameter("loginFailReasonList", loginFailReasonList);
		List<Dictionary> loginResultList = dictionaryService.findByType("LOGIN_RESULT");
		super.addParameter("loginResultList", loginResultList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = authLogService.queryAuthLogPage(authLog, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/log/authLog/authLog_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		authLog = new AuthLog();
		super.addParameter("authLog_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/authLog/authLog_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		authLog = authLogService.load(authLog.getId());
		super.addParameter("authLog", authLog);
		super.addParameter("authLog_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/authLog/authLog_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		authLog = authLogService.load(authLog.getId());
		super.addParameter("authLog", authLog);
		super.addParameter("authLog_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/authLog/authLog_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = authLogService.load(authLog.getId());
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
			authLogService.delete(authLog);
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
			if(authLog == null){
				authLog = new AuthLog();
			}
			if (authLog.getId() == null) {
				authLogService.save(authLog);
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
			if(authLog == null){
				authLog = new AuthLog();
			}
			authLogService.updateIgnoreNull(authLog);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}
	

	public String exportXls() {
		HttpServletResponse response = WebUtils.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		try {
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ URLEncoder.encode("认证审计-" + new Date().getTime()
									+ ".xls", "UTF-8"));
			OutputStream os = response.getOutputStream();
			if(authLog == null){
				authLog = new AuthLog();
			}
			if(authLog.getAuth_time()==null && authLog.getAuth_time_end()==null){
				authLog.setAuth_time_end(new Date());
			}
			if(authLog.getAuth_time()==null && authLog.getAuth_time_end()!=null){
				Date auth_time = new Date(authLog.getAuth_time_end().getTime()-7 * 24 * 60 * 60 * 1000);
				authLog.setAuth_time(auth_time);
			}else if(authLog.getAuth_time()!=null && authLog.getAuth_time_end()==null){
				Date auth_time_end = new Date(authLog.getAuth_time().getTime()+7 * 24 * 60 * 60 * 1000);
				authLog.setAuth_time_end(auth_time_end);
			}
			authLogService.exportXls(os,authLog);
			os.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return NONE;
	}

	@Resource
	public void setAuthLogService(AuthLogService authLogService) {
		this.authLogService = authLogService;
	}
	
	public AuthLogService getAuthLogService() {
		return authLogService;
	}

	public AuthLog getAuthLog() {
		return authLog;
	}

	public void setAuthLog(AuthLog authLog) {
		this.authLog = authLog;
	}

	public List<AuthLog> getAuthLogList() {
		return authLogList;
	}

	public void setAuthLogList(List<AuthLog> authLogList) {
		this.authLogList = authLogList;
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