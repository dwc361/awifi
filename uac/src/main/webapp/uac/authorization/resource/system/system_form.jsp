<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#systemForm").validate({
			rules : {
				"system.app.id" : {
					required : true
				}, 
				"system.sysdescribe" : {
					required : true
				}, 
				"system.identify" : {
					required : true
				}, 
				"system.ip" : {
					required : true
				}, 
				"system.name" : {
					required : true
				}, 
				"system.port" : {
					required : true
				}, 
				"system.state.id" : {
					required : true
				}
			}
		});

		$("#systemForm").ajaxForm({
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
			$("#systemForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#systemForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("system","system.id",$(this).attr("href"));
		});
	});
</script>
<input type="hidden" id="system_paramString" value="${system_paramString }" />
<form id="systemForm" name="systemForm" method="post" action="${submit_url }">
	<input type="hidden" id="system_id" name="system.id" value="${system.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>所属应用</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="system_app_id" class="inpy" name="system.app.id"
						type="text" value="${system.app.id }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span></span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="system_sysdescribe" class="inpy" name="system.sysdescribe" type="text" value="${system.sysdescribe }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span></span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="system_identify" class="inpy" name="system.identify" type="text" value="${system.identify }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>ip地址</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="system_ip" class="inpy" name="system.ip" type="text" value="${system.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span></span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="system_name" class="inpy" name="system.name" type="text" value="${system.name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>端口号</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="system_port" class="inpy" name="system.port" type="text" value="${system.port }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span></span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="system_state_id" class="inpy" name="system.state.id"
						type="text" value="${system.state.id }" />
				</td>
			</tr>
	</table>
</form>