package com.sam.config.websocket;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.sam.model.User;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		
		if(attributes.containsKey("userInfo")) {
			return new UserPrincipal(((User) attributes.get("userInfo")).getId());
		}
		
		return null;
	}

}
