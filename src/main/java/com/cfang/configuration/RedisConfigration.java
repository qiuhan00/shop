package com.cfang.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * describe：
 * @author cfang 2020年7月13日
 */
@Configuration
public class RedisConfigration extends CachingConfigurerSupport{

	@Autowired
	private RedisConnectionFactory factory;
	
	@Bean
	public CacheManager cacheManager() {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)); //缓存一个小时
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
        		.cacheDefaults(redisCacheConfiguration).build();
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
        // 字符串Key序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 对象值序列化,默认使用JdkSerializationRedisSerializer
        redisTemplate.setValueSerializer(jacksonSeial);
        redisTemplate.setHashValueSerializer(jacksonSeial);
        return redisTemplate;
	}
}
