<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#netDeviceForm").validate({
			rules : {
				"netDevice.app_id" : {
					number : true,
					required : true
				}, 
				"netDevice.identify" : {
					required : true
				}, 
				"netDevice.ip" : {
					required : true
				}, 
				"netDevice.modifytime" : {
					required : true
				}, 
				"netDevice.name" : {
					required : true
				}, 
				"netDevice.port" : {
					required : true
				}, 
				"netDevice.state.id" : {
					required : true
				}
			}
		});

		$("#netDeviceForm").ajaxForm({
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
			$("#netDeviceForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#netDeviceForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("netDevice","netDevice.id",$(this).attr("href"));
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
					$("#netDevice_app_id").val(id);
					$("#netDevice_app_name").val(name);
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
<input type="hidden" id="netDevice_paramString" value="${netDevice_paramString }" />
<form id="netDeviceForm" name="netDeviceForm" method="post" action="${submit_url }">
	<input type="hidden" id="netDevice_id" name="netDevice.id" value="${netDevice.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			 <tr>
				<th style="width: 30%">
					<span>所属应用</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netDevice_app_id" class="inpy" name="netDevice.app.id" type="hidden" value="${netDevice.app.id }" />
					<input id="netDevice_app_name" class="inpy" name="netDevice.app.name" readonly type="text" value="${netDevice.app.name }" />&nbsp;
					<a id="app_select_btn" class="btn" href="javascript:void(0)"><i class="icon-search"></i></a>
				</td>
			</tr> 
			<tr>
				<th style="width: 30%">
					<span>所属域</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="netDevice.group.id" id="netDevice_group_id">
						<c:forEach var="obj" items="${regionList}">
							<c:choose>
								<c:when test="${(obj.id == netDevice.group.id) || (obj.id == netDevice_group_id)}">
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
					<span>ip地址</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netDevice_ip" class="inpy" name="netDevice.ip" type="text" value="${netDevice.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netDevice_name" class="inpy" name="netDevice.name" type="text" value="${netDevice.name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>主机类型</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="netDevice.typename.id" id="netDevice_typename_id">
						<c:forEach var="obj" items="${typenameList}">
							<c:choose>
								<c:when test="${obj.id == netDevice.typename.id}">
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
					<span>服务名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="host_name" class="inpy" name="netDevice.serve_name" type="text" value="${netDevice.serve_name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>服务类型</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="netDevice.serve_type.id" id="netDevice_serve_type_id">
						<c:forEach var="obj" items="${servetypeList}">
							<c:choose>
								<c:when test="${obj.id == netDevice.serve_type.id}">
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
					<span>端口号</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="netDevice_port" class="inpy" name="netDevice.port" type="text" value="${netDevice.port }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>状态</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="netDevice.state.id" id="netDevice_state_id">
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == netDevice.state.id}">
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
					<textarea id="netDevice_sysdescribe" class="inpy" name="netDevice.sysdescribe" rows="3">${netDevice.sysdescribe }</textarea>
				</td>
			</tr>
	</table>
</form>