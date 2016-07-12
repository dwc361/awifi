<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.parent.name }</title>
<%@include file="/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${basePath}/common/js/zTree/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${basePath }/common/js/zTree/js/jquery.ztree.all-3.1.min.js"></script>
<script type="text/javascript" src="${basePath }/roof-web/web/org/js/roof.web.OrgTree.js"></script>
<script type="text/javascript">
var treeObj = null;
var tree = null;
var firstAsyncSuccessFlag = 0;
$(document).ready(function() {
	$('iframe').height(jQuery(window).height() - 40);
	var setting = {
			async : {
				enable : true,
				url : "dictionaryAction!readVal.ajax?val=SYSTEM_TYPE",
				autoParam : ["val=type"]
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : function(event, treeId, node) {
					var action = "uac_authorization_systemAction";
					if(node.description){
						action = node.description;
					}
					$('iframe').attr("src", action+"!list.action");
				},
				onAsyncSuccess : function() {
					if (firstAsyncSuccessFlag == 0) {
						var nodes = treeObj.getNodes();
						treeObj.expandNode(nodes[0], true);
						firstAsyncSuccessFlag = 1;
					}
				}
			}
		};
		treeObj = $.fn.zTree.init($('.ztree'), setting);
	
});

function getSeletedNode() {
	tree.getSeletedNode();
}

function reAsyncChildNodes() {
	tree.reAsyncChildNodes();
}

function reAsyncParentChildNodes() {
	tree.reAsyncParentChildNodes();
}
</script>
</head>
<body>
	<div class="bread">
		您的位置：
		<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; 
		<a href="javascript:void(0)" id="currNode">${module.parent.name }</a>
	</div>
	<div class="main">
		<div class="treeBox">
			<div class="ztree" style="width: 15%; float: left;" width="186" height="288"></div>
		</div>
		<div class="conBox">
			<iframe src="${basePath }/uac_authorization_systemAction!list.action" frameborder="0" scrolling="auto" width="100%"></iframe>
		</div>
	</div>
</body>
</html>