<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#osForm").validate({
			rules : {
				"os.app.id" : {
					number : true,
					required : true
				}, 
				"os.identify" : {
					required : true
				}, 
				"os.ip" : {
					required : true
				}, 
				"os.modifytime" : {
					required : true
				}, 
				"os.name" : {
					required : true
				}, 
				"os.port" : {
					required : true
				}, 
				"os.state.id" : {
					required : true
				}
			}
		});

		$("#osForm").ajaxForm({
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
			$("#osForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#osForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("os","os.id",$(this).attr("href"));
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
					$("#os_app_id").val(id);
					$("#os_app_name").val(name);
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
<input type="hidden" id="os_paramString" value="${os_paramString }" />
<form id="osForm" name="osForm" method="post" action="${submit_url }">
	<input type="hidden" id="os_id" name="os.id" value="${os.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>所属应用</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="os_app_id" class="inpy" name="os.app.id" type="hidden" value="${os.app.id }" />
					<input id="os_app_name" class="inpy" name="os.app.name" readonly type="text" value="${os.app.name }" />&nbsp;
					<a id="app_select_btn" class="btn" href="javascript:void(0)"><i class="icon-search"></i></a>
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>ip地址</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="os_ip" class="inpy" name="os.ip" type="text" value="${os.ip }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="os_name" class="inpy" name="os.name" type="text" value="${os.name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>端口号</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="os_port" class="inpy" name="os.port" type="text" value="${os.port }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>状态</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="os.state.id" id="os_state_id">
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == os.state.id}">
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
					<textarea id="os_sysdescribe" class="inpy" name="os.sysdescribe" rows="3">${os.sysdescribe }</textarea>
				</td>
			</tr>
	</table>
</form>