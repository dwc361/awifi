package com.zjhcsoft.uac.account.whitelist.action;

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
import com.zjhcsoft.uac.account.whitelist.entity.WhiteList;
import com.zjhcsoft.uac.account.whitelist.service.WhiteListService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("uac_account_whiteListAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class WhiteListAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private WhiteListService whiteListService;

	private WhiteList whiteList;

	private List<WhiteList> whiteListList;
	
	private DictionaryService dictionaryService;
	
	private ResourceService resourceService;
	
	private Long currentPage;

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
		// List<Dictionary> demoList = dictionaryService.findByType("字典表类型");
		// super.addParameter("demoList", demoList);
		super.addParameter("currentPage", currentPage);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = whiteListService.queryWhiteListPage(whiteList, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/account/whiteList/whiteList_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		whiteList = new WhiteList();
		super.addParameter("whiteList_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/whiteList/whiteList_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		whiteList = whiteListService.load(whiteList.getId());
		super.addParameter("whiteList", whiteList);
		super.addParameter("whiteList_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/whiteList/whiteList_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		whiteList = whiteListService.load(whiteList.getId());
		super.addParameter("whiteList", whiteList);
		super.addParameter("whiteList_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/whitelist/whiteList_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = whiteListService.load(whiteList.getId());
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
			whiteListService.delete(whiteList);
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
			if(whiteList == null){
				whiteList = new WhiteList();
			}
			if (whiteList.getId() == null) {
				whiteListService.save(whiteList);
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
			if(whiteList == null){
				whiteList = new WhiteList();
			}
			whiteListService.updateIgnoreNull(whiteList);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	@Resource
	public void setWhiteListService(WhiteListService whiteListService) {
		this.whiteListService = whiteListService;
	}
	
	public WhiteListService getWhiteListService() {
		return whiteListService;
	}

	public WhiteList getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(WhiteList whiteList) {
		this.whiteList = whiteList;
	}

	public List<WhiteList> getWhiteListList() {
		return whiteListList;
	}

	public void setWhiteListList(List<WhiteList> whiteListList) {
		this.whiteListList = whiteListList;
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