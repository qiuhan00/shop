package com.cfang.configuration;

import com.cfang.config.RedisKeyExpirationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author cfang 2020/10/30 10:02
 * @description
 */
@Slf4j
@Component
public class ShopRedisKeyExpirationListener extends RedisKeyExpirationListener {

	public ShopRedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String expiredKey = message.toString();
		log.info("过期key={}", expiredKey);
	}
}
