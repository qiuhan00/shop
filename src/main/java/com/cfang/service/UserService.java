package com.cfang.service;

import java.util.List;

import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.dto.VipUserDto;
import com.cfang.entity.UserAddressEntity;

public interface UserService {

	UserInfoDto loginUser(UserLoginDto dto); 
	
	UserInfoDto selectUserByUserCode(String userCode); 
	
	int regUser(UserRegisterDto dto) throws Exception;
	
	int updateUserPwd(UserRegisterDto dto);
	
	void updateViper(VipUserDto dto);
	
	List<UserAddressEntity> selectByUserCode(String userCode);
	
	int saveOrUpdateAddress(UserAddressEntity entity);
	
	int delAddress(UserAddressEntity entity);
}
