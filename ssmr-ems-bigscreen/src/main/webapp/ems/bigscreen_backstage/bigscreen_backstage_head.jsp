<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"   isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 页面主体样式 -->
<link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/index.css">

<!-- 字体&图标 -->
<link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/font-awesome-4.6.3/css/font-awesome.css">

<!-- Bootstrap相关 -->
<link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/bootstrap.min.css">
<script src="${basePath}/ems/bigscreen_backstage/main/js/bootstrap.js"></script>

<!-- JQuery相关 -->
<script src="${basePath}/ems/bigscreen_backstage/main/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.json.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.message_cn.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>

<!-- 自定义工具组件 -->
<script type="text/javascript" src="${basePath}/common/js/ROOF.Utils.js"></script>
<script type="text/javascript" src="${basePath}/common/js/ROOF.Class.js"></script>

<!-- 模拟弹窗 -->
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${basePath }/ems_common/js/open-modal.min.js"></script>

<!-- 表单验证错误提示框 -->
<script type="text/javascript" src="${basePath }/ems_common/js/com.letv.errorpacement.js"></script>

<!-- jquery-ui拖拽 -->
<script src="${basePath}/ems/bigscreen_backstage/main/js/jquery-ui"></script>

<!-- WEB操作 -->
<script type="text/javascript" src="${basePath }/ems/bigscreen_backstage/main/js/web.js"></script>

<script type="text/javascript">
	var basePathConst = "${basePath }";
	function reloadFun(){
		//window.opener.location.reload();
		window.parent.location.reload();
		window.close();
	}
</script>