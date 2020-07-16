var LOGIN_TIME = 7*24*60*60*1000; //一周
var LoginCls = {
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
		this.$toReg = $("#jq_toReg");
	},
	bindEvent:function(){
		$("#jq_dologin_input").on("click", this.toLogin);
		this.$toReg.on("click", this.toReg);
	},
	toLogin:function(){
		$.ajax({
			type: 'post',
			url: "/user/userLogin",
			data: $("#jq_login_form").serialize(),
			success:function(result){
				alert(result)
				layer.msg("登录成功...",{icon:1,time:2000,shade:0.3}, function(){
					result = result || '/shop';  //如果有回调Url则跳转，没有则默认跳转首页
					window.location.href = result;
				})
			},
			error:function(data) {
				alert(data);
			}
		});	
	},
	toReg:function(){
		window.location.href = "/user/toRedirect/reg";
	}
}

$(function(){
	LoginCls.init();
})