package com.cfang.dto.req;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @description 
 * @author cfang 2020年8月12日
 */
@Data
public class OrderReq {

	private Integer addressId;
	private BigDecimal fee; //配送费
	private List<OrderDetail> orderDetails;
	private BigDecimal amount;
	private Integer quantity;
	private Integer payChannelId;
	private List<Integer> carts;
}
