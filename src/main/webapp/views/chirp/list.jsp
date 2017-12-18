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


<!-- Listing table -->

<display:table name="chirps" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="chirp.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader}" />

	<spring:message code="chirp.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" />

	<spring:message code="chirp.attachment" var="attachmentHeader" />
	<display:column property="attachments" title="${attachmentHeader}" />

	<spring:message code="chirp.sender" var="senderHeader" />
	<display:column property="actorSender.name" title="${senderHeader}" />

	<spring:message code="chirp.recipient" var="recipientHeader" />
	<display:column property="actorRecipient.name"
		title="${recipientHeader}" />

	<display:column>
		<a href="chirp/editForward.do?chirpId=${row.id}"> <spring:message
				code="chirp.forward" />
		</a>
	</display:column>

	<jstl:if test="${principalId != row.actorSender.id}">
		<display:column>
			<a href="chirp/editReply.do?chirpId=${row.id}"> <spring:message
					code="chirp.reply" />
			</a>
		</display:column>
	</jstl:if>

	<jstl:if test="${principalId == row.actorSender.id}">
		<display:column>
			<a href="chirp/delete.do?chirpId=${row.id}"> <spring:message
					code="chirp.delete" />
			</a>
		</display:column>
	</jstl:if>

</display:table>

<acme:cancel code="cancel" url="welcome/index.do" />