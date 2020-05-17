<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-03-07
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="티켓타입 리스트"/>


<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav ticketType="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="path-bar">
            <span>${pageTitle}</span>
        </div>

        <div class="section__heading">
            <h1 class="heading">${pageTitle}</h1>
            <p>티켓타입은 어른, 어린이, 장애우, 국가유공자, 경로자 등과 같이 티켓 구매자를 정의하는데 상용된다.</p>
        </div>

        <t:datatable includeDescription="false" includeOrder="false" nameLabel="티켓타입"
                     dataList="${ticketTypeList}" datatableId="ticket-type-data-table" urlPrefix="/ticket-type"/>

    </jsp:body>

</t:masterPage>


<script>
    setDataTable('ticket-type-data-table', false, 5, '${pageContext.request.contextPath}', '/ticket-type', '/add', false, false);
</script>

</body>
</html>
