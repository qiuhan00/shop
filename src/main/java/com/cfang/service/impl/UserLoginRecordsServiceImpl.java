package com.cfang.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.entity.UserLoginRecordsEntity;
import com.cfang.mapper.UserLoginRecordsMapper;
import com.cfang.service.UserLoginRecordsService;

import cn.hutool.core.date.DateUtil;

/**
 * @description 
 * @author cfang 2020年9月2日
 */
@Service
public class UserLoginRecordsServiceImpl implements UserLoginRecordsService{
	
	@Autowired
	UserLoginRecordsMapper userLoginRecordsMapper;

	@Override
	public void saveRecord(UserLoginRecordsEntity entity) {
		UserLoginRecordsEntity dbEntity = userLoginRecordsMapper.selectByUsercode(entity.getUserCode());
		if(null == dbEntity) {
			entity.setLoginBit(1l);
			userLoginRecordsMapper.insertSelective(entity);
		}else {
			Date now = DateUtil.date();
			long day = DateUtil.betweenDay(dbEntity.getLoginTs(), now, false);
			if (day> 0) {
				long result = (dbEntity.getLoginBit() << day) + 1;
				dbEntity.setLoginBit(result);
			}
			dbEntity.setLoginTs(now);
			userLoginRecordsMapper.updateByUsercode(dbEntity);
		}
	}
	
	/**
	 * 按位与，判断是否连续登录5天
	 * 11111 & 11111 = 11111
	 * 11101 & 11111 = 11101
	 */
	@Override
	public boolean isContinuousLogin(String userCode) {
		UserLoginRecordsEntity dbEntity = userLoginRecordsMapper.selectByUsercode(userCode);
		return (dbEntity.getLoginBit() & 0x1f) == 0x1f;
	}

	
}
