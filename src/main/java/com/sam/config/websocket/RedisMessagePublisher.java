package com.sam.config.websocket;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import com.sam.model.Message;

public class RedisMessagePublisher implements MessagePublisher {
	
    private RedisTemplate<String, Object> redisTemplate;
    private ChannelTopic topic;
    private ChannelTopic onlineUserListTopic;
    
    
    public RedisMessagePublisher() {
    }
 
    public RedisMessagePublisher(
		RedisTemplate<String, Object> redisTemplate, ChannelTopic topic, ChannelTopic onlineUserListTopic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
		this.onlineUserListTopic = onlineUserListTopic;
    }
    
	@Override
	public void publish(String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
	
	@Override
	public void publish(Message message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
	
	@Override
	public void publish(List<String> onlineUserList) {
		redisTemplate.convertAndSend(onlineUserListTopic.getTopic(), onlineUserList);
	}
	
}
