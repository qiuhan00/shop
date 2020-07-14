package com.cfang.dto;

import java.math.BigDecimal;

import com.cfang.entity.CartEntity;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Data
public class CartListDto extends CartEntity{
	
	private String introduce; //商品简介
	private BigDecimal price;// 定价
	private String picture;// 小图片地址
	
}
