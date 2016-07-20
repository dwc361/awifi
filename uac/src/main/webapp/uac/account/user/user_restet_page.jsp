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
<c:set value="获取验证码" var="tip" />	

function StopButton() {
	var clickObj = jQuery('#'+arguments[0]);
	clickObj.text("" + arguments[1] + "");
    if (--arguments[1] >= 0) {
        window.setTimeout("StopButton('" + arguments[0] + "'," + arguments[1] + ")", 1000);
    }
    if (arguments[1] < 0) {
    	clickObj.text("${tip}");
    }
}

$(document).ready(function() {
	$("#user_form").validate({
		rules : {
			'user.password' : {
				required : true
			},
			'phonepwd' : {
				required : true
			}

		},
		messages : {
			
		}
		
	});
	
	var msg = $("#msg").val();
	if(msg){
		alert(msg);
	}
	
/* 
	$("#user_form").ajaxForm({
		"dataType" : "json",
		"success" : function(d) {
			
			if(d.state == 'success'){
				parent.location.href = "uac_restet_pwdAction!restet_page.action?user.username="+d.data;
			}else{
				alert(d.message);
			}
			
		},
		"error":function(d) {
			alert(d.message);
		}
	}); */

	$("#saveBtn").click(function() {
		$("#user_form").submit();
	});
	
	
	
	jQuery('#passcard').click(function() {
		if($(this).text() != "${tip}"){
			return false;
		}
		var username = jQuery('#username');
		var idNumber = jQuery('#idNumber');
		if(!username.val()){
			return false;
		}
		if(!idNumber.val()){
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "uac_restet_pwdAction!restet_passcardSend.ajax",
			dataType : "json",
			data : {
				"user.username" : username.val(),
				"user.idNumber" : idNumber.val(),
				
			},
			error : function(msg) {
				alert("Ajax调用失败");
			},
			success : function(result) {
				if ("success" == result.state) {
					alert("验证码已发送!有效期时间5分钟");
					StopButton('passcard', 60);
				} else {
					alert(result.message);
				}
			}
		});
	});
	
	
});
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="javascript:void(0)" id="parNode">自助服务</a> &gt; <a href="javascript:void(0)"
				id="currNode">重置密码</a>
	</div>
	<div class="main">
		<form id="user_form" action="uac_restet_pwdAction!restet_update.action">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<input type="hidden" id="msg" value="${msg}">
				<tr>
					<th>主账号：</th>
					<td><input class="inpy" type="text" id = "username" name = "user.username" value="${user.username}" /></td>
				</tr>
				<tr>
					<th>身份证号：</th>
					<td><input class="inpy" type="text" id ="idNumber"  value="${user.idNumber}"/></td>
				</tr>
				<tr>
					<th>验证码：</th>
					<td><input class="inpy" type="text" name = "phonepwd" />
					<span id="passcard" style="cursor:pointer" ><font color="blue">${tip }</font></span>
					</td>
					
				</tr>
				
			</table>
		</form>
		<div class="btnBox">
			<a id="saveBtn" href="javascript:void(0)">
				<p class="sBtn">验证</p>
			</a>
		</div>
	</div>

</body>
</html>