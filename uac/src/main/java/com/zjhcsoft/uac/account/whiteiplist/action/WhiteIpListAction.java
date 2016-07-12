package com.zjhcsoft.uac.account.whiteiplist.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.roof.exceptions.ApplicationException;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.PageUtils;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.whiteiplist.entity.WhiteIpList;
import com.zjhcsoft.uac.account.whiteiplist.service.WhiteIpListService;

/**
 * 自动生成模版
 */
@Component("uac_account_whiteIpListAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class WhiteIpListAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private WhiteIpListService whiteIpListService;
	
	private WhiteIpList whiteIpList;
	
	private List<WhiteIpList> whiteipListList;
	
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
		page = whiteIpListService.queryWhiteIpListPage(whiteIpList, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/account/whiteipList/whiteipList_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		whiteIpList = new WhiteIpList();
		super.addParameter("whiteipList_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/whiteipList/whiteipList_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		whiteIpList = whiteIpListService.load(whiteIpList.getId());
		super.addParameter("whiteipList", whiteIpList);
		super.addParameter("whiteipList_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/whiteipList/whiteipList_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		whiteIpList = whiteIpListService.load(whiteIpList.getId());
		super.addParameter("whiteipList", whiteIpList);
		super.addParameter("whiteipList_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/account/whiteipList/whiteipList_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = whiteIpListService.load(whiteIpList.getId());
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
			whiteIpListService.delete(whiteIpList);
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
			if(whiteIpList == null){
				whiteIpList = new WhiteIpList();
			}
			if (whiteIpList.getId() == null) {
				whiteIpListService.save(whiteIpList);
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
			if(whiteIpList == null){
				whiteIpList = new WhiteIpList();
			}
			whiteIpListService.updateIgnoreNull(whiteIpList);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
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
	public void setWhiteIpListService(WhiteIpListService whiteIpListService) {
		this.whiteIpListService = whiteIpListService;
	}

	public WhiteIpList getWhiteipList() {
		return whiteIpList;
	}

	public void setWhiteipList(WhiteIpList whiteIpList) {
		this.whiteIpList = whiteIpList;
	}

	public List<WhiteIpList> getWhiteipListList() {
		return whiteipListList;
	}

	public void setWhiteipListList(List<WhiteIpList> whiteipListList) {
		this.whiteipListList = whiteipListList;
	}

	public WhiteIpList getWhiteIpList() {
		return whiteIpList;
	}

	public void setWhiteIpList(WhiteIpList whiteIpList) {
		this.whiteIpList = whiteIpList;
	}
}