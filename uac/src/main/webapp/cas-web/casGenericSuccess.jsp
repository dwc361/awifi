<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
<script type="text/javascript">
jQuery(document).ready(function() {
	jQuery('#login_form').submit();
});
</script>
<body>
<form method="post" id="login_form" action="services/j_acegi_cas_security_check">
<div class="loginBox">
<c:if test="${not empty sessionScope.openIdLocalId}">
<strong>${sessionScope.openIdLocalId}</strong>
<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
</c:if>
<input type="hidden" name="subUser" value="" />
<input type="hidden" name="lt" value="${loginTicket}" />
<input type="hidden" name="execution" value="${flowExecutionKey}" />
<input type="hidden" name="_eventId" value="submit" />
	<ul>
		<li class="title">4A安全管理系统</li>
		<li class="user">
		<select id="changeType" style="width: 192px; margin: 5px 0 0 3px; padding: 5px; border: 0;">
		<option value="1">静态密码验证</option>
		<option value="2">证书认证</option>
		</select>
		</li>
		<li class="user">
		<c:if test="${empty sessionScope.openIdLocalId}">
		<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
		<input type="text" value="test" name="username" id="username">
		</c:if>
		</li>
		<li class="key">
		<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
		<input type="password" value="123456" name="password" id="password">
		</li>
		<li class="loginBtn"><a href="javascript:void(0)" id="btnLogin">登 录</a></li>
		<li class=""><font color="red"><form:errors path="*" id="msg" cssClass="errors" element="div" /></font></li>
	</ul>
</div>
</form>
</body>