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
		$("#jq_province").trigger("change",[0]);
	},
	initNode:function(){
		this.$vipUp = $(".vipUp");
		this.$cancle = $(".jq_vip_modcancle");
		this.$area = $(".jq_area");
		this.$save = $("#jq_btn_save");
	},
	bindEvent:function(){
		this.$vipUp.on("click", this.showModifyInfo);
		this.$cancle.on("click", this.cancleModify);
		this.$area.on("change", this.selArea);
		this.$save.on("click", this.saveInfo);
	},
	saveInfo:function(){
		var obj = new Object();
		obj.postCode = $("#postCode").val();
		obj.cardNo = $("#cardNo").val();
		obj.phone = $("#phone").val();
		obj.provinceName = $("#jq_province").find("option:selected").text();
		obj.provinceCode = $("#jq_province").val();
		obj.cityName = $("#jq_city").find("option:selected").text();
		obj.cityCode = $("#jq_city").val();
		obj.countyName = $("#jq_county").find("option:selected").text();
		obj.countyCode = $("#jq_county").val();
		obj.townName = $("#jq_town").find("option:selected").text();
		obj.townCode = $("#jq_town").val();
		obj.addressDetail = $("#addressDetail").val();
		$.ajax({
			type: 'post',
			url: "/vip/updateUser",
			data: obj,
			success:function(data){
				if(200 == data.code){
					layer.msg("修改成功..",{icon:1,time:2000,shade:0.3},function(){
						window.location.reload();
					});
				}else{
					layer.msg("系统异常，请稍候重试..",{icon:2,time:2000,shade:0.3})
				}
			},
			error:function(data) {
				alert(data);
			}
		});
	},
	showModifyInfo:function(){
		$(".address").stop(true,true).slideDown();
	},
	cancleModify:function(){
		$(".address").stop(true,true).slideUp();
	},
	selArea:function(event,flag){
		var _this = $(this);
		var type = _this.attr("next-type");
		var defaultcode = $("select[type=" + type + "]").attr("defaultcode");
		var selCode = _this.val();
		console.log(type, "-", selCode)
		$.ajax({
			url: "/vip/getArea",
			data:{type:type, code:selCode},
			success:function(data){
				if(200 == data.code){
					var _html = "<option value=''>--请选择--</option>";
					$.each(data.data, function(index, val){
						var isSelected = (val.adCode == defaultcode ? "selected='selected'":"");
						_html += ["<option value=", val.adCode, " ", isSelected, ">", val.name, "</option>"].join("");
					});
					$("select[type=" + type + "]").empty().append(_html);
					console.log(flag)
					if(0 == flag){
						$("#jq_city").trigger("change", [1]);
					}
					if(1 == flag){
						$("#jq_county").trigger("change", [2]);
					}
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