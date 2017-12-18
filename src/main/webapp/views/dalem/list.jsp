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

<display:table name="dalems" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
		<jstl:choose>
			<jstl:when test="${row.event.moment gt currentMoment && row.administrator.id eq principal.id}">
				<jstl:set value="${'myDalem'}" var="boldItalic" />
			</jstl:when>
			<jstl:when test="${row.event.moment gt currentMoment && row.administrator.id ne principal.id}">
				<jstl:set value="${'notMyDalem'}" var="boldItalic" />
			</jstl:when>
			<jstl:when test="${row.event.moment lt currentMoment && (row.administrator.id eq principal.id)}">
				<jstl:set value="${'passEventMy'}" var="boldItalic" />
			</jstl:when>
			<jstl:when test="${row.event.moment lt currentMoment && row.administrator.id ne principal.id}">
				<jstl:set value="${'passEventNotMy'}" var="boldItalic" />
			</jstl:when>
			<jstl:when test="${row.event.moment lt currentMoment && actorVar == true}">
				<jstl:set value="${'passEvent'}" var="boldItalic" />
			</jstl:when>
		</jstl:choose>

	<spring:message code="dalem.createMoment" var="createMomentHeader" />
	<display:column property="createMoment" title="${createMomentHeader}"
		format="{0,date,dd/MM/yyyy}" class="${boldItalic}"/>

	<spring:message code="dalem.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" class="${boldItalic}"/>

	<spring:message code="dalem.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" class="${boldItalic}"/>

	<spring:message code="dalem.assessment" var="assessmentHeader" />
	<display:column property="assessment" title="${assessmentHeader}" class="${boldItalic}"/>

	<spring:message code="dalem.administrator" var="administratorHeader" />
	<display:column property="administrator.name" title="${administratorHeader}" class="${boldItalic}"/>

	<spring:message code="dalem.event" var="eventHeader" />
	<display:column property="event.title" title="${eventHeader}" class="${boldItalic}"/>

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<spring:message code="dalem.justification" var="justificationHeader" />
		<display:column property="justification"
			title="${justificationHeader}" class="${boldItalic}"/>

		<spring:message code="dalem.cancel" var="cancelHeader" />
		<display:column property="cancel" title="${cancelHeader}" class="${boldItalic}"/>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column>
				<jstl:if test="${row.cancel == false}">
					<a href="dalem/editCancel.do?dalemId=${row.id}"> <spring:message
							code="cancel" />
					</a>
				</jstl:if>
			</display:column>
		</security:authorize>

	</security:authorize>

</display:table>

<acme:cancel code="cancel" url="event/list.do" />