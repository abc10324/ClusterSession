package com.sam.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(names="sessionAttr")
public class SessionController {
	private String serverName;
	
	public SessionController() {
		this.serverName = UUID.randomUUID()
							  .toString()
							  .replace("-", "")
							  .substring(0, 3);
	}
	
	@GetMapping("/SessionTest")
	public String getSessionTestPage() {
		return "sessionTest";
	}
	
	@PostMapping("/Attr")
	public String setSessionAttribute(String name,Model model) {
		model.addAttribute("sessionAttr", name);
		return "sessionTest";
	}
	
	@GetMapping("/Attr")
	@ResponseBody
	public Object getSessionAttribute(@SessionAttribute(name="sessionAttr") String attr) {
		
		return attr != null 
			 ? ResponseEntity.status(200).body("from server " + serverName + " , attr = " + attr)
			 : ResponseEntity.status(400).body("failure");
	}
	
}
