package com.cfang.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.dto.VipUserDto;
import com.cfang.entity.UserAddressEntity;
import com.cfang.entity.UserEntity;
import com.cfang.mapper.UserAddressMapper;
import com.cfang.mapper.UserMapper;
import com.cfang.service.UserService;
import com.cfang.utils.IdWorker;

import cn.hutool.system.UserInfo;

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
		UserEntity user = userMapper.selectUserEntityByUserCode(dto.getUserCode());
		if(!dto.getOldPassword().equals(user.getPassword())) {
			return 2;
		}
		return userMapper.updateUserPwd(dto);
	}
	
	@Override
	public void updateViper(VipUserDto dto) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(dto.getUserId());
		userEntity.setCardNo(dto.getCardNo());
		userEntity.setPhone(dto.getPhone());
//		userEntity.setPostCode(dto.getPostCode());
		userMapper.updateByPrimaryKeySelective(userEntity);
		UserAddressEntity userAddressEntity = new UserAddressEntity();
		BeanUtils.copyProperties(dto, userAddressEntity);
		userAddressMapper.insertAddress(userAddressEntity);
	}

	@Override
	public List<UserAddressEntity> selectByUserCode(String userCode) {
		List<UserAddressEntity> list = userAddressMapper.selectByUserCode(userCode);
		return list;
	}

	@Override
	public int saveOrUpdateAddress(UserAddressEntity entity) {
		int result = 0;
		if(StringUtils.isNotBlank(entity.getConsigneeCode())) {
			result = userAddressMapper.updateAddress(entity);
		}else {
			entity.setConsigneeCode(IdWorker.get().nextId()+"");
			result = userAddressMapper.insert(entity);
		}
		return result;
	}

	@Override
	public int delAddress(UserAddressEntity entity) {
		return userAddressMapper.delAddress(entity);
	}
	
}
