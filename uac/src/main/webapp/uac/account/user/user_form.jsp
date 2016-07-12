<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath }/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath }/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(document).tooltip({
			position : {
				my : "left+15 center",
				at : "right center"
			}
		});
		ROOF.Utils.ajaxcommon();
		$("input[name='user.pwdDisableTime']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("#user_form").validate({
			rules : {
				'user.name' : {
					required : true
				},
				'user.username' : {
					required : true,
					remote : {
						url : "uac_account_userAction!validateUsername.ajax",
						type : "post",
						dataType : "json",
						data : {
							"username" : function() {
								return $("input[name='user.username']").val();
							}
						}
					},
					rangelength : [ 6, 18 ]
				},
				'user.password' : {
					required : true
				},
				'user.repassword' : {
					required : true,
					equalTo : "#password"
				},
				'user.idNumber' : {
					required : true,
					remote : {
						url : "uac_account_userAction!validateIdNumber.ajax",
						type : "post",
						dataType : "json",
						data : {
							"idNumber" : function() {
								return $("input[name='user.idNumber']").val().toLowerCase();
							}
						}
					}
				},
				'user.tel' : {
					required : true,
					isPhone : true
				},
				'user.mail' : {
					required : false,
					email : true
				}

			},
			messages : {
				"user.username" : {
					remote : "该用户名已被使用"
				},
				"user.idNumber" : {
					remote : "该身份证号已被使用"
				},
				'user.repassword' : {
					equalTo : "两次输入的密码不相同"
				}
			}
		});
		if (!("undefined" == typeof passwordPolicys)) {
			for ( var i in passwordPolicys) {
				var passwordPolicy = passwordPolicys[i];
				var passwordPolicy_name = "uac_" + passwordPolicy.name;
				jQuery.validator.addMethod(passwordPolicy_name, function(value, element, param) {
					var passport = new RegExp(param);
					return (passport.test(value));
				}, passwordPolicy.message);
				var rule = {};
				rule[passwordPolicy_name] = passwordPolicy.expression;
				//$("input[name='user.password']").rules("add", rule);
			}
		}
		$("#user_form").ajaxForm({
			"dataType" : "json",
			"success" : function(d) {
				alert(d.message);
			}
		});

		$("#saveBtn").click(function() {
			$("#user_form").submit();
		});
		$("select[name='user.scope.id']").change(function() {
			if ($(this).val() == 53) {
				$('#pwdDisableTimeTr').show();
			}

			if ($(this).val() == 52) {
				$('#pwdDisableTimeTr').hide();
				$('input[name="user.pwdDisableTime"]').val('');
			}
		});
		$("select[name='user.scope.id']").trigger('change');

		var setting = {
			async : {
				enable : true,
				url : "orgAction!read.ajax",
				autoParam : [ "id" ]
			},
			view : {
				selectedMulti : false
			}
		};
		treeObj = $.fn.zTree.init($('.ztree'), setting);

		$("#org_select_btn").button().click(function(event) {
			$("#org_select_dialog").dialog("open");
		});

		$("#org_select_dialog").dialog({
			autoOpen : false,
			width : 400,
			height : 300,
			modal : true,
			resizable : false,
			buttons : {
				"确定" : function() {
					var selectNode = getSelected();
					if (selectNode) {
						$('input[name="user.org.org_name"]').val(selectNode.name);
						$('input[name="user.org.org_id"]').val(selectNode.id);
						$(this).dialog("close");
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});

		function getSelected() {
			var nodes = treeObj.getSelectedNodes();
			if (nodes.length > 0) {
				return nodes[0];
			}
			return null;
		}
		
		jQuery('#user_username').blur(function() {
			$('#user_tel').val($('#user_username').val());
			$('#user_mail').val($('#user_username').val()+"@189.cn");
		});
	
		jQuery('#user_idNumber').blur(function() {
			var idNumber = $("#user_idNumber").val().toLowerCase();
			
			var pwd = idNumber.substr(idNumber.length-8);
			$('#password').val(pwd);
			$('#user_repassword').val(pwd);
			$(this).val(idNumber);
		});
		
	});
</script>
<form id="user_form" action="${submit_url }" method="post">
	<input type="hidden" name="user.id" value="${user.id }">
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
		<tr>
			<th style="width: 30%">姓名<font color="red">*</font>：
			</th>
			<td style="width: 70%"><input title="请填写您的姓名" class="inpy" name="user.name" type="text" value="${user.name }" /></td>
		</tr>
		<tr>
			<th>主账号<font color="red">*</font>：
			</th>
			<td><input title="6~18个字符" class="inpy" id="user_username" name="user.username" type="text" value="${user.username }" /></td>
		</tr>
		<tr>
			<th>身份证号<font color="red">*</font>：
			</th>
			<td><input title="身份证号码将作为您子账号的绑定唯一识别 , 为了不影响您的正常使用请正确填写" class="inpy" id="user_idNumber" name="user.idNumber" type="text"
				value="${user.idNumber }" /></td>
		</tr>
		<tr>
			<th>部门<font color="red">*</font>：
			</th>
			<td><input class="inpy" name="user.org.org_id" type="hidden" value="${user.org.org_id }" /><input class="inpy"
				name="user.org.org_name" type="text" value="${user.org.org_name }" readonly="readonly" />&nbsp;&nbsp;<a
				id="org_select_btn" class="btn" href="#"> <i class="icon-search"></i>
			</a></td>
		</tr>
		<c:if test="${empty user.id }">
			<tr>
				<th>密码<font color="red">*</font>：
				</th>
				<td><input title="长度8位以上,包括数字且英文字符必须大于2位，还要有特殊字符"  readonly = "readonly" id="password" class="inpy"  name="user.password" type="password"
					value="${user.password }" /></td>
			</tr>
			<tr>
				<th>重复密码<font color="red">*</font>：
				</th>
				<td><input title="重复上面输入的密码" class="inpy"  readonly = "readonly" id="user_repassword" name="user.repassword" type="password" value="${user.password }" /></td>
			</tr>
		</c:if>
		<tr>
			<th>证书编码：</th>
			<td><input class="inpy" name="user.certCoding" type="text" value="${user.certCoding }" /></td>
		</tr>
		<tr>
			<th>账号性质：</th>
			<td><select class="inpy" name="user.scope.id">
					<c:forEach var="userScope" items="${userScopes }">
						<c:choose>
							<c:when test="${userScope.id == user.scope.id}">
								<option value="${userScope.id }" selected="selected">${userScope.text }</option>
							</c:when>
							<c:otherwise>
								<option value="${userScope.id }">${userScope.text }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
		</tr>
		<tr id="pwdDisableTimeTr" style="display: none;">
			<th>失效日期：</th>
			<td><input class="inpy" name="user.pwdDisableTime" type="text"
				value='<fmt:formatDate pattern="yyyy-MM-dd"  value="${user.pwdDisableTime }"/>' /></td>
		</tr>
		<tr>
			<th>用户类别：</th>
			<td><select class="inpy" name="user.category.id">
					<c:forEach var="userCategory" items="${userCategorys }">
						<c:choose>
							<c:when test="${userCategory.id == user.category.id}">
								<option value="${userCategory.id }" selected="selected">${userCategory.text }</option>
							</c:when>
							<c:otherwise>
								<option value="${userCategory.id }">${userCategory.text }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
		</tr>
		
		<tr>
			<th>性别：</th>
			<td><select class="inpy" name="user.gender.id">
					<c:forEach var="gender" items="${genders }">
						<c:choose>
							<c:when test="${gender.id == user.gender.id}">
								<option value="${gender.id }" selected="selected">${gender.text }</option>
							</c:when>
							<c:otherwise>
								<option value="${gender.id }">${gender.text }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<th>电话<font color="red">*</font>：
			</th>
			<td><input title="请输入您常用手机号码,如果输入有误您将无法收到系统短信" class="inpy" id="user_tel" name="user.tel" type="text" value="${user.tel }" /></td>
		</tr>
		<tr>
			<th>邮箱<font color="red">*</font>：
			</th>
			<td><input title="请输入您的常用邮箱" class="inpy" id="user_mail" name="user.mail" type="text" value="${user.mail }" /></td>
		</tr>
	</table>
</form>

<div id="org_select_dialog" title="请选择部门">
	<div class="ztree" style="height: 80%; overflow: auto;"></div>
</div>