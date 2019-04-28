package com.redis.listener;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.redis.config.RedisConfig;

@Component
public class KeySpaceNotificationMessageListener implements MessageListener {

	@Autowired
	RedisCacheManager redisCacheManager;

	@Autowired
	RedisConfig redisConfig;

	@PostConstruct
	public void init()
	{
		redisConfig.setKeySpaceNotificationMessageListener(this);
	}

	@Override
	public void onMessage(Message message, byte[] pattern)
	{
		System.out.println("Recieved action = " + new String(message.getBody()) + " " +
				" and key info = " + new String(message.getChannel()));

		/*
		 * just printing the message here. You can do anything you wish with the
		 * received info. For example you can publish it to UI clients using redis
		 * pub sub or DeepStream Client.
		 * */

	}

}
