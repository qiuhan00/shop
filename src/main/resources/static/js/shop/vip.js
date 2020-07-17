var VipCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
		$(".vipNav dd:first").show();
	},
	initData:function(){
		
	},
	initNode:function(){
		this.$vipUp = $(".vipUp");
		this.$cancle = $(".jq_vip_modcancle");
	},
	bindEvent:function(){
		this.$vipUp.on("click", this.showModifyInfo);
		this.$cancle.on("click", this.cancleModify);
	},
	showModifyInfo:function(){
		$(".address").stop(true,true).slideDown();
	},
	cancleModify:function(){
		$(".address").stop(true,true).slideUp();
	}
}

$(function(){
	VipCls.init();
})