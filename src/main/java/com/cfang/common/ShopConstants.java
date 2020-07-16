package com.cfang.common;

import antlr.actions.python.CodeLexer;

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
	
	enum errorCode{
		INVALID_PARAM(300, "请求参数错误"),INVALID_SIGN(100, "请求签名错误"),NOT_FOUND(404, "404"),SYS_ERR(500, "服务器内部错误"),SYS_BUSY(503, "服务器繁忙，请稍候重试");
		
		private int code;
		private String msg;
		
		private errorCode(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}
}
