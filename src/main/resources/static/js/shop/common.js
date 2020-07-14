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
		this.$searchItem.click("click", this.searchItem);
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
	}
}

$(function(){
	CommonCls.init();
})