package com.cfang;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cfang.entity.UserLoginRecordsEntity;
import com.cfang.mapper.UserLoginRecordsMapper;

import cn.hutool.core.date.DateUtil;

/**
 * @description 
 * @author cfang 2020年9月2日
 */
public class UserLoginRecordsTest extends BaseTest{

	@Autowired
	UserLoginRecordsMapper userLoginRecordsMapper;
	
	@Test
	public void select() {
		List<UserLoginRecordsEntity> list= userLoginRecordsMapper.selectAll();
		System.out.println(JSON.toJSONString(list));
		list.forEach(item -> {
			System.out.println(Long.toBinaryString(item.getLoginBit()));
			System.out.println((item.getLoginBit() & 0x1f) == 0x1f);
			Long result = (item.getLoginBit() << 2) + 1;
			System.out.println(Long.toBinaryString(result));
			System.out.println((result & 0x1f) == 0x1f);
		});
	}
	
	@Test
	public void save() {
		UserLoginRecordsEntity entity = new UserLoginRecordsEntity()
//				.setLoginBit(1 << 1l)
//				.setLoginBit(Integer.valueOf("11100", 2))
				.setLoginTs(DateUtil.date())
				.setUserCode("123");
		userLoginRecordsMapper.insertSelective(entity);
	}
}
