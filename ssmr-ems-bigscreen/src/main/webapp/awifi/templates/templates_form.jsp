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
			<b>是否可用</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<select id="templates_enabled" name="enabled" class="form-control input-sm">
				<option></option>
				<c:forEach  items="${enableds }" var="dic" >
					<option value="${dic.val }" 
					<c:if test="${ templates.enabled == dic.val}">
					selected="selected"</c:if>>${dic.text }</option>
				</c:forEach >
			</select>
<%-- 			<input type="text" id="templates_enabled" maxlength="1" name="enabled" value="${templates.enabled }" class="form-control input-sm"> --%>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<b>模板路径</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-9">
			<input type="text" id="templates_path" maxlength="255" name="path" value="${templates.path }" class="form-control input-sm">
			<span class='red' ></span> 
		</div>
	</div>
</div>