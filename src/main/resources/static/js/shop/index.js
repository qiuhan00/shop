var IndexCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
		$(".cls-0").parent().trigger("click").addClass("flListStyle");
		$("#kinMaxShow").kinMaxShow();
	},
	initData:function(){
		
	},
	initNode:function(){
		this.$productLi = $(".click_item");
		this.$catalogMore = $(".jq_catalog_more");
	},
	bindEvent:function(){
		this.$productLi.on("click", this.clickItem);
		this.$catalogMore.on("click", this.catalogMore);
	},
	clickItem:function(){
		var cataLogId = $(this).attr("value");
		var _this = $(this);
		_this.siblings().removeClass("flListStyle");
		_this.addClass("flListStyle");
		$.ajax({
			type:'post',
			url:'/getProducts',
			data:{cataLogId:cataLogId},
			dataType:'json',
			success:function(data){
				var _html = '';
				$.each(data, function(index, val){
					_html += ["<dl", " class='jq_proinfo' proid='", val.id, "'>", "<dt>", "<a>", "<img src='", val.picture, "' width='132' height='129' />",
								"</a></dt><dd>", val.introduce, "</dd>",
								"<dd class='cheng'>", "￥", val.price, "/", val.unit, "</dd></dl>"].join('');
				});
				_this.parent().parent().next().children(".frProList").html(_html);
				$(".frProList").show();
			}
		});
	},
	catalogMore:function(){
		var catalogid = $(this).attr("catalogid");
		window.location.href = "/product/searchPro/0?searchVal=" +　catalogid;
	}
}

$(function(){
	IndexCls.init();
})