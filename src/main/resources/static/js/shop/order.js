var OrderCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
		
	},
	initData:function(){
		
	},
	initNode:function(){
		
	},
	bindEvent:function(){
		$("#jq_btn_confirm").on("click", this.createOrder);
	},
	createOrder:function(){
		var _order = new Object();
		var _details = new Array();
		var _cartids = new Array();
		$(".orderdetail").each(function(){
			var _detail = new Object();
			_detail.productCode= $(this).attr("productcode");
			_detail.price= $(this).children(".price").attr("price");
			_detail.number=  $(this).children(".quantity").attr("quantity");
			_detail.productName= $(this).attr("productname");
			_details.push(_detail);
			_cartids.push($(this).attr("cartid"));
		});
		_order.orderDetails = _details;
		_order.addressId = $("input[name='addrRadio']:checked").val();
		_order.amount = $("#total").attr("total");
		_order.quantity = $("#number").val();
		_order.payChannelId = $(".paychannel:checked").val();
		_order.carts = _cartids;
		console.log(_order)
		$.ajax({
			url:"/order/createOrder",
			type: 'post',
			data: JSON.stringify(_order),
			contentType:"application/json",
			success: function (result) {
				if(200 == result.code){
					layer.msg("下单成功..",{icon:1,time:2000,shade:0.3},function(){
						window.location.href="/order/success?payName="+ result.data.payName + "&orderNo=" + result.data.orderNo;
					});
				}else{
					layer.msg("系统异常，请稍候重试..",{icon:2,time:2000,shade:0.3});
				}
			}
		});
	}
}

$(function(){
	OrderCls.init();
})