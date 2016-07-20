<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
			$.createGooCalendar("loginLog_login_time",ROOF.Utils.getCalendarProperty());

		$("#loginLogForm").validate({
			rules : {
				"loginLog.ip" : {
					required : true
				}, 
				"loginLog.loginFailReason.id" : {
					required : true
				}, 
				"loginLog.loginResult.id" : {
					required : true
				}, 
				"loginLog.login_time" : {
					required : true
				}, 
				"loginLog.loginType.id" : {
					required : true
				}, 
				"loginLog.user.id" : {
					required : true
				}
			}
		});

		$("#loginLogForm").ajaxForm({
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
			$("#loginLogForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#loginLogForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("loginLog","loginLog.id",$(this).attr("href"));
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
<input type="hidden" id="loginLog_paramString" value="${loginLog_paramString }" />
<form id="loginLogForm" name="loginLogForm" method="post" action="${submit_url }">
	<input type="hidden" id="loginLog_id" name="loginLog.id" value="${loginLog.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>登陆IP</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="loginLog_ip" class="inpy" name="loginLog.ip" type="text" value="${loginLog.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>登陆失败原因</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="loginLog_loginFailReason_id" class="inpy" name="loginLog.loginFailReason.id"
						type="text" value="${loginLog.loginFailReason.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>登陆结果</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="loginLog_loginResult_id" class="inpy" name="loginLog.loginResult.id"
						type="text" value="${loginLog.loginResult.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>登陆时间</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input readonly id="loginLog_login_time" class="inpy" name="loginLog.login_time" 
						type="text" value="<fmt:formatDate value="${loginLog.login_time }" pattern="yyyy-MM-dd HH:mm:ss" />" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>登陆认证方式</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="loginLog_loginType_id" class="inpy" name="loginLog.loginType.id"
						type="text" value="${loginLog.loginType.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>登陆账号</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="loginLog_user_id" class="inpy" name="loginLog.user.id"
						type="text" value="${loginLog.user.id }" />
				</td>
			</tr>
	</table>
</form>