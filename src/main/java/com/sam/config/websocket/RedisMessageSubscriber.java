package com.sam.config.websocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.sam.controller.WebSocketController;

@Service
public class RedisMessageSubscriber implements MessageListener {
	
	@Autowired
	private WebSocketController webSocketController;
	private Logger logger = Logger.getLogger(RedisMessageSubscriber.class);
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(message.getBody()));
			com.sam.model.Message msg = (com.sam.model.Message) ois.readObject();
			webSocketController.messaging(msg);
		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
			logger.debug(e.getMessage());
		}
        
	}
	

}
