<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主账号管理</title>
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${basePath }/common/js/zTree/js/jquery.ztree.all-3.1.min.js"></script>
<script type="text/javascript" src="${basePath }/roof-web/web/org/js/roof.web.OrgTree.js"></script>
<script type="text/javascript" src="${basePath }/uac/account/user/js/index.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/common/js/zTree/zTreeStyle/zTreeStyle.css" />
</head>
<body>
	<div class="bread">
		您的位置：<a href="#">账号管理</a> &gt; <a href="#">主账号管理</a>
	</div>
	<div class="main">
		<div class="treeBox">
			<div class="ztree" style="width: 15%; float: left;" width="186" height="288"></div>
		</div>
		<div class="conBox">
			<iframe src="${basePath }/uac_account_userAction!list.action" frameborder="0" scrolling="auto" width="100%"></iframe>
		</div>
	</div>
</body>
</html>