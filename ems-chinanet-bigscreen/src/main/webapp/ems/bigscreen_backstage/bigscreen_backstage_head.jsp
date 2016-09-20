<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"   isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- JQuery相关 -->
<%-- <script type="text/javascript" src="${basePath}/common/js/jquery/jquery-1.7.2.min.js"></script> --%>
<script src="http://cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
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

<script type="text/javascript">
	var basePathConst = "${basePath }";
	function reloadFun(){
		//window.opener.location.reload();
		window.parent.location.reload();
		window.close();
	}
</script>