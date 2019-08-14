package com.sam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.sam.config.websocket.MessagePublisher;
import com.sam.model.Message;
import com.sam.model.OnlineUser;
import com.sam.model.User;
import com.sam.model.dao.OnlineUserRepo;

@Controller
@SessionAttributes(names="userInfo")
public class WebSocketController {

	@Resource(name="brokerMessagingTemplate")
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private SimpUserRegistry simpUserRegistry;
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	@Autowired
	private OnlineUserRepo onlineUserRepo;
	
	private Logger logger = Logger.getLogger(WebSocketController.class);
	
	private String serverName;
	
	public WebSocketController() {
		this.serverName = UUID.randomUUID()
							  .toString()
							  .replace("-", "")
							  .substring(0, 3);
	}
	
	@GetMapping("/WebSocketTest")
	public String webSokcetPage(@SessionAttribute(name="userInfo") User user) {
		return user != null ? "webSocketTest" : "indexRedirect";
	}
	
	@MessageMapping("/chat")
	public void messagingToRedis(@RequestBody Message msg) {
		msg.setServerName(serverName);
		msg.setTime(new Date(System.currentTimeMillis()));
		
		if(!"userLogin".equals(msg.getMsg()) && !"userLogout".equals(msg.getMsg()))
			messagePublisher.publish(msg); // publish to Redis
	}
	
	
	// called by Redis message subscriber
	public void messaging(Message msg) {
		
		if("All".equals(msg.getTo())) {
			msg.setFrom(msg.getFrom() + " (Broadcast) ");
			simpMessagingTemplate.convertAndSend("/topic/messages", msg);
		} else {
			simpMessagingTemplate.convertAndSendToUser(msg.getFrom(), "/queue/messages", msg);
			simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/queue/messages", msg);
		}
		
	}
	
	public void updateUserList() {
		List<String> onlineUserList = new ArrayList<String>();
		onlineUserRepo.findAll().forEach((user) -> {
			if(user != null)
				onlineUserList.add(user.getName());
		});
		
		messagePublisher.publish(onlineUserList); // publish to Redis
	}
	
	// called by Redis userSet subscriber
	public void sendUserList(List<String> onlineUserList) {
		onlineUserList.add("All");
		simpMessagingTemplate.convertAndSend("/topic/userList", onlineUserList);
	}
	
	@EventListener
	public void onConnectEvent(SessionConnectEvent event) {
		logger.info("Client with username " + event.getUser().getName() +" connected");
	    onlineUserRepo.save(new OnlineUser(event.getUser().getName()));
	    updateUserList();
	}
	
	@EventListener
	public void onDisconnectEvent(SessionDisconnectEvent event) {
		logger.info("Client with username " + event.getUser().getName() +" disconnected");
	    onlineUserRepo.delete(event.getUser().getName());
	    updateUserList();
	}
	
	
}
