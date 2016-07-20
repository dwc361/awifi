<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<script type="text/javascript">
	$(document).ready(function() {
		$("input[name='whiteIpList.startDate']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("input[name='whiteIpList.endDate']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		


		$("#whiteipListForm").validate({
			rules : {
				"whiteIpList.nameIP" : {
					required : true
				},
				"whiteIpList.whiteIP" : {
					required : true
				},
				"whiteIpList.enddate" : {
					required : true
				},
				"whiteIpList.startdate" : {
					required : true
				}
			}
		});

		$("#whiteipListForm").ajaxForm({
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
			$("#whiteipListForm").submit();
		});

		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#whiteipListForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("whiteipList", "whiteipList.id", $(this).attr("href"));
		});
	});
</script>
<input type="hidden" id="whiteipList_paramString" value="${whiteipList_paramString }" />
<form id="whiteipListForm" name="whiteipListForm" method="post" action="${submit_url }">
	<input type="hidden" id="whiteList_id" name="whiteIpList.id" value="${whiteIpList.id }" /> 
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
		<tr>
			<th style="width: 30%"><span>IP所属部门</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input id="whiteList_nameIP" class="inpy" name="whiteIpList.nameIP"
				type="text" value="${whiteIpList.nameIP }" />
			</a>
		</tr>
		<tr>
			<th style="width: 30%"><span>IP</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input id="whiteIpList_whiteIP"  class="inpy" type="text" name = "whiteIpList.whiteIP"
				value="${whiteIpList.whiteIP}" /></td>
			</a>
		</tr>
		<tr>
			<th style="width: 30%"><span>开始日期</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input readonly id="whiteList_startDate" class="inpy" name="whiteIpList.startDate"
				type="text" value="<fmt:formatDate value="${whiteIpList.startDate }" pattern="yyyy-MM-dd" />" /></td>
		</tr>
		<tr>
			<th style="width: 30%"><span>结束日期</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input readonly id="whiteList_endDate" class="inpy" name="whiteIpList.endDate" type="text"
				value="<fmt:formatDate value="${whiteIpList.endDate }" pattern="yyyy-MM-dd" />" /></td>
		</tr>

	</table>
</form>
