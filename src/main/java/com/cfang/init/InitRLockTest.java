package com.cfang.init;

import com.cfang.config.LockConf;
import com.cfang.utils.CommonRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;


/**
 * @description：
 * @author cfang 2020年8月10日
 */
@Component
@Order(3)
@Slf4j
public class InitRLockTest implements ApplicationRunner{

	@Autowired
	CommonRedisUtil commonRedisUtil;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		LockConf conf = new LockConf();
		conf.setKey("tp_test");
		Lock lock = commonRedisUtil.lock(conf);
		log.info("==> 初始化测试RLock");
		commonRedisUtil.unlock(lock);
	}

}
