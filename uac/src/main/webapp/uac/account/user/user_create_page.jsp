<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${basePath }/common/js/ROOF.Class.js"></script>
<script type="text/javascript" src="${basePath }/common/js/zTree/js/jquery.ztree.all-3.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/common/js/zTree/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript">
var passwordPolicys = ${passwordPolicys};
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="#">账号管理</a> &gt; <a href="#">主账号新增</a>
	</div>
	<!-- end of ad -->
	

	<div class="main">
		<c:set var="submit_url" value="uac_account_userAction!create.ajax" />
		<%@include file="/uac/account/user/user_form.jsp"%>
		<!------btnBox-------->
		<div class="btnBox">
			<a id="backBtn" href="${basePath }/uac_account_userAction!index.action">
				<p class="sBtn">返回</p>
			</a> <a id="saveBtn" href="#">
				<p class="sBtn">保存</p>
			</a>
		</div>
		<!------end of btnBox-------->
	</div>

	<!-- end of main -->

</body>
</html>