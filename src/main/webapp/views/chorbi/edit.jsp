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

<form:form action="chorbi/edit.do" modelAttribute="chorbiForm">
	
	<fieldset > 
	
	<legend><b> <spring:message code="chorbi.accountData" /> </b></legend>
	
		<acme:textbox code="chorbi.username" path="username"/>
		<br/>
	
		<acme:password code="chorbi.password" path="password"/>
		<br/>
	
		<acme:password code="chorbi.repeatPassword" path="repeatPassword"/>
	
	</fieldset>
	
	
	<fieldset > 
	
		<legend><b> <spring:message code="chorbi.personalData" /></b> </legend>
	
	
		<acme:textbox code="chorbi.name" path="name"/>
		<br />
			
		<acme:textbox code="chorbi.surname" path="surname"/>
		<br />
			
		<acme:textbox code="chorbi.phone" path="phone"/>
		<br />
			
		<acme:textbox code="chorbi.email" path="email"/>
		<br />
		
		<acme:textbox code="chorbi.country" path="country"/>
		<br/>
		
		<acme:textbox code="chorbi.state" path="state"/>
		<br/>
		
		<acme:textbox code="chorbi.province" path="province"/>
		<br/>
		
		<acme:textbox code="chorbi.city" path="city"/>
		<br/>
	
	</fieldset>
	
	
	<fieldset>
		<legend><b><spring:message code="chorbi.necessaryInformation"/></b></legend>
		
		<acme:textarea code="chorbi.description" path="description"/>
		
		<br/>
		
		<acme:textbox code="Chorbi.birth" path="birth"/>
		
		<br/>
		
		<div>
			<form:label path="relationship" ><spring:message code="chorbi.relationship"/></form:label>
			<form:select path="relationship">
				<form:option value="ACTIVITIES"><spring:message code="chorbi.activities"/></form:option>
				<form:option value="FRIENDSHIP"><spring:message code="chorbi.friendship"/></form:option>
				<form:option value="LOVE"><spring:message code="chorbi.love"/></form:option>
			</form:select>
			<form:errors cssClass="error" path="relationship" />
		</div>
		
		<br />
		
		<div>
		
			<form:label path="gene" ><spring:message code="chorbi.gene"/></form:label>
			<form:select path="gene">
				<form:option value="undefined"> -- </form:option>
				<form:option value="MAN"><spring:message code="chorbi.man"/></form:option>
				<form:option value="WOMAN"><spring:message code="chorbi.woman"/></form:option>
			</form:select>
			<form:errors cssClass="error" path="gene" />
		
		</div>
		
		<br />
		
		<acme:textbox code="chorbi.picture" path="picture"/>	
		
	</fieldset>
	
	<div>
	<form:checkbox id="myCheck" onclick="comprobar();" path="check"/>
		<form:label path="check">
			<spring:message code="chorbi.accept" />
			<a href="termAndCondition/termAndCondition.do"><spring:message code="chorbi.lopd"/></a>
		</form:label>
	</div>
	

	<acme:submit id="submitButton" name="save" code="chorbi.submit"/>
	

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