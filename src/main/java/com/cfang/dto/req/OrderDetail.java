package com.cfang.dto.req;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @description 
 * @author cfang 2020年8月12日
 */
@Data
public class OrderDetail {

	private Integer cartId;
	private String productName;
	private BigDecimal price;
	private Integer number;
	private Long productCode;
	
}
