package com.sam.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sam.model.User;
import com.sam.model.dao.UserRepo;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public User addUser(User user) {
		if(userRepo.findOne(user.getId()) != null)
			return null;
		
		userRepo.save(user);
		return userRepo.findOne(user.getId());
	}
	
	public User findUser(String id) {
		return userRepo.findOne(id);
	}
	
	public List<User> findAll(){
		List<User> list = new ArrayList<User>();
		userRepo.findAll().forEach((user) -> {
			list.add(user);
		});
		
		return list;
	}
}
