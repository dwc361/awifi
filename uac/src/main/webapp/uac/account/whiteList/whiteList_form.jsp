<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<script type="text/javascript">
	$(document).ready(function() {
		$("input[name='whiteList.startDate']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("input[name='whiteList.endDate']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("#user_select_dialog").dialog({
			autoOpen : false,
			width : 800,
			height : 460,
			modal : true,
			resizable : false,
			buttons : {
				"确定" : function() {
					var trs = user_select_iframe.window.getSelected();
					var name = $(trs[0]).find("td:eq(2)").text();
					var id = $(trs[0]).find(":input:checked").val();
					var username = $(trs[0]).find("td:eq(3)").text();
					if (!id) {
						alert('请选择一行记录!');
						return;
					}
					$("#whiteList_account").val(username);
					$("#whiteList_name").val(name);
					$("#whiteList_user_id").val(id);
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#user_select_btn").button().click(function(event) {
			$("#user_select_dialog").dialog("open");
		});

		$("#whiteListForm").validate({
			rules : {
				"whiteList.account" : {
					required : true
				},
				"whiteList.enddate" : {
					required : true
				},
				"whiteList.startdate" : {
					required : true
				}
			}
		});

		$("#whiteListForm").ajaxForm({
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
			$("#whiteListForm").submit();
		});

		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#whiteListForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("whiteList", "whiteList.id", $(this).attr("href"));
		});
	});
</script>
<input type="hidden" id="whiteList_paramString" value="${whiteList_paramString }" />
<form id="whiteListForm" name="whiteListForm" method="post" action="${submit_url }">
	<input type="hidden" id="whiteList_id" name="whiteList.id" value="${whiteList.id }" /> <input type="hidden"
		id="whiteList_user_id" name="whiteList.user.id" value="${whiteList.user.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
		<tr>
			<th style="width: 30%"><span>主账号</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input id="whiteList_account" readonly="readonly" class="inpy" name="whiteList.account"
				type="text" value="${whiteList.user.username }" />&nbsp;<a id="user_select_btn" class="btn" href="#"> <i
					class="icon-search"></i></a></td>
			</a>
		</tr>
		<tr>
			<th style="width: 30%"><span>姓名</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input id="whiteList_name" readonly="readonly" class="inpy" type="text"
				value="${whiteList.user.name }" /></td>
			</a>
		</tr>
		<tr>
			<th style="width: 30%"><span>开始日期</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input readonly id="whiteList_startDate" class="inpy" name="whiteList.startDate"
				type="text" value="<fmt:formatDate value="${whiteList.startDate }" pattern="yyyy-MM-dd" />" /></td>
		</tr>
		<tr>
			<th style="width: 30%"><span>结束日期</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input readonly id="whiteList_endDate" class="inpy" name="whiteList.endDate" type="text"
				value="<fmt:formatDate value="${whiteList.endDate }" pattern="yyyy-MM-dd" />" /></td>
		</tr>

	</table>
</form>
<div id="user_select_dialog" title="请选择主账号">
	<iframe name="user_select_iframe" src="${basePath }/uac_account_userAction!list.action?unEditable=false"
		frameborder="0" scrolling="auto" width="100%" height="340"></iframe>
</div>