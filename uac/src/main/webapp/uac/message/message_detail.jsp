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
	<div class="bread">
		您的位置：
		<a href="javascript:void(0)" id="parNode">消息管理</a> &gt; 
		<a href="javascript:void(0)" id="currNode">消息列表</a>&gt; 
		<a href="javascript:void(0)" id="currNode">消息查看</a>
	</div>
	<div class="main">
		<input type="hidden" id="message_id" name="message.id" value="${message.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>标题</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					${message.title }
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>内容</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					${message.content }
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>发件人</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					${message.fromStaff.name }
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>日期</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<fmt:formatDate value="${message.send_date }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>收件人</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					${message.toStaff.name }
				</td>
			</tr>
	</table>
		<div class="btnBox">
			<a id="backBtn" href="${basePath}/uac_messageAction!list.action">
				<p class="sBtn">返回</p>
			</a> 
		</div>
	</div>
</body>
</html>