package com.sam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sam.model.Message;
import com.sam.model.User;

@Controller
@SessionAttributes(names="userInfo")
public class WebSocketController {

	@Resource(name="brokerMessagingTemplate")
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private SimpUserRegistry simpUserRegistry;
	
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
	public void messaging(@RequestBody Message msg) {
		msg.setServerName(serverName);
		
		if("userLogin".equals(msg.getMsg())
			|| "userLogout".equals(msg.getMsg())) {
			List<String> onlineUserList = new ArrayList<String>();
			onlineUserList.add("All");
			
			for(SimpUser simpUser : simpUserRegistry.getUsers())
				onlineUserList.add(simpUser.getName());
			
			simpMessagingTemplate.convertAndSend("/topic/userList", onlineUserList);
		} else if("All".equals(msg.getTo())) {
			msg.setFrom(msg.getFrom() + " (Broadcast) ");
			msg.setTime(new Date(System.currentTimeMillis()));
			simpMessagingTemplate.convertAndSend("/topic/messages", msg);
		} else {
			msg.setTime(new Date(System.currentTimeMillis()));
			simpMessagingTemplate.convertAndSendToUser(msg.getFrom(), "/queue/messages", msg);
			simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/queue/messages", msg);
		}
		
	}
	
}
