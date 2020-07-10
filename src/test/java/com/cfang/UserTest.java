package com.cfang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.cfang.entity.UserEntity;
import com.cfang.mapper.UserMapper;

/**
 * describe：
 * @author cfang 2020-7-8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

	@Autowired
	UserMapper accountMapper;
	
	@Test
	public void selectAll() {
		try {
			System.out.println(JSON.toJSONString(accountMapper.selectAll()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insert() {
		
	}
}
