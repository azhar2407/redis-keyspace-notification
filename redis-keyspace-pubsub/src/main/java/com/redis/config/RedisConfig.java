package com.redis.config;

import java.time.Duration;
import java.util.concurrent.Executors;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.redis.listener.KeySpaceNotificationMessageListener;

@Configuration
@EnableCaching
public class RedisConfig
{
	KeySpaceNotificationMessageListener keySpaceNotificationMessageListener;

	@Bean
	JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration)
	{
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(keySpaceNotificationMessageListener);
	}

	@Bean(name = "cacheManager")
	@Primary
	public RedisCacheManager redisCacheManager(JedisConnectionFactory jedisConnectionFactory)
	{
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()
				.entryTtl(Duration.ofSeconds(10))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

		redisCacheConfiguration.usePrefix();

		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory)
				.cacheDefaults(redisCacheConfiguration).build();

	}

	@Bean
	RedisMessageListenerContainer redisContainer(JedisConnectionFactory jedisConnectionFactory) {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnectionFactory);
		container.addMessageListener(messageListener(), new PatternTopic("__keyspace@*:*"));
		container.setTaskExecutor(Executors.newFixedThreadPool(4));
		return container;
	}

	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration()
	{
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost");
		return redisStandaloneConfiguration;
	}

	public void setKeySpaceNotificationMessageListener(KeySpaceNotificationMessageListener keySpaceNotificationMessageListener)
	{
		this.keySpaceNotificationMessageListener = keySpaceNotificationMessageListener;
	}
}
