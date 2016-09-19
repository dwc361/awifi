<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%@include file="head.jsp"%>
<script type="text/javascript" src="${basePath}/common/js/md5.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/login/css/goLogin.css" />
<link href="//netdna.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<script type="text/javascript">
	var errorCode = $
	{
		errorCode
	};
	jQuery(document).ready(function() {
		takeCookiedInfo();
		
		if (errorCode == 'BadCredentials') {
			alert('用户名或密码错误!');
		}
		jQuery('#btn').click(function() {
			var user_user_noObj = jQuery("#j_username");
			var user_passwordObj = jQuery("#j_password");
			var user_user_no = user_user_noObj.val();
			var user_password = user_passwordObj.val();
			if (!user_user_no || user_user_no == "") {
				alert("工号不能为空");
				return;
			}
			if (!user_password || user_password == "") {
				alert("密码不能为空");
				return;
			}
			var reg = /[a-zA-Z0-9]+/;//由数字和26个英文字母组成的字符串
			if(!reg.exec(user_user_no)){
				 alert('工号格式不正确，请重新输入');
				 return;
			}
			if(!reg.exec(user_password)){
				 alert('密码格式不正确，请重新输入');
				 return;
			}
			var md5s = to_hex_md5(user_password).toUpperCase();
			if (!md5s) {
				return;
			}
			user_passwordObj.val(md5s);
			$("#login_Form").ajaxSubmit({
				"success" : function(data) {
					if (data.state == 'success') {
						if($('#checkbox').is(':checked')){
			    		    savePasswordToCookie(user_user_no,user_password);
			    		}else{
			    			savePasswordToCookie('','');
			    		}
						
						window.location.href = data.message;
					} else {
						alert(data.message);
						user_passwordObj.val("");
					}
				}
			});
		});
	});
	document.onkeydown = function(e) {
		e = e || window.event;
		var keycode = e.which ? e.which : e.keyCode;
		if (keycode == 13) {// Enter
			jQuery('#login_Btn').click();
		}
		if (keycode == 27) {// Esc
			var username = jQuery('input[name="j_username"]');
			var password = jQuery('input[name="j_password"]');
			username.val("");
			password.val("");
		}
	}
	//记住密码
	function savePasswordToCookie(username,passwd){
		//将样式名称保存cookie中
		var days=60;
	    var cookie=document.cookie;
	    var validTime=new Date();
	    //默认cookie保存30天
	    validTime.setTime(validTime.getTime()+days*24*60*60*1000);   	
	    document.cookie="ems_login_userName="+escape(username)+";expires="+validTime.toGMTString()+";path=/";
	    document.cookie="ems_login_passwd="+escape(passwd)+";expires="+validTime.toGMTString()+";path=/";
	}

	//取出保存在cookie中的用户名和密码
	function takeCookiedInfo(){
		var userReg=new RegExp("(^| )"+"ems_login_userName"+"=([^;]*)(;|$)");
		var pwdReg=new RegExp("(^| )"+"ems_login_passwd"+"=([^;]*)(;|$)");
		var user= document.cookie.match(userReg);
		var password=document.cookie.match(pwdReg);
		if(user==null||password==null) return;
		user=unescape(user[2]);
		password=unescape(password[2]);
		if(user!=''&&password!=''){
			$("#j_username").val(user);
			$("#j_password").val(password);
		}	
	}
</script>
</head>
<body>
<body>
	<div id="login_bg111">
		<div class="login">
			<div class="logo" id="login_bg2"><img src="${basePath}/login/images/img2.png"></div>
			<div class="logo" id="login_zu5"><img src="${basePath}/login/images/组 5.png"></div>
			<div class="logo" id="login_loadBox"><img src="${basePath}/login/images/loadBox.png"></div>
			<div class="logo" id="login_line"><img src="${basePath}/login/images/line.png"></div>
		</div>
		<div id="login_wraper" class="login">
			<form id="login_Form" action="${basePath}/j_spring_security_check" method="post">
				<div id="login_div">
  					<a class="btn btn-primary" href="#"><i class="fa fa-user fa-fw"></i> 
  					<input name="j_username" id="j_username" type="text" value="admin" placeholder="User" /></a>
					<a class="btn btn-primary" href="#"><i class="fa fa-lock fa-fw"></i>
					<input name="j_password" id="j_password" type="password" value="123456abc" placeholder="PassWord" /></a>
					<label for="checkbox"><input type="checkbox" id="checkbox" checked />&nbsp;&nbsp;记住密码</label>
				</div>
				<div id="login_Btn" class="login_right" style="cursor: pointer;">
					<input id="btn" class="blue24" type="button" value="登录" />
					<p id="login_word">&nbsp;Copyright&nbsp;2016&nbsp;awifi运营基地&nbsp;版权所有&nbsp;</p>
				</div>
			</form>
		</div>
	</div>
</body>
</html>