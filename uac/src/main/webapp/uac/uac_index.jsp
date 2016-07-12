<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/cas-web/includes/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="${basePath}/uac/home.js" type="text/javascript"></script>
<script type="text/javascript">
 	$(function(){
		$("#disp").height($(window).height()-313);
	});
	jQuery(document).ready(
			function() {
				jQuery('#loginFavorites').click(function() {
					var needPwd = $("#app_id").find("option:selected").attr("rel");
					var app_path = $("#app_id").find("option:selected").attr("title");
					var sub_user_no = $("#sub_user_id").find("option:selected").attr("title");
					var is_enable = $("#sub_user_id").find("option:selected").attr("lang");
					var app_name = $("#app_id").find("option:selected").text();
					if (is_enable == "false") {
						alert("此账号已失效，请联系管理员!");
						return false;
					}
					if (app_path && sub_user_no) {
						var a = "<a media='"+app_name+"' title='"+app_path+"' rel='"+needPwd+"' lang='"+sub_user_no+"'></a>";
						//var a = "<a title='"+app_path+"' rel='"+needPwd+"' lang='"+sub_user_no+"'></a>";
						loginFavorites(a);
					}
				});
				jQuery('#addFavorites').click(function() {
					var app_id = $("#app_id").val();
					var sub_user_id = $("#sub_user_id").val();
					if (app_id && sub_user_id) {
						$.ajax({
							type : "POST",
							url : "uac_favoritesAction!create.ajax",
							dataType : "json",
							data : {
								"favorites.app.id" : app_id,
								"favorites.subUser.id" : sub_user_id
							},
							error : function(msg) {
								alert("Ajax调用失败");
							},
							success : function(result) {
								alert(result.message);
								if (result.state == "success") {
									window.location.href = "uacAction!index.action";
								}
							}
						});
						return false;
					}
				});

				jQuery('#app_id').change(
						function() {
							var app_id = $("#app_id").val();
							if (app_id) {
								$.ajax({
									type : "POST",
									url : "uacAction!loadSubUser.ajax",
									dataType : "json",
									data : {
										"subUser.sysResource.id" : app_id
									},
									error : function(msg) {
										alert("Ajax调用失败");
									},
									success : function(result) {
										$("#sub_user_id").empty();
										if (result.state == "success") {
											var arr = result.data;
											for (var i = 0; i < arr.length; i++) {
												//is_enable = arr[i].enabled;
												$("#sub_user_id").append(
														'<option title="'+arr[i].name+'" value="'+arr[i].id+'" lang="'+arr[i].enabled+'" >'
																+ arr[i].name + '</option>');
											}
										}
									}
								});
								return false;
							}
						});

			});
</script>
</head>

<body style="background: #074EA9 url('${basePath}/cas-web/css/images/login_bg.png') no-repeat right 0px; overflow: hidden;">

<!-- wrapper -->
<div id="wrapper">
	<!-- shell -->
	<div class="shell">
		<!-- container -->
		<div class="container">
								
				<!-- ad -->
			<div class="ad" style="display: none;">
				<img alt="" src="${basePath}/cas-web/css/images/ad.png"></div>
			<!-- end of ad -->
			<div class="uac_index_main">
				<!-- leftBox -->
				<div class="leftBox">
					<div class="moduleTitle">
						应用登录</div>
					<div class="yydl">
						<div class="indexLogin">
							<ul>
								<li>
								<select id="app_id" name="app_id" style="width: 192px;height:36px; margin: 5px 0 0 3px; padding: 5px; border: 1px solid #cadbef; border-radius: 5px;">
								<option value="">请选择应用系统</option>
								<c:forEach var="app" items="${appList }" varStatus="status">
									<option value="${app.id }" rel="${app.needPassword }" title="${app.path }">${app.name }</option>
								</c:forEach>
								</select></li>
								<li>
								<select id="sub_user_id" name="sub_user_id" style="width: 192px; height:36px; margin: 5px 0 0 3px; padding: 5px; border: 1px solid #cadbef; border-radius: 5px;"">
								<option value="" title="">请选择子账号</option>
								</select></li>
								<li>
								<a class="yydlBtn" id="loginFavorites">登 录</a><a class="yyscBtn" id="addFavorites">收 藏</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="moduleTitle mart20">
						<span>常用应用登录</span><a class="editor" onclick="$('#divFavorites').show();">&nbsp;编辑</a><a
								href="uac_account_subUserAction!binding_page.action" class="editor"></a></div>
					<div class="con" id="disp" style="OVERFLOW-Y: scroll;scrollbar-face-color:#B3DDF7;scrollbar-shadow-color:#B3DDF7;scrollbar-highlight-color:#B3DDF7;scrollbar-3dlight-color:#EBEBE4;scrollbar-darkshadow-color:#EBEBE4;scrollbar-track-color:#F4F4F0;scrollbar-arrow-color:#000000;">
						<table class="ytable">
							<tr>
								<th>应用系统</th>
								<th>账号</th>
								<th>操作</th>
							</tr>
							<tbody id="favoritesBody">
									<c:forEach var="favorite" items="${favoritesList }" varStatus="status">
										<tr>
											<td>${favorite.app.name }</td>
											<td>${favorite.subUser.username }</td>
							 				<td><a href="javascript:void(0)" onclick="loginFavorites(this)" title="${favorite.app.path }"
							 				 media="${favorite.app.name }" rel="${favorite.app.needPassword }" lang="${favorite.subUser.username }">登录</a></td>
										</tr>
									</c:forEach>
								</tbody>
						</table>
					</div>
				</div>
				<!-- end of leftBox -->
				<!-- rightBox-->
					<!-- rightBox-->
					<div class="rightBox">
						<div class="moduleTitle">
							<span>个人提醒</span><a class="more" title="更多" href="uac_messageAction!list.action">更多</a>
						</div>
						<div class="conNews">
							<ul>
								<c:forEach var="message" items="${messageList }" varStatus="status">
									<li title="${message.title}"><fmt:formatDate value="${message.send_date }" pattern="yyyy-MM-dd" /> <a
										href="${basePath}/uac_messageAction!detail_page.action?message.id=${message.id}"> <c:set var="len"
												value="10" /> <c:if test="${fn:length(message.title) > len}">
								${fn:substring(message.title, 0, len)}…
							</c:if> <c:if test="${fn:length(message.title) <= len}">
								${message.title}
							</c:if>
									</a></li>
								</c:forEach>
							</ul>
						</div>
						<div class="moduleTitle mart20">
							<span>系统公告</span><a class="more" title="更多" href="uac_bulletinAction!list_readonly.action">更多</a>
						</div>
						<div class="conNews">
							<ul>
								<c:forEach var="bulletin" items="${bulletinList }" varStatus="status">
									<li title="${bulletin.title}"><fmt:formatDate value="${bulletin.create_date }" pattern="yyyy-MM-dd" /> <a
										href="${basePath}/uac_bulletinAction!detail_page.action?bulletin.id=${bulletin.id}&isRead=_readonly"> <c:set
												var="len" value="10" /> <c:if test="${fn:length(bulletin.title) > len}">
								${fn:substring(bulletin.title, 0, len)}…
							</c:if> <c:if test="${fn:length(bulletin.title) <= len}">
								${bulletin.title}
							</c:if>
									</a></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!-- end of rightBox-->
			<div class="floatBox" style="display: none;" id="divFavorites">
					<div class="title">
						<p class="txt">常用系统收藏编辑</p>
						<p class="colse" onclick="window.location.href='uacAction!index.action';$('#divFavorites').hide();"></p>
					</div>
					<div class="">
						<iframe name="_mainFrame" src="${basePath }/uac_favoritesAction!list.action" frameborder="0" scrolling="auto"
							width="100%" height="360"></iframe>
					</div>
				</div>
				<div class="floatBox" id="fillPassword" style="display: none; width: 450px;">
					<div class="title">
						<p class="txt">请输入密码</p>
						<p class="colse" onclick="$.unblockUI();"></p>
					</div>
					<div class="floatCon">
						<font color="red"><span id="apptip"></span></font><br/>
						<br/>
						<span id="pwdip" style="display:inline;" ></span>
						<input type="password" id="inputPassword" value="" /> <input type="hidden" id="sub_href" value="" /> <input
							type="hidden" id="subUser_name" value="" />
						<p class="floatBtn" onclick="loginByPwd()">确定</p>
					</div>
				</div>
			<!-- end of main --></div>
		<!-- end of container --></div>
	<!-- end of shell --></div>
<!-- end of wrappert --></div>
</body>
</html>
