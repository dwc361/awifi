<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#appForm").validate({
			rules : {
				"app.expression" : {
					required : true
				}, 
				"app.identify" : {
					required : true
				}, 
				"app.name" : {
					required : true
				}, 
				"app.path" : {
					required : true
				}, 
				"app.region.id" : {
					required : true
				}, 
				"app.state.id" : {
					required : true
				}
			}
		});

		$("#appForm").ajaxForm({
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
			$("#appForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#appForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("app","app.id",$(this).attr("href"));
		});
	});
</script>
<input type="hidden" id="app_paramString" value="${app_paramString }" />
<form id="appForm" name="appForm" method="post" action="${submit_url }">
	<input type="hidden" id="app_id" name="app.id" value="${app.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>表达式</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="app_expression" class="inpy" name="app.expression" type="text" value="${app.expression }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>名称</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="app_name" class="inpy" name="app.name" type="text" value="${app.name }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>路径</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="app_path" class="inpy" name="app.path" type="text" value="${app.path }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>所属域</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="app.region.id" id="app_region_id">
						<c:forEach var="obj" items="${regionList}">
							<c:choose>
								<c:when test="${(obj.id == app.region.id) || (obj.id == app_region_id)}">
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
					<span>账号状态</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<select class="inpy" name="app.state.id" id="app_state_id">
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == app.state.id}">
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
					<textarea id="app_describe" class="inpy" name="app.describe" rows="3">${app.describe }</textarea>
				</td>
			</tr>
	</table>
</form>