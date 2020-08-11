(function($){
	var _ajax = $.ajax;
	$.ajax = function(opt){
		var fn = {     
           error:function(XMLHttpRequest, textStatus, errorThrown){},     
           success:function(data, textStatus){}     
        }
        if(opt.error){     
            fn.error=opt.error;     
        }
        if(opt.success){     
            fn.success=opt.success;     
        }
        var _opt = $.extend(opt, {
        	 error:function(XMLHttpRequest, textStatus, errorThrown){
//        		 if(error.response.status === 509)
        		 console.log(textStatus)
        		 console.log(errorThrown)
        		 if (layer) {  
                     layer.msg("出错了,请联系管理员!", 2, 3, null, true);  
                 } else {  
                     alert("出错了,请联系管理员!");  
                 }  
                 fn.error(XMLHttpRequest, textStatus, errorThrown);
        	 },
        	 success:function(data, textStatus){ 
                 //成功回调方法增强处理     
                 var error = data.error;  
                 if (error != undefined && error == true) {
                     if (layer) {  
                         layer.msg(data.reason, 2, 3, null, true);  
                     } else {  
                         alert(data.reason);  
                     }  
                     return;  
                 }  
                 fn.success(data, textStatus);     
             }
        });
        _ajax(_opt);
	};
})(jQuery)