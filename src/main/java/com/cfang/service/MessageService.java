package com.cfang.service;

import com.cfang.entity.MessageEntity;

/**
 * @description：
 * @author cfang 2020年7月22日
 */
public interface MessageService {

	int insert(MessageEntity entity);
	
	int updateReply(MessageEntity entity); 
}
