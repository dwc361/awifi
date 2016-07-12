<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主账号修改</title>
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${basePath }/common/js/ROOF.Class.js"></script>
<script type="text/javascript" src="${basePath }/common/js/zTree/js/jquery.ztree.all-3.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/common/js/zTree/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript">
	$(document).ready(function() {
		$("input[name='user.username']").attr('disabled', 'disabled');
		$("input[name='user.idNumber']").attr('disabled', 'disabled');

		$('#disableBtn').click(function() {
			if (confirm("确定要停用吗？")) {
				$.post("uac_account_userAction!delete.action?user.id=${user.id}", {}, function(data) {
					alert(data.message);
					window.location.reload();
				});
			}
			return false;
		});
		$('#reuseBtn').click(function() {
			if (confirm("确定要启用吗？")) {
				$.post("uac_account_userAction!reuse.action?user.id=${user.id}", {}, function(data) {
					alert(data.message);
					window.location.reload();
				});
			}
			return false;
		});
	});
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="#">账号管理</a> &gt; <a href="#">主账号修改</a>
	</div>
	<!-- end of ad -->
	<div class="main">
		<c:set var="submit_url" value="uac_account_userAction!update.ajax" />
		<%@include file="/uac/account/user/user_form.jsp"%>
		<!------btnBox-------->
		<div class="btnBox">
			<c:if test="${empty flag}">
				<a id="backBtn" href="${basePath }/uac_account_userAction!index.action">
					<p class="sBtn">返回</p>
				</a>
			</c:if>
			<c:if test="${not empty flag}">
				<a id="backBtn" href="${basePath }/uac_account_subUserAction!list_myself.action">
					<p class="sBtn">返回</p>
				</a>
			</c:if>
			<a id="saveBtn" href="#">
				<p class="sBtn">保存</p>
			</a>
			<c:if test="${user.enabled }">
				<a id="disableBtn" href="#">
					<p class="sBtn">停用</p>
				</a>
			</c:if>
			<c:if test="${!user.enabled }">
				</a>
				<a id="reuseBtn" href="#">
					<p class="sBtn">启用</p>
				</a>
			</c:if>
			<a id="subUserBtn" href="${basePath }/uac_account_subUserAction!list.action?subUser.user.id=${user.id }">
				<p class="sBtn">子账号</p>
			</a>
		</div>
		<!------end of btnBox-------->
	</div>

	<!-- end of main -->

</body>
</html>