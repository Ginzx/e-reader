//重置密码
function resetPwd(userId){
	layer.confirm('您确定要重置该帐号密码吗？', {
		btn: ['确定','取消']
	},function(){
		var options = {
			url : "resetPwd.html",
			data : {userId : userId},
			error : function(e){
	        	layer.msg('请求错误');
	        },
	        success : function(e){
	        	layer.msg(e.msg);
	        }
		};
		commmonAjax(options);
	}
	);
}

//退出登录
function exit(url){
	//确认框
	layer.confirm('您确定要退出？', {
		btn: ['退出','取消']
	},function(){
		var options = {
			url : "exit.html?",
			error : function(e){
	        	layer.msg('请求错误');
	        },
	        success : function(e){
				var contextPath = document.location.pathname;
				var index =contextPath.substr(1).indexOf("/");
				contextPath = contextPath.substr(0,index+1);
				var root =location.protocol+"//"+location.hostname+":"+location.port+contextPath+"/login.html";
				window.location.href=root;
			}
		};
		commmonAjax(options);
	}
	);
}
/////////////////////////////////////通用脚本/////////////////////////////////////
//分页大小，cookies获取
var page_size;
if(typeof $.cookie("_cookie_page_size") == 'undefined') {
	page_size=10;
}else{
	page_size=$.cookie("_cookie_page_size");
}
$("#set_pagesize").val(page_size);
//全局分页大小设置
function setPageSize(size,path,formId){
	$.cookie("_cookie_page_size",size,{expires:365,path:path});
	$('#'+formId).submit();
}

//删除单个
function del(id, url){
	var options = {
		id : id,
		confirm	: "确认删除选中的记录？",
		ajaxurl : url
	};
	selOneToRequest(options);
}
//添加单个
function add(id, url){
	var options = {
		id : id,
		confirm	: "确认添加选中的记录？",
		ajaxurl : url
	};
	selOneToRequest(options);
}

//多个删除
function dels(url){
	var options = {
		confirm	: "确认批量删除选中的记录？",
		ajaxurl : url
	};
	selMultiToRequest(options);
}
//添加加多个
function addAll(url){
	var options = {
		confirm	: "确认批量添加选中的记录？",
		ajaxurl : url
	};
	selMultiToRequest(options);
}
//单个操作 成功后刷新页面
function action(id, url,msg){
	var options = {
		id : id,
		confirm	: msg,
		ajaxurl : url
	};
	selOneToRequest(options);
}
//批量操作 
function batch(url,msg){
	var options = {
		confirm	: msg,
		ajaxurl : url
	};
	selMultiToRequest(options);
}
//验证并提交指定Form
function validSubmitForm(formId){
		$('#' + formId).Validform({
			tiptype : 2,
			ajaxPost : true,
			postonce:true,
			beforeSubmit : function(form){
				var index = layer.load(0, {shade: false}); 
				var options = {
					success : function(e){
						layer.close(index);
						layer.msg(e.msg);
					},
					error : function(e){
						layer.close(index);
						layer.msg('请求失败');
					}
				};
				$(form[0]).ajaxSubmit(options);
				return false;  
		    }
		});
	
}

//验证并提交指定Form,成功则跳转指定页面
function validSubmitForm(formId,url){
	$('#' + formId).Validform({
		tiptype : 2,
		ajaxPost : true,
		postonce:true,
		beforeSubmit : function(form){
			var index = layer.load(0, {shade: false});
			//如果有密码的表单，提交时进行加密
			if($('#enPwd').length > 0){
				$('#enPwd').val(($('#pwd').val()));
				$('#pwd').val('');
			}
			var options = {
				success : function(e){
					layer.close(index);
					layer.msg(e.msg);
					if(e.code == 200){
						window.location.href=url;
					}
				},
				error : function(e){
					layer.close(index);
					layer.msg('请求失败');
				}
			};
			$(form[0]).ajaxSubmit(options);
			return false;  
	    }
	});
}

//验证并提交指定Form，然后刷新父级——添加等场景——适合操作后父级窗口到第一页
function validSubmitFormAndRefreshParent(formId){
	$('#' + formId).Validform({
		tiptype : 2,
		ajaxPost : true,
		postonce:true,
		beforeSubmit : function(form){
			var index = layer.load(0, {shade: false});
			//如果有密码的表单，提交时进行加密
			if($('#enPwd').length > 0){
				$('#enPwd').val(($('#pwd').val()));
				$('#pwd').val('');
				//通常有确认密码
				$('#pwd2').val('');
			}
				var options = {
				success : function(e){
					layer.close(index);
					if(e.code == 200){
						var parentIndex = parent.layer.getFrameIndex(window.name);
//						parent.location.replace(parent.location.href);
						parent.localAjaxReload();
						parent.layer.close(parentIndex);
                        layer.msg("成功");
					}
					else{
						layer.msg(e.msg);
					}
				},
				error : function(e){
					layer.close(index);
					layer.msg('加载失败');
				}
				};
				$(form[0]).ajaxSubmit(options);
				layer.closeAll('dialog');
		        return false;  
	    }
	});
}

//验证并提交指定Form，然后刷新父级（添加、修改等场景）——修改等场景——适合操作后父级窗口页数不变
function validSubmitFormAndRefreshParent_page(formId){
	$('#' + formId).Validform({
		tiptype : 2,
		ajaxPost : true,
		postonce:true,
		beforeSubmit : function(form){
			var index = layer.load(0, {shade: false});
			//如果有密码的表单，提交时进行加密
			if($('#enPwd').length > 0){
				$('#enPwd').val($('#pwd').val());
				$('#pwd').val('');
				//通常有确认密码
				$('#pwd2').val('');
			}
			var options = {
				success : function(e){
					layer.close(index);
					if(e.code == 200){
						var parentIndex = parent.layer.getFrameIndex(window.name);
	//							//调用刷新父级窗口list记录的方法
	//							parent.freshPage();
						parent.localAjaxReload();
						parent.layer.close(parentIndex);
					}
					else{
						layer.msg(e.msg);
					}
				},
				error : function(e){
					layer.close(index);
					layer.msg('加载失败');
				}
			};
			
			$(form[0]).ajaxSubmit(options);
			layer.closeAll('dialog');
	        return false;  
	    }
	});
	
}

//对单值进行请求，如果操作成功则刷新本页面
function selOneToRequest(options){
	var defaults = {
			id : "",
			confirm	: "确认操作选中的记录？",
			ajaxtype : "POST",
			ajaxurl : "",
			ajaxdata : {}
		};
	var op = $.extend({}, defaults, options);
	layer.confirm(op.confirm, {
	    btn: ['确定','取消'] //按钮
	}, function(){
		var ajaxOptions = {
    		type : op.ajaxtype,
    		dataType : 'json',
    		url : op.ajaxurl,
    		data : $.extend({}, op.ajaxdata, {id : op.id}),
	        error : function(e){
	        	layer.msg('请求错误');
	        },
	        success : function(e){
	        	if(e.code == 200){
	        		localAjaxReload();
	        	}
	        	else{
	        		layer.msg('操作失败:'+e.msg);
	        	}
	        }
    	};
    	commmonAjax(ajaxOptions);
    	layer.closeAll('dialog');
	}, function(){
	    
	});
}

//选中多个复选框的值进行请求，如果成功则刷新本页面
function selMultiToRequest(options){
	var defaults = {
		chkname	: "chkId",
		confirm	: "确认批量操作选中的记录？",
		ajaxtype : "POST",
		ajaxurl : "",
		ajaxdata : {}
	};
	var op = $.extend({}, defaults, options);
	var checkedNum = $("input[name='" + op.chkname + "']:checked").length; 
	if(checkedNum == 0) { 
		layer.msg("请选择至少一项！"); 
		return; 
	}
	layer.confirm(op.confirm, {
	    btn: ['确定','取消'] //按钮
	}, function(){
		var checkedList = [];
		$("input[name='" + op.chkname + "']:checked").each(function() { 
			checkedList.push($(this).val()); 
		});
		var ajaxOptions = {
    		type : op.ajaxtype,
    		dataType : 'json',
    		url : op.ajaxurl,
    		data : $.extend({}, op.ajaxdata, {ids : checkedList.toString()}),
	        error : function(e){
	        	layer.msg('请求错误');
	        },
	        success : function(e){
	        	if(e.code == 200){
	        		localAjaxReload();
	        	}
	        	else{
	        		layer.msg('操作失败:'+e.msg);
	        	}
	        }
    	};
    	commmonAjax(ajaxOptions);
    	layer.closeAll('dialog');
	}, function(){
	    
	});
}

//通用分页
function pager(url, data, total, size,curr){
	var options = {
        url: url,
        data: data,
        total : total,
        size : size,
        curr : curr || 1
    };
	commonPager(options);
}

//分页处理
function commonPager(options){
	var defaults = {
			datacont : 'dataContent',
			cont : 'pager',
	        type : 'POST',
	        url : '',
	        data : {},
	        timeout : 30000,
	        total : 0,
	        size : 10,
	        mask : true
	    };
	var op = $.extend({}, defaults, options);
	var total = parseInt(op.total); //总记录数 
	var size = parseInt(op.size); //每页显示数 
	var pages = Math.floor(total / size); 
	if (total % size != 0) { 
		pages++; 
	} 
	laypage({
        cont: op.cont, //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
        pages: pages, //总页数
        curr: op.curr, //初始化当前页
        skip: true, //是否开启跳页
        skin: '#448aff',//molv
        jump: function(e, first){ //触发分页后的回调
        	var ajaxOptions = {
        		type : op.type,
        		dataType : 'html',
        		url : op.url,
        		data : $.extend({}, {
    	        	curr : e.curr,
    	        	size : op.size
    	        }, op.data),
    	        error : op.error || function(e){
    	        	layer.msg('请求错误');
    	        },
    	        success : op.success || function(e){
    	        	$("#" + op.datacont).html(e);
    	        	$("input[name='']").attr("checked",false);
    	        	document.documentElement.scrollTop=0;
    	        	document.body.scrollTop=0;
    	        }
        	};
        	commmonAjax(ajaxOptions);
        }
    });
}

//通用分页
function pagerColumn(contName,url, data, total, size,curr){
	var options = {
        url: url,
        data: data,
        total : total,
        size : size,
        curr : curr || 1
    };
	commonPagerColumn(contName,options);
}

//分页处理
function commonPagerColumn(contName,options){
	var defaults = {
			datacont : contName,
			cont : 'pager',
	        type : 'POST',
	        url : '',
	        data : {},
	        timeout : 30000,
	        total : 0,
	        size : 10,
	        mask : true
	    };
	var op = $.extend({}, defaults, options);
	var total = parseInt(op.total); //总记录数 
	var size = parseInt(op.size); //每页显示数 
	var pages = Math.floor(total / size); 
	if (total % size != 0) { 
		pages++; 
	} 
	laypage({
        cont: op.cont, //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
        pages: pages, //总页数
        curr: op.curr, //初始化当前页
        skip: false, //是否开启跳页
        skin: '#242424',//molv
        jump: function(e, first){ //触发分页后的回调
        	var ajaxOptions = {
        		type : op.type,
        		dataType : 'html',
        		url : op.url,
        		data : $.extend({}, {
    	        	curr : e.curr,
    	        	size : op.size
    	        }, op.data),
    	        error : op.error || function(e){
    	        	layer.msg('请求错误');
    	        },
    	        success : op.success || function(e){
					$("#" + op.datacont).html(e);
    	        }
        	};
        	commmonAjax(ajaxOptions);
        }
    });
}

//通用的ajax方法
function commmonAjax(options){
	var defaults = {
		type : 'POST',
		cache : false,
		dataType : 'json',
		timeout : 30000,
	};
	var op = $.extend({}, defaults, options);
	var index = layer.load(0, {shade: false}); 
	$.ajax({
        type     : op.type,
        url      : op.url,
        data     : op.data || {},
        cache    : op.cache,
        dataType : op.dataType,
        timeout  : op.timeout,
        error	 : op.error || function(e){
        	layer.close(index);
        	if(typeof op.error == 'function'){
        		op.error(e);
        	}
        },
        success  : function(e){
        	layer.close(index);
        	if(typeof op.success == 'function'){
        		op.success(e);
        	}
        }
	});
}

//全屏展开子页面
function layer_full_show(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url,
		area:['100%','100%']
	});
	layer.full(index);
}
//图片上传
function AjaxUploadFn(imgBtn, imgSrc, coverImg){
	var button = $(imgBtn);
	new AjaxUpload(button, {
        action:'../site/uploadLogo.html',
        responseType:'text',
        data: {},
        name: 'file',
        onSubmit: function(file, ext) {  
            if (!(ext && /^(png|PNG)$/.test(ext))) {  
                layer.msg('您上传的图片格式不对，请重新选择！');
                return false;  
            }
        },  
        onComplete: function(file, e) {
         	var data = eval('('+e+')');
           	if(data.code==1000){
           		$(imgSrc).attr("src",data.fileUrl);
           		$(coverImg).val(data.fileUrl);
            }  
            else {  
            	layer.msg('失败:'+data.msg);
            }  
        }  
    });
}

	$("table").delegate("input[name='chkId']","click",function(){
		//$("input[name='chkId']").each(function(){
			if(!$(this).is(':checked')){    
				$("input[name='']").attr("checked",false);
			}else{
				var a=0;
				$("input[name='chkId']").each(function(){
					if(!$(this).is(':checked')){   
						a=1;
					}					
				})
				//$("input[name='']").attr("checked",'true');
				if(a==0){
					document.getElementsByName("")[0].checked=true; 
				}
			}			
		//})
	});
	var bind_name = 'input';  
	if (navigator.userAgent.indexOf("MSIE") != -1) {  
	    bind_name = 'propertychange';  
	}  
	$('#pitchOnNode').bind("input propertychange", function(){
		var name=$("#pitchOnNode").val();
		var siteTree = $.fn.zTree.getZTreeObj("siteTree");
		var node=siteTree.getNodesByParamFuzzy("name",name,null)
		siteTree.selectNode(node[0]); 
		/*var container = $('#treeDiv');
		scrollTo = $("#"+node[0].tId);
		if(scrollTo.offset().top<200){
			container.scrollTop(0)
		}*/
		$("#pitchOnNode").focus();
	});
	/*function pitchOnNode(){
		var name=$("#pitchOnNode").val();
		var siteTree = $.fn.zTree.getZTreeObj("siteTree");
		var node=siteTree.getNodesByParamFuzzy("name",name,null)
		siteTree.selectNode(node[0]); 
		var container = $('#treeDiv');
		scrollTo = $("#"+node[0].tId);
		if(scrollTo.offset().top<200){
			container.scrollTop(0)
		}
		$("#pitchOnNode").focus();
		
	}*/


