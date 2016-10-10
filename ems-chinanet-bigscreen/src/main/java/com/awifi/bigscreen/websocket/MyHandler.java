package com.awifi.bigscreen.websocket;

import java.io.IOException;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * websocket处理类
 * @author zhangmm
 */
public class MyHandler extends TextWebSocketHandler {

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
		System.out.println("handleBinaryMessage");
		super.handleBinaryMessage(session, message);
		TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
		try {
			session.sendMessage(returnMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage");
		super.handleTextMessage(session, message);
		TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
		session.sendMessage(returnMessage);
	}
}