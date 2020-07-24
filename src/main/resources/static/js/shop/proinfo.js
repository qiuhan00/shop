var ProInfoCls = {
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
		this.$addCart = $("#addCart");
	},
	bindEvent:function(){
		this.$addCart.on("click", this.addCart);
	},
	addCart:function(){
		var productCode = $("#productCode").text();
		var quantity = $("#quantity").val();
		$.ajax({
			type:'post',
			url:'/order/addCart',
			data:{productCode:productCode,quantity:quantity},
			success:function(result){
				 if(result){
					CommonCls.statUserCart();
					layer.msg("已添加购物车！", {time:2000,icon:1},function(){
					});
				}else{
					layer.msg("添加购物车异常，请稍候重试...");
				}
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
	}
}

$(function(){
	ProInfoCls.init();
})