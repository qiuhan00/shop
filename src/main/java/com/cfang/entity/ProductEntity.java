package com.cfang.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "tbl_product")
public class ProductEntity extends BaseEntity{

	private String productName;
	private Long productCode;	//商品编号
	private String introduce; //商品简介
	private BigDecimal price;// 定价
	private BigDecimal nowPrice;// 现价
	private String productHtml;// 商品介绍的HTML
	private String picture;// 小图片地址
	private String maxPicture;// 大图片地址
	private String images;//商品图片列表	
	private String unit;//商品单位。默认“item:件”
	private Integer sellcount;//销售数量
	private Integer stock;//库存
	private String title;//商品页面标题
	private Integer status;//商品状态。1：新增，2：已上架，3：已下架
	private Integer catalogId;// 商品目录ID 
	private String isTimePromotion;// 是否限时促销。n：否，y：是
	private String isRecommend;//是否首页推荐产品。n：否，y：是
	private String showInNav;//是否首页大图滚动。n：否，y：是
	private String isHotSearch;//是否热搜。n：否，y：是
}
