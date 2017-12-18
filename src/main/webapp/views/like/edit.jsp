<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<form:form action="like/chorbi/edit.do" modelAttribute="like">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:select path="stars">
	
	<form:option value="0">0</form:option>
	<!--<form:option value="1">1</form:option>
	<form:option value="2">2</form:option>
	<form:option value="3">3</form:option>-->
	
	</form:select>
	
	<br/>
	
	<b><spring:message code="like.doYouWantLeaveAComment" /></b>
	<br/>
	<acme:textarea code="like.comment" path="comment"/>
	
	<br />
	
	<acme:submit name="save" code="submit"/>
	
</form:form>