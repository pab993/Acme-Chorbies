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

<form:form action="manag/edit.do" modelAttribute="managerForm">	
	<fieldset > 
	
	<legend><b> <spring:message code="manager.accountData" /> </b></legend>
	
		<acme:textbox code="manager.username" path="username"/>
		<br/>
	
		<acme:password code="manager.password" path="password"/>
		<br/>
	
		<acme:password code="manager.repeatPassword" path="repeatPassword"/>
	
	</fieldset>
	
	
	<fieldset > 
	
		<legend><b> <spring:message code="manager.personalData" /></b> </legend>
	
	
		<acme:textbox code="manager.name" path="name"/>
		<br />
			
		<acme:textbox code="manager.surname" path="surname"/>
		<br />
			
		<acme:textbox code="manager.phone" path="phone"/>
		<br />
			
		<acme:textbox code="manager.email" path="email"/>
		<br />
	
	</fieldset>
	
	<fieldset>
		<legend><b> <spring:message code="manager.additional" /></b> </legend>
		
		<acme:textbox code="manager.company" path="company"/>
		<br />
			
		<acme:textbox code="manager.vat" path="vat"/>
		<br />
	</fieldset>
	
	<div>
	<form:checkbox id="myCheck" onclick="comprobar();" path="check"/>
		<form:label path="check">
			<spring:message code="manager.accept" />
			<a href="termAndCondition/termAndCondition.do"><spring:message code="manager.lopd"/></a>
		</form:label>
	</div>
	

	<acme:submit id="submitButton" name="save" code="manager.submit"/>
	

</form:form>


<script type="text/javascript">

document.getElementById("submitButton").disabled = true;
document.getElementById("myCheck").checked = false;

function comprobar() {
	
	var x = document.getElementById("myCheck").checked;
	
	if(x == true){
		document.getElementById("submitButton").disabled = false;
	}
	else{
		document.getElementById("submitButton").disabled = true;
	}
}



</script>