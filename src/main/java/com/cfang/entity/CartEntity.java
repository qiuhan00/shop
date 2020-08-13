package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Data
@Accessors(chain = true)
@Table(name = "tbl_cart")
public class CartEntity extends BaseEntity{

	private Long productCode;
	private String productAttrs;	//选择的商品属性ID，多个用，隔开
	private Integer quantity;	//数量
	private Integer userId;
	private String status; //I-初始、P-已下单未付款、O-已付款
}
