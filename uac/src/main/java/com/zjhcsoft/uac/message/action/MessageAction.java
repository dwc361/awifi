package com.zjhcsoft.uac.message.action;

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
import org.roof.web.user.entity.Staff;
import org.roof.web.dictionary.entity.Dictionary;
import com.zjhcsoft.uac.message.entity.Message;
import com.zjhcsoft.uac.message.service.MessageService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("uac_messageAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class MessageAction extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private MessageService messageService;

	private Message message;

	private List<Message> messageList;

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
		if (message == null) {
			message = new Message();
		}
		message.setToStaff((Staff) BaseUserContext.getCurrentUser());
		page = messageService.queryMessagePage(message, page);
		super.addParameter("page", page);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		super.addParameter("pageBar", pageBar);
		loadCommonData();
		result = "/uac/message/message_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		message = new Message();
		super.addParameter("message_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/message/message_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * 
	 * @return
	 */
	public String update_page() throws Exception {
		message = messageService.load(message.getId());
		super.addParameter("message", message);
		super.addParameter("message_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/message/message_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		message = messageService.load(message.getId());
		message.setSts("已读");
		super.addParameter("message", message);
		super.addParameter("message_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/uac/message/message_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = messageService.load(message.getId());
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
			messageService.delete(message);
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
			if (message == null) {
				message = new Message();
			}
			message.setSts("未读");
			messageService.save(message);
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
			if (message == null) {
				message = new Message();
			}
			messageService.updateIgnoreNull(message);
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	@Resource
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
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