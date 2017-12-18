<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img width="180px" height="180px" src="images/logo1.png"
		alt="Acme-Chorbies Co., Inc." />
</div>

<security:authorize access="isAnonymous()">



</security:authorize>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<li><a class="fNiv"><spring:message code="master.page.events" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="event/list.do"><spring:message
							code="master.page.events.list" /> </a></li>
				<li><a href="event/lessOneMonth.do"><spring:message
							code="master.page.upComingEvents" /> </a></li>

				<security:authorize access="hasRole('CHORBI')">
					<li><a href="event/chorbi/myEvents.do"><spring:message
								code="master.page.my.events.list" /> </a></li>
					<%-- <li><a href="event/chorbi/list.do"><spring:message
								code="master.page.my.events.listChorbi" /></a></li> --%>
				</security:authorize>

				<security:authorize access="hasRole('MANAGER')">
					<li><a href="manag/events/myEvents.do"><spring:message
								code="master.page.my.events.list" /> </a></li>
					<%-- <li><a href="manag/events/list.do"><spring:message
								code="master.page.listEvents" /> </a></li> --%>
				</security:authorize>

			</ul></li>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv" href="administrator/dashboard.do"><spring:message
						code="master.page.dashboard" /></a></li>
			<li><a class="fNiv" href="dalem/list.do"><spring:message
						code="master.page.review" /></a></li>
			<li><a class="fNiv" href="administrator/updateFees.do"><spring:message
						code="master.page.updateFees" /></a></li>
			<li><a class="fNiv" href="configurationSystem/edit.do"><spring:message
						code="master.page.configurationSystem" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chorbi/adminList.do"><spring:message
								code="master.page.listChorbi" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message code="master.page.chirp" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chirp/list.do"><spring:message
								code="master.page.listChirp" /></a></li>
					<li><a href="chirp/edit.do"><spring:message
								code="master.page.createChirp" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="searchTemplate/display.do"><spring:message
						code="master.page.searchTemplate.display" /></a></li>
			<li><a class="fNiv" href="creditCard/edit.do"><spring:message
						code="master.page.creditCard.edit" /></a></li>
			<%-- <li><a class="fNiv" href="event/chorbi/list.do"><spring:message code="master.page.event.list" /></a></li> --%>


			<li><a class="fNiv"><spring:message
						code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chorbi/list.do"><spring:message
								code="master.page.listChorbi" /> </a></li>
					<li><a href="chorbi/listLikes.do"><spring:message
								code="master.page.listChorbiLike" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv" href="creditCard/edit.do"><spring:message
						code="master.page.creditCard.edit" /></a></li>

			<li><a class="fNiv"><spring:message
						code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chorbi/managerList.do"><spring:message
								code="master.page.listChorbi" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="chorbi/create.do"><spring:message
						code="master.page.registerChorbi" /></a></li>
			<li><a class="fNiv" href="manag/edit.do"><spring:message
						code="master.page.registerManager" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">

			<%-- <li><a class="fNiv"><spring:message
						code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chorbi/list.do"><spring:message
								code="master.page.listChorbi" /> </a></li>
				</ul></li> --%>

			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('CHORBI')">
						<li><a href="chorbi/editChorbi.do"><spring:message
									code="master.page.editChorbi" /> </a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

