package com.cfang.entity;

import java.math.BigDecimal;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Data
@Table(name = "tbl_order_detail")
public class OrderdetailEntity extends BaseEntity{

	private Integer orderID;
	private Integer productID; 
	private BigDecimal price;
	private Integer number;
	private BigDecimal fee;	//配送费
	private String productName;
	private BigDecimal total0;// 小计
}
