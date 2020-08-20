package com.cfang.entity;

import java.math.BigDecimal;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Data
@Table(name = "tbl_order_detail")
public class OrderdetailEntity extends BaseEntity{

	private String orderNo;
	private Long productCode; 
	private BigDecimal price;
	private Integer quantity;
	private BigDecimal fee;	//配送费
	private String productName;
	private BigDecimal total0;// 小计
	@Transient
	private String picture;
}
