<#assign key = primaryKey[0] />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${includeBase}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${includeBase}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		<#list fields as field>
			<#if (field.dbType == "DATE")>
			$.createGooCalendar("${alias}_${field.fieldName}",ROOF.Utils.getCalendarProperty());
			</#if>
		</#list>

		$("#${alias}Form").validate({
			rules : {
			<#list fields as field>
			<#if field.fieldName != key>
			<#assign isForeign = false />
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
				"${alias}.${relation.alias}.${relation.primaryCol}" : {
					required : true
				}<#if (field_index != (fields?size-1))>, </#if>
				</#if>
				</#list>
				<#if (!(isForeign))>
				<#if (field.dbType == "NUMBER")>
				"${alias}.${field.fieldName}" : {
					number : true,
					required : true
				}<#if (field_index != (fields?size-1))>, </#if>
				<#else>
				"${alias}.${field.fieldName}" : {
					required : true
				}<#if (field_index != (fields?size-1))>, </#if>
				</#if>
				</#if>
			</#if>
			</#list>
			}
		});

		$("#${alias}Form").ajaxForm({
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
			$("#${alias}Form").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#${alias}Form"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("${alias}","${alias}.${key}",$(this).attr("href"));
		});
	});
</script>
<!--
<select class="inpy" name="user.scope.id" id="">
		<c:forEach var="obj" items="${el$}{list}">
			<c:choose>
				<c:when test="${el$}{obj.id == user.scope.id}">
					<option value="${el$}{obj.id }" selected="selected">${el$}{obj.text }</option>
				</c:when>
				<c:otherwise>
					<option value="${el$}{obj.id }">${el$}{obj.text }</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
</select>
-->
<input type="hidden" id="${alias}_paramString" value="${el$}{${alias}_paramString }" />
<form id="${alias}Form" name="${alias}Form" method="post" action="${el$}{submit_url }">
	<input type="hidden" id="${alias}_${key}" name="${alias}.${key}" value="${el$}{${alias}.${key} }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
	<#list fields as field>
			<#assign isForeign = false />
			<#if field.fieldName != key>
			<tr>
				<th style="width: 30%">
					<span>${(field.fieldDisplay)!''}</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
				<#if type != "detail" >
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
					<input id="${alias}_${relation.alias}_${relation.primaryCol}" class="inpy" name="${alias}.${relation.alias}.${relation.primaryCol}"
						type="text" value="${el$}{${alias}.${relation.alias}.${relation.primaryCol} }" />
				</#if>
				</#list>
				<#if (!(isForeign))>
				<#if (field.dbType == "DATE")>
					<input readonly id="${alias}_${field.fieldName}" class="inpy" name="${alias}.${field.fieldName}" 
						type="text" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" />" />
				<#else>
					<input id="${alias}_${field.fieldName}" class="inpy" name="${alias}.${field.fieldName}" type="text" value="${el$}{${alias}.${field.fieldName} }" />
				</#if>
				</#if>
				<#else>
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
				${el$}{${alias}.${relation.alias}.${relation.primaryCol} }
				</#if>
				</#list>
				<#if (!(isForeign))>
				<#if (field.dbType == "DATE")>
				<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" />
				<#else>
				${el$}{${alias}.${field.fieldName} }
				</#if>
				</#if>
				</#if>
				</td>
			</tr>
			</#if>
		</#list>
	</table>
</form>