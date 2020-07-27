var HelpCls = {
	init:function(){
		this.initNode();
		this.bindEvent();
		this.initData();
		this.initContent();
	},
	initContent:function(){
//		HelpCls.initMap();
		$(".helpNav a[viewName='base']:first").trigger("click");
	},
	initData:function(){
		HelpCls.markerArr = [{title:"上海市xxx有限公司",content:"地址：上海市xx区xx路xx弄一号楼xx大厦xx楼<br/>电话：181-0000-0000<br/>传真：021-00000000&nbsp;<br/>客服电话：021-0000000<br/>销售邮箱：sales@qq.com<br/>客服邮箱：kefu@wqq.com&nbsp;<br/>公司网址：http://www.xxx.com",
					point:"121.415831|31.251281",isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}}];
	},
	initNode:function(){
		this.$changeMenu = $(".jq_to_helpdetail");
	},
	bindEvent:function(){
		this.$changeMenu.on("click", this.changeMenu);
	},
	initMap:function(){ //创建和初始化地图
		HelpCls.createMap();//创建地图
		HelpCls.setMapEvent();//设置地图事件
		HelpCls.addMapControl();//向地图添加控件
		HelpCls.addMarker();//向地图中添加marker
	},
	createMap:function(){	//创建地图
		var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(121.416056,31.251181);//定义一个中心点坐标
        map.centerAndZoom(point,17);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
	},
	setMapEvent:function(){	//地图事件设置
		map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
	},
	addMapControl:function(){
		var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
		map.addControl(ctrl_nav);
	        //向地图中添加缩略图控件
		var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
		map.addControl(ctrl_ove);
	        //向地图中添加比例尺控件
		var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
		map.addControl(ctrl_sca);
	},
	addMarker:function(){
		for(var i=0;i<HelpCls.markerArr.length;i++){
            var json = markerArr[i];
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0,p1);
			var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point,{icon:iconImg});
			var iw = createInfoWindow(i);
			var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
			marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                        borderColor:"#808080",
                        color:"#333",
                        cursor:"pointer"
            });
			(function(){
				var index = i;
				var _iw = createInfoWindow(i);
				var _marker = marker;
				_marker.addEventListener("click",function(){
				    this.openInfoWindow(_iw);
			    });
			    _iw.addEventListener("open",function(){
				    _marker.getLabel().hide();
			    })
			    _iw.addEventListener("close",function(){
				    _marker.getLabel().show();
			    })
				label.addEventListener("click",function(){
				    _marker.openInfoWindow(_iw);
			    })
				if(!!json.isOpen){
					label.hide();
					_marker.openInfoWindow(_iw);
				}
			})()
        }
	},
	createInfoWindow:function(i){
		var json = markerArr[i];
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");
        return iw;
	},
	createIcon:function(json){
		var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        return icon;
	},
	changeMenu:function(){
		HelpCls.$changeMenu.removeClass("vipCur");
		$(this).addClass("vipCur");
		var viewName = $(this).attr("viewName");
		var posCur = $(this).text();
		$(".posCur").text(posCur);
		$.ajax({
	        url: "/help/toView?viewName=" + viewName,
	        success: function (data) {
	            $("#jq_helpcontright").html(data);
	        }
	    })
	}
}

$(function(){
	HelpCls.init();
})