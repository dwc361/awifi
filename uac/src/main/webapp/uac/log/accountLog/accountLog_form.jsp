<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#accountLogForm").validate({
			rules : {
				"accountLog.op_result" : {
					required : true
				}, 
				"accountLog.opStaff.id" : {
					required : true
				}, 
				"accountLog.op_time" : {
					required : true
				}, 
				"accountLog.opType.id" : {
					required : true
				}, 
				"accountLog.user.id" : {
					required : true
				}
			}
		});

		$("#accountLogForm").ajaxForm({
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
			$("#accountLogForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#accountLogForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("accountLog","accountLog.id",$(this).attr("href"));
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
<input type="hidden" id="accountLog_paramString" value="${accountLog_paramString }" />
<form id="accountLogForm" name="accountLogForm" method="post" action="${submit_url }">
	<input type="hidden" id="accountLog_id" name="accountLog.id" value="${accountLog.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>操作结果</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="accountLog_op_result" class="inpy" name="accountLog.op_result" type="text" value="${accountLog.op_result }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>操作账号</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="accountLog_opStaff_id" class="inpy" name="accountLog.opStaff.id"
						type="text" value="${accountLog.opStaff.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>操作时间</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input readonly id="accountLog_op_time" class="inpy" name="accountLog.op_time" 
						type="text" value="<fmt:formatDate value="${accountLog.op_time }" pattern="yyyy-MM-dd HH:mm:ss" />" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>账号操作类型</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="accountLog_opType_id" class="inpy" name="accountLog.opType.id"
						type="text" value="${accountLog.opType.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>操作人</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="accountLog_user_id" class="inpy" name="accountLog.user.id"
						type="text" value="${accountLog.user.id }" />
				</td>
			</tr>
	</table>
</form>