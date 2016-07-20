<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#hostForm").validate({
			rules : {
				"host.app_id" : {
					number : true,
					required : true
				}, 
				"host.identify" : {
					required : true
				}, 
				"host.ip" : {
					required : true
				}, 
				"host.modifytime" : {
					required : true
				}, 
				"host.name" : {
					required : true
				}, 
				"host.port" : {
					required : true
				}, 
				"host.state_id" : {
					number : true,
					required : true
				}
			}
		});

		$("#hostForm").ajaxForm({
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
			$("#hostForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#hostForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("host","host.id",$(this).attr("href"));
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
					$("#host_app_id").val(id);
					$("#host_app_name").val(name);
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
<input type="hidden" id="host_paramString" value="${host_paramString }" />
<form id="hostForm" name="hostForm" method="post" action="${submit_url }">
	<input type="hidden" id="host_id" name="host.id" value="${host.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			 <tr>
				<th style="width: 30%">
					<span>所属应用</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="host_app_id" class="inpy" name="host.app.id" type="hidden" value="${host.app.id }" />
					<input id="host_app_name" class="inpy" name="host.app.name" readonly type="text" value="${host.app.name }" />&nbsp;
					<a id="app_select_btn" class="btn" href="javascript:void(0)"><i class="icon-search"></i></a>
				</td>
			</tr> 
			<tr>
				<th style="width: 30%">
					<span>所属域</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="host.group.id" id="host_group_id">
						<c:forEach var="obj" items="${regionList}">
							<c:choose>
								<c:when test="${(obj.id == host.group.id) || (obj.id == host_group_id)}">
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
					<input id="host_ip" class="inpy" name="host.ip" type="text" value="${host.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>系统名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="sys_name" class="inpy" name="host.name" type="text" value="${host.name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>主机名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="host_name" class="inpy" name="host.host_name" type="text" value="${host.host_name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>主机类型</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="host.typename.id" id="host_typename_id">
						<c:forEach var="obj" items="${typenameList}">
							<c:choose>
								<c:when test="${obj.id == host.typename.id}">
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
					<input id="host_name" class="inpy" name="host.serve_name" type="text" value="${host.serve_name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>服务类型</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="host.serve_type.id" id="host_serve_type_id">
						<c:forEach var="obj" items="${servetypeList}">
							<c:choose>
								<c:when test="${obj.id == host.serve_type.id}">
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
					<input id="host_port" class="inpy" name="host.port" type="text" value="${host.port }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>状态</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="host.state.id" id="host_state_id">
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == host.state.id}">
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
					<textarea id="host_sysdescribe" class="inpy" name="host.sysdescribe" rows="3">${host.sysdescribe }</textarea>
				</td>
			</tr>
	</table>
</form>