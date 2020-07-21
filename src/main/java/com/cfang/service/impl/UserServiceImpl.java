package com.cfang.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.entity.UserAddressEntity;
import com.cfang.entity.UserEntity;
import com.cfang.exception.BusyException;
import com.cfang.mapper.UserAddressMapper;
import com.cfang.mapper.UserMapper;
import com.cfang.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserAddressMapper userAddressMapper;

	@Override
	public UserInfoDto loginUser(UserLoginDto dto){
		UserInfoDto user = userMapper.loginUser(dto);
		return user;
	}

	@Override
	public int regUser(UserRegisterDto dto) throws Exception{
		if(!dto.getPassword().equals(dto.getRePassword())) {
			throw new Exception("密码不一致...");
		}
		UserEntity record = new UserEntity();
		record.setUserName(dto.getUserName());
		record.setPassword(dto.getPassword());
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		return userMapper.insert(record);
	}

	@Override
	public UserInfoDto selectUserByUserCode(String userCode) {
		return userMapper.selectUserByUserCode(userCode);
	}

	@Override
	public int updateUserPwd(UserRegisterDto dto) {
		return userMapper.updateUserPwd(dto);
	}
	
	
}
