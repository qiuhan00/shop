package com.cfang.service;

import java.util.List;

import com.cfang.dto.UserInfoDto;
import com.cfang.dto.req.OrderReq;
import com.cfang.entity.OrderEntity;
import com.cfang.entity.PayChannelEntity;

/**
 * @description：
 * @author cfang 2020年8月10日
 */
public interface OrderService {

	List<PayChannelEntity> selectAllPays();
	
	OrderEntity createOrder(UserInfoDto user, OrderReq req);
	
	int updateOrder(OrderEntity entity);
}
