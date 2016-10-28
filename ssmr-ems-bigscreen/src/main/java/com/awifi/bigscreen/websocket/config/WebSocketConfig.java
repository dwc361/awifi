package com.awifi.bigscreen.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.awifi.bigscreen.websocket.handler.SystemWebSocketHandler;
import com.awifi.bigscreen.websocket.interceptor.HandshakeInterceptor;

@Configuration
@EnableWebMvc
@EnableWebSocket //开启websocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    public WebSocketConfig() {
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		//支持websocket 的访问链接
		registry.addHandler(systemWebSocketHandler(), "/websocket").addInterceptors(new HandshakeInterceptor());
		//不支持websocket的访问链接
		registry.addHandler(systemWebSocketHandler(), "/websocket/sockjs").addInterceptors(new HandshakeInterceptor()).withSockJS();
		
		
		
//    	registry.addHandler(systemWebSocketHandler(), "/websocketDemo");
//		registry.addHandler(systemWebSocketHandler(), "/websocketDemo/sockjs").setAllowedOrigins("*").withSockJS();
//		registry.addHandler(systemWebSocketHandler(), "/websocketDemo/sockjs").withSockJS();
    }

    @Bean
    public WebSocketHandler systemWebSocketHandler() {
        //return new InfoSocketEndPoint();
        return new SystemWebSocketHandler();
    }

}