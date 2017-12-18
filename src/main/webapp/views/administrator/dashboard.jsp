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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<display:table name="listChorbiesByCountry" id="listChorbiesByCountry"
			pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbies.country" var="dash1" />
			<display:column title="${dash1}">
				<jstl:out value="${listChorbiesByCountry.userAccount.username}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<display:table name="listChorbiesByCity" id="listChorbiesByCity"
			pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbies.city" var="dash1" />
			<display:column title="${dash1}">
				<jstl:out value="${listChorbiesByCity.userAccount.username}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<h3>
			<spring:message code="dashboard.chorbies.age.min" />
		</h3>
		<h4>
			<jstl:out value="${minChorbiesAges}" />
		</h4>
	</div>

	<div>
		<h3>
			<spring:message code="dashboard.chorbies.age.max" />
		</h3>
		<h4>
			<jstl:out value="${maxChorbiesAges}" />
		</h4>
	</div>

	<div>
		<h3>
			<spring:message code="dashboard.chorbies.age.avg" />
		</h3>
		<h4>
			<jstl:out value="${avgChorbiesAges}" />
		</h4>
	</div>

	<div>
		<display:table name="ratioChorbiesWithCreditCard"
			id="ratioChorbiesWithCreditCard" pagesize="5"
			requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbies.with" var="dash3" />
			<display:column title="${dash3}">
				<jstl:out value="${ratioChorbiesWithCreditCard}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<display:table name="ratioChorbiesWithoutCreditCard"
			id="ratioChorbiesWithoutCreditCard" pagesize="5"
			requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbies.without" var="dash3" />
			<display:column title="${dash3}">
				<jstl:out value="${ratioChorbiesWithoutCreditCard}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<display:table name="ratioChorbiesFindActivities"
			id="ratioChorbiesFindActivities" pagesize="5"
			requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbies.activities" var="dash4" />
			<display:column title="${dash4}">
				<jstl:out value="${ratioChorbiesFindActivities}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<display:table name="ratioChorbiesFindFriendship"
			id="ratioChorbiesFindFriendship" pagesize="5"
			requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbies.friendship" var="dash4" />
			<display:column title="${dash4}">
				<jstl:out value="${ratioChorbiesFindFriendship}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<display:table name="ratioChorbiesFindLove" id="ratioChorbiesFindLove"
			pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbies.love" var="dash4" />
			<display:column title="${dash4}">
				<jstl:out value="${ratioChorbiesFindLove}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<h3>
			<spring:message code="dashboard.avg.likes" />
		</h3>
		<h4>
			<jstl:out value="${avgLikesPerChorbi}" />
		</h4>
	</div>
	<br>
	<div>
		<h3>
			<spring:message code="dashboard.min.likes" />
		</h3>
		<h4>
			<jstl:out value="${minLikesPerChorbi}" />
		</h4>
	</div>
	<br>
	<div>
		<h3>
			<spring:message code="dashboard.max.likes" />
		</h3>
		<h4>
			<jstl:out value="${maxLikesPerChorbi}" />
		</h4>
	</div>
	<br>
	<%-- 		<div>
			<h3><spring:message code="dashboard.chorbies.likes"/></h3>
			<jstl:forEach items="${chorbiesByLikes}" var="item">
				<h4><jstl:out value="${item }"/></h4><br>
				<h4><jstl:out value="${chorbiesByLikes }"/></h4>
			</jstl:forEach>
		</div>
		<br> --%>

	<div>
		<h3>
			<spring:message code="dashboard.chorbies.likes" />
		</h3>
		<display:table name="chorbiesByLikes" id="chorbiesByLikes"
			pagesize="5" requestURI="${requestURI}" class="displaytag">
			<display:column>
				<jstl:out value="${chorbiesByLikes}" />
			</display:column>
		</display:table>
	</div>
	<br>
	<div>
		<h3>
			<spring:message code="dashboard.min.chirpsReceived" />
		</h3>
		<h4>
			<jstl:out value="${minChirpsReceived}" />
		</h4>
	</div>
	<br>
	<div>
		<h3>
			<spring:message code="dashboard.max.chirpsReceived" />
		</h3>
		<h4>
			<jstl:out value="${maxChirpsReceived}" />
		</h4>
	</div>
	<br>
	<div>
		<h3>
			<spring:message code="dashboard.min.chirpsSends" />
		</h3>
		<h4>
			<jstl:out value="${minChirpsSends}" />
		</h4>
	</div>
	<br>
	<div>
		<h3>
			<spring:message code="dashboard.max.chirpsSends" />
		</h3>
		<h4>
			<jstl:out value="${maxChirpsSends}" />
		</h4>
	</div>
	<br>
	<div>
		<h3>
			<spring:message code="dashboard.avg.chirps" />
		</h3>
		<h4>
			<jstl:out value="${avgChirps}" />
		</h4>
	</div>
	<br>

	<div>
		<display:table name="chorbiMaxChirpsReceived"
			id="chorbiMaxChirpsReceived" pagesize="5" requestURI="${requestURI}"
			class="displaytag">
			<spring:message code="dashboard.chorbiMaxChirpsReceived" var="dash5" />
			<display:column title="${dash5}">
				<jstl:out value="${chorbiMaxChirpsReceived.userAccount.username}" />
			</display:column>
		</display:table>
	</div>

	<br>

	<div>
		<display:table name="chorbiMaxChirpsSends" id="chorbiMaxChirpsSends"
			pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.chorbiMaxChirpsSends" var="dash6" />
			<display:column title="${dash6}">
				<jstl:out value="${chorbiMaxChirpsSends.userAccount.username}" />
			</display:column>
		</display:table>
	</div>

	<div>
		<display:table name="managerByEvents" id="managerByEvents"
			pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="managerByEvents" var="dash7" />
			<display:column title="${dash7}">
				<jstl:out value="${managerByEvents.userAccount.username}" />
			</display:column>
		</display:table>
	</div>
	<div>
	<display:table name="allManagers" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="username" var="managerUsernameHeader" />
	<display:column property="userAccount.username" title="${managerUsernameHeader}" />
	
	<spring:message code="monthlyFee" var="managerMonthlyFeeHeader" />
	<display:column property="fee" title="${managerMonthlyFeeHeader}" />
	</display:table>
	</div>
	<div>
		<display:table name="chorbiOrderByEvents" id="chorbiOrderByEvents"
			pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="chorbiOrderByEvents" var="dash9" />
			<display:column title="${dash9}">
				<jstl:out value="${chorbiOrderByEvents.userAccount.username}" />
			</display:column>
		</display:table>
	</div>
	<div>
		<display:table name="allChorbis" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="username" var="chorbiUsernameHeader" />
	<display:column property="userAccount.username" title="${chorbiUsernameHeader}" />
	
	<spring:message code="monthlyFee" var="chorbiMonthlyFeeHeader" />
	<display:column property="monthlyFee" title="${chorbiMonthlyFeeHeader}" />
	</display:table>
	</div>
	
	
	<div>
		<h3>
			<spring:message code="dashboard.chorbies.stars.min" />
		</h3>
		<h4>
			<jstl:out value="${minStarsByChorbi}" />
		</h4>
	</div>
	
	
	<div>
		<h3>
			<spring:message code="dashboard.chorbies.stars.max" />
		</h3>
		<h4>
			<jstl:out value="${maxStarsByChorbi}" />
		</h4>
	</div>
	
	<div>
		<h3>
			<spring:message code="dashboard.chorbies.stars.avg" />
		</h3>
		<h4>
			<jstl:out value="${avgStarsByChorbi}" />
		</h4>
	</div>
	
	<div>
		<h3>
			<spring:message code="dashboard.chorbies.sorted.AVG.stars" />
		</h3>
		<h4>
			<jstl:out value="${chorbiesSortedByAVGStars}" />
		</h4>
	</div>

</security:authorize>