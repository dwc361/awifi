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

import com.zjhcsoft.uac.authorization.resource.entity.Db;
import com.zjhcsoft.uac.authorization.resource.service.DbService;
import com.zjhcsoft.uac.authorization.resource.service.SystemService;
import com.zjhcsoft.uac.blj.service.BljService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("uac_authorization_system_dbAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DbAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private DbService dbService;

	private Db db;

	private List<Db> dbList;
	
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
		super.addParameter("stateList", stateList);
		List<Dictionary> dbTypeList = dictionaryService.findByType("DB_TYPE");
		List<Dictionary> typenameList = dictionaryService.findByType("SYSTEM_CATEGORY");
		super.addParameter("typenameList", typenameList);
		super.addParameter("dbTypeList", dbTypeList);
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
		page = dbService.queryDbPage(db, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/authorization/resource/db/db_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		db = new Db();
		super.addParameter("db_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/db/db_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		db = dbService.load(db.getId());
		super.addParameter("db", db);
		super.addParameter("db_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/db/db_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		db = dbService.load(db.getId());
		super.addParameter("db", db);
		super.addParameter("db_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/authorization/resource/db/db_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = dbService.load(db.getId());
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
			re = bljService.Servicedelete(db);
			if(systemService.hasOneIp(db.getId(),db.getIp())){
				re =re+ bljService.Systemdelete(db);
			}
			dbService.delete(db);
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
			if(db == null){
				db = new Db();
			}
			if (db.getId() == null) {
				/**
				 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
				 */
				lastTime = sysTime();
				db.setModifytime(lastTime);
				dbService.save(db);
				String re = "";
				if(systemService.hasOneIp(db.getIp())){
					re = bljService.Systemadd(db);
				}
				re =re+ bljService.Serviceadd(db);
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
			if(db == null){
				db = new Db();
			}
			/**
			 * 添加人：yxg 添加时间：2014-08-29 添加原因：增加系统当前时间
			 */
			lastTime = sysTime();
			db.setModifytime(lastTime);
			
			dbService.updateIgnoreNull(db);
			String re = "";
			re = bljService.Systemupdate(db);
			re = re + bljService.Serviceupdate(db);
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
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	
	public DbService getDbService() {
		return dbService;
	}

	public Db getDb() {
		return db;
	}

	public void setDb(Db db) {
		this.db = db;
	}

	public List<Db> getDbList() {
		return dbList;
	}

	public void setDbList(List<Db> dbList) {
		this.dbList = dbList;
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