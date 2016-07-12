<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath }/common/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${basePath }/common/js/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="${basePath }/uac/bulletin/config.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#bulletin_content').ckeditor(config);
		
		$("#bulletinForm").validate({
			rules : {
				"bulletin.content" : {
					required : true
				}, 
				"bulletin.title" : {
					required : true
				}
			}
		});

		$("#bulletinForm").ajaxForm({
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
			$("#bulletinForm").submit();
		});
		
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#bulletinForm"));
		});

		$("#backBtn").click(function() {
			ROOF.Utils.back("bulletin","bulletin.id",$(this).attr("href"));
		});
	});
</script>
<input type="hidden" id="bulletin_paramString" value="${bulletin_paramString }" />
<form id="bulletinForm" name="bulletinForm" method="post" action="${submit_url }">
	<input type="hidden" id="bulletin_id" name="bulletin.id" value="${bulletin.id }" />
	<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%">
					<span>标题</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<input id="bulletin_title" class="inpy" name="bulletin.title" type="text" value="${bulletin.title }" />
				</td>
			</tr>
			<tr>
				<th style="width: 30%">
					<span>内容</span><font color="red">*</font>：
				</th>
				<td style="width: 70%">
					<textarea id="bulletin_content" name="bulletin.content" rows="10" cols="73">${bulletin.content }</textarea>
				</td>
			</tr>
			
	</table>
</form>