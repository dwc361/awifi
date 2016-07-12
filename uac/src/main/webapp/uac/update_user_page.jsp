<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主账号列表</title>
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${basePath }/roof-web/web/js/ROOF.SelectableTable.js"></script>
<script type="text/javascript" src="${basePath }/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	var table = null;
	function getSelected() {
		return table.getSelectedTrNoClone();
	}
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			$("#mainForm :input").val('');
		});
		$("#searchBtn").click(function() {
			$("#mainForm").submit();
		});

		table = new ROOF.SelectableTable($('#mainTable'));
		
		$("#updateErrorBtn").click(function() {
			var id = table.getSeleted();
			if (id) {
				$.ajax({
					type : "POST",
					url : "uac_account_userAction!updateError.ajax",
					dataType : "json",
					async:false,
					data : {
						"user.id" : id
					},
					error : function(msg) {
						alert("Ajax调用失败");
					},
					success : function(result) {
						alert(result.message);
						$("#mainForm").submit();
					}
				});
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});
		$("#updateUserNameBtn").click(function() {
			var id = table.getSeleted();
			if (id) {
				var name = $("#user_newusername_"+id+"").val();
				if(name.length == 0){
					alert("主账号不能为空");
					return false;
				}
				if($(".error:visible").size()>0){
					return false;
				}
				var idNumber = $("#user_idNumber_"+id+"").val();
				var pwd = idNumber.substr(idNumber.length-8);
				$.ajax({
					type : "POST",
					url : "uac_account_userAction!update_user.ajax",
					dataType : "json",
					async:false,
					data : {
						"user.id" : id,
						"user.name" : $("#user_name_"+id+"").val(),
						"user.username" : $("#user_newusername_"+id+"").val(),
						"user.tel" : $("#user_tel_"+id+"").val(),
						"user.idNumber" : idNumber
					},
					error : function(msg) {
						alert("Ajax调用失败");
					},
					success : function(result) {
						alert(result.message);
						$("#mainForm").submit();
					}
				});
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});
		$('#resetPwdBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				var idNumber = $("#user_idNumber_"+id+"").val().toLowerCase();
				var pwd = idNumber.substr(idNumber.length-8);
				$.ajax({
					type : "POST",
					url : "uac_account_userAction!update_user.ajax",
					dataType : "json",
					async:false,
					data : {
						"user.id" : id,
						"user.name" : $("#user_name_"+id+"").val(),
						"user.username" : $("#user_username_"+id+"").val(),
						"user.tel" : $("#user_tel_"+id+"").val(),
						"user.idNumber" : idNumber,
						"user.password" : pwd
					},
					error : function(msg) {
						alert("Ajax调用失败");
					},
					success : function(result) {
						alert(result.message);
						$("#mainForm").submit();
					}
				});
				
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});

		$('#updateIdBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				if($(".error:visible").size()>0){
					return false;
				}
				var idNumber = $("#user_newidNumber_"+id+"").val().toLowerCase();
				if(!(idNumber.length == 15 || idNumber.length == 18)){
					alert("身份证长度不对");
					return false;
				}
				
				var pwd = idNumber.substr(idNumber.length-8);
				$.ajax({
					type : "POST",
					url : "uac_account_userAction!update_user.ajax",
					dataType : "json",
					async:false,
					data : {
						"user.id" : id,
						"user.name" : $("#user_name_"+id+"").val(),
						"user.username" : $("#user_username_"+id+"").val(),
						"user.tel" : $("#user_tel_"+id+"").val(),
						"user.idNumber" : idNumber,
						"user.password" : pwd
						
					},
					error : function(msg) {
						alert("Ajax调用失败");
					},
					success : function(result) {
						alert(result.message);
						$("#mainForm").submit();
					}
				});
				//alert($("#user_name_"+id+"").val());
				//$("#user_name_"+id+"").show();
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});

		$("#mainForm").validate({
			rules : {
				'newidNumber' : {
					required : false,
					remote : {
						url : "uac_account_userAction!validateIdNumber.ajax",
						type : "post",
						dataType : "json",
						data : {
							"idNumber" : function() {
								return $("input[name='newidNumber']").val();
							}
						}
					}
				},
			'newusername' : {
				required : false,
				remote : {
					url : "uac_account_userAction!validateUsername.ajax",
					type : "post",
					dataType : "json",
					data : {
						"username" : function() {
							return $("input[name='newusername']").val();
						}
					}
				}
			}
				

			},
			messages : {
				"newidNumber" : {
					remote : "该身份证号已被使用"
				},
				"newusername" : {
					remote : "该主账号已被使用"
				}
			}
		});
	});
</script>
</head>
<body>
	<form id="mainForm"
		action="${basePath }/uac_account_userAction!user_list.action?user.org.org_id=${user.org.org_id }&unEditable=${unEditable}"
		method="post">
		<input class="inpy" id="isMayor" name = "isMayor" type="hidden" value="${isMayor }"  />
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 15%">姓名：<input type="hidden" name="org_id" value="${user.org.org_id }" /></th>
				<td style="width: 35%"><input class="inpy" name="user.name" type="text" value="${user.name }" /></td>

				<th style="width: 15%">主账号：</th>
				<td style="width: 35%"><input class="inpy" name="user.username" type="text" value="${user.username }" /></td>
			</tr>
			<tr>
				<th style="width: 15%">身份证号：</th>
				<td style="width: 35%"><input class="inpy" name="user.idNumber" type="text" value="${user.idNumber }" /></td>

				<th style="width: 15%">邮箱：</th>
				<td style="width: 35%"><input class="inpy" name="user.mail" type="text" value="${user.mail }" /></td>
			</tr>
		</table>

		<!------btnBox-------->
		<div class="btnBox">
			<a id="resetBtn" href="#">
				<p class="sBtn">重置</p>
			</a> <a id="searchBtn" href="#">
				<p class="sBtn">查询</p>
			</a>
			<c:if test="${empty unEditable || !unEditable}">
				<!-- 
				<a id="deleteBtn" href="${basePath }/uac_account_userAction!delete.action">
					<p class="sBtn">停用</p>
				</a>
				 -->
				<c:if test="${!isMayor }">
				<a id="updateIdBtn" href="#">
					<p class="sBtn">修改身份证</p>
				</a>
				<a id="updateUserNameBtn" href="#">
					<p class="sBtn">修改主账号</p>
				</a>
				<a id="updateErrorBtn" href="#">
					<p class="sBtn">异常账号</p>
				</a>
				</c:if>
				<a id="resetPwdBtn" href="#">
					<p class="sBtn">重置密码</p>
				</a>
				
				
			</c:if>
		</div>
		<!------end of btnBox-------->
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th></th>
				<th>序号</th>
				<th>姓名</th>
				<th>主账号</th>
				<c:if test="${!isMayor }">
				<th>新主账号</th>
				</c:if>
				<!--<th>密码设置时间</th>
 				<th>证书编码</th>-->
			
				
				<th>身份证号</th>
				<c:if test="${!isMayor }">
				<th>新身份证号</th>
				</c:if>
				
			</tr>
			<c:forEach var="user" items="${page.dataList }" varStatus="status">
				<tr >
					<td><input type="checkbox" <%-- name="users[${status.index }].id" --%> value="${user.id }" /></td>
					<td>${status.index + 1 }</td>
					<td><input class="inpy" id="user_name_${user.id }"  type="hidden" value="${user.name }"  />${user.name }</td>
					<c:if test="${!isMayor }">
					<td><input class="inpy" id="user_username_${user.id }"  type="hidden" value="${user.username }" />${user.username }</td>
					</c:if>
					<td><input class="inpy" name="newusername" id="user_newusername_${user.id }" type="text"  /></td>
					<%-- <td><fmt:formatDate value="${user.pwdSetTime }" pattern="yyyy-MM-dd" /></td>
					<td>${user.certCoding }</td> --%>
					
					<c:set var="string1" value="${user.idNumber }"/>
					<c:set var="string2" value="${fn:substring(string1, 0, 6)}" />
					<c:set var="string3" value="${fn:substring(string1, 14, 19)}" />

					<td><input class="inpy" id="user_idNumber_${user.id }"  type="hidden" value="${user.idNumber }" />
					<c:if test="${isMayor }">
					${user.idNumber }
					</c:if>
					<c:if test="${!isMayor }">
					${string2}****${string3}
					</c:if></td>
					<c:if test="${!isMayor }">
					<td><input class="inpy" name="newidNumber" id="user_newidNumber_${user.id }" type="text"  /></td>
					</c:if>
					<%-- <td>${user.gender.text }</td>
					<td><input class="inpy" id="user_tel_${user.id }"  type="hidden" value="${user.tel }" />${user.tel }</td> --%>
					
					
				</tr>
			</c:forEach>
		</table>
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>