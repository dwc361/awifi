<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#sysResource_select_btn").button().click(
						function(event) {
							var src_type = $("#src_type_select").val();
							if (src_type == "App") {
								$("iframe[name='sysResource_select_iframe']").attr('src',
										"uac_authorization_appAction!list.action");
							}
							if (src_type == "System") {
								var sys_src_type = $("#sys_src_type_select").val();
								if (sys_src_type == "Os") {//操作系统
									$("iframe[name='sysResource_select_iframe']").attr('src',
											"uac_authorization_system_osAction!list.action");
								}
								if (sys_src_type == "Db") {//数据库
									$("iframe[name='sysResource_select_iframe']").attr('src',
											"uac_authorization_system_dbAction!list.action");
								}
								if (sys_src_type == "NetDevice") {//网络设备
									$("iframe[name='sysResource_select_iframe']").attr('src',
											"uac_authorization_system_netDeviceAction!list.action");
								}
								if (sys_src_type == "NetSecurityDevice") {//安全设备
									$("iframe[name='sysResource_select_iframe']").attr('src',
											"uac_authorization_system_netSecurityDeviceAction!list.action");
								}
								if (sys_src_type == "Host") {//主机
									$("iframe[name='sysResource_select_iframe']").attr('src',
											"uac_authorization_system_hostAction!list.action");
								}
							}
							$("#sysResource_select_dialog").dialog("open");
						});

				$("#sysResource_select_dialog").dialog({
					autoOpen : false,
					width : 800,
					height : 460,
					modal : true,
					resizable : false,
					buttons : {
						"确定" : function() {
							$(this).dialog("close");
							var trs = sysResource_select_iframe.window.getSelected();
							var name = $(trs[0]).find("td:eq(1)").text();
							var id = $(trs[0]).find(":input:checked").val();
							if (!id) {
								alert('请选择一行记录!');
								return;
							}
							$("#subUser_sysResource_id").val(id);
							$("#subUser_sysResource_name").val(name);
							$("iframe[name='sysResource_select_iframe']").attr('src', '')
						},
						"取消" : function() {
							$(this).dialog("close");
							$("iframe[name='sysResource_select_iframe']").attr('src', '')
						}
					}
				});
				$("input[name='subUser.pwdDisableTime']").datepicker({
					changeMonth : true,
					changeYear : true
				});
				$("#subUserForm").validate({
					rules : {
						'subUser.name' : {
							required : true
						},
						'subUser.username' : {
							required : true
						/* 	remote : {
								url : "uac_account_userAction!validateUsername.ajax",
								type : "post",
								dataType : "json",
								data : {
									"username" : function() {
										return $("input[name='user.username']").val();
									}
								}
							} */
						},
						'subUser.password' : {
							required : true
						},
						'subUser.repassword' : {
							required : true,
							equalTo : "#subUser_password"
						}
					},
					messages : {
						"subUser.username" : {
							remote : "该用户名已被使用"
						},
						'subUser.repassword' : {
							equalTo : "两次输入的密码不相同"
						}
					}
				});

				$("#subUserForm").ajaxForm({
					type : 'post',
					cache : false,
					dataType : 'json',
					error : function() {
						alert('Ajax信息加载出错,请重试');
					},
					success : function(replay) {
						alert(replay.message);
						$("#subUser_staff_id").val(replay.data);
						document.subUserForm.attributes["action"].value  = "uac_account_subUserAction!update.ajax";
					}
				});

				$("#saveBtn").click(function() {
					if($('#reuseBtn').length>0){
						alert("请先启用账号");
						return false;
					};
					$("#subUserForm").submit();
					return false;
				});

				$("#resetBtn").click(function() {
					ROOF.Utils.emptyForm($("#subUserForm"));
					return false;
				});

				$("#backBtn").click(function() {
					window.location.href = $(this).attr("href");
					return false;
				});

				$("#src_type_select").change(function() {
					if ($(this).val() == "App") {
						$("#sys_src_type_select_tr").hide();
						$("#subUser_password_tr").hide();
						$("#subUser_repassword_tr").hide();
					}
					if ($(this).val() == "System") {
						$("#sys_src_type_select_tr").show();
						$("#subUser_password_tr").show();
						$("#subUser_repassword_tr").show();
					}
				});

				$("#src_type_select").trigger('change');

				$("select[name='subUser.scope.id']").change(function() {
					if ($(this).val() == 53) {
						$('#subUser_pwdDisableTime_tr').show();
					}

					if ($(this).val() == 52) {
						$('#subUser_pwdDisableTime_tr').hide();
						$('input[name="subUser.pwdDisableTime"]').val('');
					}
				});
				$("select[name='subUser.scope.id']").trigger('change');
			});
</script>
<input type="hidden" id="subUser_paramString" value="${subUser_paramString }" />
<form id="subUserForm" name="subUserForm" method="post" action="${submit_url }">
	<input type="hidden" id="subUser_staff_id" name="subUser.id" value="${subUser.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
		<tr>
			<th style="width: 30%"><span>资源类型</span><font color="red">*</font>：</th>
			<td style="width: 70%"><select id="src_type_select" class="inpy">
					<c:if test="${isApp }">
						<option value="App" selected="selected">应用资源</option>
						<option value="System">系统资源</option>
					</c:if>
					<c:if test="${!isApp && not empty isApp }">
						<option value="App">应用资源</option>
						<option value="System" selected="selected">系统资源</option>
					</c:if>
					<c:if test="${empty isApp}">
						<option value="App" selected="selected">应用资源</option>
						<option value="System" >系统资源</option>
					</c:if>
					
			</select></td>
		</tr>
		<tr id="sys_src_type_select_tr">
			<th style="width: 30%"><span>系统资源类型</span><font color="red">*</font>：</th>
			<td style="width: 70%"><select id="sys_src_type_select" class="inpy">
					<c:choose>
						<c:when test="${currType == 'Os'}">
							<option value="Os" selected="selected">操作系统</option>
						</c:when>
						<c:otherwise>
							<option value="Os">操作系统</option>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${currType == 'Db'}">
							<option value="Db" selected="selected">数据库</option>
						</c:when>
						<c:otherwise>
							<option value="Db">数据库</option>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${currType == 'NetDevice'}">
							<option value="NetDevice" selected="selected">网络设备</option>
						</c:when>
						<c:otherwise>
							<option value="NetDevice">网络设备</option>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${currType == 'NetSecurityDevice'}">
							<option value="NetSecurityDevice" selected="selected">安全设备</option>
						</c:when>
						<c:otherwise>
							<option value="NetSecurityDevice">安全设备</option>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${currType == 'Host'}">
							<option value="Host" selected="selected">主机</option>
						</c:when>
						<c:otherwise>
							<option value="Host">主机</option>
						</c:otherwise>
					</c:choose>
			</select></td>
		</tr>
		<tr>
			<th style="width: 30%"><span>所属资源</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input id="subUser_sysResource_id" class="inpy" name="subUser.sysResource.id"
				type="hidden" value="${subUser.sysResource.id }" /><input id="subUser_sysResource_name" class="inpy"
				name="subUser.sysResource.name" readonly="readonly" type="text" value="${subUser.sysResource.name }" />&nbsp;<a
				id="sysResource_select_btn" class="btn" href="#"> <i class="icon-search"></i></a></td>
		</tr>
		<tr>
			<th style="width: 30%"><span></span><font color="red">*</font>子账号：<input type="hidden" name="subUser.user.id"
				value="${user_id }" /></th>
			<td style="width: 70%"><input id="subUser_username" class="inpy" name="subUser.username" type="text"
				value="${subUser.username }" /></td>
		</tr>
		
			<tr id="subUser_password_tr">
				<th style="width: 30%"><span></span><font color="red">*</font>密码：</th>
				<td style="width: 70%"><input id="subUser_password" class="inpy" name="subUser.password" type="password"
					value="${subpwd}" /></td>
			</tr>
			<tr id="subUser_repassword_tr">
				<th style="width: 30%"><span></span><font color="red">*</font>重复密码：</th>
				<td style="width: 70%"><input class="inpy" name="subUser.repassword" type="password" value="${subpwd }" /></td>
			</tr>
		
		<tr>
			<th style="width: 30%"><span>权限类别</span><font color="red">*</font>：</th>
			<td style="width: 70%"><select class="inpy" name="subUser.privilege.id">
					<c:forEach var="privilege" items="${userPrivilegs}">
						<c:choose>
							<c:when test="${privilege.id == subUser.privilege.id}">
								<option value="${privilege.id }" selected="selected">${privilege.text }</option>
							</c:when>
							<c:otherwise>
								<option value="${privilege.id }" <c:if test = "${privilege.id == 62 }" >selected="selected" </c:if> >${privilege.text }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<th style="width: 30%"><span>账号性质</span><font color="red">*</font>：</th>
			<td style="width: 70%"><select class="inpy" name="subUser.scope.id">
					<c:forEach var="userScope" items="${userScopes}">
						<c:choose>
							<c:when test="${userScope.id == subUser.scope.id}">
								<option value="${userScope.id }" selected="selected">${userScope.text }</option>
							</c:when>
							<c:otherwise>
								<option value="${userScope.id }" >${userScope.text }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
		</tr>
		<tr id="subUser_pwdDisableTime_tr">
			<th style="width: 30%"><span>账号失效时间</span><font color="red">*</font>：</th>
			<td style="width: 70%"><input id="subUser_pwdDisableTime" class="inpy" name="subUser.pwdDisableTime" type="text"
				value="<fmt:formatDate value="${subUser.pwdDisableTime }" pattern="yyyy-MM-dd" />" /></td>
		</tr>
	</table>

	<div id="sysResource_select_dialog" title="请选择资源">
		<iframe name="sysResource_select_iframe" frameborder="0" scrolling="auto" width="100%" height="340"></iframe>
	</div>
</form>