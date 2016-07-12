<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
			$.createGooCalendar("authLog_auth_time",ROOF.Utils.getCalendarProperty());

		$("#authLogForm").validate({
			rules : {
				"authLog.auth_time" : {
					required : true
				}, 
				"authLog.ip" : {
					required : true
				}, 
				"authLog.loginFailReason.id" : {
					required : true
				}, 
				"authLog.loginResult.id" : {
					required : true
				}, 
				"authLog.staff.id" : {
					required : true
				}, 
				"authLog.sysresource_id" : {
					number : true,
					required : true
				}
			}
		});

		$("#authLogForm").ajaxForm({
				type : 'post',
				cache : false,
				dataType : 'json',
				error : function() {
					alert('Ajax信息加载出错,请重试');
				},
				success : function(replay) {
					alert(replay.message);
				}
		});

		$("#saveBtn").click(function() {
			$("#authLogForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#authLogForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("authLog","authLog.id",$(this).attr("href"));
		});
	});
</script>
<!--
<select class="inpy" name="user.scope.id">
		<c:forEach var="userScope" items="${list}">
			<c:choose>
				<c:when test="${userScope.id == user.scope.id}">
					<option value="${userScope.id }" selected="selected">${userScope.text }</option>
				</c:when>
				<c:otherwise>
					<option value="${userScope.id }">${userScope.text }</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
</select>
-->
<input type="hidden" id="authLog_paramString" value="${authLog_paramString }" />
<form id="authLogForm" name="authLogForm" method="post" action="${submit_url }">
	<input type="hidden" id="authLog_id" name="authLog.id" value="${authLog.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>认证时间</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input readonly id="authLog_auth_time" class="inpy" name="authLog.auth_time" 
						type="text" value="<fmt:formatDate value="${authLog.auth_time }" pattern="yyyy-MM-dd HH:mm:ss" />" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>认证IP</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="authLog_ip" class="inpy" name="authLog.ip" type="text" value="${authLog.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>登陆失败原因</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="authLog_loginFailReason_id" class="inpy" name="authLog.loginFailReason.id"
						type="text" value="${authLog.loginFailReason.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>登陆结果</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="authLog_loginResult_id" class="inpy" name="authLog.loginResult.id"
						type="text" value="${authLog.loginResult.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>认证用户</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="authLog_staff_id" class="inpy" name="authLog.staff.id"
						type="text" value="${authLog.staff.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>请求系统</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="authLog_sysresource_id" class="inpy" name="authLog.sysresource_id" type="text" value="${authLog.sysresource_id }" />
				</td>
			</tr>
	</table>
</form>