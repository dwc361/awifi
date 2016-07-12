<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>错误信息</title>
	</head>
	<body>
	<span id="result" style="display: none;">${result }</span>
	<span id="tip" style="display: none;">${tip }</span>
	
	<script type="text/javascript">
	try{
		var msg = document.getElementById("result").innerHTML;
		var tip = document.getElementById("tip").innerHTML;
		if(msg == 'BL00001'){
			//if(confirm("是否前往登陆页面?")){}
			alert(tip);
			window.top.location.href = "userAction!goLogin.action";
		}else if(msg == 'SYS00002'){
			//alert(msg + "：" + tip);
			window.top.location.href = "err.jsp";
		}else if((!msg) || (!tip)){
			window.top.location.href = "err.jsp";
		}else {
			alert(msg + "：" + tip);
		}
	}catch(e){
		alert("脚本错误："+e.message);
	}
	</script>
	</body>
</html>
