package com.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisKeyNotificationServiceApp 
{
	public static void main(String [] args)
	{
		 SpringApplication.run(RedisKeyNotificationServiceApp.class, args);
	}
}
