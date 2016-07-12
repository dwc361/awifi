package com.zjhcsoft.uac.bulletin.action;

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
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.resources.service.ResourceService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.bulletin.entity.Bulletin;
import com.zjhcsoft.uac.bulletin.service.BulletinService;
import com.zjhcsoft.uac.cxf.SmsService;

/**
 * 自动生成模版
 */
@Component("uac_bulletinAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class BulletinAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private BulletinService bulletinService;

	private Bulletin bulletin;

	private List<Bulletin> bulletinList;

	private DictionaryService dictionaryService;

	private ResourceService resourceService;

	private Long currentPage;
	
	private SmsService smsService;
	
	@Resource
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	public String sendSms(){
		smsService.send("001111", "18919816671");
		result = new Result(Result.SUCCESS,"发送短信时间：");
		return JSON;
	}
	
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
		page = bulletinService.queryBulletinPage(bulletin, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/bulletin/bulletin_list.jsp";
		return JSP;
	}

	public String list_readonly() {
		super.addParameter("isRead", "_readonly");
		return list();
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		bulletin = new Bulletin();
		super.addParameter("bulletin_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/bulletin/bulletin_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		bulletin = bulletinService.load(bulletin.getId());
		super.addParameter("bulletin", bulletin);
		super.addParameter("bulletin_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/bulletin/bulletin_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		bulletin = bulletinService.load(bulletin.getId());
		bulletin.setVisitors(bulletin.getVisitors() + 1);
		super.addParameter("bulletin", bulletin);
		super.addParameter("bulletin_paramString", super.getParamString(WebUtils.getRequest()));
		super.addParameter("isRead", super.getParamByName("isRead"));
		loadCommonData();
		result = "/uac/bulletin/bulletin_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = bulletinService.load(bulletin.getId());
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
			bulletinService.delete(bulletin);
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
			if (bulletin == null) {
				bulletin = new Bulletin();
			}
			bulletin.setCreate_date(new Date());
			bulletin.setVisitors(0L);
			bulletinService.save(bulletin);
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
			if (bulletin == null) {
				bulletin = new Bulletin();
			}
			bulletinService.updateIgnoreNull(bulletin);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	@Resource
	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}

	public List<Bulletin> getBulletinList() {
		return bulletinList;
	}

	public void setBulletinList(List<Bulletin> bulletinList) {
		this.bulletinList = bulletinList;
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