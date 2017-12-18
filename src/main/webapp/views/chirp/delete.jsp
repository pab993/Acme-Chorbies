<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="chirp/delete.do" modelAttribute="chirpDelete">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="createMoment" />
	<form:hidden path="subject" />
	<form:hidden path="text" />
	<form:hidden path="attachments" />
	
	<form:hidden path="actorSender" />
	<form:hidden path="actorRecipient" />
	
	<h1><spring:message code="chirp.delete.accepted" /></h1>
	
	<acme:submit name="delete" code="chirp.yes" />
	<acme:cancel url="chirp/list.do" code="chirp.no" />

</form:form>
