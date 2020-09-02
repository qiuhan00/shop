package com.cfang.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cfang.common.CommonMapper;
import com.cfang.entity.UserLoginRecordsEntity;

/**
 * @description 
 * @author cfang 2020年9月2日
 */
public interface UserLoginRecordsMapper extends CommonMapper<UserLoginRecordsEntity>{

	@Select("select * from tbl_user_login where user_code=#{code}")
	UserLoginRecordsEntity selectByUsercode(String userCode);
	
	@Update("update tbl_user_login set login_ts=#{loginTs},login_bit=#{loginBit},update_time=#{updateTime} where user_code=#{userCode}")
	int updateByUsercode(UserLoginRecordsEntity entity);
}
