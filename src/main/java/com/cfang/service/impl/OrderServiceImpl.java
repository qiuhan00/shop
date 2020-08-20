package com.cfang.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cfang.common.ShopConstants;
import com.cfang.common.ShopConstants.payStatus;
import com.cfang.dto.OrderDelayed;
import com.cfang.dto.UserInfoDto;
import com.cfang.dto.req.OrderReq;
import com.cfang.dto.resp.VipOrderResp;
import com.cfang.entity.CartEntity;
import com.cfang.entity.OrderEntity;
import com.cfang.entity.OrderdetailEntity;
import com.cfang.entity.PayChannelEntity;
import com.cfang.mapper.CartMapper;
import com.cfang.mapper.OrderDetailMapper;
import com.cfang.mapper.OrderMapper;
import com.cfang.mapper.PayChannelMapper;
import com.cfang.mapper.ProductMapper;
import com.cfang.service.OrderService;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * @description：
 * @author cfang 2020年8月10日
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
	
	private final static DelayQueue<OrderDelayed> OR_QUEUE = new DelayQueue<OrderDelayed>();
	
	@Autowired
	PayChannelMapper payChannelMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderDetailMapper orderDetailMapper;
	@Autowired
	CartMapper cartMapper;
	@Autowired
	ProductMapper productMapper;

	@Override
	@Cacheable(value = "pay.channel", key = "'records'")
	public List<PayChannelEntity> selectAllPays() {
		List<PayChannelEntity> list = payChannelMapper.selectAll();
		return list;
	}

	@Override
	@Transactional
	public OrderEntity createOrder(UserInfoDto user, OrderReq req) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setAddressId(req.getAddressId());
		orderEntity.setAmount(req.getAmount());
		orderEntity.setPtotal(req.getAmount());
		orderEntity.setQuantity(req.getQuantity());
		orderEntity.setOrderNo(createOrderNo());
		orderEntity.setFee(BigDecimal.ZERO);
		orderEntity.setPayStatus(ShopConstants.PRODUCT_N);
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
		//放入延迟队列,时间单位秒
		OrderDelayed orderDelayed = new OrderDelayed(orderEntity, req.getCarts(), 60 * 15);
		OR_QUEUE.add(orderDelayed);
		return orderEntity;
	}
	
	@Override
	public int updateOrder(OrderEntity entity) {
		return orderMapper.updateOrder(entity);
	}
	
	@Override
	public List<VipOrderResp> selectUserOrder(String userCode) {
		List<VipOrderResp> list = orderMapper.selectUserOrder(userCode);
		list.forEach(item -> {
			item.getDetails().forEach(it -> {
				it.setPicture(productMapper.selectByProductCode(it.getProductCode()).getPicture());
				it.setTotal0(it.getPrice().multiply(BigDecimal.valueOf(it.getQuantity())));
			});
			item.setCreateTimeStr(DateUtil.format(item.getCreateTime(), DatePattern.NORM_DATETIME_FORMAT));
			if(ShopConstants.orderStatus.C.name().equals(item.getStatus())) {
				item.setPayStatusStr(ShopConstants.orderStatus.C.getStatus());
			}else {
				item.setPayStatusStr(ShopConstants.payStatus.getStatus(item.getPayStatus()));
			}
		});
		return list;
	}

	private String createOrderNo() {
		String time = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
		String random = new Random().nextInt(9999) + "";
		return "OD" + time + random;
	}
	
	private Integer calcScore(BigDecimal amount) {
		return amount.divide(BigDecimal.TEN).intValue();
	}
	
	@Scheduled(cron = "0/5 * * * * ?")
	public void configureTasks() {
//		log.info("定时任务检查超时订单 start...");
		OrderDelayed orderDelayed = OR_QUEUE.poll();
		if(null != orderDelayed) {
			OrderEntity orderEntity = orderDelayed.getOrderEntity();
			log.warn("订单{} 超时未支付", orderEntity.getOrderNo());
			orderDelayed.getCarts().forEach(item -> {
				CartEntity entity = new CartEntity().setStatus(ShopConstants.CAT_STATUS_O);
				entity.setId(item);
				cartMapper.updateStatus(entity);
			});
			orderEntity.setStatus(ShopConstants.orderStatus.C.name());
			orderMapper.updateOrder(orderEntity);
		}
//		log.info("定时任务检查超时订单 end...");
	}
}
