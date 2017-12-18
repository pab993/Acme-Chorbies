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

<display:table name="reviews" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<jstl:choose>
			<jstl:when test="${row.actor.id eq principal.id}">
				<jstl:set value="${'myReview'}" var="boldItalic" />
			</jstl:when>
			<jstl:when test="${row.actor.id ne principal.id}">
				<jstl:set value="${'notMyReview'}" var="boldItalic" />
			</jstl:when>
		</jstl:choose>
	</security:authorize>

	<spring:message code="review.createMoment" var="createMomentHeader" />
	<display:column property="createMoment" title="${createMomentHeader}"
		format="{0,date,dd/MM/yyyy}" class="${boldItalic}"/>

	<spring:message code="review.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" class="${boldItalic}"/>

	<spring:message code="review.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" class="${boldItalic}"/>

	<spring:message code="review.score" var="scoreHeader" />
	<display:column property="score" title="${scoreHeader}" class="${boldItalic}"/>

	<spring:message code="review.actor" var="actorHeader" />
	<display:column property="actor.name" title="${actorHeader}" class="${boldItalic}"/>

	<spring:message code="review.event" var="eventHeader" />
	<display:column property="event.title" title="${eventHeader}" class="${boldItalic}"/>

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<spring:message code="review.justification" var="justificationHeader" />
		<display:column property="justification"
			title="${justificationHeader}" class="${boldItalic}"/>

		<spring:message code="review.cancel" var="cancelHeader" />
		<display:column property="cancel" title="${cancelHeader}" class="${boldItalic}"/>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column>
				<jstl:if test="${row.cancel == false}">
					<a href="review/editCancel.do?reviewId=${row.id}"> <spring:message
							code="cancel" />
					</a>
				</jstl:if>
			</display:column>
		</security:authorize>

	</security:authorize>

</display:table>

<acme:cancel code="cancel" url="welcome/index.do" />