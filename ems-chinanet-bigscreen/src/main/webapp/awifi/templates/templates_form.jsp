<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input type="hidden" id="templates_id" name="id" value="${templates.id }" />

<div class="row text-center form-wrapper">
	<div class="row">
		<div class="col-xs-2">
			<b>名称</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="templates_name" maxlength="255" name="name" value="${templates.name }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>模板路径</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="templates_path" maxlength="255" name="path" value="${templates.path }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>是否可用</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="templates_enabled" maxlength="1" name="enabled" value="${templates.enabled }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>新建时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="templates_create_time" readonly name="create_time" value="<fmt:formatDate value="${templates.create_time }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>所属用户</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="templates_create_by" maxlength="255" name="create_by" value="${templates.create_by }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>更新时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="templates_update_time" readonly name="update_time" value="<fmt:formatDate value="${templates.update_time }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>更新用户</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="templates_update_by" maxlength="255" name="update_by" value="${templates.update_by }" class="form-control input-sm">
		</div>
	</div>
</div>