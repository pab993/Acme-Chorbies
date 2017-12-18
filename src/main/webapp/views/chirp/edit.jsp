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

<form:form action="chirp/edit.do" modelAttribute="chirpForm">

<%-- 	<form:hidden path="id" />
	<form:hidden path="version" /> --%>
	
	<fieldset>
		<legend>
			<b> <spring:message code="chirp.recipient" /></b>
		</legend>

		<acme:select items="${chorbiRecipients}" itemLabel="name"
			code="choose" path="chorbiRecipient" />

	</fieldset>

	<fieldset>
		<legend>
			<b> <spring:message code="chirp.name" /></b>
		</legend>

		<acme:textbox code="chirp.subject" path="subject" />
		<br />

		<acme:textarea code="chirp.text" path="text" />
		<br />

		<acme:textbox code="chirp.attachment" path="attachments" />
		<br />

	</fieldset>

	<br />

	<acme:submit name="save" code="chirp.submit" />
	<acme:cancel url="welcome/index.do" code="cancel" />

</form:form>
