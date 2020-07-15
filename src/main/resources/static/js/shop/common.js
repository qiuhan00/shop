var CommonCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
		
	},
	initData:function(){
		this.statUserCart();
		this.initHotWord();
		this.initCatalogTree();
	},
	initNode:function(){
		this.$toIndex = $(".jq_to_index");
		this.$toCart = $("#toCart");
		this.$toSearch = $("#jq_tosearch");
		this.$searchItem = $(".jq_search_item");
		this.$hotSearch = $("#jq_hot_search");
	},
	bindEvent:function(){
		this.$toCart.on("click", this.toCart);
		this.$toIndex.on("click", this.toIndex);
		this.$toSearch.on("click", this.toSearch);
		$(document).on("click", ".jq_search_item", this.searchItem);
	},
	toCart:function(){
		window.location.href = "/order/toCart";
	},
	toIndex:function(){
		window.location.href = "/shop";
	},
	searchItem:function(){
		var searchVal = $(this).text();
		CommonCls.$hotSearch.val(searchVal);
		CommonCls.toSearch();
	},
	toSearch:function(){
		var searchVal = CommonCls.$hotSearch.val();
		window.location.href = "/product/searchPro/" + searchVal;
	},
	statUserCart:function(){
		$.ajax({
			url:'/order/statUserCart',
			data:{},
			success:function(result){
				if(result){
					$("#jq_cartNum").text(result.number);
					$("#jq_cartTotal").text(result.total);
				}
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
	},
	initHotWord:function(){
		$.ajax({
			url:'/searchHotProduct',
			data:{},
			success:function(result){
				$(".hotWord").empty();
				$(".hotWord").html(result);
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
	},
	initCatalogTree:function(){
		$.ajax({
			url:'/initCatalogTree',
			data:{},
			success:function(result){
				$(".InPorNav").empty();
				$(".InPorNav").html(result);
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
		$.ajax({
			url:'/initCatalogTree2',
			data:{},
			success:function(result){
				$(".InPorNav2").empty();
				$(".InPorNav2").html(result);
			},
			error:function(data) {
				layer.msg(data, {icon:2});
			}
		});
	}
}

$(function(){
	CommonCls.init();
})