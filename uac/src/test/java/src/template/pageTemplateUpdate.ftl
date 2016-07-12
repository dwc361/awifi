<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${el$}{module.parent.name }</title>
<%@include file="/head.jsp"%>
</head>
<body>
	<div class="bread">
		您的位置：
		<a href="javascript:void(0)" id="parParNode">${el$}{module.parent.parent.parent.name }</a> &gt; 
		<a href="javascript:void(0)" id="parNode">${el$}{module.parent.parent.name }</a> &gt; 
		<a href="javascript:void(0)" id="currNode">${el$}{module.parent.name }</a>
	</div>
	<div class="main">
		<c:set var="submit_url" value="${actionName}Action!update.ajax" />
		<%@include file="/${sysName}/${packageArr[packageArr?size-2]}/${alias}/${alias}_form.jsp"%>
		<div class="btnBox">
			<a id="backBtn" href="${includeBase}/${actionName}Action!list.action">
				<p class="sBtn">返回</p>
			</a> 
			<a id="saveBtn" href="javascript:void(0)">
				<p class="sBtn">保存</p>
			</a>
		</div>
	</div>
</body>
</html>