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
		
	},
	initNode:function(){
		this.$checkBox = $("#jq_reg_checkbox");
		this.$doReg = $("#jq_doreg_input");
		this.$tips = $("#jq_tips");
		this.$userName = $("#userName");
	},
	bindEvent:function(){
		this.$userName.on("blur", this.validUserName);
		this.$doReg.on("click", this.clickReg);
		$("#jq_user_login").on("click", function(){window.location.href = "/user/toRedirect/login";})
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
				alert(result);
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