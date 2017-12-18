<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 

<jstl:if test="${showImage == true }">
	<img id = bannerContainer src="${mainBanner}" />
	<script>
	
	function changeBanner(){
		
		var imagenSelect = "images/banner"+ (Math.floor(Math.random() * (5 - 1)) + 1) +".jpg";
		document.getElementById("bannerContainer").src = imagenSelect;
	}
	
	
	</script>
</jstl:if>

<jstl:if test="${showImage == false }">

	<img id = bannerContainer src="${mainBanner}" />
	
</jstl:if>



