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

import com.zjhcsoft.uac.log.entity.AccountLog;
import com.zjhcsoft.uac.log.service.AccountLogService;

/**
 * 自动生成模版
 */
@Component("uac_log_accountLogAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class AccountLogAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private AccountLogService accountLogService;

	private AccountLog accountLog;

	private List<AccountLog> accountLogList;

	private DictionaryService dictionaryService;

	private ResourceService resourceService;

	private Long currentPage;

	private static final Logger LOGGER = Logger
			.getLogger(AccountLogAction.class);

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
		List<Dictionary> opTypeList = dictionaryService
				.findByType("ACCOUNT_OP_TYPE");
		super.addParameter("opTypeList", opTypeList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = accountLogService.queryAccountLogPage(accountLog, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/log/accountLog/accountLog_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		accountLog = new AccountLog();
		super.addParameter("accountLog_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/accountLog/accountLog_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		accountLog = accountLogService.load(accountLog.getId());
		super.addParameter("accountLog", accountLog);
		super.addParameter("accountLog_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/accountLog/accountLog_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		accountLog = accountLogService.load(accountLog.getId());
		super.addParameter("accountLog", accountLog);
		super.addParameter("accountLog_paramString",
				super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/log/accountLog/accountLog_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = accountLogService.load(accountLog.getId());
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
			accountLogService.delete(accountLog);
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
			if (accountLog == null) {
				accountLog = new AccountLog();
			}
			if (accountLog.getId() == null) {
				accountLogService.save(accountLog);
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
			if (accountLog == null) {
				accountLog = new AccountLog();
			}
			accountLogService.updateIgnoreNull(accountLog);
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
							+ URLEncoder.encode("账号审计-" + new Date().getTime()
									+ ".xls", "UTF-8"));
			OutputStream os = response.getOutputStream();
			if (accountLog == null) {
				accountLog = new AccountLog();
			}
			accountLogService.exportXls(os, accountLog);
			os.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return NONE;
	}

	@Resource
	public void setAccountLogService(AccountLogService accountLogService) {
		this.accountLogService = accountLogService;
	}

	public AccountLogService getAccountLogService() {
		return accountLogService;
	}

	public AccountLog getAccountLog() {
		return accountLog;
	}

	public void setAccountLog(AccountLog accountLog) {
		this.accountLog = accountLog;
	}

	public List<AccountLog> getAccountLogList() {
		return accountLogList;
	}

	public void setAccountLogList(List<AccountLog> accountLogList) {
		this.accountLogList = accountLogList;
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