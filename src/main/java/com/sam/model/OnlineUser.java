package com.sam.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@RedisHash("online_user")
@Data
public class OnlineUser {
	@Id
	private String name;
	
	public OnlineUser(String name) {
		this.name = name;
	}
}
