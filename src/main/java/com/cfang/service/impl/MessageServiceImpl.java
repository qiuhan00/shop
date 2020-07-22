package com.cfang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.entity.MessageEntity;
import com.cfang.mapper.MessageMapper;
import com.cfang.service.MessageService;

/**
 * @description：
 * @author cfang 2020年7月22日
 */
@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	private MessageMapper messageMapper;

	@Override
	public int insert(MessageEntity entity) {
		return messageMapper.insert(entity);
	}

	@Override
	public int updateReply(MessageEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}

}
