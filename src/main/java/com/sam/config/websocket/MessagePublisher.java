package com.sam.config.websocket;

import java.util.List;

import com.sam.model.Message;

public interface MessagePublisher {
	public void publish(String message);
	public void publish(Message message);
	public void publish(List<String> onlineUserList);
}
