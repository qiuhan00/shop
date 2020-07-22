package com.cfang.mapper;

import java.util.List;

import com.cfang.common.CommonMapper;
import com.cfang.entity.UserAddressEntity;

/**
 * @description：
 * @author cfang 2020年7月21日
 */
public interface UserAddressMapper extends CommonMapper<UserAddressEntity>{

	void insertAddress(UserAddressEntity entity);
	
	UserAddressEntity selectOneByUserCode(String userCode);
	
	List<UserAddressEntity> selectByUserCode(String userCode);
}
