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
			<b>是否可用</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<select id="theme_enabled" name="enabled" class="form-control input-sm">
				<option></option>
				<c:forEach  items="${enableds }" var="dic" >
					<option value="${dic.val }" 
					<c:if test="${ theme.enabled == dic.val}">
					selected="selected"</c:if>>${dic.text }</option>
				</c:forEach >
			</select>
<%-- 			<input type="text" id="theme_enabled" maxlength="1" name="enabled" value="${theme.enabled }" class="form-control input-sm"> --%>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<b>背景图片</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-8">
			<input type="text" id="theme_picture" maxlength="255" name="picture" value="${theme.picture }" class="form-control input-sm">
			（图片路径）
		</div>
	</div>
	<div class="row">	
		<div class="col-xs-2">
			<b>备注</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-8">
			<textarea id="theme_remark" name="remark" class="form-control" rows="3">${theme.remark }</textarea>
<%-- 			<input type="text" id="theme_remark" maxlength="255" name="remark" value="${theme.remark }" class="form-control input-sm"> --%>
		</div>
	</div>
</div>