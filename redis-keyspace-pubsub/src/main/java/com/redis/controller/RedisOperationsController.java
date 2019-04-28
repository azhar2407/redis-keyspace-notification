package com.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisOperationsController {
	
	@Autowired
	@Qualifier("cacheManager")
	RedisCacheManager redisCacheManager;
	
	@PostMapping("/add")
	public void add(@RequestParam String a, @RequestParam String b)
	{
		redisCacheManager.getCache("myCache").put(a, b);
	}
	
	@PostMapping("/evict")
	public void evict(@RequestParam String a)
	{
		redisCacheManager.getCache("myCache").evict(a);
	}
}
