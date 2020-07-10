package com.cfang.service;

import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.entity.UserEntity;

public interface UserService {

	UserEntity loginUser(UserLoginDto dto); 
	
	int regUser(UserRegisterDto dto) throws Exception;
}
