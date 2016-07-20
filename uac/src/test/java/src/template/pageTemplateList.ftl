<#assign key = primaryKey[0] />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${el$}{module.parent.name }</title>
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${includeBase}/roof-web/web/js/ROOF.SelectableTable.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#${alias}Form"));
		});
		
		$("#searchBtn").click(function() {
			$("#${alias}Form").submit();
		});
		
		$("#createBtn").click(function() {
			$("#${alias}Form").attr("action",$(this).attr('href'));
			$("#${alias}Form").submit();
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#${alias}Form").attr("action",$(this).attr('href') + '?${alias}.${key}=' + id);
				$("#${alias}Form").submit();
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});
		
		$('#deleteBtn').click(function() {
			if (confirm("确定要删除吗？")) {
				var id = table.getSeleted();
				if (id) {
					ROOF.Utils.showBlock();
					$.post($(this).attr('href'), {
						'${alias}.${key}' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#${alias}Form").submit();
					}, 'json');
				} else {
					alert('请选择一行记录!');
				}
			}
			return false;
		});
	});
</script>
</head>
<body>
<!--
<c:if test="${el$}{user.enabled == true}">
	有效
</c:if>
<c:if test="${el$}{user.enabled == false}">
	失效
</c:if>
-->
<form id="${alias}Form" action="${includeBase}/${actionName}Action!list.action" method="post">
	<div class="">
		您的位置：
		<a href="javascript:void(0)" id="parNode">${el$}{module.parent.parent.name }</a> &gt; 
		<a href="javascript:void(0)" id="currNode">${el$}{module.parent.name }</a>
	</div>
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<#assign rows = 2 />
			<#assign remainder = 0 />
			<#list fields as field>
			<#assign isForeign = false />
			<#if field.fieldName != key>
			<#if (field_index % rows == 0) >
			<#assign remainder = fields?size % rows  />
			<tr>
			</#if>
			<th style="width: 15%">${(field.fieldDisplay)!''}：</th>
			<td style="width: 35%">
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
				<input type="text" id="${alias}_${relation.alias}_${relation.primaryCol}" name="${alias}.${relation.alias}.${relation.primaryCol}" class="inpy" value='${el$}{${alias}.${relation.alias}.${relation.primaryCol} }'/>
				</#if>
				</#list>
				<#if (!(isForeign))>
				 <input type="text" id="${alias}_${field.fieldName}" name="${alias}.${field.fieldName}" class="inpy" <#if (field.dbType == "DATE")>readonly value='<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />'<#else>value='${el$}{${alias}.${field.fieldName} }'</#if>/>
				</#if>
			</td>
			<#if (field_index % rows == (rows-1)) >
			</tr>
			</#if>
			</#if>
			</#list>
			<#list (remainder+1)..rows as i >
			<th style="width: 15%">&nbsp;</th><td style="width: 35%">&nbsp;</td>
			</#list>
			<#if remainder != (rows-1)></tr></#if>
			</table>
		<div class="btnBox">
			<a id="resetBtn" href="javascript:void(0)">
				<p class="sBtn">重置</p>
			</a> 
			<a id="searchBtn" href="javascript:void(0)">
				<p class="sBtn">查询</p>
			</a> 
			<a id="deleteBtn" href="${includeBase}/${actionName}Action!delete.action">
				<p class="sBtn">删除</p>
			</a>
			<a id="updateBtn" href="${includeBase}/${actionName}Action!update_page.action">
				<p class="sBtn">修改</p>
			</a>
			<a id="createBtn" href="${includeBase}/${actionName}Action!create_page.action">
				<p class="sBtn">新增</p>
			</a>
		</div>
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th>
					&nbsp;
				</th>
				<#list fields as field>
				<#if field.fieldName != key>
				<th>
					${(field.fieldDisplay)!''}
				</th>
				</#if> 
				</#list>
			</tr>
			<c:forEach var="${alias}" items="${el$}{page.dataList }" varStatus="status">
				<tr>
					<td align="center">
					<input name="${alias}List.${key}" id="${alias}_id_${el$}{status.index + 1 }" type="checkbox" value="${el$}{${alias}.${key} }">
					</td>
					<#list fields as field>
					<#assign isForeign = false />
					<#if field.fieldName != key>
					<td align="center">
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
					</td>
					</#if>
					</#list>
				</tr>
			</c:forEach>
		</table>
	</div>
	<c:set var="pageForm" value="${alias}Form" />
	<%@include file="/roof-web/page_bar.jsp"%>
</form>
</body>
</html>