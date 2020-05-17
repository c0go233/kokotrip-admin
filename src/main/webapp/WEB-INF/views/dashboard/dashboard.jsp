<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-01-21
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<html>
<head>
    <title>관리자 페이지</title>

</head>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav dashboard="true"/>
    </jsp:attribute>


    <jsp:body>

        <p><a href="${pageContext.request.contextPath}/state/list">지역관리페이지</a></p>
        <p><a href="${pageContext.request.contextPath}/ticket-type/list">티켓타입 관리 페이지</a></p>
        <p><a href="${pageContext.request.contextPath}/theme/list">카테고리 관리 페이지</a></p>
        <p><a href="${pageContext.request.contextPath}/tour-spot/list">여행지 관리 페이지</a></p>




        <br/>
        <br/>
        <br/>

        <p><a href="${pageContext.request.contextPath}/state/list">시·도 관리 페이지</a></p>
        <p><a href="${pageContext.request.contextPath}/city/list">도시 관리 페이지</a></p>
        <p><a href="${pageContext.request.contextPath}/region/list">동네 관리 페이지</a></p>






    </jsp:body>

</t:masterPage>

</body>
</html>
