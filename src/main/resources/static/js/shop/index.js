var IndexCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
		$(".cls-0").parent().trigger("click").addClass("flListStyle");
	},
	initData:function(){
		
	},
	initNode:function(){
		this.$productLi = $(".click_item");
		this.$toReg = $("#jq_toReg");
	},
	bindEvent:function(){
		this.$productLi.on("click", this.clickItem);
		this.$toReg.on("click", this.toReg);
		$("#jq_toLogin").on("click", function(){window.location.href = "/user/toRedirect/login";});
		$("#jq_toOut").on("click", this.logout);
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
					_html += ["<dl><dt>", "<a href='proinfo.html'>", "<img src='", val.picture, "' width='132' height='129' />",
								"</a></dt><dd>", val.introduce, "</dd>",
								"<dd class='cheng'>", "￥", val.price, "/", val.unit, "</dd></dl>"].join('');
				});
				_this.parent().parent().next().children(".frProList").html(_html);
				$(".frProList").show();
			}
		});
	},
	toReg:function(){
		window.location.href = "/user/toRedirect/reg";
	},
	logout:function(){
		$.ajax({
			url:"/user/logout",
			success:function(data){
				window.location.href = "/shop";
			}
		});
	}
}

$(function(){
	IndexCls.init();
})