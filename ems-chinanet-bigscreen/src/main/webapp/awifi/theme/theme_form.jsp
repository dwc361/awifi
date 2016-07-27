<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input type="hidden" id="theme_id" name="id" value="${theme.id }" />

<div class="row text-center form-wrapper">
	<div class="row">
		<div class="col-xs-2">
			<b>名称</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_name" maxlength="255" name="name" value="${theme.name }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>背景图片</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_picture" maxlength="255" name="picture" value="${theme.picture }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>是否可用</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_enabled" maxlength="1" name="enabled" value="${theme.enabled }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>备注</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_remark" maxlength="255" name="remark" value="${theme.remark }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>新建时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_create_time" readonly name="create_time" value="<fmt:formatDate value="${theme.create_time }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>所属用户</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_create_by" maxlength="255" name="create_by" value="${theme.create_by }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>更新时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_update_time" readonly name="update_time" value="<fmt:formatDate value="${theme.update_time }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>更新用户</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="theme_update_by" maxlength="255" name="update_by" value="${theme.update_by }" class="form-control input-sm">
		</div>
	</div>
</div>