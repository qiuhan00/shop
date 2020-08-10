package com.cfang.service;

import java.util.List;

import com.cfang.entity.PayChannelEntity;

/**
 * @description：
 * @author cfang 2020年8月10日
 */
public interface OrderService {

	List<PayChannelEntity> selectAllPays();
}
