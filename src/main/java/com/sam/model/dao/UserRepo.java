package com.sam.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.model.User;

public interface UserRepo extends JpaRepository<User, String> {

}
