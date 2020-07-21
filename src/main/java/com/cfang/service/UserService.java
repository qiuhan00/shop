package com.cfang.service;

import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.entity.UserEntity;

public interface UserService {

	UserInfoDto loginUser(UserLoginDto dto); 
	
	UserInfoDto selectUserByUserCode(String userCode); 
	
	int regUser(UserRegisterDto dto) throws Exception;
	
	int updateUserPwd(UserRegisterDto dto);
}
