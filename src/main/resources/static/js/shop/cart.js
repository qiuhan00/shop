var CartCls = {
	init:function(){
		this.initNode();
		this.initData();
		this.initContent();
		this.bindEvent();
	},
	initContent:function(){
		
	},
	initData:function(){
		this.initCartItem();
	},
	initNode:function(){
		this.$clearCart = $("#jq_clearCart");
	},
	bindEvent:function(){
		$(document).on("click", ".jq_del_item", this.delItem);
		this.$clearCart.on("click", this.clearCart);
	},
	clearCart:function(){
		$.ajax({
			type:'post',
			url:'/order/clearCart',
			data:{},
			success:function(result){
				if(result){
					layer.msg("删除成功！", {time:2000,shade:0.3},function(){
						CartCls.initCartItem();
					});
				}
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
	},
	delItem:function(){
		var id = $(this).attr("id");
		$.ajax({
			type:'post',
			url:'/order/delCartItem',
			data:{id:id},
			success:function(result){
				if(result){
					layer.msg("删除成功！", {time:2000,shade:0.3},function(){
						CartCls.initCartItem();
					});
				}
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
	},
	initCartItem:function(){
		$.ajax({
			type:'post',
			url:'/order/selectUserCart',
			data:{},
			success:function(result){
				$(".jq_carts_item").remove();
				$(".cartItem").after(result);
				var total = 0;
				$(".item_total").each(function(index, data){
					total += Number($(this).text());
				})
				$("#cartTotal").text(total.toFixed(2));
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
	}
}

$(function(){
	CartCls.init();
})