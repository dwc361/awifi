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
<title>大屏配置</title>
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
<script type="text/javascript"
	src="${basePath}/common/js/ROOF.Utils.js"></script>
<script type="text/javascript"
	src="${basePath}/common/js/ROOF.Class.js"></script>
<script type="text/javascript"
	src="${basePath}/awifi/bigscreen/js/bigscreen.Control.js"></script>
<script type="text/javascript"
	src="${basePath}/awifi/bigscreen/js/bigscreen.Input.js"></script>
<script type="text/javascript"
	src="${basePath}/awifi/bigscreen/js/bigscreen.InputList.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.message_cn.js"></script>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery.validate.rules.js"></script>
<script type="text/javascript" src="${basePath }/ems_common/js/com.letv.errorpacement.js"></script>

<style type="text/css">
.proxy {
	border: 1px solid #ccc;
	width: 80px;
	background: #fafafa;
}
</style>
<script>
	$(function() {
		$('#bigscreen').validate({
			rules : {
				'name' : {
					required : true
				}
			},
			messages : {
				'name' : {
					required : "名称必填"
				}
			},
			errorPlacement : com.letv.errorpacement
		});
		
		var ajaxFormOption = {
			 url: "${basePath}/awifi/bigscreenAction/update.action", //请求url  
			type : 'post',
			cache : false,
			dataType : 'json',
			clearForm : true,
			success : function(d) {
				alert(d.message);
				window.location.href="${basePath}/awifi/bigscreenAction/list.action";
			}
		}
		
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
		
		$("#preview").click(function(){
			$("#bigscreen").attr("action","${basePath}/awifi/bigscreenAction/preview.action");	
			$("#bigscreen").attr("target","_blank");	
			$("#bigscreen").submit();
			$("#bigscreen").attr("action","");	
			$("#bigscreen").attr("target","_self");
			return false;
		})
		
		$("#back").click(function(){
// 			window.location.href="${basePath}/awifi/bigscreenAction/list.action";
			$("#bigscreen").attr("action","${basePath}/awifi/bigscreenAction/list.action");	
			$("#bigscreen").submit();
			$("#bigscreen").attr("action","");	
			return false;
		})
		
		$("#update").click(function(){
			$("#bigscreen").ajaxSubmit(ajaxFormOption);
			return false;
		})
		
	});
</script>
</head>
<body>
	<form id="bigscreen" class="form-inline" method="post" action="" target="_self">
		<div class="wrapper">
			<h4 class="fl">
				<input type="text" value="${bigscreen.name }" name="name"
					class="form-control input-sm" style="width: 300px;" />
			</h4>
			<div class="btn-group fr" role="group"
				aria-label="Default button group">
				<button type="button" class="btn btn-default" id="update">保存</button>
				<button type="button" class="btn btn-default" id="preview">预览</button>
				<button type="button" class="btn btn-default" id="back">返回</button>
			</div>
			<div class="cl" style="height: 20px"></div>
			<div class="row fl min-img" style="width: 120px;">
				<c:forEach var="chart" items="${charts}">
					<div class="col-xs-12">
						<p class="title">${chart.name }</p>
						<a href="#" class="thumbnail"> <img
							src="${basePath}/${chart.icon}" alt="" width="100%" />
							<input type="hidden" value="${chart.id}" />
							<input type="hidden" value="${chart.path}" />
						</a>
					</div>
				</c:forEach>
			</div>
		
			<input type="hidden" value="${bigscreen.id }" name="id"/>
			<input type="hidden" value="${templates.id }" name="templates.id"/>
			<input type="hidden" value="${templates.path }" name="templates.path"/>
			<input type="hidden" value="${theme.picture}" name="theme.picture"/>
			<input type="hidden" value="${theme.id}" name="theme.id"/>
			<div id="bigscreen_chart"></div>
			<div id="target_div" class="row box-group fl">
				<div class="box easyui-droppable targetarea" id="part_1_1"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						var local = '1_1';
    					},
    					onDragLeave: function(e,source){
    						var local = '1_1';
    					},
    					onDrop: function(e,source){
    						var local = '1_1';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local); 
    					}
    				">
					
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_1_2" name="vos.target_name" id="part_1_2" /> -->
<!-- 					<input type="hidden" value="part_1_2" name="vos.templete_name" id="templete_1_2" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_1_3" name="vos.target_name" id="part_1_3" /> -->
<!-- 					<input type="hidden" value="part_1_3" name="vos.templete_name" id="templete_1_3" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_1_4" name="target_name" id="part_1_4" /> -->
<!-- 					<input type="hidden" value="part_1_4" name="templete_name" id="templete_1_4" /> -->
				</div>
				<div class="box easyui-droppable targetarea" id="part_1_5"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						var local = '1_5';
    					},
    					onDragLeave: function(e,source){
    					},
    					onDrop: function(e,source){
    						var local = '1_5';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local); 
    					}
    				">
					<input type="hidden" value="part_1_5" name="target_name" id="part_1_5" />
<!-- 					<input type="hidden" value="part_1_5" name="templete_name" id="templete_1_5" /> -->
				</div>
				<div class="box easyui-droppable targetarea" id="part_2_1"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						var local = '2_1';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local);  
    					}
    				">
					<input type="hidden" value="part_2_1" name="target_name" id="part_2_1" />
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_2_1" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_2_2" name="target_name" id="part_2_2" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_2_2" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_2_3" name="target_name" id="part_2_3" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_2_3" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_2_4" name="target_name" id="part_2_4" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_2_4" /> -->
				</div>
				<div class="box easyui-droppable targetarea" id="part_2_5"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						var local = '2_5';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local); 
    					}
    				">
					<input type="hidden" value="part_2_5" name="target_name" id="part_2_5" />
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_2_5" /> -->
				</div>
				<div class="box easyui-droppable targetarea" id="part_3_1"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						var local = '3_1';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local); 
    					}
    				">
<!-- 					<input type="hidden" value="part_3_1" name="target_name" id="part_3_1" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_3_1" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_3_2" name="target_name" id="part_3_2" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_3_2" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_3_3" name="target_name" id="part_3_3" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_3_3" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_3_4" name="target_name" id="part_3_4" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_3_4" /> -->
				</div>
				<div class="box easyui-droppable targetarea" id="part_3_5"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						var local = '3_5';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local); 
    					}
    				">
					<input type="hidden" value="part_3_5" name="target_name" id="part_3_5" />
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_3_5" /> -->
				</div>
				<div class="box easyui-droppable targetarea" id="part_4_1"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						var local = '4_1';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local); 
    					}
    				">
					<input type="hidden" value="part_4_1" name="target_name" id="part_4_1" />
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_4_1" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_4_2" name="target_name" id="part_4_2" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_4_2" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_4_3" name="target_name" id="part_4_3" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_4_3" /> -->
				</div>
				<div class="box">
<!-- 					<input type="hidden" value="part_4_4" name="target_name" id="part_4_4" /> -->
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_4_4" /> -->
				</div>
				<div class="box easyui-droppable targetarea" id="part_4_5"
					data-options="
    					accept: '.thumbnail',
    					onDragEnter:function(e,source){
    						console.log('onDragEnter');
    					},
    					onDragLeave: function(e,source){
    						console.log('onDragLeave');
    					},
    					onDrop: function(e,source){
    						var local = '4_5';
    						inputList.delChartInput(local); 
   							var target = $(source).clone(); 
   							$(target).children('img').attr('height','100%'); 
     						$(target).children('img').attr('width','100%'); 
      						$(this).html($(target).children('img'));
      						var input1 = $(target).children('input')[0];
      						var input2 = $(target).children('input')[1];
    						inputList.addChartInput($(input1).val(),$(input2).val(),local); 
    					}
    				">
					<input type="hidden" value="part_4_5" name="target_name" id="part_4_5" />
<!-- 					<input type="hidden" value="" name="templete_name" id="templete_4_5" /> -->
				</div>
			</div>
		
	</div>
</form>
<script type="text/javascript">
var inputList = new bigscreen.InputList($("#bigscreen_chart"));
var reList = ${reList};
$.each( reList, function(i, n){
  $("#"+n.target).html('<img src="${basePath}/'+n.chart_icon+'" alt="" width="100%" height="100%"/>');
  var target = n.target;
  var local = target.substring(target.indexOf('_')+1,target.length);
  inputList.addChartInput(n.chart_id,n.chart_path,local); 
});


</script>	
</body>
</html>