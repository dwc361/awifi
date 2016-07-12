<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#passwordPolicyForm").validate({
			rules : {}
		});

		$("#passwordPolicyForm").ajaxForm({
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
			$("#passwordPolicyForm").submit();
			return false;
		});

		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#passwordPolicyForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("passwordPolicy", "passwordPolicy.id");
		});
	});
</script>
<form id="passwordPolicyForm" name="passwordPolicyForm" method="post"
	action="${basePath}/uac_account_passwordAction!update.action">
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
		<c:forEach var="passwordPolicy" items="${passwordPolicies }" varStatus="status">
			<tr>
				<th style="width: 30%">${passwordPolicy.nameCn }：</th>
				<td style="width: 70%"><input type="hidden" name="passwordPolicies[${status.index }].id"
					value="${passwordPolicy.id }" /> <input class="inpy" name="passwordPolicies[${status.index }].val" type="text"
					value="${passwordPolicy.val }" />${passwordPolicy.unit }</td>
			</tr>
		</c:forEach>
	</table>
</form>