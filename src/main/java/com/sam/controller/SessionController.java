package com.sam.controller;

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
	
	@PostMapping("/Attr")
	public String setSessionAttribute(String name,Model model) {
		model.addAttribute("sessionAttr", name);
		return "/index.jsp";
	}
	
	@GetMapping("/Attr")
	@ResponseBody
	public Object getSessionAttribute(@SessionAttribute(name="sessionAttr") String attr) {
		
		return attr != null 
			 ? ResponseEntity.status(200).body("from server 2 , attr = " + attr)
			 : ResponseEntity.status(400).body("failure");
	}
	
}
