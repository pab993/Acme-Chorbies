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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!-- Display chorbi -->

<h3>
	<spring:message code="actor.name" />
</h3>
<jstl:out value="${actor.getName()}" />

<h3>
	<spring:message code="actor.surname" />
</h3>
<jstl:out value="${actor.getSurname()}" />

<h3>
	<spring:message code="actor.phoneNumber" />
</h3>
<jstl:out value="${actor.getPhoneNumber()}" />

<h3>
	<spring:message code="actor.email" />
</h3>
<jstl:out value="${actor.getEmail()}" />
