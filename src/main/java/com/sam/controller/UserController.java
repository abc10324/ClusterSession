package com.sam.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sam.model.User;
import com.sam.model.service.UserService;

@Controller
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/Regist")
	@ResponseBody
	public Object regist(@Valid @RequestBody User user){
		System.out.println(user);
		User result = userService.addUser(user);

		return result != null 
			 ? ResponseEntity
			  .status(200)
			  .body(result) 
			 : ResponseEntity
			  .status(400)
			  .body(Collections.singletonMap("error", "user already existed"));
	}
	
	@GetMapping("/Regist")
	public String registPage() {
		return "regist";
	}
	
	@GetMapping("/Login")
	public String getLoginPage() {
		return "login";
	}
	
	@PostMapping("/Login")
	public String login(String id, Model model) {
		User result = userService.findUser(id);
		
		if(result != null) {
			return "index";
		} else {
			model.addAttribute("errorMsg", "user not existed");
			return "login";
		}
	}
	
	@GetMapping("/User")
	@ResponseBody
	public Object findUser(@NotBlank String id){
		User result = userService.findUser(id);
		
		return result != null ? result 
			 : ResponseEntity
			  .status(404)
			  .body(Collections.singletonMap("error", "user not existed"));
	}
	
	@GetMapping("/Users")
	@ResponseBody
	public Object findUsers(){
		List<User> result = userService.findAll();
		
		return result.size() != 0 ? result  
			 : ResponseEntity
			  .status(404)
			  .body(Collections.singletonMap("error", "no user"));
	}
	
	@GetMapping("/Test")
	@ResponseBody
	public Map<String,Object> test(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("error", "something goes wrong");
		
		return map;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Map<String, Object> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, Object> errors = new HashMap<>();
	    List<String> errorList = new ArrayList<String>();
	    
	    ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
	    	errorList.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
	    });
	    
	    errors.put("errors",errorList);
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public Map<String, Object> handleParameterValidationExceptions(
			ConstraintViolationException ex) {
	    Map<String, Object> errors = new HashMap<>();
	    List<String> errorList = new ArrayList<String>();
	    
	    ex.getConstraintViolations().forEach((error) -> {
	    	errorList.add(((PathImpl)error.getPropertyPath()).getLeafNode().getName() + " : " + error.getMessage());
	    });
	    
	    errors.put("errors",errorList);
	    
	    return errors;
	}
	
}
