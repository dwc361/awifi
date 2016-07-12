package com.zjhcsoft.uac.message.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.message.dao.MessageDao;
import com.zjhcsoft.uac.message.entity.Message;

/**
 * 自动生成
 */
@Component
public class MessageService {

	private MessageDao messageDao;

	/**
	 * 查询指定数量的消息
	 * 
	 * @param paramObj
	 * @param maxResults
	 * @return
	 */
	public List<Message> queryCountMessage(Message paramObj, int maxResults) {
		return messageDao.queryCountMessage(paramObj, maxResults);
	}

	/**
	 * 列表展示
	 */
	public Page queryMessagePage(Message paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new Message();
		}
		return messageDao.queryMessagePage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public Message save(Message paramObj) throws Exception {
		messageDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public Message delete(Message paramObj) throws Exception {
		List<Message> list = (List<Message>) messageDao.findByMappedQuery("Message_exp_select_message_list", paramObj);
		for (Message message : list) {
			messageDao.delete(message);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<Message> select(Message paramObj) {
		List<Message> list = (List<Message>) messageDao.findByMappedQuery("Message_exp_select_message_list", paramObj);
		return list;
	}

	/**
	 * 查询数据
	 */
	public Message selectObject(Message paramObj) {
		List<Message> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new Message();
		}
	}

	/**
	 * 修改数据
	 */
	public Message update(Message paramObj) throws Exception {
		messageDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public Message updateIgnoreNull(Message paramObj) throws Exception {
		messageDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public Message load(Serializable id) throws Exception {
		Message paramObj = (Message) messageDao.load(Message.class, id, false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<Message> loadAll() throws Exception {
		return (List<Message>) messageDao.loadAll(Message.class);
	}

	@Resource
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

}
