package com.cfang.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @description：
 * @author cfang 2020年8月10日
 */
@Component
@Order(2)
@Slf4j
public class InitDictionary implements ApplicationRunner{

	@Override
	@Async
	public void run(ApplicationArguments args) throws Exception {
		log.info("==> 初始化字典缓存数据完成");
	}

}
