package com.cfang.dto.resp;

import java.util.Date;
import java.util.List;

import com.cfang.entity.OrderdetailEntity;

import lombok.Data;

/**
 * @description 
 * @author cfang 2020年8月20日
 */
@Data
public class VipOrderResp {

	private String orderNo;
	private String status;// 订单状态
	private String statusStr;// 订单状态
	private String payStatus;// 订单支付状态：n-未支付、y-已支付
	private String payStatusStr;// 订单支付状态：n-未支付、y-已支付
	private Date createTime;
	private String createTimeStr;
	private String payName;
	private String consignee;
	private List<OrderdetailEntity> details;
}
