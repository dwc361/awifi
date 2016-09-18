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
			var md5s = to_hex_md5(user_password).toUpperCase();
			if (!md5s) {
				return;
			}
			user_passwordObj.val(md5s);
			$("#login_Form").ajaxSubmit({
				"success" : function(data) {
					if (data.state == 'success') {
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
</script>
</head>
<body>
<body>
	<div id="login_bg111">
		<div class="login">
			<div id="login_bg2"></div>
			<div id="login_logo2"></div>
			<div id="login_loadBox"></div>
			<div id="login_zu5"></div>
		</div>
		<div id="login_wraper" class="login">
		<input id="login_line"/><label id="login_word">&nbsp;&nbsp;&nbsp;&nbsp;SCREEN管理平台&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="login_line2"/>
			<form id="login_Form" action="${basePath}/j_spring_security_check" method="post">
				<div id="login_div">
  					<a class="btn btn-primary" href="#"><i class="fa fa-user fa-fw"></i> 
  					<input name="j_username" id="j_username" type="text" value="admin" placeholder="User" /></a>
					<a class="btn btn-primary" href="#"><i class="fa fa-lock fa-fw"></i>
					<input name="j_password" id="j_password" type="password" value="123456abc" placeholder="PassWord" /></a>
					<label for="checkbox"><input type="checkbox" id="checkbox" checked="checked" style="width: 16px;height: 16px;"/>&nbsp;&nbsp;记住密码</label>
				</div>
				<div id="login_Btn" class="login_right" style="cursor: pointer;">
					<input id="btn" class="blue24" type="button" value="登录" />
					<p id="login_word2">&nbsp;Copyright&nbsp;2016&nbsp;awifi运营基地&nbsp;版权所有&nbsp;</p>
				</div>
			</form>
		</div>
	</div>
</body>
</html>