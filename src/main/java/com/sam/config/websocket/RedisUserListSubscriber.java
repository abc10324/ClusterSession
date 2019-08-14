package com.sam.config.websocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.sam.controller.WebSocketController;

@Service
public class RedisUserListSubscriber implements MessageListener {
	
	@Autowired
	private WebSocketController webSocketController;
	private Logger logger = Logger.getLogger(RedisUserListSubscriber.class);
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(message.getBody()));
			List<String> onlineUserList = (List<String>) ois.readObject();
			webSocketController.sendUserList(onlineUserList);
		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
			logger.debug(e.getMessage());
		}
        
	}
	

}
