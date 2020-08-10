package com.cfang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cfang.entity.PayChannelEntity;
import com.cfang.mapper.PayChannelMapper;
import com.cfang.service.OrderService;

/**
 * @description：
 * @author cfang 2020年8月10日
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	PayChannelMapper payChannelMapper;

	@Override
	@Cacheable(value = "pay.channel", key = "'records'")
	public List<PayChannelEntity> selectAllPays() {
		List<PayChannelEntity> list = payChannelMapper.selectAll();
		return list;
	}

}
