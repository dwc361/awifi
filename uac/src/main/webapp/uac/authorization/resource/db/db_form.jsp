<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#dbForm").validate({
			rules : {
				"db.app.id" : {
					number : true,
					required : true
				}, 
				"db.dbType.id" : {
					required : true
				},
				"db.identify" : {
					required : true
				}, 
				"db.ip" : {
					required : true
				}, 
				"db.modifytime" : {
					required : true
				}, 
				"db.name" : {
					required : true
				}, 
				"db.port" : {
					required : true
				}, 
				"db.state.id" : {
					required : true
				}
			}
		});

		$("#dbForm").ajaxForm({
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
			$("#dbForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#dbForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("db","db.id",$(this).attr("href"));
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
					$("#db_app_id").val(id);
					$("#db_app_name").val(name);
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
<input type="hidden" id="db_paramString" value="${db_paramString }" />
<form id="dbForm" name="dbForm" method="post" action="${submit_url }">
	<input type="hidden" id="db_id" name="db.id" value="${db.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>数据库类型</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="db.dbType.id" id="db_dbType_id">
						<c:forEach var="obj" items="${dbTypeList}">
							<c:choose>
								<c:when test="${obj.id == db.dbType.id}">
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
					<span>所属应用</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="db_app_id" class="inpy" name="db.app.id" type="hidden" value="${db.app.id }" />
					<input id="db_app_name" class="inpy" name="db.app.name" readonly type="text" value="${db.app.name }" />&nbsp;
					<a id="app_select_btn" class="btn" href="javascript:void(0)"><i class="icon-search"></i></a>
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>所属域</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="db.group.id" id="db_group_id">
						<c:forEach var="obj" items="${regionList}">
							<c:choose>
								<c:when test="${(obj.id == db.group.id) || (obj.id == db_group_id)}">
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
					<input id="db_ip" class="inpy" name="db.ip" type="text" value="${db.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>系统名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="sys_name" class="inpy" name="db.name" type="text" value="${db.name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>数据库名</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="db_name" class="inpy" name="db.db_name" type="text" value="${db.db_name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>主机类型</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="db.typename.id" id="db_typename_id">
						<c:forEach var="obj" items="${typenameList}">
							<c:choose>
								<c:when test="${obj.id == db.typename.id}">
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
					<input id="db_port" class="inpy" name="db.port" type="text" value="${db.port }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>状态</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="db.state.id" id="db_state_id">
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == db.state.id}">
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
					<textarea id="db_describe" class="inpy" name="db.describe" rows="3">${db.describe }</textarea>
				</td>
			</tr>
	</table>
</form>