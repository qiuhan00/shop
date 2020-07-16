var RegCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
		
	},
	initData:function(){
		this.$imgVerify.trigger("click");
	},
	initNode:function(){
		this.$checkBox = $("#jq_reg_checkbox");
		this.$doReg = $("#jq_doreg_input");
		this.$tips = $("#jq_tips");
		this.$userName = $("#userName");
		this.$imgVerify = $("#imgVerify");
		this.$verifyCode = $("#jq_verifyCode");
	},
	bindEvent:function(){
		this.$userName.on("blur", this.validUserName);
		this.$doReg.on("click", this.clickReg);
		$("#jq_user_login").on("click", function(){window.location.href = "/user/toRedirect/login";});
		this.$imgVerify.on("click", this.changeImgVerify);
		this.$verifyCode.on("blur", this.checkVerify);
	},
	changeImgVerify:function(){
		$(this).attr("src", "/user/getVerify?timestamp=" + new Date().getTime());
	},
	checkVerify:function(){
		var code = $(this).val();
		var _this = $(this);
		$.ajax({
			type: 'post',
			url: "/user/checkVerify",
			data: {code:code},
			success:function(result){
				 if("fail" == result){
					 _this.focus();
					 layer.msg("验证码错误.",{icon:5,time:2000});
				 }
			},
			error:function(data) {
				alert(data);
			}
		});
	},
 	validUserName:function(){
		var _this = $(this);
		var userName = _this.val();
		$("#name_err_tips").hide();
		$.ajax({
			type: 'post',
			url: "/user/validUserName",
			data: {userName:userName},
			success:function(result){
				 if(result){
					$("#name_err_tips").prev().hide();
					$("#name_err_tips").show();
				}
			},
			error:function(data) {
				alert(data);
			}
		});
	},
	clickReg : function(){
		if(!RegCls.$checkBox.is(":checked")){
			RegCls.$tips.show();
			return;
		}else{
			RegCls.$tips.hide();
		}
		$.ajax({
			type: 'post',
			url: "/user/register",
			data: $("#jq_reg_form").serialize(),
			success:function(result){
				if("success" == result){
					layer.msg("注册成功.",{icon:1,shade:0.3,time:2000},function(){
						window.location.href = "/shop";
					})
				}else{
					layer.msg("注册失败，请稍候重试.",{iocn:2,shade:0.3,time:2000})
				}
			},
			error:function(data) {
				alert(data);
			}
		});	
	}
}

$(function(){
	RegCls.init();
})