<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.createGooCalendar("message_send_date",ROOF.Utils.getCalendarProperty());

		$("#messageForm").validate({
			rules : {
				"message.content" : {
					required : true
				}, 
				"message.fromStaff.id" : {
					required : true
				}, 
				"message.send_date" : {
					required : true
				}, 
				"message.send_type" : {
					required : true
				}, 
				"message.sts" : {
					required : true
				}, 
				"message.title" : {
					required : true
				}, 
				"message.toStaff.id" : {
					required : true
				}
			}
		});

		$("#messageForm").ajaxForm({
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
			$("#messageForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#messageForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("message","message.id",$(this).attr("href"));
		});
	});
</script>
<!--
<select class="inpy" name="user.scope.id" id="">
		<c:forEach var="obj" items="${list}">
			<c:choose>
				<c:when test="${obj.id == user.scope.id}">
					<option value="${obj.id }" selected="selected">${obj.text }</option>
				</c:when>
				<c:otherwise>
					<option value="${obj.id }">${obj.text }</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
</select>
-->
<input type="hidden" id="message_paramString" value="${message_paramString }" />
<form id="messageForm" name="messageForm" method="post" action="${submit_url }">
	<input type="hidden" id="message_id" name="message.id" value="${message.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>内容</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="message_content" class="inpy" name="message.content" type="text" value="${message.content }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>发件人</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="message_fromStaff_id" class="inpy" name="message.fromStaff.id"
						type="text" value="${message.fromStaff.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>日期</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input readonly id="message_send_date" class="inpy" name="message.send_date" 
						type="text" value="<fmt:formatDate value="${message.send_date }" pattern="yyyy-MM-dd HH:mm:ss" />" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>收件in,发件 out,</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="message_send_type" class="inpy" name="message.send_type" type="text" value="${message.send_type }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>已读,未读</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="message_sts" class="inpy" name="message.sts" type="text" value="${message.sts }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>标题</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="message_title" class="inpy" name="message.title" type="text" value="${message.title }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>收件人</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="message_toStaff_id" class="inpy" name="message.toStaff.id"
						type="text" value="${message.toStaff.id }" />
				</td>
			</tr>
	</table>
</form>