<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#favoritesForm").validate({
			rules : {
				"favorites.app.id" : {
					required : true
				}, 
				"favorites.subUser.id" : {
					required : true
				}
			}
		});

		$("#favoritesForm").ajaxForm({
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
			$("#favoritesForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#favoritesForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("favorites","favorites.id",$(this).attr("href"));
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
<input type="hidden" id="favorites_paramString" value="${favorites_paramString }" />
<form id="favoritesForm" name="favoritesForm" method="post" action="${submit_url }">
	<input type="hidden" id="favorites_id" name="favorites.id" value="${favorites.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>系统名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="favorites_app_id" class="inpy" name="favorites.app.id"
						type="text" value="${favorites.app.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>子账号</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="favorites_subUser_id" class="inpy" name="favorites.subUser.id"
						type="text" value="${favorites.subUser.id }" />
				</td>
			</tr>
	</table>
</form>