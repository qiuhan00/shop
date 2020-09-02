package com.cfang.service;

import com.cfang.entity.UserLoginRecordsEntity;

/**
 * @description 
 * @author cfang 2020年9月2日
 */
public interface UserLoginRecordsService {

	void saveRecord(UserLoginRecordsEntity entity);
	
	boolean isContinuousLogin(String userCode);
}
