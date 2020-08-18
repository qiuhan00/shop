package com.cfang.mapper;

import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.cfang.common.CommonMapper;
import com.cfang.entity.OrderEntity;

/**
 * @description 
 * @author cfang 2020年8月13日
 */
public interface OrderMapper extends CommonMapper<OrderEntity>{

	@Update("update tbl_order set status = #{status},update_time=#{updateTime} where order_no=#{orderNo}")
	int cancelOrder(OrderEntity orderEntity);
}
