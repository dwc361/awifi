<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword"
          content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <title>大屏前台三层监控</title>
    <link href="//cdn.bootcss.com/tether/1.3.6/css/tether.min.css"
          rel="stylesheet">
    <script src="//cdn.bootcss.com/tether/1.3.6/js/tether.min.js"></script>

    <%@include file="/ems/bigscreen_show/bigscreen_show_head.jsp"%>
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_show/third/css/screen_3.css">
</head>
<body>
    <div id="thirdScreen_show" class="container"></div>
    <script src="${basePath}/react/build/thirdScreen.js"></script>
</body>
</html>
