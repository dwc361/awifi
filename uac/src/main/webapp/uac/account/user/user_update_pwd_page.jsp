<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<%@include file="/head.jsp"%>
<style type='text/css'>
.pwdreeor { color:red; }
</style>
<script type="text/javascript">
var passwordPolicys = ${passwordPolicys};

$(document).ready(function() {
	$("#user_form").validate({
		rules : {
			'oldpassword' : {
				required : true,
				remote : {
					url : "uac_account_userAction!validateOldPassword.ajax",
					type : "post",
					dataType : "json",
					data : {
						"user.id" : function() {
							return $("input[name='user.id']").val();
						},
						"user.password" : function() {
							return $("input[name='oldpassword']").val();
						}
					}
				}
			},
			'user.password' : {
				required : true
			},
			'user.repassword' : {
				required : true,
				equalTo : "#password"
			}

		},
		messages : {
			'oldpassword':{
				remote : '旧密码不正确'
			},
			'user.repassword' : {
				equalTo : "两次输入的密码不相同"
			}
		},
		errorClass:"pwdreeor"
	});
	
	if (!("undefined" == typeof passwordPolicys)) {
		for ( var i in passwordPolicys) {
			var passwordPolicy = passwordPolicys[i];
			var passwordPolicy_name = "uac_" + passwordPolicy.name;
			jQuery.validator.addMethod(passwordPolicy_name, function(value, element, param) {
				var passport = new RegExp(param);
				return (passport.test(value));
			}, passwordPolicy.message);
			var rule = {};
			rule[passwordPolicy_name] = passwordPolicy.expression;
			$("input[name='user.password']").rules("add", rule);
		}
	}

	$("#user_form").ajaxForm({
		"dataType" : "json",
		"success" : function(d) {
			alert(d.message+"退出重新登录！");
			logoutUac();
			//parent.location.href = "uacAction!goMain.action";
		}
	});

	$("#saveBtn").click(function() {
		$("#user_form").submit();
	});
	
	function logoutUac() {
			$.ajax({
				type : "POST",
				url : "systemAction!j_spring_security_logout",
				dataType : "json",
				data : {},
				error : function(msg) {
					//alert("Ajax调用失败");
					window.top.location.href = "logout";
				},
				success : function(result) {
					window.top.location.href = "logout";
					//alert("注销成功");
				}
			});
	}
	
	
	
});
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="javascript:void(0)" id="parNode">自助服务</a> &gt; <a href="javascript:void(0)"
				id="currNode">修改密码</a>
	</div>
	<div class="main">
		<form id="user_form" action="uac_account_userAction!update.ajax">
			<input type="hidden" name="user.id" value="${user.id }">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 30%">姓名：</th>
					<td style="width: 70%"><input class="inpy" type="text" value="${user.name }" disabled/></td>
				</tr>
				<tr>
					<th>主账号：</th>
					<td><input class="inpy" type="text" value="${user.username }" disabled/></td>
				</tr>
				<tr>
					<th>旧密码：</th>
					<td><input id="oldpassword" name="oldpassword" class="inpy" type="password" /></td>
				</tr>
				<tr>
					<th>新密码：</th>
					<td><input id="password" class="inpy" name="user.password" type="password" /></td>
				</tr>
				<tr>
					<th>重复密码：</th>
					<td><input class="inpy" name="user.repassword" type="password" /></td>
				</tr>
			</table>
		</form>
		<div class="btnBox">
			<a id="saveBtn" href="javascript:void(0)">
				<p class="sBtn">保存</p>
			</a>
		</div>
	</div>

</body>
</html>