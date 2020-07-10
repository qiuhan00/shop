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
		
	},
	bindEvent:function(){
		$("#jq_dologin_input").on("click", this.toLogin);
	},
	toLogin:function(){
		var date = new Date();
		date.setTime(date.getTime() + LOGIN_TIME);
		$.ajax({
			type: 'post',
			url: "/user/userLogin",
			data: $("#jq_login_form").serialize(),
			success:function(result){
				var sid = result.sessionId;
				alert(sid);
				window.location.href = "/shop";
			},
			error:function(data) {
				alert(data);
			}
		});	
	}
}

$(function(){
	LoginCls.init();
})