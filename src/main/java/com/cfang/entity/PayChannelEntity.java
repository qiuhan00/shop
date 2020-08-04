package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年8月4日
 */
@Data
@Table(name = "tbl_pay_channel")
public class PayChannelEntity extends BaseEntity{

	private String payName;
	private String payType; //1-网银支付-借记卡、2-网银支付-信用卡、3-微信/支付宝支付
	private String imageUrl;
	private String status;
	
}
