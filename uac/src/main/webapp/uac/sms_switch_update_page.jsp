<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改短信开关</title>
<%@include file="/head.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#user_form").ajaxForm({
		"dataType" : "json",
		"success" : function(d) {
			alert(d.message);
		}
	});

	$("#SMS_SWITCH_Y").click(function() {
		$(this).find("input").attr("checked",true);
		$("#user_form").submit();
	});

	$("#SMS_SWITCH_N").click(function() {
		$(this).find("input").attr("checked",true);
		$("#user_form").submit();
	});
	
	
});
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="javascript:void(0)" id="parNode">系统管理</a> &gt; <a href="javascript:void(0)"
				id="currNode">修改短信开关</a>
	</div>
	<div class="main">
		<form id="user_form" action="uacAction!update_sms.ajax">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th width="35%">短信开关：</th>
					<td style="cursor: pointer;">
					
					<span id="SMS_SWITCH_Y"><input class="" name="sms_val" type="radio" value="SMS_SWITCH_Y" <c:if test="${is_open == true }"> checked="checked" </c:if>/>开
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<span id="SMS_SWITCH_N"><input class="" name="sms_val" type="radio" value="SMS_SWITCH_N" <c:if test="${is_open == false }"> checked="checked" </c:if> />关
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>