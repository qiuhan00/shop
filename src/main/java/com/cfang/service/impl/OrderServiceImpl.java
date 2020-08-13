package com.cfang.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cfang.common.ShopConstants;
import com.cfang.dto.UserInfoDto;
import com.cfang.dto.req.OrderReq;
import com.cfang.entity.CartEntity;
import com.cfang.entity.OrderEntity;
import com.cfang.entity.OrderdetailEntity;
import com.cfang.entity.PayChannelEntity;
import com.cfang.mapper.CartMapper;
import com.cfang.mapper.OrderDetailMapper;
import com.cfang.mapper.OrderMapper;
import com.cfang.mapper.PayChannelMapper;
import com.cfang.service.OrderService;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;

/**
 * @description：
 * @author cfang 2020年8月10日
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	PayChannelMapper payChannelMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderDetailMapper orderDetailMapper;
	@Autowired
	CartMapper cartMapper;

	@Override
	@Cacheable(value = "pay.channel", key = "'records'")
	public List<PayChannelEntity> selectAllPays() {
		List<PayChannelEntity> list = payChannelMapper.selectAll();
		return list;
	}

	@Override
	@Transactional
	public void createOrder(UserInfoDto user, OrderReq req) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setAddressId(req.getAddressId());
		orderEntity.setAmount(req.getAmount());
		orderEntity.setPtotal(req.getAmount());
		orderEntity.setQuantity(req.getQuantity());
		orderEntity.setOrderNo(createOrderNo());
		orderEntity.setFee(BigDecimal.ZERO);
		orderEntity.setPayStatus("n");
		orderEntity.setStatus(ShopConstants.orderStatus.P.name());
		orderEntity.setUserCode(user.getUserCode());
		orderEntity.setScore(calcScore(req.getAmount()));
		orderEntity.setPayChannelId(req.getPayChannelId());
		orderMapper.insert(orderEntity);
		req.getOrderDetails().forEach(item -> {
			OrderdetailEntity orderdetailEntity = new OrderdetailEntity();
			orderdetailEntity.setOrderNo(orderEntity.getOrderNo());
			orderdetailEntity.setFee(BigDecimal.ZERO);
			orderdetailEntity.setQuantity(item.getNumber());
			orderdetailEntity.setPrice(item.getPrice());
			orderdetailEntity.setProductCode(item.getProductCode());
			orderdetailEntity.setProductName(item.getProductName());
			orderdetailEntity.setTotal0(item.getPrice().multiply(new BigDecimal(item.getNumber())));
			orderDetailMapper.insert(orderdetailEntity);
		});
		req.getCarts().forEach(item -> {
			CartEntity entity = new CartEntity().setStatus(ShopConstants.CAT_STATUS_P);
			entity.setId(item);
			cartMapper.updateStatus(entity);
		});
	}

	private String createOrderNo() {
		String time = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
		String random = new Random().nextInt(9999) + "";
		return "OD" + time + random;
	}
	
	private Integer calcScore(BigDecimal amount) {
		return amount.divide(BigDecimal.TEN).intValue();
	}
}
