package com.cfang.dto;

import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.cfang.entity.OrderEntity;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;

/**
 * @description 
 * @author cfang 2020年8月18日
 */
public class OrderDelayed implements Delayed{
	
	@Getter
	private OrderEntity orderEntity;//订单号
	@Getter
	private List<Integer> carts;//购物车id
	private long expireTime;//有效期
	
	public OrderDelayed(OrderEntity orderEntity, List<Integer> carts, long expireTime) {
        this.orderEntity = orderEntity;
        this.carts = carts;
        this.expireTime = DateUtil.current(false) + expireTime * 1000;
    }

	@Override
	public int compareTo(Delayed arg0) {
		return (int)(this.getDelay(TimeUnit.MILLISECONDS) - arg0.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

}
