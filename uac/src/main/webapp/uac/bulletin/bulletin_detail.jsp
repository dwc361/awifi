<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.parent.name }</title>
<%@include file="/head.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#backBtn").click(function() {
		ROOF.Utils.back("bulletin","bulletin.id",$(this).attr("href"));
	});
});
</script>
</head>
<body>
	<div class="bread">
		您的位置：
		<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; 
		<a href="javascript:void(0)" id="currNode">${module.parent.name }</a>
	</div>
	<div class="main">
	<input type="hidden" id="bulletin_paramString" value="${bulletin_paramString }" />
	<form id="bulletinForm" name="bulletinForm" method="post" action="${submit_url }">
		<input type="hidden" id="bulletin_id" name="bulletin.id" value="${bulletin.id }" />
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 30%">
						<span>标题</span><font color="red">*</font>：
					</th>
					<td style="width: 70%">
						${bulletin.title }
					</td>
				</tr>
				<tr>
					<th style="width: 30%">
						<span>发布时间</span><font color="red">*</font>：
					</th>
					<td style="width: 70%">
						<fmt:formatDate value="${bulletin.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<th style="width: 30%">
						<span>内容</span><font color="red">*</font>：
					</th>
					<td style="width: 70%">
						${bulletin.content }
					</td>
				</tr>
				
		</table>
	</form>
	<div class="btnBox">
		<a id="backBtn" href="${basePath}/uac_bulletinAction!list${isRead }.action">
			<p class="sBtn">返回</p>
		</a>
	</div>
	</div>
</body>
</html>