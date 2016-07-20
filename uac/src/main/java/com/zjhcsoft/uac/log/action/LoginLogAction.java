package com.zjhcsoft.uac.log.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

import com.zjhcsoft.uac.log.entity.LoginLog;
import com.zjhcsoft.uac.log.service.LoginLogService;

/**
 * 自动生成模版
 */
@Component("uac_log_loginLogAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class LoginLogAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private LoginLogService loginLogService;

	private LoginLog loginLog;

	private List<LoginLog> loginLogList;

	private DictionaryService dictionaryService;

	private ResourceService resourceService;

	private Long currentPage;

	private static final Logger LOGGER = Logger.getLogger(LoginLogAction.class);

	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		String url = super.getRequest().getRequestURI();
		List<org.roof.security.entity.Resource> list = resourceService
				.findModuleByPath(url.substring(url.lastIndexOf("/"),
						url.indexOf(".")));
		if (list.size() > 0) {
			super.addParameter("module", list.get(0));
		}
		// (Staff)BaseUserContext.getCurrentUser();// 得到当前用户
		List<Dictionary> loginFailReasonList = dictionaryService
				.findByType("LOGIN_FAIL_REASON");
		super.addParameter("loginFailReasonList", loginFailReasonList);
		List<Dictionary> loginResultList = dictionaryService
				.findByType("LOGIN_RESULT");
		super.addParameter("loginResultList", loginResultList);
		List<Dictionary> loginTypeList = dictionaryService
				.findByType("LOGIN_TYPE");
		super.addParameter("loginTypeList", loginTypeList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = loginLogService.queryLoginLogPage(loginLog, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/log/loginLog/loginLog_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		loginLog = new LoginLog();
		super.addParameter("loginLog_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/loginLog/loginLog_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		loginLog = loginLogService.load(loginLog.getId());
		super.addParameter("loginLog", loginLog);
		super.addParameter("loginLog_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/loginLog/loginLog_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		loginLog = loginLogService.load(loginLog.getId());
		super.addParameter("loginLog", loginLog);
		super.addParameter("loginLog_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/loginLog/loginLog_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = loginLogService.load(loginLog.getId());
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
			loginLogService.delete(loginLog);
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
			if (loginLog == null) {
				loginLog = new LoginLog();
			}
			if (loginLog.getId() == null) {
				loginLogService.save(loginLog);
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
			if (loginLog == null) {
				loginLog = new LoginLog();
			}
			loginLogService.updateIgnoreNull(loginLog);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	/*public String exportXls() {
		loginLogService.exportByTask(loginLog);
		result = new Result("文件导出成功,请稍候去下载页面下载!");
		return JSON;
	}*/
	
	public String exportXls() {
		HttpServletResponse response = WebUtils.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		try {
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ URLEncoder.encode("登录审计-" + new Date().getTime()
									+ ".xls", "UTF-8"));
			OutputStream os = response.getOutputStream();
			if (loginLog == null) {
				loginLog = new LoginLog();
			}
			if(loginLog.getLogin_time()==null && loginLog.getLogin_time_end()==null){
				loginLog.setLogin_time_end(new Date());
			}
			if(loginLog.getLogin_time()==null && loginLog.getLogin_time_end()!=null){
				Date Longin_time = new Date(loginLog.getLogin_time_end().getTime()-7 * 24 * 60 * 60 * 1000);
				loginLog.setLogin_time(Longin_time);
			}else if(loginLog.getLogin_time()!=null && loginLog.getLogin_time_end()==null){
				Date Longin_time_end = new Date(loginLog.getLogin_time().getTime()+7 * 24 * 60 * 60 * 1000);
				loginLog.setLogin_time_end(Longin_time_end);
			}
			loginLogService.exportXls(os,loginLog);
			os.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return NONE;
	}

	@Resource
	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	public LoginLogService getLoginLogService() {
		return loginLogService;
	}

	public LoginLog getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(LoginLog loginLog) {
		this.loginLog = loginLog;
	}

	public List<LoginLog> getLoginLogList() {
		return loginLogList;
	}

	public void setLoginLogList(List<LoginLog> loginLogList) {
		this.loginLogList = loginLogList;
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