<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.parent.name }</title>
<%@include file="/head.jsp"%>
</head>
<body>
		<c:set var="submit_url" value="uac_authorization_system_osAction!update.ajax" />
		<%@include file="/uac/authorization/resource/os/os_form.jsp"%>
		<div class="btnBox">
			<a id="backBtn" href="${basePath}/uac_authorization_system_osAction!list.action">
				<p class="sBtn">返回</p>
			</a> 
			<a id="saveBtn" href="javascript:void(0)">
				<p class="sBtn">保存</p>
			</a>
		</div>
</body>
</html>