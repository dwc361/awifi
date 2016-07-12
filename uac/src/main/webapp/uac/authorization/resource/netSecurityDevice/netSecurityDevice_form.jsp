<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#netSecurityDeviceForm").validate({
			rules : {
				"netSecurityDevice.app_id" : {
					number : true,
					required : true
				}, 
				"netSecurityDevice.identify" : {
					required : true
				}, 
				"netSecurityDevice.ip" : {
					required : true
				}, 
				"netSecurityDevice.modifytime" : {
					required : true
				}, 
				"netSecurityDevice.name" : {
					required : true
				}, 
				"netSecurityDevice.port" : {
					required : true
				}, 
				"netSecurityDevice.state.id" : {
					required : true
				}
			}
		});

		$("#netSecurityDeviceForm").ajaxForm({
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
			$("#netSecurityDeviceForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#netSecurityDeviceForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("netSecurityDevice","netSecurityDevice.id",$(this).attr("href"));
		});
		
		$("#app_select_btn").button().click(function(event) {
			$("iframe[name='select_iframe']").attr('src', "uac_authorization_appAction!list.action");
			$("#select_dialog").dialog("open");
		});
		$("#select_dialog").dialog({
			autoOpen : false,
			width : 700,
			height : 390,
			modal : true,
			resizable : false,
			buttons : {
				"确定" : function() {
					$(this).dialog("close");
					var trs = select_iframe.window.getSelected();
					var name = $(trs[0]).find("td:eq(2)").text();
					var id = $(trs[0]).find(":input:checked").val();
					if (!id) {
						alert('请选择一行记录!');
						return;
					}
					$("#netSecurityDevice_app_id").val(id);
					$("#netSecurityDevice_app_name").val(name);
					$("iframe[name='select_iframe']").attr('src', '')
				},
				"取消" : function() {
					$(this).dialog("close");
					$("iframe[name='select_iframe']").attr('src', '')
				}
			}
		});
	});
</script>
<div id="select_dialog" title="请选择">
	<iframe name="select_iframe" frameborder="0" scrolling="auto" width="100%" height="340"></iframe>
</div>
<input type="hidden" id="netSecurityDevice_paramString" value="${netSecurityDevice_paramString }" />
<form id="netSecurityDeviceForm" name="netSecurityDeviceForm" method="post" action="${submit_url }">
	<input type="hidden" id="netSecurityDevice_id" name="netSecurityDevice.id" value="${netSecurityDevice.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>所属应用</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netSecurityDevice_app_id" class="inpy" name="netSecurityDevice.app.id" type="hidden" value="${netSecurityDevice.app.id }" />
					<input id="netSecurityDevice_app_name" class="inpy" name="netSecurityDevice.app.name" readonly type="text" value="${netSecurityDevice.app.name }" />&nbsp;
					<a id="app_select_btn" class="btn" href="javascript:void(0)"><i class="icon-search"></i></a>
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>ip地址</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netSecurityDevice_ip" class="inpy" name="netSecurityDevice.ip" type="text" value="${netSecurityDevice.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netSecurityDevice_name" class="inpy" name="netSecurityDevice.name" type="text" value="${netSecurityDevice.name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>端口号</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netSecurityDevice_port" class="inpy" name="netSecurityDevice.port" type="text" value="${netSecurityDevice.port }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>状态</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="netSecurityDevice.state.id" id="netSecurityDevice_state_id">
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == netSecurityDevice.state.id}">
									<option value="${obj.id }" selected="selected">${obj.text }</option>
								</c:when>
								<c:otherwise>
									<option value="${obj.id }">${obj.text }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>描述</span>：
				</th>
				<td style="width: 70%">
					<textarea id="netSecurityDevice_describe" class="inpy" name="netSecurityDevice.describe" rows="3">${netSecurityDevice.describe }</textarea>
				</td>
			</tr>
	</table>
</form>