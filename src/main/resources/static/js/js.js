// JavaScript Document
$(function(){
	//rdList
	$(".rdList li:first").addClass("rdListStyle");
	$(".rdProBox:first").show();
	$(".rdList li").click(function(){
		$(this).addClass("rdListStyle").siblings("li").removeClass("rdListStyle");
		var index=$(this).index();
		$(".rdProBox").eq(index).show().siblings(".rdProBox").hide();
		})	
	//.rdPro dl
	$(".rdPro dl").hover(function(){
		$(this).addClass("rddlstyle");
		$(this).stop(true,true).animate({
			opacity:"0.7"
			})
		},function(){$(this).removeClass("rddlstyle");$(this).stop(true,true).animate({
			opacity:"1"
			})})
	//.upd,.add
	$(".upd,.add").click(function(){
		$(".address").stop(true,true).slideDown();
		})
	//.floorRight dl
	$(".floorRight .frProList dl,.contRight .frProList dl").hover(function(){
		$(this).stop(true,true).addClass("frListDlstyle");
		$(this).animate({
		    opacity:"0.8",
			left:"-4px"
			})
		},function(){
	    $(this).removeClass("frListDlstyle");
		$(this).stop(true,true).animate({
		    opacity:"1",
			left:"0px"
			})
			})
	//返回首页代码
 var bodyWidth2=$($(window)).height()-200;	
$(window).scroll(function(){
	if($(this).scrollTop() > bodyWidth2)
	{
		$('.backTop').fadeIn();
	}
	else{
		$('.backTop').fadeOut();
	}
})
   //.Title 下拉产品菜单
   $(".Title").hover(function(){
	   $(this).children(".InPorNav").show();
	   },function(){$(this).children(".InPorNav").hide();})
   //.leftPorNav
   $(".leftPorNav li").hover(function(){
	   $(this).children(".chilInPorNav").show();
	   },function(){
		   $(this).children(".chilInPorNav").hide();
		   })  
	//选择支付方式 
	$(".zhiList li:first").addClass("zhistyle");
	$(".zhifufangshi:first").show();
	$(".zhiList li").click(function(){
		$(this).addClass("zhistyle").siblings("li").removeClass("zhistyle");
		var zhi=$(this).index();
		$(".zhifufangshi").eq(zhi).show().siblings(".zhifufangshi").hide();
		})
	//.rongliang li
	$(".rongliang li").click(function(){
		$(this).addClass("rongStyle").siblings("li").removeClass("rongStyle");
		})
	//.fangLists:first
	$(".fangList:first").show();
	$(".fangyuan li").click(function(){
		$(this).addClass("fangyuanstyle").siblings("li").removeClass("fangyuanstyle");
		var fangyuan=$(this).index();
		$(".fangList").eq(fangyuan).show().siblings(".fangList").hide();
		})	
	//vipNav
//	$(".vipNav dt").click(function(){
//		$(this).addClass("vipNavStyle").siblings("dt").removeClass("vipNavStyle");
//		$(this).next("dd").show().siblings("dd").hide();
//		})
	//登录验证
	$('.submit').click(function(event){
                var name = $('.name').val();
				var pwd = $('.pwd').val();
				
                var namelen = name.length;
				var pwdlen = pwd.length;
                if(namelen < 6){
                        alert("用户名不能小于六位，请重新输入！")
					    return false;
                        }
				
				if(pwdlen < 6){
                        alert("你输入的密码不正确，请重新输入！")
					    return false;
                        }
				
                });
	})
	
