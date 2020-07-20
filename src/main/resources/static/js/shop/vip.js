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
		this.$area = $(".jq_area");
	},
	bindEvent:function(){
		this.$vipUp.on("click", this.showModifyInfo);
		this.$cancle.on("click", this.cancleModify);
		this.$area.on("change", this.selArea);
	},
	showModifyInfo:function(){
		$(".address").stop(true,true).slideDown();
	},
	cancleModify:function(){
		$(".address").stop(true,true).slideUp();
	},
	selArea:function(){
		var type = $(this).attr("next-type");
		var selCode = $(this).val();
		var _this = $(this);
		$.ajax({
			url: "/vip/getArea",
			data:{type:type, code:selCode},
			success:function(data){
				if(200 == data.code){
					var _html = "<option value=''>--请选择--</option>";
					$.each(data.data, function(index, val){
						_html += ["<option value=", val.adCode,">", val.name, "</option>"].join("");
					});
					$("select[type=" + type + "]").empty().append(_html);
				}
			},
			error:function(data) {
				alert(data);
			}
		});
	}
}

$(function(){
	VipCls.init();
})