package com.cfang.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cfang.service.MapAreaService;

/**
 * @description：
 * @author cfang 2020年8月10日
 */
@Component
@Order(1)
@Slf4j
public class InitArea implements CommandLineRunner{
	
	 @Autowired
	 MapAreaService mapAreaService;

	@Override
	@Async
	public void run(String... args) throws Exception {
		log.info("init area start...");
		mapAreaService.initArea();
		log.info("init area end...");
	}

}
