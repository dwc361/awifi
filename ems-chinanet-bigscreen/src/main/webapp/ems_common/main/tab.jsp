<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<script type="text/javascript" src="${basePath}/letv_common/static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="${basePath}/letv_common/static/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="${basePath}/letv_common/plugins/tab/js/framework.js"></script>
		<link href="${basePath}/letv_common/plugins/tab/css/import_basic.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" id="skin" prePath="${basePath}/letv_common/plugins/tab/" />
		<!--默认相对于根目录路径为../，可添加prePath属性自定义相对路径，如prePath="<%=request.getContextPath()%>"-->
		<script type="text/javascript" charset="utf-8" src="${basePath}/letv_common/main/js/tab.js"></script>
		<%@include file="/letv_common/messages.jsp"%>
	</head>
	<body>
		<div id="tab_menu" style="height: auto;"></div>
		<div style="width: 100%;">
			<div id="page" style="width: 100%; height: 100%;"></div>
			<input type="hidden" id=index_en name=index_en_name value="<spring:message code="Index" />">

			<!-- ROI高级管理层 -->
			<sec:authorize url="${roi_gjglc_url }">
				<input type="hidden"  id="poi_gjglc" value="poi_gjglc">
			</sec:authorize>
			<!-- ROI内容线层 -->
			<sec:authorize url="${roi_nrxc_url }">
				<input type="hidden"  id="poi_nrxc" value="poi_nrxc">
			</sec:authorize>

			<%-- <sec:authorize url="${basePath }/letv/gcr/copyright_hold_selectAction/list">
			</sec:authorize> --%>
		</div>
	</body>
	<script type="text/javascript">
		var tab;
		function tabAddHandler(mid,mtitle,murl){
			var path = "<%=path%>";
			var end_ = ".action"
			if(murl.indexOf("http://")!=-1 || murl.indexOf("{0}")!=-1){
				path = "";
				end_ = "";
			}
			var url = path + murl + end_;
			url = buildUrl(url);
			tab.add({
				id: mid,
				title: mtitle,
				url: url,
				isClosed: true
			});
			tab.update({
				id: mid,
				title: mtitle,
				url: url,
				isClosed: true
			});
			tab.activate(mid);
		}

		//调用不带action的url
		function tabAddNoActionHandler(mid,mtitle,murl){
			murl = murl + "&have_back_button=NotHave";
			murl = buildUrl(murl);
			tab.add({
				id :mid,
				title :mtitle,
				url :"<%=path%>" + murl,
				isClosed :true
			});
			tab.update({
				id :mid,
				title :mtitle,
				url :"<%=path%>" + murl,
				isClosed :true
			});
			tab.activate(mid);
		}

		function buildUrl(url){
			if (url!=null && url.indexOf('{0}')!=-1) {
				url = url.replace('{0}', '${flow_base_url}');
			}
			return url;
		}

		$( function() {
			tab = new TabView({
				containerId : 'tab_menu',
				pageid : 'page',
				cid : 'tab1',
				position : "top"
			});

			if (document.getElementById("index_en")) {
				var url = buildUrl('${flow_base_url}${flow_index_url}');
				var index_en_name = document.getElementById("index_en").value;
				tab.add({
					id : 'tab1_index1',
					title : index_en_name,
					url : url,
					isClosed : false
				});
			}
			
			var poi_gjglc = $("#poi_gjglc").val();
			var poi_nrxc = $("#poi_nrxc").val();
			var poi_title=roi_senior_management,poi_url;
			if(poi_gjglc == "poi_gjglc"){
				poi_url = "${roi_gjglc_url}";
			}
			if(poi_nrxc=="poi_nrxc"){
				poi_title = roi_business_lines;//Business Lines
				poi_url = "${roi_nrxc_url}";
			}
			if(poi_gjglc == "poi_gjglc" && poi_nrxc=="poi_nrxc"){
				poi_title = roi_senior_management;//Senior Management
				poi_url = "${roi_gjglc_url}";
			}
			if(poi_gjglc == "poi_gjglc" || poi_nrxc=="poi_nrxc"){
				tab.add( {
					id :'roi_id',
					title :poi_title,
					url :poi_url,
					isClosed :true
				});
			}

			$("#tab1").css("height","auto");
		});

		window.onresize = function() {
			cmainFrameT();
			$("#mainFrame", parent.document).css("height", "800px");
		};
		cmainFrameT();
		function cmainFrameT() {
			var hmainT = document.getElementById("page");
			var bheightT = document.documentElement.clientHeight;
			hmainT.style.width = '100%';
			hmainT.style.height = (bheightT - 51) + 'px';
		}
	</script>
</html>
