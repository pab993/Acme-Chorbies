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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="events" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<%-- 	<jstl:set var="color" value="background-color:#FFFFFF" />
	<jstl:if test="${currentDate != null && OneMonthDate!=null}">
		<jstl:choose>
			<jstl:when test="${row.moment lt currentDate }">
				 <jstl:set var="color" value="background-color:#A4A4A4" />
			</jstl:when>
			<jstl:when test="${(row.moment lt OneMonthDate || row.moment == OneMonthDate) && (row.seatsNumber - fn:length(row.chorbies))>0}">
				 <jstl:set var="color" value="background-color:#F3F781" />
			</jstl:when>
		</jstl:choose>
	</jstl:if> --%>


	<spring:message code="event.picture" var="pictureH" />
	<display:column title="${pictureH}">
		<img height="65" width="65" src="${row.getPicture()}" />
	</display:column>

	<spring:message code="event.title" var="titleH" />
	<display:column title="${titleH}" property="title" />

	<spring:message code="event.createMoment" var="createMomentH" />
	<display:column title="${createMomentH}" property="moment" />

	<spring:message code="event.description" var="descriptionH" />
	<display:column title="${descriptionH}" property="description" />

	<spring:message code="event.seatsNumber" var="seatsH" />
	<display:column title="${seatsH}" property="seatsNumber" />

	<spring:message code="event.seatsNumber.available" var="seatsAH" />
	<display:column title="${seatsAH}"
		value="${row.seatsNumber - fn:length(row.chorbies)}" sortable="true" />

	<!-- REGISTER AND UNREGISTER CHORBIES IN EVENTS -->

	<security:authorize access="hasRole('CHORBI')">

		<display:column>

			<jstl:choose>

				<jstl:when
					test="${row.chorbies.contains(principal) and row.moment > fecha}">
					<a href="event/chorbi/unRegister.do?idEvent=${row.id }"><spring:message
							code="event.unRegister" /></a>

				</jstl:when>

				<jstl:when
					test="${fn:length(row.chorbies) < row.seatsNumber and row.moment > fecha}">
					<a href="event/chorbi/register.do?idEvent=${row.id }"><spring:message
							code="event.register" /></a>
				</jstl:when>

				<jstl:otherwise>
					--
				</jstl:otherwise>

			</jstl:choose>

		</display:column>

	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">

		<spring:message code="event.edit" var="eEdit" />
		<display:column title="${eEdit}">
			<jstl:choose>
				<jstl:when test="${principal.events.contains(row) == true}">
					<a href="manag/events/edit.do?eventId=${row.id}"><spring:message
							code="event.edit" /></a>
				</jstl:when>
				<jstl:otherwise>
					--
				</jstl:otherwise>
			</jstl:choose>
		</display:column>

		<spring:message code="event.broadcast" var="eventBroadcast" />
		<display:column title="${eventBroadcast}">
			<jstl:choose>
				<jstl:when test="${principal.events.contains(row) == true}">
					<a href="manag/events/broadcast.do?eventId=${row.id}"><spring:message
							code="event.broadcast" /></a>
				</jstl:when>
				<jstl:otherwise>
					--
				</jstl:otherwise>
			</jstl:choose>
		</display:column>


	</security:authorize>

	
		<display:column>
			<jstl:choose>
				<jstl:when test="${fn:length(row.dalems) == 0}">
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<a href="dalem/create.do?eventId=${row.id}"> <spring:message
								code="event.create" />
						</a>
					</security:authorize>
				</jstl:when>
				<jstl:otherwise>
				<a href="dalem/listByEvent.do?eventId=${row.id}"> <spring:message
						code="event.dalem" />
				</a>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>

</display:table>

<security:authorize access="hasRole('MANAGER')">

	<div>
		<a href="manag/events/create.do"><spring:message
				code="manager.events.create" /></a>
	</div>

</security:authorize>