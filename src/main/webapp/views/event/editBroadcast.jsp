<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="chirpBroadcast">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend>
			<b> <spring:message code="chirp.name" /></b>
		</legend>
	
		<acme:textarea code="chirp.subject" path="subject" />
		<br />

		<acme:textarea code="chirp.text" path="text" />
		<br />

		<acme:textbox code="chirp.attachment" path="attachments" />
		<br />

	</fieldset>

	<br />

	<acme:submit name="save" code="event.submit" />
	<acme:cancel url="/manag/events/myEvents.do" code="event.cancel" />

</form:form>
