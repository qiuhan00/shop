package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Data
@Table(name = "tbl_order")
public class OrderEntity extends BaseEntity{

	private Integer userCode;
	private String status;// 订单状态
	private String refundStatus;
	private String paystatus;// 订单支付状态：n-未支付、y-已支付
	private String lowStocks;//n:库存不足；y:库存充足。默认n
	private String remark;//订单支付时候显示的文字
	private String amount;// 订单总金额
	private String fee;// 总配送费
	private String ptotal;//商品总金额
	private Integer quantity;//商品总数量
	private String expressCode;//提交订单时候选中的配送方式
	private String expressName;//配送方式名称
	private String expressNo;//物流单号
	private String expressCompanyName;//物流公司名称
	private String confirmSendProductRemark;
	private String otherRequirement;//客户附加要求
	private String closedComment;//此订单的所有订单项对应的商品都进行了评论，则此值为y，表示此订单的评论功能已经关闭，默认为n，在订单状态为已签收后，订单可以进行评价。
	private Integer score;//订单能获得的积分
}
