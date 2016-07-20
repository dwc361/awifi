<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>4A安全管理系统用户注册</title>
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
		<h2>4A安全管理系统用户注册</h2>
	</div>
	<!-- end of ad -->
	

	<div class="main">
		<c:set var="submit_url" value="uacAction!register.ajax" />
		<%@include file="/uac/account/user/user_form.jsp"%>
		<!------btnBox-------->
		<div class="btnBox">
			<a id="backBtn" href="${basePath }">
				<p class="sBtn">返回登陆页面</p>
			</a> <a id="saveBtn" href="#">
				<p class="sBtn">注册</p>
			</a>
		</div>
		<!------end of btnBox-------->
	</div>

	<!-- end of main -->

</body>
</html>