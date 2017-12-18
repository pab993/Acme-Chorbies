<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="manag/events/edit.do" modelAttribute="event">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<jstl:if test="${event.id == 0}">
			<div>
				<spring:message var="fee" code="payment.info"/>
				<h3 style="color:#FF0000;"><jstl:out value="${fee}"/><jstl:out value="${csFee}"/> Euros</h3>
			</div>
		</jstl:if>
	
		<div >
		
	
		<fieldset> 
	
			<legend> <spring:message code="datos.event" /> </legend>
			
			<div>
			
				<acme:textbox code="event.picture" path="picture"/>
				<br>
			
			</div>
			
			<div> 
			
				<acme:textbox code="event.title" path="title"/>
				<br>
				
			</div>
			
			<div>
			
				<acme:textbox code="event.description" path="description"/>
				<br>
			
			</div>
			
			<div>
			
				<acme:textbox code="event.moment" path="moment"/>
				<br>
			
			</div>
			
			<div>
			
				<acme:textbox code="event.seatsNumber" path="seatsNumber"/>
				<br>
			
			</div>
		
		</fieldset>
	
	</div>

		<div id="botones">

		<jstl:if test="${event.id != 0}">
			<acme:submit code="event.submit" name="save" />
		</jstl:if>
		
		<!-- Si se crea por primera vez -->
 		<jstl:if test="${event.id == 0}">
			<acme:submitConfirm code="event.submit" name="save" msg="confirm.event.payment"/>
		</jstl:if> 
		
		<jstl:if test="${event.id != 0}">
			<acme:delete name="delete" code="event.delete" msg="confirm.delete"/>
		</jstl:if>
		<acme:cancel code="event.cancel" url="manag/events/list.do" />

	</div>


</form:form>