<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <title>一层监控</title>
    <%@include file="/ems_common/ems_head_boot.jsp"%>
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_show/first/css/firstPro.css">
    <script type="text/javascript">
        var wordList = ${wordList};
        $(function() {
            var src =ROOF.Utils.projectName() + "/ems/bigscreen_show/first/svg/"+wordList[0].name;
            $("#object").append("<object data='"+src+"' type='image/svg+xml' width='100%' height='100%' />");
            $("#wordid").val(wordList[0].wordid);
        });
    </script>
</head>
<body>

    <div id="object"></div>
    <div id="word_div"><input id="wordid" type="text" /></div>
</body>
</html>