var tp = (window.innerHeight-$("#loginBox").height())/2;
$("#loginBox").css({"top":tp});
$("#account").bind("input propertychange",function(){
	if($(this).val().length!=0){
		$(".user").attr("src","images/login/user_hover.png");
	}else{
		$(".user").attr("src","images/login/user.png");
	}
});
$("input:password").bind("input propertychange",function(){
	if($(this).val().length!=0){
		$(".pwd").attr("src","images/login/pwd_hover.png");
	}else{
		$(".pwd").attr("src","images/login/pwd.png");
	}
});
$("#smscode").bind("input propertychange",function(){
	if($(this).val().length!=0){
		$(".smsCode").attr("src","images/login/smsCode_hover.png");
	}else{
		$(".smsCode").attr("src","images/login/smsCode.png");
	}
});
$("input").bind("input propertychange",function(){
	if($("#account").val().length!=0 && $("input:password").val().length!=0 && $("#smscode").val().length!=0){
		$(".btn").css("background","#448aff");
	}else{
		$(".btn").css("background","#61a3ff");
	}
});
	
$(".loginBtn").click(function(){
	if($(".username").val().length!=0 && $("input:password").val().length!=0 && $(".verificationCode").val().length!=0){
		$(".warn").show();
		setTimeout(function(){
			$(".warn").hide();
		},2000);
	}
});
