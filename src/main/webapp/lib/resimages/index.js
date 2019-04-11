(function($) {
	$.fn.loopedSlider = function(options){
		var defaults={
			autoStart:0,
			sliders:".slides",
			sliderSpeed:500,
			beBig:1.4,//倍率
			opc:0.8,
			autoHeight:false,
		}
		/*初始化**/
		var o = $.extend(defaults, options);
		var obj = $(o.sliders);
		var lis = obj.children("div");
		/*console.log(lis)*/
		var len = lis.length-1;
		var w = lis.eq(0).width();
		var h = $(o.sliders).height();
		var ind = 0;
		var maxW = (len-1)*w;
		this.each(function(){
			if(len==0){
				var offsetX = ($(".slides").width()-w)/2;
				lis.eq(0).css({"margin-top":"-"+(o.beBig-1)/2*h+"px","height":h*o.beBig});
				lis.eq(0).css("opacity",1);
				lis.eq(0).css("left",w*len+offsetX);
				return;
			}
			lis.css({"height":h});
			lis.eq(0).css({"margin-top":"-"+(o.beBig-1)/2*h+"px","height":h*o.beBig});
			lis.each(function(){
				var ind = $(this).index();
				$(this).css("z-index",(len-ind));
				var offsetX = ($(".slides").width()-w)/2;
				/*console.log(offsetX)*/
				$(this).css("opacity",o.opc);
				if(ind<len){
					$(this).css("left",w*ind+offsetX)
				}else{
					$(this).css("left","-"+(w-offsetX)+"px")
				}
			})
			obj.height(h);
			lis.eq(0).css("opacity",1);
		});
		function moveRight(){
			if(!$(lis).is(":animated")){
				getRight();
				lis.each(function(){
					var ind = $(this).index();
					var nextInd = (ind-1)<0?len:(ind-1);
					var marTp = $(this).css("margin-top");
					var h = $(this).css("height");
					var lf = $(this).css("left");
					var opc = $(this).css("opacity");
					$(lis).eq(nextInd).animate({
						left:lf,
						marginTop:marTp,
						opacity:opc,
						height:parseInt($(this).css("height"))
					},o.sliderSpeed,"swing")
				})
			}
		}
		function getRight(){
			var arr = [],newArr=[];
			for(var i = 0;i<=len;i++){
				var dataIndex = $(lis).eq(i).css("z-index");
				arr.push(dataIndex);
			}
			for(var i = 0;i<=len;i++){
				if(i<len){
					newArr[i] = arr[i+1]
				}else{
					newArr[i] = arr[0]
				}
				$(lis).eq(i).css("z-index",newArr[i]);
			}
		}
		function moveLeft(){
			if(!$(lis).is(":animated")){
				
				lis.each(function(){
					var ind = $(this).index();
					var prevInd = (ind+1)>len?0:(ind+1);
					var marTp = $(this).css("margin-top");
					var h = $(this).css("height");
					var lf = $(this).css("left");
					var opc = $(this).css("opacity");
					$(lis).eq(prevInd).animate({
						left:lf,
						marginTop:marTp,
						opacity:opc,
						height:parseInt($(this).css("height"))
					},o.sliderSpeed,"swing")
				})
				setTimeout(function(){
					getLeft();
				},o.sliderSpeed)
			}
		}
		function getLeft(){
			var arr = [],newArr=[];
			for(var i = 0;i<=len;i++){
				var dataIndex = $(lis).eq(i).css("z-index");
				arr.push(dataIndex);
			}
			for(var i = 0;i<=len;i++){
				if(i>0){
					newArr[i] = arr[i-1]
				}else{
					newArr[i] = arr[len]
				}
				$(lis).eq(i).css("z-index",newArr[i]);
			}
		}
		$(".lf").click(function(){
			moveRight();
		})
		$(".rt").click(function(){
			moveLeft();
		})
	    // setInterval(function(){
	    // 	moveLeft();
	    // },2000)
	}
})(jQuery);

/*var images = ["../images/format/1.png","../images/format/2.png","../images/format/3.png","../images/format/4.png","../images/format/1.png"];*/
/**图片预加载**/
function preloadimages(arr){
	var newimages = [],loadedImages = 0;
	var arr = (typeof arr!="object")? [arr] : arr;
	console.log(arr)
	function imageloadpost(){
		loadedImages++;
		if(loadedImages==arr.length){
			postaction(newimages)
		}
	}
	for(var i = 0;i<arr.length;i++){
		newimages[i]=new Image();
		newimages[i].src = arr[i];
		newimages[i].onload = function(){
			imageloadpost();
		}
		newimages[i].onerror = function(){
			imageloadpost();
		}
	}
	return {
		done:function(f){
            postaction=f || postaction
        }
	}
}
preloadimages($(".slides div img").attr("src")).done(function(newimages){
	var index = {
		w:0,
		init:function(){
			for(var i = 0;i<newimages.length;i++){
				var w = newimages[i].width;
				var h = newimages[i].height;
				$(".slides div").eq(i).children("img").attr("src",newimages[i].src)
				.attr("data-width",newimages[i].width)
				.attr("data-height",newimages[i].height)
			}
		}
	};
	index.init();
});