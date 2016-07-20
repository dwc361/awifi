<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.parent.name }</title>
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${basePath}/roof-web/web/js/ROOF.SelectableTable.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#passwordPolicyForm"));
		});
		
		$("#searchBtn").click(function() {
			$("#passwordPolicyForm").submit();
		});
		
		$("#createBtn").click(function() {
			$("#passwordPolicyForm").attr("action",$(this).attr('href') + '?passwordPolicy.id=' + 1);
			$("#passwordPolicyForm").submit();
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#passwordPolicyForm").attr("action",$(this).attr('href') + '?passwordPolicy.id=' + id);
				$("#passwordPolicyForm").submit();
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
						'passwordPolicy.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#passwordPolicyForm").submit();
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
<c:if test="${user.enabled == true}">
	有效
</c:if>
<c:if test="${user.enabled == false}">
	失效
</c:if>
-->
<form id="passwordPolicyForm" action="${basePath}/uac_account_passwordAction!list.action" method="post">
	<div class="bread">
		您的位置：<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; <a href="javascript:void(0)" id="currNode">${module.parent.name }</a>
	</div>
	<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
			<th style="width: 15%">英文表示：</th>
			<td style="width: 35%">
				 <input type="text" id="passwordPolicy_name" name="passwordPolicy.name" class="inpy" value='${passwordPolicy.name }'/>
			</td>
			<th style="width: 15%">判断值：</th>
			<td style="width: 35%">
				 <input type="text" id="passwordPolicy_val" name="passwordPolicy.val" class="inpy" value='${passwordPolicy.val }'/>
			</td>
			</tr>
			</table>
		<div class="btnBox">
			<a id="resetBtn" href="javascript:void(0)">
				<p class="sBtn">重置</p>
			</a> 
			<a id="searchBtn" href="javascript:void(0)">
				<p class="sBtn">查询</p>
			</a> 
			<a id="deleteBtn" href="${basePath}/uac_account_passwordAction!delete.action">
				<p class="sBtn">删除</p>
			</a>
			<a id="updateBtn" href="${basePath}/uac_account_passwordAction!update_page.action">
				<p class="sBtn">修改</p>
			</a>
			<a id="createBtn" href="${basePath}/uac_account_passwordAction!create_page.action">
				<p class="sBtn">新增</p>
			</a>
		</div>
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th>
					&nbsp;
				</th>
				<th>
					提示
				</th>
				<th>
					英文表示
				</th>
				<th>
					判断值
				</th>
			</tr>
			<c:forEach var="passwordPolicy" items="${page.dataList }" varStatus="status">
				<tr>
					<td align="center">
					<input name="passwordPolicyList.id" id="passwordPolicy_id_${status.index + 1 }" type="checkbox" value="${passwordPolicy.id }">
					</td>
					<td align="center">
						${passwordPolicy.message }
					</td>
					<td align="center">
						${passwordPolicy.name }
					</td>
					<td align="center">
						${passwordPolicy.val }
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</div>
	<c:set var="pageForm" value="passwordPolicyForm" />
	<%@include file="/uac/uac_page_bar.jsp"%>
</form>
</body>
</html>