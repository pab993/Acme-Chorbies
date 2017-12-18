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
		<display:table name="chorbies" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag">

			<spring:message code="chorbi.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" />

			<spring:message code="chorbi.picture" var="pictureHeader" />
			<display:column property="picture" title="${pictureHeader}" />

			<spring:message code="chorbi.description" var="descriptionHeader" />
			<display:column property="description" title="${descriptionHeader}" />

			<spring:message code="chorbi.relationship" var="relationshipHeader" />
			<display:column property="relationship" title="${relationshipHeader}" />

			<spring:message code="chorbi.birth" var="birthHeader" />
			<display:column property="birth" title="${birthHeader}"
				format="{0,date,dd/MM/yyyy}" />

			<spring:message code="chorbi.genre" var="genreHeader" />
			<display:column property="genre" title="${genreHeader}" />

			<security:authorize access="hasRole('ADMINISTRATOR')">
				<display:column>
					<a href="administrator/ban.do?chorbiId=${row.id}"
						onclick="return confirm('<spring:message code = "chorbi.confirm.ban"/>')">
						<jstl:choose>
							<jstl:when test="${row.userAccount.enabled == false}">

								<spring:message code="chorbi.unban" />
							</jstl:when>
							<jstl:otherwise>
								<spring:message code="chorbi.ban" />
							</jstl:otherwise>
						</jstl:choose>
					</a>
				</display:column>
			</security:authorize>

			<security:authorize access="hasRole('CHORBI')">


				<spring:message code="chorbi.doYouLike" var="likeHeader" />
				<display:column title="${likeHeader}">

					<jstl:choose>

						<jstl:when test="${chorbiLikeReceivedFromPrincipal.contains(row)}">

							<a href="like/chorbi/delete.do?chorbiId=${row.id }"> <spring:message
									code="chorbi.dislike" />
							</a>

						</jstl:when>

						<jstl:otherwise>

							<a href="like/chorbi/create.do?chorbiId=${row.id }"> <spring:message
									code="chorbi.like" />
							</a>

						</jstl:otherwise>

					</jstl:choose>

				</display:column>

			</security:authorize>

		</display:table>
		
<acme:cancel code="cancel" url="welcome/index.do" />