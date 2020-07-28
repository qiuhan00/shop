package com.cfang.configuration;

import java.text.SimpleDateFormat;
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
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

/**
 * describe：
 * @author cfang 2020年7月13日
 */
@Configuration
public class RedisConfigration extends CachingConfigurerSupport{

	@Autowired
	private RedisConnectionFactory factory;
	
	/**
	 * 设置默认缓存管理策略
	 */
	@Bean
	public CacheManager cacheManager() {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofHours(1)) //缓存一个小时
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))//StringRedisSerializer序列化和反序列化key
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))//Jackson2JsonRedisSerializer序列化和反序列化value
				.disableCachingNullValues();//禁用空值
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
        		.cacheDefaults(redisCacheConfiguration).build();
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // 字符串Key序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 对象值序列化,默认使用JdkSerializationRedisSerializer
        redisTemplate.setValueSerializer(serializer());
        redisTemplate.setHashValueSerializer(serializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
	}
	
	private Jackson2JsonRedisSerializer<Object> serializer() {
		//设置序列化器，默认使用JDK的序列化
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        //指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //只序列化非空、非null属性字段
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        /**
         *  指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
         *  以下设置必须，作用是序列化时将对象全类名一起保存下来
         *  设置之后序列化结果如下：[
		 *						    "com.cfang.entity.UserEntity",
		 *						    {
		 *						        "name": "1",
		 *						        "age": "11",
		 *						        "message": "牛逼"
		 *						    }
		 *						]
		 *	如果不设置的话，无法实现反序列化操作，序列化结果为：{
		 *						        "name": "1",
		 *						        "age": "11",
		 *						        "message": "牛逼"
		 *							 }
		 * 2.10版本后，弃用enableDefaultTyping改用 activateDefaultTyping
         */
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        //如果是空对象的时候,不抛异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      	//反序列化的时候如果多了其他属性,不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jacksonSeial.setObjectMapper(mapper);
        return jacksonSeial;
	}
}
