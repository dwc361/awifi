<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input type="hidden" id="bigscreen_id" name="id" value="${bigscreen.id }" />

<div class="row text-center form-wrapper">
	<div class="row">
		<div class="col-xs-2">
			<b>名称</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_name" maxlength="255" name="name" value="${bigscreen.name }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>模板id</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_template_id" name="template_id" value="${bigscreen.template_id }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>主题id</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_theme_id" name="theme_id" value="${bigscreen.theme_id }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>是否发布</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_publish" maxlength="1" name="publish" value="${bigscreen.publish }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>刷新时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_re_time" name="re_time" value="${bigscreen.re_time }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>re_type</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_re_type" maxlength="20" name="re_type" value="${bigscreen.re_type }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>新建时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_create_time" readonly name="create_time" value="<fmt:formatDate value="${bigscreen.create_time }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>所属用户</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_create_by" maxlength="255" name="create_by" value="${bigscreen.create_by }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>更新时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_update_time" readonly name="update_time" value="<fmt:formatDate value="${bigscreen.update_time }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>更新用户</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreen_update_by" maxlength="255" name="update_by" value="${bigscreen.update_by }" class="form-control input-sm">
		</div>
	</div>
</div>