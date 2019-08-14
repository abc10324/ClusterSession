package com.sam.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sam.model.OnlineUser;

@Repository
public interface OnlineUserRepo extends CrudRepository<OnlineUser, String> {

}
