<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.name }</title>
<%@include file="/head.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {

		$('#disableBtn').click(function() {
			if (confirm("确定要停用吗？")) {
				$.post("uac_account_subUserAction!delete.action?subUser.id=${subUser.id}", {}, function(data) {
					alert(data.message);
					window.location.reload();
				});
			}
			return false;
		});
		$('#reuseBtn').click(function() {
			if (confirm("确定要启用吗？")) {
				$.post("uac_account_subUserAction!reuse.action?subUser.id=${subUser.id}", {}, function(data) {
					alert(data.message);
					window.location.reload();
				});
			}
			return false;
		});
		if($('#reuseBtn').length>0){
			$(":input").attr("disabled",true);
			$("#sysResource_select_btn").button({
				disabled : true
			});
		}else{
			$(":input").attr("disabled",false);
			$("#sysResource_select_btn").button({
				disabled : false
			});
		}
	});
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="javascript:void(0)" id="parNode">${module.parent.parent.parent.name }</a> &gt; 
		<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; 
		<a href="javascript:void(0)" id="currNode">${module.parent.name }</a>
	</div>
	<div class="main">
		<c:set var="submit_url" value="uac_account_subUserAction!update.ajax" />
		<%@include file="/uac/account/user/subUser/subUser_form.jsp"%>
		<div class="btnBox">
			<c:if test="${empty flag}">
			<a id="backBtn" href="${basePath}/uac_account_subUserAction!list.action?subUser.user.id=${user_id}">
				<p class="sBtn">返回</p>
			</a> 
			</c:if>
			<c:if test="${not empty flag}">
			<a id="backBtn" href="${basePath }/uac_account_subUserAction!list_myself.action">
				<p class="sBtn">返回</p>
			</a> 
			</c:if>
			
			<a id="saveBtn" href="javascript:void(0)">
				<p class="sBtn">保存</p>
			</a>
			<c:if test="${subUser.enabled }">
				<a id="disableBtn" href="javascript:void(0)">
					<p class="sBtn">停用</p>
				</a>
			</c:if>
			<c:if test="${!subUser.enabled }">
			<a id="reuseBtn" href="javascript:void(0)">
				<p class="sBtn">启用</p>
			</a>
			</c:if>
		</div>
	</div>
</body>
</html>