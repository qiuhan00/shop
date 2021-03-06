var VipCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
		$(".vipNav dd:first").show();
		$(".vipNav a[viewName='vipContRight']").trigger("click");
	},
	initData:function(){
		
	},
	initNode:function(){
		this.$vipUp = $(".vipUp");
		this.$cancle = $(".jq_vip_modcancle");
		this.$area = $(".jq_area");
		this.$save = $("#jq_btn_save");	
		this.$changeMenu = $(".jq_vip_cur");
	},
	bindEvent:function(){
		$(document).on("click", ".vipUp", this.showModifyInfo);
		$(document).on("click", ".jq_vip_modcancle", this.cancleModify);
		$(document).on("change", ".jq_area", this.selArea);
		$(document).on("click", "#jq_btn_save", this.saveInfo);
		$(document).on("click", ".jq_mdify", this.showModifyAddress);
		$(document).on("click", ".jq_vipaddress_save", this.saveAddress);
		$(document).on("click", ".jq_del", this.delAddress);
		$(document).on("click", "#jq_vipsub", this.updatePwd);
		$(document).on("click", "#jq_savemessage", this.saveMessage);
		this.$changeMenu.on("click", this.changeMenu);
	},
	saveMessage:function(){
		$.post("/vip/liveMessage", $("#jq_message").serialize(), function(data){
			if(200 == data.code){
				layer.msg("操作成功..",{icon:1,time:2000,shade:0.3},function(){
					$(".jq_clear").val("");
				});
			}else{
				layer.msg("系统异常，请稍候重试..",{icon:2,time:2000,shade:0.3});
			}
		})
	},
	updatePwd:function(){
		var pwd1=$("#password").val();
	    var pwd2=$("#rePassword").val();
	    var oldPassword = $("#oldPassword").val();
		if( pwd1 !== pwd2 || pwd1 < 1 ){
			layer.msg("两次输入的密码不一样,请重新输入",{icon:2,time:2000,shade:0.3},function(){
				$(".vipPwd1").focus();
			});
		}else{
			$.post("/vip/updateUserPwd",{password:pwd1,oldPassword:oldPassword},function(data){
				if(200 == data.code){
					if(data.msg){
						layer.msg(data.data, {icon:2,time:2000,shade:0.3},function(){
							$("#oldPassword").focus();
						});
					}else{
						layer.msg("操作成功..",{icon:1,time:2000,shade:0.3},function(){
							$("#password,#rePassword").val("");
						});
					}
				}else{
					layer.msg("系统异常，请稍候重试..",{icon:2,time:2000,shade:0.3});
				}
			})
		}
	},
	delAddress:function(){
		var _this = $(this);
		layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index){
			var obj = new Object();
			obj.type = 1;
			obj.consigneeCode = _this.parent().attr("consigneecode");
			$.post("/vip/delAddress", obj, function(data){
				if(200 == data.code){
					layer.msg("操作成功..",{icon:1,time:2000,shade:0.3},function(){
						$(".jq_vip_cur[viewName='vipAddressRight']").trigger("click");
					});
				}else{
					layer.msg("系统异常，请稍候重试..",{icon:2,time:2000,shade:0.3});
				}
			});
			layer.close(index);
		});
	},
	saveAddress:function(){
		var obj = new Object();
		obj.consignee = $("#jq_rec_consignee").val();
		obj.phone = $("#jq_rec_phone").val();
		obj.postCode = $("#jq_rec_postcode").val();
		obj.provinceName = $("#jq_rec_province").find("option:selected").text();
		obj.provinceCode = $("#jq_rec_province").val();
		obj.cityName = $("#jq_rec_city").find("option:selected").text();
		obj.cityCode = $("#jq_rec_city").val();
		obj.countyName = $("#jq_rec_county").find("option:selected").text();
		obj.countyCode = $("#jq_rec_county").val();
		obj.townName = $("#jq_rec_town").find("option:selected").text();
		obj.townCode = $("#jq_rec_town").val();
		obj.addressDetail = $("#jq_rec_adderssedtail").val();
		obj.consigneeCode = $("#jq_rec_consigneecode").val();
		obj.type = 1;
		$.post("/vip/saveOrUpdateAddress", obj, function(data){
			if(200 == data.code){
				layer.msg("操作成功..",{icon:1,time:2000,shade:0.3},function(){
//					window.location.reload();
					$(".jq_vip_cur[viewName='vipAddressRight']").trigger("click");
				});
			}else{
				layer.msg("系统异常，请稍候重试..",{icon:2,time:2000,shade:0.3})
			}
		});
	},
	showModifyAddress:function(){
		var isModify = $(this).hasClass("upd");
		var $vals = $(this).parent();
		var provincecode = $vals.attr("provincecode");
		if(isModify){
			$("#jq_rec_adderssedtail").val($vals.siblings(".jq_addressdetail").text());
			$("#jq_rec_postcode").val($vals.siblings(".jq_postcode").text());
			$("#jq_rec_consignee").val($vals.siblings(".jq_consignee").text());
			$("#jq_rec_phone").val($vals.siblings(".jq_hone").text());
			$("#jq_rec_province").val($vals.attr("provincecode"));
			$("#jq_rec_city").attr("defaultCode", $vals.attr("citycode"));
			$("#jq_rec_county").attr("defaultCode", $vals.attr("countycode"));
			$("#jq_rec_town").attr("defaultCode", $vals.attr("towncode"));
			$("#jq_rec_consigneecode").val($vals.attr("consigneecode"));
		}else{
			$(".jq_clear").empty().val("");
		}
		$.get("/vip/getArea?type=province&code=" + provincecode, null, function(data){
			if(200 == data.code){
				var _html = "<option value=''>--请选择--</option>";
				$.each(data.data, function(index, val){
					var isSelected = "";
					if(isModify){
						isSelected = (val.adCode == provincecode ? "selected='selected'":"");
					}
					_html += ["<option value=", val.adCode, " ", isSelected, ">", val.name, "</option>"].join("");
				});
				$("select[type=province]").empty().append(_html);
				if(isModify){
					$("#jq_rec_province").trigger("change",[0]);
				}
			}
		})
		$(".jq_rec_address").stop(true,true).slideDown();
	},
	changeMenu:function(){
		VipCls.$changeMenu.removeClass("vipCur");
		$(this).addClass("vipCur");
		var viewName = $(this).attr("viewName");
		var posCur = $(this).text();
		$(".posCur").text(posCur);
		$.ajax({
	        url: "/vip/toView?viewName=" + viewName,
	        success: function (data) {
	            $("#jq_vipcontright").html(data);
	            if("vipContRight" == viewName){
	            	$("#jq_province").trigger("change",[0]);
	            }
	        }
	    })
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
//		console.log(type, "-", selCode)
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
					if(0 == flag){
						$("#jq_city,#jq_rec_city").trigger("change", [1]);
					}
					if(1 == flag){
						$("#jq_county,#jq_rec_county").trigger("change", [2]);
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