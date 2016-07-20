<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代码模版信息配置</title>
<script type="text/javascript">
	var tip = "${tip}";
	if (tip) {
		alert(tip);
	}
	function clearForm() {
		document.getElementById("packagePath").value = "";
		document.getElementById("actionName").value = "";
		document.getElementById("tableName").value = "";
	}

	function setAction() {
		var p = document.getElementById("packagePath").value;
		var cut = document.getElementById("cut").value;
		if (p) {
			if(!cut){
				cut = 2;
			}
			var arr = p.split(".");
			var a = "";
			for ( var i = arr.length-cut; i < arr.length; i++) {
				if(i == arr.length-cut){
					a += arr[i];
				}else{
					a += "_" + arr[i];
				}
			}
			document.getElementById("actionName").value = a;
		}
	}
</script>
</head>
<body>
<form action="createAction!createCode.action" method="post">
工程的路径userDir<font color="red">*</font>:<br/>
<input id="userDir" name="userDir" type="text" title="如：E:/work34/uac_lz" size="100" value="${userDir }" /><br/>
文件模版目录templatePrefix<font color="red">*</font>:<br/>
<input id="templatePrefix" name="templatePrefix" type="text" title="如：E:/work34/uac/src/test/java/src/template/" size="100" value="${templatePrefix }" /><br/>
包路径packagePath<font color="red">*</font>:<br/>
<input id="packagePath" name="packagePath" type="text" title="如：com.zjhcsoft.uac.authorization.resource，到entity前" size="80" value="${packagePath }"/>
<input id="cut" name="" type="text" title="" size="16" onblur="setAction()"/><br/>
Action名称actionName:<br/>
<input id="actionName" name="actionName" type="text" value="${actionName }" title="如：infopush_strategy_variable" size="100"/><br/>
表名tableName<font color="red">*</font>:<br/>
<input id="tableName" name="tableName" type="text" value="${tableName }" size="100"/><br/>
<input type="submit" value="生成" title="结果请看控制台输出信息" />
<input type="button" value="清空" onclick="clearForm()">
</form>
</body>
</html>