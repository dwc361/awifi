<%@page import="org.roof.spring.CurrentSpringContext"%>
<%@page import="com.zjhcsoft.uac.cxf.SmsService"%>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
<script type="text/javascript">
<c:set value="获取验证码" var="tip" />	
document.onkeydown = function(e) {
	e = e || window.event;
	var keycode = e.which ? e.which : e.keyCode;
	if (keycode == 13) {// Enter
		jQuery('#btnLogin').click();
	}
	if (keycode == 27) {// Esc
		var username = jQuery('#username');
		var password = jQuery('#password');
		username.val("");
		password.val("");
	}
}
function StopButton() {
	var clickObj = jQuery('#'+arguments[0]);
	clickObj.text("" + arguments[1] + "");
    if (--arguments[1] >= 0) {
        window.setTimeout("StopButton('" + arguments[0] + "'," + arguments[1] + ")", 1000);
    }
    if (arguments[1] < 0) {
    	clickObj.text("${tip}");
    }
}
jQuery(document).ready(function() {
	jQuery('#btnSubLogin').click(function() {
		var ck = $('input[name="subs"]:checked').val();
		if(!ck){
			alert("请先选择一个子账号!");
			return false;
		}
		$('input[name="subUser"]').val(ck);
		var txtObj = $('input[name="subs"]:checked').parent().next().next().next().find("input");
		if((txtObj.length > 0) && !txtObj.val()){
			alert("请输入密码!");
			return false;
		}
		$('input[name="userPwd"]').val(txtObj.val());
		$('#fm1').submit();
		return false;
	});

	jQuery('#btnSubLogin2').click(function() {
		var ck = $("#inputPassword").val();
		if(!ck){
			alert("请输入密码!");
			return false;
		}
		$('input[name="userPwd"]').val(ck);
		$('#fm1').submit();
		return false;
	});
	
	jQuery('#btnLogin').click(function() {
		<c:if test="${empty service}">
		$('#fm1').submit();
		return;
		</c:if>
		var username = jQuery('#username');
		var password = jQuery('#password');
		var phonepwd = jQuery('#phonepwd');
		if(!username.val()){
			alert("用户名不能为空!");
			return false;
		}
		if(!password.val()){
			alert("密码不能为空!");
			return false;
		}
		$.ajax({
			type : "POST",
			url : "uacLdapAction!whiteList_check.ajax",
			dataType : "json",
			async:false,
			data : {
				"username" : username.val()
			},
			success : function(result) {
				if(result.message == "fail"){
					if((phonepwd.length > 0) && (phonepwd.val().length != phonepwd.attr("maxlength"))){
						alert("请输入"+phonepwd.attr("maxlength")+"位数值的验证码!");
						return false;
					}else{
						$.ajax({
							type : "POST",
							url : "uacLdapAction!login_check.ajax",
							dataType : "json",
							async:false,
							data : {
								"username" : username.val(),
								"password" : password.val(),
								"service" : "${service}",
								"phonepwd" : phonepwd.val()
							},
							error : function(msg) {
								alert("Ajax调用失败");
							},
							success : function(result) {
								if(result.state == "success"){
									if(result.message && result.message != "") {
										alert(result.message);
									}
									var subs=result.data;
									$("#sub").empty();
									if(subs.length==0){
										alert("请先配置子账号! 把姓名，手机，身份证，目标系统，工号(目标系统账号)，发邮件到 gsdx4a@163.com");
										return false;
									}
									if(subs.length==1){
										$('input[name="subUser"]').val(subs[0].sn);
										if(subs[0].needPassword == true){
											$("#apptip").html("本次登录账号："+subs[0].sn);
											$("#pwdip").html(subs[0].appName+"的密码：");
											$.blockUI({message: $('#fillPassword'), css:{top:"30%", left:"35%", width:"", height:"", border: "0"}});
										}else{
											$('#fm1').submit();
										}
										return false;
									}
									for(var i=0;i<subs.length;i++){
										var trObj = 
											"<tr>"+
											"<td>"+(i+1)+"<input name='subs' type='radio' value='"+subs[i].sn+"'/></td>"+
											//"<td>"+subs[i].appName+"</td>"+
											"<td>"+((subs[i].needPassword == true)? subs[i].appName : "&nbsp;")+"</td>"+
											"<td>"+subs[i].sn+"</td>"+
											"<td>"+((subs[i].needPassword == true)? "<input type=\"password\"/>" : "&nbsp;")+"</td>"+
											"</tr>";
										$("#sub").append(trObj);
									}
									$.blockUI({message: $('#choose'), css:{top:"30%", left:"35%", width:"", height:"", border: "0"}});
								}else{
									alert(result.message);
								}
							}
						});
					}
				}else{
					$.ajax({
						type : "POST",
						url : "uacLdapAction!login_check.ajax",
						dataType : "json",
						async:false,
						data : {
							"username" : username.val(),
							"password" : password.val(),
							"service" : "${service}",
							"phonepwd" : phonepwd.val()
						},
						error : function(msg) {
							alert("Ajax调用失败");
						},
						success : function(result) {
							if(result.state == "success"){
								if(result.message && result.message != "") {
									alert(result.message);
								}
								var subs=result.data;
								$("#sub").empty();
								if(subs.length==0){
									alert("请先配置子账号! 把姓名，手机，身份证，目标系统，工号(目标系统账号)，发邮件到 gsdx4a@163.com");
									return false;
								}
								if(subs.length==1){
									$('input[name="subUser"]').val(subs[0].sn);
									if(subs[0].needPassword == true){
										$("#apptip").html("本次登录账号："+subs[0].sn);
										$("#pwdip").html(subs[0].appName+"的密码：");
										$.blockUI({message: $('#fillPassword'), css:{top:"30%", left:"35%", width:"", height:"", border: "0"}});
									}else{
										$('#fm1').submit();
									}
									return false;
								}
								for(var i=0;i<subs.length;i++){
									var trObj = 
										"<tr>"+
										"<td>"+(i+1)+"<input name='subs' type='radio' value='"+subs[i].sn+"'/></td>"+
										//"<td>"+subs[i].appName+"</td>"+
										"<td>"+((subs[i].needPassword == true)? subs[i].appName : "&nbsp;")+"</td>"+
										"<td>"+subs[i].sn+"</td>"+
										"<td>"+((subs[i].needPassword == true)? "<input type=\"password\"/>" : "&nbsp;")+"</td>"+
										"</tr>";
									$("#sub").append(trObj);
								}
								$.blockUI({message: $('#choose'), css:{top:"30%", left:"35%", width:"", height:"", border: "0"}});
							}else{
								alert(result.message);
							}
						}
					});
				}
			}
		});
		return false;
	});
	$('#changeType').change(function() {
		var v = $(this).val();
		if(v=="1"){
			jQuery('#username').attr("disabled",false);
		}
		if(v=="2"){
			jQuery('#username').attr("disabled",true);
		}
	});
	
	jQuery('#username').focus(function() {
		if($(this).val() == "请输入用户名(手机号码 )"){
			$(this).val("");
		}
	});
	jQuery('#username').blur(function() {
		if($(this).val() == ""){
			$(this).val("请输入用户名(手机号码 )");
		}
	});
	jQuery('#password').focus(function() {
		if($(this).val() == "请输入密码"){
			$(this).val("");
		}
	});
	jQuery('#password').blur(function() {
		if($(this).val() == ""){
			//$(this).val("请输入密码");
		}
	});
	jQuery('#phonepwd').focus(function() {
		if($(this).val() == "短信验证码"){
			$(this).val("");
		}
	});
	jQuery('#phonepwd').blur(function() {
		if($(this).val() == ""){
			$(this).val("短信验证码");
		}
	});
	
	jQuery('#passcard').click(function() {
		if($(this).text() != "${tip}"){
			return false;
		}
		var username = jQuery('#username');
		var password = jQuery('#password');
		if(!username.val()){
			return false;
		}
		if(!password.val()){
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "uacLdapAction!login_passcardSend.ajax",
			dataType : "json",
			data : {
				"username" : username.val(),
				"password" : password.val(),
				"service" : "${service}"
			},
			error : function(msg) {
				alert("Ajax调用失败");
			},
			success : function(result) {
				if ("success" == result.state) {
					alert("验证码已发送!有效期时间5分钟");
					StopButton('passcard', 60);
				} else {
					alert(result.message);
				}
			}
		});
	});

	$('#helpDiv1').mousedown(  
	        function (event) {  
	            var isMove = true;  
	            var abs_x = event.pageX - $('#helpDiv').offset().left;  
	            var abs_y = event.pageY - $('#helpDiv').offset().top;  
	            $(document).mousemove(function (event) {  
	                        if (isMove) {  
	                            var obj = $('#helpDiv');  
	                            obj.css({'left':event.pageX - abs_x, 'top':event.pageY - abs_y});  
	                        }  
	                    }  
	            ).mouseup(  
	                    function () {  
	                        isMove = false;  
	                    }  
	            );  
	        }  
	);
});

 
</script>
<%
SmsService smsService = (SmsService) CurrentSpringContext.getBean("smsService");
boolean flg = smsService.isOpen();// 开启否
if(flg){
%>
<c:set value="true" var="isOpen" />	
<%
}else{
%>
<c:set value="false" var="isOpen" />	
<%	
}
%>
<style type="text/css">
<c:if test="${!isOpen }">
.loginBox li{ margin-top:16px; }
</c:if>
</style>
<body style="background: #074EA9 url('cas-web/css/images/login_bg.png') no-repeat right 0px; overflow: hidden;">
<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
<div class="loginBox">
<c:if test="${not empty sessionScope.openIdLocalId}">
<strong>${sessionScope.openIdLocalId}</strong>
<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
</c:if>
<input type="hidden" name="subUser" value="" />
<input type="hidden" name="userPwd" value="" />
<input type="hidden" name="lt" value="${loginTicket}" />
<input type="hidden" name="execution" value="${flowExecutionKey}" />
<input type="hidden" name="_eventId" value="submit" />
<p class="loginBox_title"><span><img alt="" src="cas-web/css/images/telecom.png"></span>
<!-- <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国电信甘肃公司4A安全管理系统</span></p> -->
<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4A安全管理系统</span></p>
	<ul>
		<li class="yp">
		<select id="changeType" style="width: 192px; margin: 5px 0 0 3px; padding: 5px; border: 0;">
		<option value="1">静态密码验证</option>
		<option value="2">证书认证</option>
		</select>
		</li>
		<li class="user">
		<c:if test="${empty sessionScope.openIdLocalId}">
		<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
		<form:input value="请输入用户名(手机号码 )" title="请输入用户名(手机号码 )" style="float: left; width: 192px; margin: 5px 0 0 3px; border: 0;" cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
		</c:if>
		</li>
		<li class="key">
		<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
		<form:password  title="请输入密码" style="float: left; width: 192px; margin: 5px 0 0 3px;  border: 0;" cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
		</li>
		<c:if test="${isOpen }">
		<li class="captcha">
		<input id="phonepwd" name="phonepwd" value="短信验证码" style="float: left; width: 100px; margin: 2px 0px 2px 35px; border: 0; " type="text" maxlength="4" />
		<span id="passcard" class="captcha_img">${tip }</span>
		</li>
		</c:if>
		<li class="loginBtn" id="btnLogin"><a href="javascript:void(0)"><font color="white"><b>登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</b></font></a></li><li class="" style="text-align:right;" ><a href="uac_restet_pwdAction!index.action"><font color="blue"><b>忘记密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></font></a></li>
		<li class=""><font color="red"><form:errors path="*" id="msg" cssClass="errors" element="div" /></font></li></ul>
</div>
</form:form>
<jsp:directive.include file="includes/bottom.jsp" />

<div class="floatBox" id="choose" style="display: none;">
<div class="title"><p class="txt">请选择子账号</p><p class="colse" onclick="$.unblockUI();"></p></div>
<div class="floatCon" style="overflow: auto; height: 200px;">
  <table border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
		<tr>
			<th>序号</th>
			<th>系统</th>
			<th>账号</th>
			<th>密码</th>
		</tr>
		<tbody id="sub">
		</tbody>
	</table>
	<p class="floatBtn" onclick="" id="btnSubLogin">确定</p>
 </div>
</div>

<div class="floatBox" style="display: block; left: 5px; width: 260px; top: 23%; " id="helpDiv">
<div class="title" style="text-align: center; color: #FFF; font-size: 12px;" id="helpDiv1"><b>4A系统简易操作指引</b><p class="colse" onclick="$('#helpDiv').hide();"></p></div>
<div class="floatCon" style="height: 290px; overflow: auto;">
<b style="color: red;">一、使用指引</b><br/>
4A系统地址：http://135.149.16.26/<br/>4A支撑邮箱：gsdx4a@163.com<br/>1、未注册4A账号或工号未绑定的用户<br/>请发邮件到4A支撑邮箱申请，邮件内容包括：姓名，所属地市，所属分公司名称，手机号码，身份证号码，业务系统名称，业务系统工号。<br/><strong><a  href="uacAction!downFile.ajax?file_name=4Atemp1.xls"><u>下载模板</u></a></strong><br/>2、需更换4A账号信息的用户<br/>请发邮件到4A支撑邮箱申请，邮件内容包括：姓名，手机号码，身份证号码，身份证正面拍照。<br/><strong><a  href="uacAction!downFile.ajax?file_name=4Atemp2.xls"><u>下载模板</u></a></strong><br/>3、4A账号密码错误的用户<br/>请拨打支撑电话0931-8788542或发送邮件到4A支撑邮箱修改密码。邮件内容包括：姓名，手机号码，身份证号码，身份证正面拍照。<br/><strong><a  href="uacAction!downFile.ajax?file_name=4Atemp2.xls"><u>下载模板</u></a></strong><br/>4、登录4A系统提示请配置子账号的用户<br/>请输入4A地址http://135.149.16.26；登录成功后查看哪些工号没绑定，再按上述提示发送邮件处理。<br/><strong><a  href="uacAction!downFile.ajax?file_name=4Atemp1.xls"><u>下载模板</u></a></strong><br/>5、登录4A系统提示密码即将到期的用户<br/>请点击“确定”按钮进入4A系统，在“自助服务”菜单下的“修改密码”进行修改新密码。“自助服务”菜单无法打开的用户请设置浏览器兼容性或将计算机IE浏览器升级到7.0以上版本。<br/>6、4A系统登录无法获取手机验证码<br/>获取短信验证码的手机号码必须是电信手机号码。无法获取到验证码时，请查看手机防御软件是否将验证码短信拉黑或更换个手机再次获取验证码。无法解决时请拨打支撑电话0931-8788542处理。<br/>
<b style="color: red;">二、操作说明</b><br/>
1、用户登录<br/>
输入在4A登录平台申请的主账号用户名和密码，用户名为：手机号，密码默认为身份证后8位（如果开启手机验证码，则需要输入手机验证码），点击登录按钮。<br/>
2、修改密码<br/>
进入首页后在：自助服务—>修改密码中修改密码，目前密码策略为：长度至少8位，至少包含数字、字母、特殊字符，为了确保账号密码安全首次登录时先进行密码修改，以确保账号密码的安全性，不进行修改的将进行账号锁定及通报处理。友情提醒：首次登录4A安全管理系统必须修改初始密码。<br/>
3、应用系统单点登录<br/>
在第一个下拉框中会显示当前用户所有可见的系统，选择某个系统，第二个下拉框会自动加载出登录用户的对应子账号，可以选择登录（用该子账号登录系统）或者收藏（添加到常用应用登录）。在常用应用登录列表中，选择对应的系统和账号，点击列表中的登录按钮，即用该子账号登录，默认最多显示9个账号，如果需要查看更多，则点击个人收藏夹链接。<br/>

<br/>
<b style="color: red;">三、常见问题及解决方案</b><br/>
1、 账号或者密码错误<br/>
解决方案：登录系统时提示“账号或者密码错误”请联系4A安全管理系统维护支撑人员确定问题原因，如果账号不存在需联系相关系统账号管理人员，由账号管理员人员在itsm中进行账号申请。<br/>
2、 应用系统列表中没有需要登陆的业务平台<br/>
解决方案：请联系4A安全管理系统维护支撑人员确定问题原因，如果账号不存在需联系相关系统账号管理人员，由账号管理员人员在itsm中进行账号申请。<br/>
3、 个人短信发送失败<br/>
解决方案：短信发送失败，联系运维人员进行信息确认，确认无误后先通过系统获取验证码进行登录，再找短信网关进行故障确认。<br/>
4、 短信网关拥塞<br/>
解决方案：短信网关拥塞，联系4A安全管理系统运维人员，先关闭短信验证进行正常登录，等短信网关恢复后再开启短信认证。<br/>
5、 系统定位失败<br/>
解决方案：系统定位失败时，先联系4A系统运维人员确认，确认是普遍现象时先开通绿色通道保证纳入系统的正常登录，再做后续处理。<br/>
<br/>
<b style="color: red;">四、运维支撑</b><br/>
支撑联系电话：0931-8788542<br/>
支撑群：387381260<br/>
</div>
</div>

<div class="floatBox" id="fillPassword" style="display: none; width: 450px;">
<div class="title"><p class="txt">请输入密码</p><p class="colse" onclick="$.unblockUI();"></p></div>
<div class="floatCon">
	<font color="red"><span id="apptip"></span></font><br/>
	<br/>
	<span id="pwdip" style="display:inline;" ></span>
  	<input type="password" id="inputPassword" value="" />
	<p class="floatBtn" onclick="" id="btnSubLogin2">确定</p>
 </div>
</div>

</body>
