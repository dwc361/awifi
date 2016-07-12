<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.name }</title>
<%@include file="/head.jsp"%>
<script type="text/javascript">
	function timedMsg(numb) {
		if (numb >= 1) {
			$("#scodeSendBtn").button("option", "label", numb + "秒后重新发送");
			setTimeout("timedMsg(" + (numb - 1) + ")", 1000);
		} else {
			$("#scodeSendBtn").button({
				disabled : false
			});
			$("#scodeSendBtn").button("option", "label", "点击获得验证码");
		}
	}
	$(document).ready(function() {

		$("#scodeSendBtn").button().click(function() {
			$(this).button({
				disabled : true
			});
			$("#subUserBindingForm").ajaxSubmit({
				url : "uac_account_subUserAction!binding_scode.ajax",
				type : "post",
				dataType : "json",
				success : function(data) {
					alert(data.message);
					if (data.state == 'success') {
						timedMsg(60);
					} else {
						$("#scodeSendBtn").button({
							disabled : false
						});
					}
				}
			});
		});
		
		$("#bindingBtn").click(function() {
			$("#subUserBindingForm").ajaxSubmit({
				url : "uac_account_subUserAction!binding.ajax",
				type : "post",
				dataType : "json",
				success : function(data) {
					alert(data.message);
				}
			});
		});
		
	});
</script>
</head>
<body style="overflow: hidden;">
	<div class="bread">
		您的位置：
		<a href="javascript:void(0)" id="parNode">账号管理</a> &gt; 
		<a href="javascript:void(0)" id="currNode">子账号绑定</a>
	</div>
	<div class="main">
		<form id="subUserBindingForm" method="post" action="">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 30%"><span>主账号</span>：</th>
					<td style="width: 70%"><input disabled="disabled" class="inpy" value="${subUser.user.username }" /></td>
				</tr>
				<tr>
					<th style="width: 30%"><span>所属系统</span>：</th>
					<td style="width: 70%"><select name="sysResource_id" class="inpy">
							<c:forEach var="app" items="${apps }">
								<option value="${app.id }">${app.name }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th style="width: 30%"><span>所属系统账号</span>：</th>
					<td style="width: 70%"><input class="inpy" name="username" value="" /></td>
				</tr>
				<tr>
					<th style="width: 30%"><span>验证码</span>：</th>
					<td style="width: 70%"><input class="inpy" name="scode" value="" />&nbsp;&nbsp;<a id="scodeSendBtn"
						class="btn" href="#"> 点击获得验证码 </a></td>
				</tr>
			</table>
		</form>
		<div class="btnBox">
			<a id="backBtn" href="${basePath}/uacAction!index.action">
			<p class="sBtn">返回</p>
			</a>
			<a id="bindingBtn" href="javascript:void(0)">
				<p class="sBtn">绑定</p>
			</a>
		</div>
	</div>
</body>
</html>