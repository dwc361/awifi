var treeObj = null;
var tree = null;
var firstAsyncSuccessFlag = 0;
$(document).ready(function() {
	$('iframe').height(jQuery(window).height() - 40);
	tree = new roof.web.OrgTree($('.ztree'), {
		onClick : function(event, treeId, node) {
			var url = null;
			if (node.isParent == true) {
				//url = "uac_account_userAction!list.action";
				if(node.getParentNode()==null){//根节点
					url = "uac_account_userAction!list.action";
				}else{
					url = "uac_account_userAction!list.action?user.org.org_id=" + node.id;
				}
			} else {
				url = "uac_account_userAction!list.action?user.org.org_id=" + node.id;
			}

			$('iframe').attr("src", url);
		},
		onAsyncSuccess : function() {
			if (firstAsyncSuccessFlag == 0) {
				var nodes = treeObj.getNodes();
				treeObj.expandNode(nodes[0], true);
				treeObj.selectNode(nodes[0]);
				firstAsyncSuccessFlag = 1;
			}
		}
	});
	treeObj = tree.getTreeObj();
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