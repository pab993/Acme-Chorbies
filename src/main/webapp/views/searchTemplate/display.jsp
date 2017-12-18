<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<jstl:if test="${pageContext.response.locale.language == 'es'}">
		<jstl:set value="nombre" var="enumLabel" />
	</jstl:if>

	<jstl:if test="${pageContext.response.locale.language == 'en'}">
		<jstl:set value="nombreIngles" var="enumLabel" />
	</jstl:if>


 	<form:form action="searchTemplate/search.do" modelAttribute="searchTemplateForm">
		
		
<%-- 		<acme:select items="${relationships}" itemLabel=""
				code="choose.relationship" path="relationship" /> 
				
			<acme:select items="${genres}" itemLabel=""
				code="choose.genre" path="genre" />
				
			<acme:select items="${ages}" itemLabel=""
				code="choose.genre" path="age" />
				--%>
<%-- 		<form:label path="relationship">
			<spring:message code="choose.relationship" />
		</form:label>
		<form:select path="relationship">
    		<form:options items="${relationships}" />
		</form:select>
		<form:errors cssClass="error" path="relationship" /> --%>
		
		<acme:select2 path="relationship" itemLabel="${enumLabel}" code="choose.relationship" items="${relationships}"/>
		
		<form:label path="age">
			<spring:message code="choose.age" />
		</form:label>
		<form:select path="age">
    		<form:options items="${ages}" />
		</form:select>
		<form:errors cssClass="error" path="age" />
		
		<acme:select2 path="genre" itemLabel="${enumLabel}" code="choose.genre" items="${genres}"/>

		<form:errors cssClass="error" path="genre" />
				
		<acme:textbox code="searchTemplate.keyword" path="keyword" />
		
		<acme:textbox code="searchTemplate.coord.city" path="city"/>
		
		<acme:textbox code="searchTemplate.coord.country" path="country"/>
		
		<acme:textbox code="searchTemplate.coord.state" path="state"/>
		
		<acme:textbox code="searchTemplate.coord.province" path="province"/>
		
		<acme:submit code="searchTemplate.search" name="search"/>
	
	</form:form>

	<br />
	
	<display:table name = "chorbies" id = "row" requestURI = "${requestURI}" pagesize = "5" class = "displaytag" >
		
		<spring:message code = "chorbi.picture" var = "pictureHeader" />
		<display:column property = "picture" title = "${pictureHeader}" />
		
		<spring:message code = "chorbi.name" var = "nameHeader" />
		<display:column property = "name" title = "${nameHeader}" />
	
		<spring:message code="chorbi.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}" />
	
		<spring:message code="chorbi.relationship" var="relationshipHeader" />
		<display:column property="relationship" title="${relationshipHeader}" />
		
		<spring:message code="chorbi.genre" var="genreHeader" />
		<display:column property="genre" title="${genreHeader}"/>
		
		<spring:message code="chorbi.birth" var="birthHeader" />
		<display:column property="birth" title="${birthHeader}"/>
		
<%-- 		<security:authorize access="hasRole('CHORBI')" >
			<display:column>
				<a href="booking/tenant/create.do?propertyId=${row.id}">
					<spring:message code="property.newBooking" />
				</a>
			</display:column>
		</security:authorize> --%>

	</display:table>