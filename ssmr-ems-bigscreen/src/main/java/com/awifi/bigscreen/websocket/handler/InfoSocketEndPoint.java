package com.awifi.bigscreen.websocket.handler;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * websocket处理类
 * @author zhangmm
 */
//@Component
public class InfoSocketEndPoint extends TextWebSocketHandler {

	public InfoSocketEndPoint() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
		try {
			session.sendMessage(returnMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
		super.handleBinaryMessage(session, message);
		TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
		try {
			session.sendMessage(returnMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}