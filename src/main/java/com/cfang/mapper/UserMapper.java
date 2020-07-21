package com.cfang.mapper;

import com.cfang.common.CommonMapper;
import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.entity.UserEntity;

/**
 * describe：
 * @author cfang 2020-7-8
 */
public interface UserMapper extends CommonMapper<UserEntity> {

	UserInfoDto loginUser(UserLoginDto dto);
	
	UserInfoDto selectUserByUserCode(String userCode);
	
	int updateUserPwd(UserRegisterDto dto);
}
