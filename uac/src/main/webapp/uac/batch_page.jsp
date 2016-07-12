<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
<%@include file="/head.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#upload_form").ajaxForm({
		type : 'post',
		cache : false,
		dataType : 'json',
		error : function() {
			alert('Ajax信息加载出错,请重试');
		},
		success : function(replay) {
			alert(replay.message);
			if (replay.state == "success") {
				// 成功之后处理
				$("#filepaths").val(replay.data);
				$(".btnBox").show();
			}
		}
	});
	
	$(".btnBox").hide();
	
	$("input[name='file']").change(function() {
		$(".btnBox").hide();
		$("#upload_form").submit();
	});
	
	$("#updateblj").click(function() {
		alert("点击确定不要重复点击");
			$.ajax({
				type : "POST",
				url : "batchAction!batch_blj.ajax",
				dataType : "json",
				async:false,
				data : {
					"filepaths" : $("#filepaths").val()
				},
				error : function(msg) {
					alert("Ajax调用失败");
				},
				success : function(result) {
					alert(result.message);
					
				}
			});
		return false;
	});
	
	$("#updateBtn").click(function() {
		alert("点击确定不要重复点击");
		$.ajax({
			type : "POST",
			url : "batchAction!batch.ajax",
			dataType : "json",
			async:false,
			data : {
				"filepaths" : $("#filepaths").val()
			},
			error : function(msg) {
				alert("Ajax调用失败");
			},
			success : function(result) {
				alert(result.message);
				
			}
		});
	return false;
});
	
	
	$("#updateUser").click(function() {
		alert("不要重复点击");
		$.ajax({
			type : "POST",
			url : "batchAction!batchbljuser.ajax",
			dataType : "json",
			async:false,
			data : {
				
			},
			error : function(msg) {
				alert("Ajax调用失败");
			},
			success : function(result) {
				alert(result.message);
			}
		});
	return false;
});
	
});
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="javascript:void(0)" id="parNode">系统管理</a> &gt; <a href="javascript:void(0)"
				id="currNode">Excel导入</a>
	</div>
	
	<br>
	<br>
	<div class="main">
		<form id="upload_form" action="${basePath }/batchAction!upload.action" enctype="multipart/form-data" method="post">
			<input style="width: 380px;" name="file" type="file" />
			<br>
			<br>
			<input type="text" style="width: 380px;" readonly = "readonly" id = "filepaths" name="filepaths"  />
		</form>
	</div>
	
	<!------btnBox-------->
		<div class="btnBox" >
			<a id="updateblj" href="#">
				<p class="sBtn">批量堡垒机</p>
			</a> <a id="updateBtn" href="#">
				<p class="sBtn">批量4A</p>
			</a>
		</div>
		<a id="updateUser" href="#">
				<p class="sBtn">堡垒机主账号</p>
			</a>

</body>
</html>