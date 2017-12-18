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
<form:form action="dalem/edit.do" modelAttribute="dalem">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="code" />
	<form:hidden path="createMoment" />
	<form:hidden path="cancel" />
	
	<form:hidden path="administrator" />
	<form:hidden path="event" />

	<fieldset>

		<legend>
			<b> <spring:message code="dalem.create" />
			</b>
		</legend>
		
		<acme:textarea code="dalem.title" path="title"/>
		<br/>
		
		<acme:textarea code="dalem.description" path="description"/>
		<br/>
		
		<spring:message code="dalem.assessment" />:
		<form:select path="assessment">
			<spring:message code="assessment.HighlyRecommended" var="HRHeader"/><form:option value="Highly Recommended" label="${HRHeader}"/>	
			<spring:message code="assessment.Recommended" var="RHeader"/><form:option value="Recommended" label="${RHeader}" />	
			<spring:message code="assessment.NotRecommended" var="NRHeader"/><form:option value="Not Recommended" label="${NRHeader}" />
		</form:select>
		
	</fieldset>
	
	<div>
		<acme:submit name="save" code="submit" />
		<acme:cancel code="cancel" url="dalem/list.do" />
	</div>
	
</form:form>