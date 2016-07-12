<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>4A安全管理系统</title>
<%@include file="/head.jsp"%>
<link href="${basePath }/cas-web/css/images/logo_icon.ico" rel="shortcut icon" type="image/x-icon" />
<link href="${basePath }/cas-web/css/flexslider.css" media="all" rel="stylesheet" type="text/css" />
<!--[if lt IE 9]>
<script src="${basePath }/cas-web/js/modernizr.custom.js"></script>
<![endif]-->
<style type="text/css">
body {
	background: #EDF6FF url('${basePath}/cas-web/css/images/login_bg.png')
		no-repeat 0px -60px;
}
</style>
<script src="${basePath }/cas-web/js/jquery.flexslider-min.js" type="text/javascript"></script>
<script src="${basePath }/cas-web/js/functions.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath = "${basePath}";
	$(document).ready(function() {
		$('iframe').height(jQuery(window).height() - 180);
		$("li[id^='lvl']").mouseover(function() {
			//$("li[id^='lvl']").removeClass("active");
			//$(this).addClass("active");
		});
	});
	function doJs(hiddJsId) {
		var f = $("#hiddJs_" + hiddJsId).val();
		eval(f);
	}
	function logoutUac() {
		if (confirm("确定系统登出？")) {
			$.ajax({
				type : "POST",
				url : "systemAction!j_spring_security_logout",
				dataType : "json",
				data : {},
				error : function(msg) {
					//alert("Ajax调用失败");
					window.location.href = "logout";
				},
				success : function(result) {
					//alert("注销成功");
				}
			});
		}
	}
</script>
</head>
<body>
<div id="wrapper">
		<!-- shell -->
		<div class="shell">
			<!-- header -->
			<header class="header">
			<h1 id="logo">
				<span><img alt="" src="${basePath }/cas-web/css/images/telecom.png"></span><!-- <em>中国电信甘肃公司4A安全管理系统</em> --><em>4A安全管理系统</em>
			</h1>
			<div class="welcome">
				<span><a class="blue" href="javascript:void(0)" onclick="logoutUac();">注&nbsp;销</a></span><span>欢迎您，${empty currUser.user.name ? currUser.user.username : currUser.user.name }&nbsp;(${currUser.user.org.org_name })</span>
			</div>
			</header>
			<!-- end of header -->
			<!-- container -->
			<div class="container">
				<nav id="navigation">
				<ul>
					<li class="active" id="lvl0"><a href="${basePath }/uacAction!goMain.action">首页</a></li>
					<c:forEach var="menu" items="${menus.children }" varStatus="status">
				<%-- 	<c:if test="${!empty menu.children }"> --%>
						<li id="lvl${status.index + 1 }"><a href="javascript:void(0)">${menu.name }<span></span></a> <c:if
								test="${status.index == 0}">
								<ul class="w108">
							</c:if> <c:if test="${status.index > 0}">
								<ul class="w118">
							</c:if> <c:forEach var="subMenu" items="${menu.children }">
								<c:set var="m1" value="${basePath }${subMenu.module.path }.action" />
								<c:set var="m2" value="${basePath }${subMenu.module.path }" />
								<li><c:if test="${not empty subMenu.script}">
										<a href="javascript:void(0)" onclick="doJs('${subMenu.id }')">${subMenu.name }</a>
										<input type="hidden" id="hiddJs_${subMenu.id }" value='${subMenu.script }' />
									</c:if> <c:if test="${empty subMenu.script}">
										<c:choose>
											<c:when test="${fn:indexOf(subMenu.module.path, 'http://')!= -1 or fn:indexOf(subMenu.module.path, 'https://')!= -1 }">
												<a href="${subMenu.module.path}" target="${(empty subMenu.target) ? '_mainFrame' : subMenu.target  }">${subMenu.name }</a>
											</c:when>
											<c:otherwise>
												<a href="${fn:indexOf(subMenu.module.path, '?')== -1 ? m1 : m2}"
													target="${(empty subMenu.target) ? '_mainFrame' : subMenu.target  }">${subMenu.name }</a>
											</c:otherwise>
										</c:choose>

									</c:if></li>
							</c:forEach>
				</ul>
				</li>
				<%-- </c:if> --%>
				</c:forEach>
				</ul>
				</nav>
				<iframe name="_mainFrame" src="${basePath }/uacAction!index.action" frameborder="0" scrolling="auto" width="100%" style="background:#fff;"></iframe>
				<!-- end of container -->
			</div>
			<jsp:directive.include file="/cas-web/includes/bottom.jsp" />
			<!-- end of shell -->
		</div>
		<!-- end of wrappert -->
	<c:if test="${empty currUser.user.update_time}">
		<div class="floatBox" id="updatePwdDiv" style="width: 80%; left: 10%; top: 12%;">
			<div class="title" style="text-align: center; color: #FFF; font-size: 15px;"><b>第一次登录请先修改密码</b></div>
			<div class="floatCon">
			<iframe name="_mainFrame" src="${basePath }/uac_account_userAction!update_pwd_page.action" frameborder="0" scrolling="auto" width="100%"></iframe>
			</div>
		</div>
	</c:if>
</body>
</html>