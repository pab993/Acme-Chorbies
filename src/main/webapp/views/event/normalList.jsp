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


<fieldset>
	<legend>
		<spring:message code="events.events" />
	</legend>

	<display:table name="events" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

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

		<spring:message code="event.seatsNumber.available" var="seatsAH" />
		<display:column title="${seatsAH}"
			value="${row.seatsNumber - fn:length(row.chorbies)}" sortable="true" />

		
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
</fieldset>

<br>

<fieldset>
	<legend>
		<spring:message code="events.eventsPast" />
	</legend>

	<display:table name="eventsPast" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<spring:message code="event.picture" var="pictureH" />
		<display:column title="${pictureH}">
			<img height="65" width="65" src="${row.getPicture()}"
				style="background-color: grey;" />
		</display:column>

		<spring:message code="event.title" var="titleH" />
		<display:column title="${titleH}" property="title"
			style="background-color:grey;" />

		<spring:message code="event.createMoment" var="createMomentH" />
		<display:column title="${createMomentH}" property="moment"
			style="background-color:grey;" />

		<spring:message code="event.description" var="descriptionH" />
		<display:column title="${descriptionH}" property="description"
			style="background-color:grey;" />

		<spring:message code="event.seatsNumber.available" var="seatsAH" />
		<display:column title="${seatsAH}"
			value="${row.seatsNumber - fn:length(row.chorbies)}" sortable="true"
			style="background-color:grey;" />

		
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
</fieldset>

<br>


<fieldset>
	<legend>
		<spring:message code="events.eventsFuture" />
	</legend>

	<display:table name="eventsFuture" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<spring:message code="event.picture" var="pictureH" />
		<display:column title="${pictureH}">
			<img height="65" width="65" src="${row.getPicture()}"
				style="background-color: orange;" />
		</display:column>

		<spring:message code="event.title" var="titleH" />
		<display:column title="${titleH}" property="title"
			style="background-color:orange;" />

		<spring:message code="event.createMoment" var="createMomentH" />
		<display:column title="${createMomentH}" property="moment"
			style="background-color:orange;" />

		<spring:message code="event.description" var="descriptionH" />
		<display:column title="${descriptionH}" property="description"
			style="background-color:orange;" />

		<spring:message code="event.seatsNumber.available" var="seatsAH" />
		<display:column title="${seatsAH}"
			value="${row.seatsNumber - fn:length(row.chorbies)}" sortable="true"
			style="background-color:orange;" />

		
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
</fieldset>


