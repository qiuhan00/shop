package com.cfang;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cfang.entity.UserEntity;
import com.cfang.service.RedisService;

/**
 * @description：
 * @author cfang 2020年7月28日
 */
public class CacheTest extends BaseTest{

	@Autowired
	RedisService redisService;

	@Test
	public void hasKey() {
		System.out.println(redisService.hasKey("user"));
		redisService.incr("num", 5);
	}
	
	@Test
	public void set() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1);
		userEntity.setUserName("zhangsan");
		userEntity.setCardNo("1234");
		userEntity.setCreateTime(new Date());
		redisService.set("user", userEntity);
	}
	
	@Test
	public void get() {
		UserEntity retEntity = (UserEntity) redisService.get("user");
		System.out.println(retEntity.getCreateTime());
		System.out.println(JSON.toJSONString(retEntity));
	}
	
}
