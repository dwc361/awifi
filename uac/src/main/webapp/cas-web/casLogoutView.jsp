<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
<script language="javascript">
function logout()
{
    parent.window.opener = null;
    parent.window.open("", "_self");
    parent.window.close();
}
</script>
<body style="background: #074EA9 url('cas-web/css/images/login_bg.png') no-repeat right 0px; overflow: hidden;">
<div class="logoutBox">
<center>
<br/>
		<div id="msg" class="success">
			<h2><spring:message code="screen.logout.header" /></h2><br/>
			<p><spring:message code="screen.logout.success" /></p><br/>
			<p><spring:message code="screen.logout.security" /></p><br/>
			<p><a href="uacAction!goMain.action">点击前往登录</a><!-- <a onclick="javascript:logout();">点击关闭窗口</a> --></p>
		</div>
</center>
</div>
<jsp:directive.include file="includes/bottom.jsp" />
</body>
