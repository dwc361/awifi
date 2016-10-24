/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.awifi.bigscreen.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Spring WebSocket API的核心接口是WebSocketHandler。
 * 我把它叫做消息处理中心。
 * @author zhangmm
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {
	private Logger log = Logger.getLogger(SystemWebSocketHandler.class);

	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;

	/**
	 * 初次链接成功执行
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("ConnectionEstablished");
		users.add(session);
		//session.sendMessage(new TextMessage("connect success"));
	}

	/**
	 * 接受消息处理消息
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.debug("handleMessage: " + message.getPayload().toString());
		// sendMessageToUsers();
		// session.sendMessage(new TextMessage(new Date() + ""));
	}

	/**
	 * 链接出错，关闭链接
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);
		log.debug("handleTransportError" + exception.getMessage());
	}

	/**
	 * 链接关闭后执行
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		users.remove(session);
		log.debug("afterConnectionClosed" + closeStatus.getReason());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUser(String userName, TextMessage message) {
		for (WebSocketSession user : users) {
			if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}