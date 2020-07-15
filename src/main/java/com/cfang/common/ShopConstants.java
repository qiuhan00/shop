package com.cfang.common;

/**
 * describe：
 * @author cfang 2020-7-8
 */
public interface ShopConstants {

	//商品状态
	int PRODUCT_STATUS_ADD = 1;//新增商品
	int PRODUCT_STATUS_Y = 2;//已上架
	int PRODUCT_STATUS_N = 3;//已下架
	
	String PRODUCT_N = "n";
	String PRODUCT_Y = "y";
	
	String CATALOG_TYPE_P = "p";//商品
	String CATALOG_TYPE_A = "a";//文章
	
	enum orderStatus{
		P("初始化"),I("已下单"),S("已发货"),O("已签收"),C("已取消");
		
		private String status;
		private orderStatus(String status) {
			this.status = status;
		}
		public String getStatus() {
			return status;
		}
	}
}