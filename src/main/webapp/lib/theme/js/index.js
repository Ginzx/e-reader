(function($) {
	$.fn.loopedSlider = function(options) {
	var defaults = {			
		container: '.container',
		slides: '.slides',
		containerClick: false, 
		autoStart: 0, 
		slidespeed: 300, 
		fadespeed: 300, 
		autoHeight: false 
	};
	this.each(function() {
		var obj = $(this);
		var o = $.extend(defaults, options);
		var pagination = $(o.pagination+' li a');
		var m = 0;
		var t = 1;
		var s = $(o.slides).children().size();
		var w = $(o.slides).children().outerWidth();
		var p = 0;
		var u = false;
		var n = 0;
		$(o.slides).css({width:(s*w)});
		$(o.slides).children().each(function(){
			$(this).css({position:'absolute',left:p,display:'block'});
			p=p+w;
		});
		$(pagination).each(function(){
			n=n+1;
			$(this).attr('rel',n);
			$(pagination.eq(0)).parent().addClass('active');
		});
		$(o.slides).children(':eq('+(s-1)+')').css({position:'absolute',left:-w});
		if(o.autoHeight){autoHeight(t);}
		$(".next,.previous").unbind();
		$(o.container).unbind();
		$(pagination).unbind();
		$('.next').click(function(){
			if(u===false) {
				animate('next',true);
				if(o.autoStart){clearInterval(sliderIntervalID);}
			} return false;
		});
		$('.previous').click(function(){
			if(u===false) {	
				animate('prev',true);
				if(o.autoStart){clearInterval(sliderIntervalID);}
			} return false;
		});
		if (o.containerClick) {
			$(o.container).click(function(){
				if(u===false) {
					animate('next',true);
					if(o.autoStart){clearInterval(sliderIntervalID);}
				} return false;
			});
		}
		$(pagination).click(function(){
			if ($(this).parent().hasClass('active')) {return false;}
			else {
				t = $(this).attr('rel');
				$(pagination).parent().siblings().removeClass('active');
				$(this).parent().addClass('active');
				animate('fade',t);
				if(o.autoStart){clearInterval(sliderIntervalID);}
			} return false;
		});
		if (o.autoStart) {
			sliderIntervalID = setInterval(function(){
				if(u===false) {animate('next',true);}
			}, o.autoStart);
		}
		function current(t) {
			if(t===s+1){t=1;}
			if(t===0){t=s;}
		};
		function autoHeight(t) {
			if(t===s+1){t=1;}
			if(t===0){t=s;}	
			var getHeight = $(o.slides).children(':eq('+(t-1)+')').outerHeight();
			$(o.container).animate({height: getHeight},o.autoHeight);					
		};		
		function animate(dir,clicked){	
			u = true;	
			switch(dir){
				case 'next':
					t = t+1;
					m = (-(t*w-w));
					current(t);
					if(o.autoHeight){autoHeight(t);}
					$(o.slides).animate({left: m}, o.slidespeed,function(){
						if (t===s+1) {
							t = 1;
							$(o.slides).css({left:0},function(){$(o.slides).animate({left:m})});							
							$(o.slides).children(':eq(0)').css({left: 0});
							$(o.slides).children(':eq('+(s-1)+')').css({ position:'absolute',left:-w});				
						}
						if (t===s) $(o.slides).children(':eq(0)').css({left:(s*w)});
						if (t===s-1) $(o.slides).children(':eq('+(s-1)+')').css({left:s*w-w});
						u = false;
					});					
					break; 
				case 'prev':
					t = t-1;
					m = (-(t*w-w));
					current(t);
					if(o.autoHeight){autoHeight(t);}
					$(o.slides).animate({left: m}, o.slidespeed,function(){
						if (t===0) {
							t = s;
							$(o.slides).children(':eq('+(s-1)+')').css({position:'absolute',left:(s*w-w)});
							$(o.slides).css({left: -(s*w-w)});
							$(o.slides).children(':eq(0)').css({left:(s*w)});
						}
						if (t===2 ) $(o.slides).children(':eq(0)').css({position:'absolute',left:0});
						if (t===1) $(o.slides).children(':eq('+ (s-1) +')').css({position:'absolute',left:-w});
						u = false;
					});
					break;
				case 'fade':
					t = [t]*1;
					m = (-(t*w-w));
					current(t);
					if(o.autoHeight){autoHeight(t);}
					$(o.slides).children().fadeOut(o.fadespeed, function(){
						$(o.slides).css({left: m});
						$(o.slides).children(':eq('+(s-1)+')').css({left:s*w-w});
						$(o.slides).children(':eq(0)').css({left:0});
						if(t===s){$(o.slides).children(':eq(0)').css({left:(s*w)});}
						if(t===1){$(o.slides).children(':eq('+(s-1)+')').css({ position:'absolute',left:-w});}
						$(o.slides).children().fadeIn(o.fadespeed);
						u = false;
					});
					break; 
				default:
					break;
				}					
			};
		});
	};
})(jQuery);

/**图片预加载**/
function preloadimages(arr){
	var newimages = [],loadedImages = 0;
	var arr = (typeof arr!="object")? [arr] : arr;
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
function initImage(images){
	preloadimages(images).done(function(newimages){
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
			},
			event:function(){
				$('#carousel').loopedSlider();
				$("#loopedSlider").delegate(".slides div","click",function(){
					var w = $(this).find("img").attr("data-width");
					var h = $(this).find("img").attr("data-height");
					var src = $(this).find("img").attr("src");
					var lf = (window.innerWidth-w)/2;
					var tp = (window.innerHeight-h)/2<0?0:(window.innerHeight-h)/2;
					$(".model_box").find("img").attr("src",src).attr("width",w).attr("height",h).attr("style","left:"+lf+"px;top:"+tp);
					$(".model_box").show();
					return false;
				})
				$(document).click(function(event){
					  var _con = $('.model_box img');   // 设置目标区域
					  console.log(!_con.is(event.target))
					  if(!_con.is(event.target)){ // Mark 1
						//$('#divTop').slideUp('slow');   //滑动消失
						$('.model_box').hide();          //淡出消失
					  }
				});
			},
			popUp:function(){

			}
		};
		index.init();
		index.event();
	});
}