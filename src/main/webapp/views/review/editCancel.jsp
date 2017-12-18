<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="review/editCancel.do" modelAttribute="review">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="code" />
	<form:hidden path="createMoment" />
	<form:hidden path="cancel" />
	<form:hidden path="title" />
	<form:hidden path="description" />
	<form:hidden path="score" />
	
	<form:hidden path="actor" />
	<form:hidden path="event" />

	<fieldset>

		<legend>
			<b> <spring:message code="review.cancelForms" />
			</b>
		</legend>
		
		<acme:textarea code="review.justification" path="justification"/>
		<br/>
		
	</fieldset>
	
	<div>
		<acme:submit name="save" code="submit" />
		<acme:cancel code="cancel" url="welcome/index.do" />
	</div>
	
</form:form>