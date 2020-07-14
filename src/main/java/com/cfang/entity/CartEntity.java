package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Data
@Table(name = "tbl_cart")
public class CartEntity extends BaseEntity{

	private Long productCode;
	private String productAttrs;	//选择的商品属性ID，多个用，隔开
	private Integer quantity;	//数量
	private Integer userId;
}
