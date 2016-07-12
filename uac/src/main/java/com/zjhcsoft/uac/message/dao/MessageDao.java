package com.zjhcsoft.uac.message.dao;

import java.util.List;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.message.entity.Message;

/**
 * 自动生成
 */
@Component
public class MessageDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryMessagePage(Message paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "Message_exp_select_message_list", "Message_exp_select_message_count");
		return pageQuery.findByMappedQuery(paramObj);
	}

	public List<Message> queryCountMessage(Message paramObj, int maxResults) {
		return (List<Message>) super.findByMappedQuery("Message_exp_select_message_list", 0, maxResults, paramObj);
	}
}
