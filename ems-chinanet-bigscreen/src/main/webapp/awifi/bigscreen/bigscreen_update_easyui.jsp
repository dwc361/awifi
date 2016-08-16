<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css"
	href="${basePath}/ems_common/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${basePath}/ems_common/easyui/themes/icon.css">
<script type="text/javascript"
	src="${basePath}/ems_common/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${basePath}/ems_common/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${basePath}/awifi/styles/vendor.css">
<link rel="stylesheet"
	href="${basePath}/awifi/styles/bootstrap-theme.css">
<link rel="stylesheet" href="${basePath}/awifi/styles/bootstrap.css">
<link rel="stylesheet" href="${basePath}/awifi/styles/app.css">
<style type="text/css">
.proxy {
	border: 1px solid #ccc;
	width: 80px;
	background: #fafafa;
}
</style>
<script>
	$(function() {
		$('.thumbnail').draggable({
			revert : true,
			deltaX : 10,
			deltaY : 10,
			proxy : function(source) {
				var n = $('<div class="proxy"></div>');
				n.html($(source).html()).appendTo('body');
				return n;
			}
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<h4 class="fl">
			<input type="text" value="标题标题标题标题标题标题标题标题" name="name"
				class="form-control input-sm" style="width: 300px;" />
		</h4>
		<div class="btn-group fr" role="group"
			aria-label="Default button group">
			<button type="button" class="btn btn-default">保存</button>
			<button type="button" class="btn btn-default">预览</button>
			<button type="button" class="btn btn-default">返回</button>
		</div>
		<div class="cl" style="height: 20px"></div>
		<div class="row fl min-img" style="width: 120px;">
			<c:forEach var="chart" items="${charts}">
				<div class="col-xs-12">
					<p class="title">${chart.name }</p>
					<a href="#" class="thumbnail"> <img
						src="${basePath}/${chart.icon}" alt="" width="100%" />
					</a>
				</div>
			</c:forEach>
		</div>
		<div id="target_div" class="row box-group fl">
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_1_1" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_1_2" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_1_3" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_1_4" />
			</div>
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_1_5" />
			</div>
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_2_1" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_2_2" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_2_3" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_2_4" />
			</div>
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_2_5" />
			</div>
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_3_1" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_3_2" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_3_3" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_3_4" />
			</div>
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_3_5" />
			</div>
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_4_1" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_4_2" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_4_3" />
			</div>
			<div class="box">
				<input type="hidden" value="" name="part_1_1" id="part_4_4" />
			</div>
			<div class="box easyui-droppable targetarea"
				data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						console.log($(this));
    						var target = $(source).clone();
    						$(target).children('img').attr('height','100%');
    						$(target).children('img').attr('width','100%');
     						$(this).html($(target).html()); 
    					}
    				">
				<input type="hidden" value="" name="part_1_1" id="part_4_5" />
			</div>
		</div>
	</div>
</body>
</html>