<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <title>大屏监控网</title>
    <%@include file="/ems_common/ems_head_boot.jsp"%>
    <!-- 字体&图标 -->
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/font-awesome-4.6.3/css/font-awesome.css">
    <!-- bootstrap样式 -->
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/bootstrap.min.css">
    <!-- 自定义样式和js -->
    <link rel="stylesheet" href="${basePath}/ems/firstWelcome/css/firstPro.css">
    <script type="text/javascript">
        $(function() {
            $("#file_upload_form").ajaxForm({
                'type' : 'post',
                'cache' : false,
                'dataType' : 'json',
                'clearForm' : true,
                'success' : function(d) {
//                    var src = ROOF.Utils.projectName() + "/fileAction/get/" + d.data.name + ".action";
                    var src =ROOF.Utils.projectName() + "/ems/bigscreen_show/first/svg/welcome.svg";
                    $("body").append("<object data='"+src+"' type='image/svg+xml' width='1px' height='1px' />");
                    /*$("body").append("<img src='"+src+"' alt='svg not supported!'' />");*/
                },
                'error' : function(d) {
                }
            });
        });
    </script>
</head>
<body>
    <div >
    <%--<object data="${basePath }/ems/bigscreen_show/first/svg/welcome.svg" type="image/svg+xml" width="1px" height="1px"></object>--%>
    <img src="${basePath }/file/test/welcome.svg" alt="" />
    </div>
    <input name="name" id="name" type="hidden" placeholder="图片名称" />
    <input name="wordId" id="word" type="hidden" placeholder="文字" />
</body>
</html>